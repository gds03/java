
public class Executable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Dictionary<Integer, String> dict = new Dictionary<>(8196);
		
		//
		// Insertion
		//
		
		System.out.println("Starting test");
		long start = System.currentTimeMillis();
		
		for(int i = 0; i < 5000000; i++) {
			dict.insert(new Integer(i), "" + i);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Ending test");
		System.out.println("Insertion stage took: " + (end - start) + "ms");
		
		
		//
		// Searching
		//
		
		System.out.println("Starting test");
		start = System.currentTimeMillis();
		
		for(int i = 0; i < 5000000; i++) {
			dict.search(new Integer(i));
		}
		
		end = System.currentTimeMillis();
		
		System.out.println("Ending test");
		System.out.println("Search stage took: " + (end - start) + "ms");
		
		
		//
		// Delete
		// 
		
		System.out.println("Starting test");
		start = System.currentTimeMillis();
		
		for(int i = 0; i < 5000000; i++) {
			dict.remove(new Integer(i));
		}
		
		end = System.currentTimeMillis();
		
		System.out.println("Ending test");
		System.out.println("Delete stage took: " + (end - start) + "ms");
	}
	
	

}
