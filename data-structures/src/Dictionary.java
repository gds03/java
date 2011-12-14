
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
	// First n passed is the _lists
	// 
	private Node<Key, Value> _search(Key k, int level, Node<Key, Value> node)
	{
		if( level == 0  && k.compareTo(node.key) < 0 )
			return null;
		
		if( k.compareTo(node.key) == 0 )
			return node;
				
		while( node.next[level] == null )
			level--;
		
		while( k.compareTo(node.next[level].key) < 0 )
			level--;
		
		return _search(k, level, node.next[level]);		
	}
	
	//
	// Searches the given key on the dictionary.
	// Returns: null if is not found or the value associated with the key if was found.
	//
	public Value search(Key key) {
		if( isEmpty() )
			return null;
		
		return _search(key, _currentLevel, _lists.next[_currentLevel])
				.value;
	}
	
	
}
