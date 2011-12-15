
public class Dictionary<Key extends Comparable<Key>, Value> {
	
	
	private static class Node<K extends Comparable<K>, V> {
		
		K 				key;
		V 				value;
		Node<K,V>[]  	next;
		
		public Node(K k, V v, int level) {
			key = k;
			value = v;
			next = new Node[level + 1];
		}		
	}
	
	//
	// Private Fields
	// 
	
	private Node<Key, Value> _lists;
	private int 			 _currentLevel;
	
	
	//
	// Constructors
	// 
	
	public Dictionary(int numLists) {
		_lists = new Node<>(null, null, numLists);
		_currentLevel = 0;
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
		return _lists.next[0] == null;
	}
	
	
	//
	// Starts finding from the higher list
	// First node passed is the _lists
	// 
	private Node<Key, Value> _search(Key k, int level, Node<Key, Value> node)
	{
		
		//
		// Stop conditions
		//
		
		if( level == 0  && k.compareTo(node.key) < 0 ) 
			return null;
		
		// There are no more nodes in front.
		if( node.next[0] == null && k.compareTo(node.key) > 0 ) 
			return null;
		
		
		
		// Key found
		if( k.compareTo(node.key) == 0 ) return node;
				
		
		//
		// Search
		// 
		
		// Until we don't get a valid node (!= NULL) we decrement the level
		for(  ; node.next[level] == null; level--);
		
		// After this, all nodes down are != NULL
		for(  ; level > 0 && k.compareTo(node.next[level].key) < 0; level--);
		
		
		// Repeat the process for the next node		
		return _search(k, level, node.next[level]);		
	}
	
	//
	// Searches the given key on the dictionary.
	// Returns: null if is not found or the value associated with the key if was found.
	//
	public Value search(Key key) {
		if( isEmpty() )
			return null;
		
		Node<Key, Value> node = _search(key, _currentLevel, _lists.next[_currentLevel]);
		return node != null ? node.value : null;
	}	
	
	private static class NodeStatus<Key extends Comparable<Key>, Value> {
		Node<Key, Value> node;
		boolean inserted;
		
		public NodeStatus(Node<Key, Value> n, boolean inserted) {
			this.node = n;
			this.inserted = inserted;
		}
	}
	
	
	private NodeStatus<Key, Value> _insert(int level, Node<Key, Value> n, Node<Key, Value> newNode)
	{
		
		//
		// Stop conditions
		//
		
		if( level == 0  && newNode.key.compareTo(n.key) < 0 ) 
			return new NodeStatus<>(null, true);	// set null indicating that is the previous node that we want to return!
		
		// There are no more nodes in front.
		if( n.next[0] == null && newNode.key.compareTo(n.key) > 0 ) 
			return new NodeStatus<>(n, true);
		
		
		// Key found and we are trying to add this key.
		if( newNode.key.compareTo(n.key) == 0 ) {
			return new NodeStatus<>(null, false);
		}

				
		
		//
		// Search
		// 
		
		// Until we don't get a valid node (!= NULL) we decrement the level
		for(  ; n.next[level] == null; level--);
		
		// After this, all nodes down are != NULL
		for(  ; level > 0 && newNode.key.compareTo(n.next[level].key) < 0; level--);
		
		
		// Repeat the process for the next node		
		NodeStatus<Key, Value> status = _insert(level, n.next[level], newNode);
		
		if( status.inserted )
		{
			Node<Key, Value> ctxNode = (status.node != null) ? status.node : n;
						
			//
			// Fix up relationships
			//
			
			if( level <= (newNode.next.length - 1) )
				newNode.next[level] = ctxNode.next[level];					
		}
		
		return status;
	}
	
	public boolean insert(Key key, Value value, int levelForDebug) 
	{
		if(key == null) 
			return false;
		
		//
		// We cannot use search algorithm here because we must establish the
		// connections while recursion is going back!
		// 
		
		Node<Key, Value> newNode = new Node<>(key, value, levelForDebug);
		NodeStatus<Key, Value> status = _insert(levelForDebug, _lists.next[_currentLevel], newNode);		
		
		if( !status.inserted )
			return false;
		
		return true;
	}
}
