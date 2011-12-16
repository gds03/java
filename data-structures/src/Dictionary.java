
public class Dictionary<Key extends Comparable<Key>, Value> {
	
	
	private static class Node<K extends Comparable<K>, V> {
		
		K 				key;
		V 				value;
		Node<K,V>[]  	next;
		
		public Node(K k, V v, int length) {
			key = k;
			value = v;
			next = new Node[length];
		}		
	}
	
	private static class NodeStatus<Key extends Comparable<Key>, Value> 
	{
		boolean duplicated;
		int levelLinked;
		
		public NodeStatus(boolean duplicated) {
			this.duplicated = duplicated;	
			this.levelLinked = -1;
		}
	}
	
	//
	// Private Fields
	// 
	
	private static final int default_num_lists = 4;
	
	
	private Node<Key, Value> _lists;
	private int 			 _currentLevel;
	
	
	//
	// Constructors
	// 
	
	public Dictionary(int numLists) 
	{
		if(numLists <= 0) 
			numLists = default_num_lists;
		
		_lists = new Node<>(null, null, numLists);	// null key & null value (dummy)
		_currentLevel = 1;
	}
	
	
	//
	// Private auxiliary methods
	//
	
	private static int newLevel(int maxLevel) 
	{		
		int level, j;
		double t = Math.random();
		
		for(level = 1, j = 2;  level < maxLevel;  ++level, j+=j)
			if( t*j > 1.0 ) break;
		
		return level;
	}
	
	
	//
	// Public interface
	//
	
	public boolean isEmpty() {
		
		// O(1) Operation
		
		return _lists.next[0] == null;
	}
	
	
	//
	// Note: Starts finding from the higher list.
	// First node passed is the _lists. (This method always have 1 recursion level because key is null "sentinel")
	// 
	private Value _searchR(Key k, int level, Node<Key, Value> currNode)
	{
		
		//
		// Stop conditions
		//
		
		if( currNode.key != null) {
		
			// Key found
			if( k.compareTo(currNode.key) == 0 ) return currNode.value;
						
			// Between the call we passed to a higher key than the key we are searching.
			if( level == 0  &&  k.compareTo(currNode.key) < 0 ) 
				return null;
			
			// This is the last node and the key we are searching is higher than actual
			if( currNode.next[0] == null  &&  k.compareTo(currNode.key) > 0 ) 
				return null;			
		}
					
		
		//
		// Process for each node
		// 
		
		// Until we don't get a valid node (!= NULL) we decrement the level.
		for(  ; level > 0  &&  currNode.next[level] == null; level--);
		
		//
		// After this, all nodes down are != NULL.
		//
		
		// While k is lower than next node we decrement the level. 
		// (If is lower means than the key searching is between currNode and currNode.next[level])
		for(  ; level > 0  &&  k.compareTo(currNode.next[level].key) < 0; level--);
		
		
		// Repeat the process for the next node	recursively
		return _searchR(k, level, currNode.next[level]);		
	}
	
	//
	// Searches the given key on the dictionary.
	// Returns: null if is not found or the value associated with the key if was found.
	//
	public Value search(Key key) 
	{
		if( isEmpty() )	
			return null;
		
		return _searchR(key, _currentLevel - 1, _lists);		
	}	

	private NodeStatus<Key, Value> _insertR(
			Node<Key, Value> prevNode,
			Node<Key, Value> currNode, 
			int level, 
			Node<Key, Value> newNode)
	{
	
		if( currNode.key != null ) 
		{			
			if( newNode.key.compareTo(currNode.key) == 0 )
				return new NodeStatus<>(true);	// duplicated key!
				
			if( currNode.next[0] == null && newNode.key.compareTo(currNode.key) > 0) {
				
				//
				// We add the node at front of currentNode
				//
				
				return insertNodeAfter(currNode, newNode);				
			}
				
			if( level == 0 && newNode.key.compareTo(currNode.key) < 0 ) {
				
				//
				// We add the node at front of prevNode
				//
				
				return insertNodeAfter(prevNode, newNode);			
			}
		}
		
		for(  ; level > 0  &&  currNode.next[level] == null; level-- );
		
		for(  ; level > 0  &&  newNode.key.compareTo(currNode.next[level].key) < 0; level--);
		
		// Try insert
		NodeStatus<Key, Value> status = _insertR(currNode, currNode.next[level], level, newNode);
		
		// We cannot insert, so we return that information
		if( status.duplicated )
			return status;
		
		//
		// We can insert and we need to fix-up relationships until all level links are linked
		// 
		
		if( status.levelLinked < (newNode.next.length - 1) ) {
			
			for(int i = status.levelLinked + 1; i < currNode.next.length && i < newNode.next.length; i++) {
				newNode.next[i] = currNode.next[i];
				currNode.next[i] = newNode;
				
				++status.levelLinked;
			}			
		}
		
		return status;		
	}


	private NodeStatus<Key, Value> insertNodeAfter(Node<Key, Value> n, Node<Key, Value> newNode) 
	{
		NodeStatus<Key, Value> s = new NodeStatus<>(false);
		
		// Is the first node, so we start in a bottom-up manner
		for(int i = 0;  i < n.next.length  &&  i < newNode.next.length ; i++) {
			
			newNode.next[i] = n.next[i];
			n.next[i] = newNode;
			
			++s.levelLinked;
		}
		
		return s;
		
	}
	
	public boolean insert(Key key, Value value, int levelForDebug) 
	{
		if(key == null) 
			return false;
		
		//
		// We cannot use search algorithm here because we must establish the
		// connections while recursion is going back!
		// 
		
		
		int level = levelForDebug; 	// Set level
	
		Node<Key, Value> newNode = new Node<>(key, value, level);
		NodeStatus<Key, Value> status;
		
		if( isEmpty() ) {
			
			//
			// Set lists links to the new node
			// 
			
			for(int i = 0; i < level; _lists.next[i] = newNode, i++);
			status = new NodeStatus<>(false);
		}
		
		else {
			
			int l = _currentLevel - 1;
			status = _insertR(null, _lists, l, newNode);
			
		}
		
		
		if( status.duplicated )
			return false;	// This key already is on dictionary and cannot be duplicated
		
		//
		// We update the currentLevel if the generated value is higher than actual.
		// 
		
		if( level > _currentLevel )
			_currentLevel = level;
		
		return true;
	}
}
