/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.csv;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.support.util.SupportUpdateListener;

import junit.framework.TestCase;

import java.io.EOFException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestCSVReader extends TestCase
{
	public void testParsing() throws Exception
	{
		String path = "regression/parseTests.csv";
		CSVReader reader = new CSVReader(new AdapterInputSource(path));

		String[] nextRecord = reader.getNextRecord();
		String[] expected = new String[] {"8", "8.0", "c", "'c'", "string", "string"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"", "string","", "string","","",""};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"leading spaces", "trailing spaces", ""};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"unquoted value 1", "unquoted value 2", ""};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"value with embedded \"\" quotes"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"value\r\nwith newline"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"value after empty lines"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"value after comments"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		try
		{
			reader.getNextRecord();
			fail();
		}
		catch(EOFException ex)
		{
			// Expected
		}
	}

	public void testNonLooping() throws EOFException
	{
		assertNonLooping("regression/endOnNewline.csv");
		assertNonLooping("regression/endOnEOF.csv");
		assertNonLooping("regression/endOnCommentedEOF.csv");
	}

	public void testLooping() throws Exception
	{
		assertLooping("regression/endOnNewline.csv");
		assertLooping("regression/endOnEOF.csv");
		assertLooping("regression/endOnCommentedEOF.csv");
	}

	public void testClose() throws EOFException
	{
		String path = "regression/parseTests.csv";
		CSVReader reader = new CSVReader(new AdapterInputSource(path));

		reader.close();
		try
		{
			reader.getNextRecord();
			fail();
		}
		catch (EPException e)
		{
			// Expected
		}
		try
		{
			reader.close();
			fail();
		}
		catch (EPException e)
		{
			// Expected
		}
	}

	public void testReset() throws EOFException, EPException
	{
		CSVReader reader = new CSVReader(new AdapterInputSource("regression/endOnNewline.csv"));

		String[] nextRecord = reader.getNextRecord();
		String[] expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		reader.reset();

		nextRecord = reader.getNextRecord();
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		reader.reset();

		nextRecord = reader.getNextRecord();
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));
	}

	public void testTitleRow() throws EOFException, EPException
	{
		CSVReader reader = new CSVReader(new AdapterInputSource("regression/titleRow.csv"));
		reader.setLooping(true);

		// isUsingTitleRow is false by default, so get the title row
		String[] nextRecord = reader.getNextRecord();
		String[] expected = new String[] {"myString", "myInt", "timestamp", "myDouble"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		// Acknowledge the title row and reset the file afterwards
		reader.setIsUsingTitleRow(true);
		reader.reset();

		// First time through the file
		nextRecord = reader.getNextRecord();
		expected = new String[] {"one", "1", "100", "1.1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"three", "3", "300", "3.3"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"five", "5", "500", "5.5"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		// Second time through the file
		nextRecord = reader.getNextRecord();
		expected = new String[] {"one", "1", "100", "1.1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"three", "3", "300", "3.3"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"five", "5", "500", "5.5"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		// Pretend no title row again
		reader.setIsUsingTitleRow(false);

		nextRecord = reader.getNextRecord();
		expected = new String[] {"myString", "myInt", "timestamp", "myDouble"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		reader.reset();

		nextRecord = reader.getNextRecord();
		expected = new String[] {"myString", "myInt", "timestamp", "myDouble"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));
	}

	private void assertLooping(String path) throws EOFException
	{
		CSVReader reader = new CSVReader(new AdapterInputSource(path));
		reader.setLooping(true);

		String[] nextRecord = reader.getNextRecord();
		String[] expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		assertTrue(reader.getAndClearIsReset());

		nextRecord = reader.getNextRecord();
		expected = new String[] {"second line", "2"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		assertFalse(reader.getAndClearIsReset());

		nextRecord = reader.getNextRecord();
		expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		assertTrue(reader.getAndClearIsReset());

		nextRecord = reader.getNextRecord();
		expected = new String[] {"second line", "2"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		assertFalse(reader.getAndClearIsReset());

		nextRecord = reader.getNextRecord();
		expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		assertTrue(reader.getAndClearIsReset());

		reader.close();
	}

	private void assertNonLooping(String path) throws EOFException
	{
		CSVReader reader = new CSVReader(new AdapterInputSource(path));

		String[] nextRecord = reader.getNextRecord();
		String[] expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"second line", "2"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		try
		{
			reader.getNextRecord();
			fail();
		}
		catch(EOFException ex)
		{
			// Expected
		}

		reader.close();
	}
	
	public void testNestedProperties() {
		Configuration configuration = new Configuration();
		configuration.addEventType(Figure.class);
		EPServiceProvider ep = EPServiceProviderManager.getProvider("testNestedProperties", configuration);
		SupportUpdateListener ul = new SupportUpdateListener();
		ep.getEPAdministrator().createEPL("select * from Figure").addListener(ul);

		AdapterInputSource source = new AdapterInputSource("regression/nestedProperties.csv");
		CSVInputAdapterSpec spec = new CSVInputAdapterSpec(source, "Figure");
		CSVInputAdapter adapter = new CSVInputAdapter(ep, spec);
		adapter.start();
		
		assertTrue(ul.isInvoked());
		EventBean e = ul.assertOneGetNewAndReset();
		Figure f = (Figure) e.getUnderlying();
		assertEquals(1, f.getPoint().getX());
	}
	
	public void testNestedMapProperties() {
		Configuration configuration = new Configuration();
		Map point = new HashMap();
		point.put("x", int.class);
		point.put("y", int.class);
		Map figure = new HashMap();
		figure.put("name", String.class);
		figure.put("point", point);
		configuration.addEventType("Figure", figure);
		EPServiceProvider ep = EPServiceProviderManager.getProvider("testNestedMapProperties", configuration);
		SupportUpdateListener ul = new SupportUpdateListener();
		ep.getEPAdministrator().createEPL("select * from Figure").addListener(ul);

		AdapterInputSource source = new AdapterInputSource("regression/nestedProperties.csv");
		CSVInputAdapterSpec spec = new CSVInputAdapterSpec(source, "Figure");
		CSVInputAdapter adapter = new CSVInputAdapter(ep, spec);
		adapter.start();
		
		assertTrue(ul.isInvoked());
		EventBean e = ul.assertOneGetNewAndReset();
		assertEquals(1, e.get("point.x"));
	}

	public static class Figure {
		String name;
		Point point;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Point getPoint() {
			return point;
		}
		public void setPoint(Point point) {
			this.point = point;
		}
	}
	public static class Point {
		int x;
		int y;
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
	}
}
