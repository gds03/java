
public class Executable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Dictionary<Integer, String> dict = new Dictionary<>();
		System.out.println("Starting test");
		
		for(int i = 0; i < 1000000; i++) {
			dict.insert(new Integer(i), "" + i);
		}
		
		System.out.println("Ending test");

	}

}
