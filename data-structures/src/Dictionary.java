
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
		_lists = new Node<Key, Value>(null, null, numLists);
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
		for(  ; k.compareTo(node.next[level].key) < 0; level--);
		
		
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
}
