import junit.framework.Assert;

import org.junit.Test;


public class DictionaryTests {

	@Test
	public void test() 
	{
		Dictionary<String, Integer> dictionary = new Dictionary<>(4);
		
		Assert.assertNull(dictionary.search("D"));
		Assert.assertTrue(dictionary.insert("D", 1, 3));
		Assert.assertEquals(new Integer(1), dictionary.search("D"));
		
		Assert.assertNull(dictionary.search("F"));
		Assert.assertTrue(dictionary.insert("F", 2, 1));
		Assert.assertEquals(new Integer(2), dictionary.search("F"));
		
		Assert.assertNull(dictionary.search("G"));
		Assert.assertTrue(dictionary.insert("G", 3, 4));
		Assert.assertEquals(new Integer(3), dictionary.search("G"));
		
		Assert.assertNull(dictionary.search("A"));
		Assert.assertTrue(dictionary.insert("A", 4, 1));
		Assert.assertEquals(new Integer(4), dictionary.search("A"));
		
		Assert.assertNull(dictionary.search("C"));
		Assert.assertTrue(dictionary.insert("C", 5, 2));
		Assert.assertEquals(new Integer(5), dictionary.search("C"));
		
		Assert.assertFalse(dictionary.insert("D", 4, 1));
		
	}

}

