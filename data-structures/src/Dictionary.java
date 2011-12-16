
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
	// Starts finding from the higher list
	// First node passed is the _lists
	// 
	private Node<Key, Value> _searchR(Key k, int level, Node<Key, Value> ctxNode)
	{
		
		//
		// Stop conditions
		//
		
		if( ctxNode.key != null) {
		
			// Key has not found!
			if( level == 0  &&  k.compareTo(ctxNode.key) < 0 ) 
				return null;
			
			// There are no more nodes in front of ctxNode. Key has not found!
			if( ctxNode.next[0] == null  &&  k.compareTo(ctxNode.key) > 0 ) 
				return null;
			
			// Key found
			if( ctxNode.key != null && k.compareTo(ctxNode.key) == 0 ) return ctxNode;
		}
					
		
		//
		// Search
		// 
		
		// Until we don't get a valid node (!= NULL) we decrement the level
		for(  ; ctxNode.next[level] == null; level--);
		
		//
		// After this, all nodes down are != NULL.
		//
		
		// While k is lower than next node we decrement the level. 
		// (If is lower means that the node is at left of the next node for sure).
		for(  ; level > 0  &&  k.compareTo(ctxNode.next[level].key) < 0; level--);
		
		
		// Repeat the process for the next node		
		return _searchR(k, level, ctxNode.next[level]);		
	}
	
	//
	// Searches the given key on the dictionary.
	// Returns: null if is not found or the value associated with the key if was found.
	//
	public Value search(Key key) 
	{
		if( isEmpty() )	return null;
		
		Node<Key, Value> node = _searchR(key, _currentLevel - 1, _lists);
		return node != null ? node.value : null;
	}	
	
	
	
	private static class NodeStatus<Key extends Comparable<Key>, Value> 
	{
		boolean isLinked, duplicated;
		int levelLinked;
		
		public NodeStatus(boolean duplicated) {
			this.duplicated = duplicated;
			this.isLinked = false;			
			this.levelLinked = -1;
		}
	}
	
	
	
	private NodeStatus<Key, Value> _insertR(
			Node<Key, Value> prevNode,
			Node<Key, Value> currNode, 
			int level, 
			Node<Key, Value> newNode)
	{
	
		if( currNode.key != null ) {
			
			//
			// Safety compare keys
			//
					
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
		
		for(  ; level > 0 && currNode.next[level] == null; level-- );
		
		for(  ; level > 0 && newNode.key.compareTo(currNode.next[level].key) < 0; level--);
		
		// Search
		NodeStatus<Key, Value> status = _insertR(currNode, currNode.next[level], level, newNode);
		
		// Insert if possible
		if( status.duplicated )
			return status;
		
		
		//
		// Fix-up relationships
		// 
		
		if( !status.isLinked ) {
		
			// newNode must be linked to old references of the status.node
			for (int i = status.levelLinked + 1;  i < currNode.next.length ; i++) {
				newNode.next[i] = currNode.next[i];
				currNode.next[i] = newNode;
				
				status.levelLinked++;
				
				if( status.levelLinked == (newNode.next.length - 1) ) {
					status.isLinked = true;
					break;
				}
			}
		}
		
		return status;		
	}


	private NodeStatus<Key, Value> insertNodeAfter(Node<Key, Value> node, Node<Key, Value> newNode) 
	{
		NodeStatus<Key, Value> s = new NodeStatus<>(false);
		
		newNode.next[0] = node.next[0];
		node.next[0] = newNode;			
		
		if( ++s.levelLinked == (newNode.next.length - 1) )
			s.isLinked = true;
		
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
			status.isLinked = true;
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
