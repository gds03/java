
public class Executable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int count = 5000000;
		
		Dictionary<Integer, String> dict = new Dictionary<>(20000);
		
		//
		// Insertion
		//
		
		System.out.println("Starting to Insert");
		long start = System.currentTimeMillis();
		
		for(int i = 0; i < count; i++) {
			dict.insert(new Integer(i), "" + i);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Stoping to insert");
		System.out.println("Insertion stage took: " + (end - start) + "ms");
		
		
		//
		// Searching
		//
		
		System.out.println("Starting to Search");
		start = System.currentTimeMillis();
		
		for(int i = 0; i < count; i++) {
			dict.search(new Integer(i));
		}
		
		end = System.currentTimeMillis();
		
		System.out.println("Stoping to search");
		System.out.println("Search stage took: " + (end - start) + "ms");
		
		
		//
		// Delete
		// 
		
		System.out.println("Starting to delete");
		start = System.currentTimeMillis();
		
		for(int i = 0; i < count; i++) {
			dict.remove(new Integer(i));
		}
		
		end = System.currentTimeMillis();
		
		System.out.println("Stoping to delete");
		System.out.println("Delete stage took: " + (end - start) + "ms");
	}
	
	

}
