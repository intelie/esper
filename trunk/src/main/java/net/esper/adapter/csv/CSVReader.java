package net.esper.adapter.csv;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.esper.adapter.AdapterInputSource;
import net.esper.client.EPException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A reader that processes a CSV file and returns CSV records 
 * from that file.
 */
public class CSVReader
{
	private static final Log log = LogFactory.getLog(CSVReader.class);
	
	private final AdapterInputSource inputSource;
	private boolean isLooping;
	private boolean isUsingTitleRow;
	
	private final List<String> values = new ArrayList<String>();
	private InputStream inputStream;
	private BufferedInputStream reader;
	private boolean isClosed = false;
	private boolean atEOF = false;
	private boolean isReset = true;
	/**
	 * Ctor.
	 * @param adapterInputSource - the source of the CSV file
	 * @throws EPException in case of errors in reading the CSV file
	 */
	public CSVReader(AdapterInputSource adapterInputSource) throws EPException
	{
		this.inputSource = adapterInputSource;
		inputStream = inputSource.openStream();
		reader = new BufferedInputStream(inputStream);
	}
	
	/**
	 * Close the reader and release the input source.
	 * @throws EPException in case of error in closing resources
	 */
	public void close() throws EPException
	{
		if(isClosed)
		{
			throw new EPException("Calling close() on an already closed CSVReader");
		}
		try
		{
			isClosed = true;
			inputStream.close();
			reader.close();
		} 
		catch (IOException e)
		{
			throw new EPException(e);
		}
	}

	/**
	 * Get the next record from the CSV file.
	 * @return a string array containing the values of the record
	 * @throws EOFException in case no more records can be read (end-of-file has been reached and isLooping is false)
	 * @throws EPException in case of error in reading the CSV file
	 */
	public String[] getNextRecord() throws EOFException, EPException
	{
		try
		{
			String[] result = getNextValidRecord(); 
			
			if(atEOF && result == null)
			{
				throw new EOFException("In reading CSV file " + inputSource + "reached end-of-file and not looping to the beginning");
			}
			
			log.debug(".getNextRecord record==" + Arrays.asList(result));
			return result; 
		} 
		catch (EOFException e)
		{
			throw e;
		}
		catch(IOException e)
		{
			throw new EPException(e);
		}
	}
	
	/**
	 * Set the isUsingTitleRow value.
	 * @param isUsingTitleRow - true if the CSV file contains a valid title row
	 */
	public void setIsUsingTitleRow(boolean isUsingTitleRow)
	{
		this.isUsingTitleRow = isUsingTitleRow;
	}
	
	/**
	 * Set the isLooping value.
	 * @param isLooping - true if processing should start over from the beginning after the end of the CSV file is reached
	 */
	public void setIsLooping(boolean isLooping)
	{
		this.isLooping = isLooping;
	}

    /**
     * Reset the reader to the beginning of the file.
     * @throws EPException in case of errors in resetting the reader
     */
	public void reset() 
	{
		try
		{
			log.debug(".reset");
			inputStream.close();
			reader.close();
			inputStream = inputSource.openStream();
			reader = new BufferedInputStream(inputStream);
			atEOF = false;
			if(isUsingTitleRow)
			{
				// Ignore the title row
				getNextRecord();
			}
			isReset = true;
		} 
		catch (IOException e)
		{
			throw new EPException(e);
		}
	}
	
	/**
	 * Return and set to false the isReset value, which is set to
	 * true whenever the CSVReader is reset.
	 * @return isReset
	 */
	public boolean getAndClearIsReset()
	{
		log.debug(".getAndClearIsReset isReset==" + isReset);
		boolean result = isReset;
		isReset = false;
		return result;
	}

	private String[] getNextValidRecord() throws IOException
	{
		String[] result = null;
		
		// Search for a valid record to the end of the CSV file
		result = getNoCommentNoWhitespace();
		
		// If haven't found a valid record and at the end of the
		// file and looping, search from the beginning of the file
		if(result == null && atEOF && isLooping)
		{
			reset();
			result = getNoCommentNoWhitespace();
		}

		return result;
	}

	private String[] getNoCommentNoWhitespace() throws IOException
	{
		String[] result = null;
		// This loop serves to filter out commented lines and
		//lines that contain only whitespace
		while(result == null && !atEOF)
		{
			skipCommentedLines();
			result = getNewValues();
		}
		return result;
	}
	
	private String[] getNewValues() throws IOException
	{
		values.clear();
		boolean doConsume = true;
		
		while(true)
		{
			String value = matchValue();

			if(atComma(doConsume))
			{
				addNonFinalValue(value);
				continue;
			}
			else if(atNewline(doConsume) || atEOF(doConsume))
			{
				addFinalValue(value);
				break;
			}
			else
			{
				throw unexpectedCharacterException((char)reader.read());
			}
		}
		
		// All values empty means that this line was just whitespace
		return values.isEmpty() ? null : values.toArray(new String[0]);
	}
	
