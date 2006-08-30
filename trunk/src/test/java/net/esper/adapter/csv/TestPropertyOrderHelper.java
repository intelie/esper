package net.esper.adapter.csv;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TestPropertyOrderHelper extends TestCase
{
	private Map<String, Class> propertyTypes;
	protected void setUp() throws ClassNotFoundException
	{
		propertyTypes = new LinkedHashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);
	}

	public void testResolveTitleRow()
	{
		// Use first row
		String[] firstRow = new String[] { "myDouble", "myInt", "myString" };
		assertEquals(firstRow, PropertyOrderHelper.resolvePropertyOrder(firstRow, propertyTypes));
		
		firstRow = new String[] { "myDouble", "myInt", "myString", "timestamp" };
		assertEquals(firstRow, PropertyOrderHelper.resolvePropertyOrder(firstRow, propertyTypes));
		
		// Use propertyTypes
		firstRow = new String[] { "1", "2.0", "text" };
		String[] expected = propertyTypes.keySet().toArray(new String[0]);
		assertEquals(Arrays.asList(expected), Arrays.asList(PropertyOrderHelper.resolvePropertyOrder(firstRow, propertyTypes)));

		propertyTypes.put("timestamp", long.class);
		expected = propertyTypes.keySet().toArray(new String[0]);
		assertEquals(Arrays.asList(expected), Arrays.asList(PropertyOrderHelper.resolvePropertyOrder(firstRow, propertyTypes)));
	}
}
