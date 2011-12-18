import junit.framework.Assert;

import org.junit.Test;


public class DictionaryTests {

	@Test
	public void test() 
	{
		Dictionary<String, Integer> dictionary = new Dictionary<>(4);
		
		Assert.assertNull(dictionary.search("D"));
		Assert.assertTrue(dictionary.insert("D", 1));
		Assert.assertEquals(new Integer(1), dictionary.search("D"));
		
		Assert.assertNull(dictionary.search("F"));
		Assert.assertTrue(dictionary.insert("F", 2));
		Assert.assertEquals(new Integer(2), dictionary.search("F"));
		
		Assert.assertNull(dictionary.search("G"));
		Assert.assertTrue(dictionary.insert("G", 3));
		Assert.assertEquals(new Integer(3), dictionary.search("G"));
		
		Assert.assertNull(dictionary.search("A"));
		Assert.assertTrue(dictionary.insert("A", 4));
		Assert.assertEquals(new Integer(4), dictionary.search("A"));
		
		Assert.assertNull(dictionary.search("C"));
		Assert.assertTrue(dictionary.insert("C", 5));
		Assert.assertEquals(new Integer(5), dictionary.search("C"));
		
		Assert.assertFalse(dictionary.insert("D", 4));
		
		Assert.assertFalse(dictionary.remove("X"));
		Assert.assertTrue(dictionary.remove("G"));
		
		Assert.assertNull(dictionary.search("G"));
		
	}
	
	@Test
	public void TestDictionary() {
		
		Dictionary<String, String> dictionary = new Dictionary<>(4);
		
		Assert.assertNull(dictionary.search("A"));
		Assert.assertTrue(dictionary.insert("A", "A"));
		Assert.assertNotNull(dictionary.search("A"));
		
		Assert.assertNull(dictionary.search("C"));
		Assert.assertTrue(dictionary.insert("C", "C"));
		Assert.assertNotNull(dictionary.search("C"));
		
		Assert.assertNull(dictionary.search("D"));
		Assert.assertTrue(dictionary.insert("D", "D"));
		Assert.assertNotNull(dictionary.search("D"));
		
		Assert.assertNull(dictionary.search("E"));
		Assert.assertTrue(dictionary.insert("E", "E"));
		Assert.assertNotNull(dictionary.search("E"));
		
		Assert.assertNull(dictionary.search("F"));
		Assert.assertTrue(dictionary.insert("F", "F"));
		Assert.assertNotNull(dictionary.search("F"));
		
		Assert.assertNull(dictionary.search("G"));
		Assert.assertTrue(dictionary.insert("G", "G"));
		Assert.assertNotNull(dictionary.search("G"));
		
		Assert.assertNull(dictionary.search("H"));
		Assert.assertTrue(dictionary.insert("H", "H"));
		Assert.assertNotNull(dictionary.search("H"));
		
		Assert.assertNull(dictionary.search("I"));
		Assert.assertTrue(dictionary.insert("I", "I"));
		Assert.assertNotNull(dictionary.search("I"));
		
		Assert.assertNull(dictionary.search("M"));
		Assert.assertTrue(dictionary.insert("M", "M"));
		Assert.assertNotNull(dictionary.search("M"));
		
		Assert.assertNull(dictionary.search("N"));
		Assert.assertTrue(dictionary.insert("N", "N"));
		Assert.assertNotNull(dictionary.search("N"));
		
		Assert.assertNull(dictionary.search("P"));
		Assert.assertTrue(dictionary.insert("P", "P"));
		Assert.assertNotNull(dictionary.search("P"));
		
		Assert.assertNull(dictionary.search("R"));
		Assert.assertTrue(dictionary.insert("R", "R"));
		Assert.assertNotNull(dictionary.search("R"));
		
		Assert.assertNull(dictionary.search("S"));
		Assert.assertTrue(dictionary.insert("S", "S"));
		Assert.assertNotNull(dictionary.search("S"));
		
		Assert.assertNull(dictionary.search("X"));
		Assert.assertTrue(dictionary.insert("X", "X"));
		Assert.assertNotNull(dictionary.search("X"));
		
		
		Assert.assertNull(dictionary.search("L"));
		Assert.assertTrue(dictionary.insert("L", "L"));
		
		Assert.assertNotNull(dictionary.search("L"));
		
		Assert.assertTrue(dictionary.remove("L"));
		Assert.assertNull(dictionary.search("L"));
	}

}