	private void addNonFinalValue(String value)
	{
		// Represent empty values as empty strings
		value = (value == null) ? "" : value;
		values.add(value);
	}
	
	private void addFinalValue(String value)
	{
		// Add this value only if it is nonempty or if it is the
		// last value of a nonempty record.
		if(value != null)
		{
			values.add(value);
		}
		else
		{
			if(!values.isEmpty())
			{
				values.add("");
			}
		}
	}
	
	private boolean atNewline(boolean doConsume) throws IOException
	{
		return atWinNewline(doConsume) || atChar('\n', doConsume) || atChar('\r', doConsume);
	}
	
	private boolean atWinNewline(boolean doConsume) throws IOException
	{
		markReader(2, doConsume);
		
		char firstChar = (char)reader.read();
		char secondChar = (char)reader.read();
		boolean result = (firstChar == '\r' && secondChar == '\n');
		
		resetReader(doConsume, result);
		return result;
	}
	
	private boolean atChar(char character, boolean doConsume) throws IOException
	{
		markReader(1, doConsume);
		
		char firstChar = (char)reader.read();
		boolean result = (firstChar == character);
		
		resetReader(doConsume, result);
		return result;
	}

	private void resetReader(boolean doConsume, boolean result) throws IOException
	{
		// Reset the reader unless in consuming mode and the 
		// matched character was what was expected
		if(!(doConsume && result))
		{
			reader.reset();
		}
	}

	private void markReader(int markLimit, boolean doConsume) throws IOException
	{
			reader.mark(markLimit);
	}
	
	private boolean atEOF(boolean doConsume) throws IOException
	{
		markReader(1, doConsume);
		
		int value = reader.read();
		atEOF = (value == -1);
		
		resetReader(doConsume, atEOF);
		return atEOF;
	}
	
	private boolean atComma(boolean doConsume) throws IOException
	{
		return atChar(',', doConsume);
	}
	
	private String matchValue() throws IOException
	{
		consumeWhiteSpace();
		
		String value = matchQuotedValue();
		if(value == null)
		{
			value = matchUnquotedValue();
		}
		
		consumeWhiteSpace();
		return value;
	}
	
	private String matchQuotedValue() throws IOException
	{
		// Enclosing quotes and quotes used to escape other quotes
		// are discarded
		
		boolean doConsume = true;
		if(!atChar('"', doConsume))
		{
			// This isn't a quoted value
			return null;
		}

		StringBuffer value = new StringBuffer();
		while(true)
		{
			char currentChar = (char)reader.read();

			if(currentChar == '"' && !atChar('"', doConsume))
			{
				// Single quote ends the value
				break;
			}

			value.append(currentChar);
		}
		
		return value.toString();
	}
	
	private String matchUnquotedValue() throws IOException
	{
		boolean doConsume = false;
		StringBuffer value = new StringBuffer();
		int trailingSpaces = 0;
		
		while(true)
		{
			// Break on newline or comma without consuming
			if(atNewline(doConsume) || atEOF(doConsume) || atComma(doConsume))
			{
				break;
			}
			
			// Unquoted values cannot contain quotes
			if(atChar('"', doConsume))
			{
				log.debug(".matchUnquotedValue matched unexpected double-quote while matching " + value);
				log.debug(".matchUnquotedValue values==" + values);
				throw unexpectedCharacterException('"');
			}
			
			char currentChar = (char)reader.read();
			
			// Update the count of trailing spaces
			trailingSpaces = (isWhiteSpace(currentChar)) ?
					trailingSpaces + 1 : 0;
			
			value.append(currentChar);
		}
		
		// Remove the trailing spaces
		int end = value.length();
		value.delete(end - trailingSpaces, end);
		
		// An empty string means that this value was just whitespace, 
		// so nothing was matched
		return value.length() == 0 ? null : value.toString();
	}
	
	private void consumeWhiteSpace() throws IOException
	{
		while(true)
		{	
			reader.mark(1);
			char currentChar = (char)reader.read();

			if(!isWhiteSpace(currentChar))
			{
				reader.reset();
				break;
			}
		}
	}
	
	private boolean isWhiteSpace(char currentChar)
	{
		return currentChar == ' ' || currentChar == '\t';
	}
	
	private EPException unexpectedCharacterException(char unexpected)
	{
		return new EPException("Encountered unexpected character " + unexpected);
	}
	
	private void skipCommentedLines() throws IOException
	{
		boolean doConsume = false;
		while(true)
		{
			if(atEOF && isLooping)
			{
				reset();
			}
			if(atChar('#', doConsume))
			{
				consumeLine();
			}
			else
			{
				break;
			}
		}
	}
	
	private void consumeLine() throws IOException
	{
		boolean doConsume = true;
		while(!atEOF(doConsume) && !atNewline(doConsume))
		{
			// Discard input
			reader.read();
		}
	}
}
