package net.esper.adapter.csv;

import java.io.EOFException;
import java.util.Arrays;

import net.esper.adapter.csv.CSVAdapterException;
import net.esper.adapter.csv.CSVReader;

import junit.framework.TestCase;

public class TestCSVReader extends TestCase
{
	public void testParsing() throws Exception
	{
		boolean isLooping = false;
		String path = "regression/parseTests.csv";
		CSVReader reader = new CSVReader(path, isLooping);

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
		boolean isLooping = false;
		String path = "regression/parseTests.csv";
		CSVReader reader = new CSVReader(path, isLooping);
		
		reader.close();
		try
		{
			reader.getNextRecord();
			fail();
		}
		catch (CSVAdapterException e)
		{
			// Expected
		}
		try
		{
			reader.close();
			fail();
		}
		catch (CSVAdapterException e)
		{
			// Expected
		}
	}
	
	private void assertLooping(String path) throws EOFException
	{
		boolean isLooping = true;
		CSVReader reader = new CSVReader(path, isLooping);
		
		String[] nextRecord = reader.getNextRecord();
		String[] expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));

		nextRecord = reader.getNextRecord();
		expected = new String[] {"second line", "2"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));
		
		nextRecord = reader.getNextRecord();
		expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));
		
		nextRecord = reader.getNextRecord();
		expected = new String[] {"second line", "2"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));
		
		nextRecord = reader.getNextRecord();
		expected = new String[] {"first line", "1"};
		assertEquals(Arrays.asList(expected), Arrays.asList(nextRecord));
		
		reader.close();
	}
	
	private void assertNonLooping(String path) throws EOFException
	{
		boolean isLooping = false;
		CSVReader reader = new CSVReader(path, isLooping);
		
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
