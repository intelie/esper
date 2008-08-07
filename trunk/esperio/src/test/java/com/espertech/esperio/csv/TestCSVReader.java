package com.espertech.esperio.csv;

import com.espertech.esper.client.EPException;
import com.espertech.esperio.AdapterInputSource;
import junit.framework.TestCase;

import java.io.EOFException;
import java.util.Arrays;

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
}
