package es.sendit2us.wastetracker.server.tests;

import es.sendit2us.wastetracker.server.lib.StringUtils;
import junit.framework.TestCase;

public class TestRDFStringUtils extends TestCase {

	public void testStringToInt() {
		
		Integer[] ints = StringUtils.stringToArray("1,2 ,4");
		
		assertEquals(1, (int)ints[0]);
		assertEquals(2, (int)ints[1]);
		assertEquals(4, (int)ints[2]);
	}
	
	public void testEmptyString() {
		Integer[] ints = StringUtils.stringToArray("  \t");
		
		assertEquals(0, ints.length);
	}
	
	public void testSingleInt() {
		Integer[] ints = StringUtils.stringToArray("1");
		
		assertEquals(1, (int)ints[0]);
		
	}
	
	public void testWithNull() {
		Integer[] ints = StringUtils.stringToArray(null);
		
		assertEquals(0, ints.length);		
	}
}
