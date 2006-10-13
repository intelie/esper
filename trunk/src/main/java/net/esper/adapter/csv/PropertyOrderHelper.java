package net.esper.adapter.csv;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.client.EPException;

/**
 * A utility for resolving property order information based on a
 * propertyTypes map and the first record of a CSV file (which 
 * might represent the title row).
 */
public class PropertyOrderHelper
{
	private static final Log log = LogFactory.getLog(PropertyOrderHelper.class);

	/**
	 * Indicate whether a timestamp column is represented among the properties
	 * @param propertyOrder - the order of properties in a CSV file
	 * @return true if propertyOrder contains the timestamp column
	 */
	public static boolean propertyOrderContainsTimestamp(String[] propertyOrder)
	{
		boolean result = false;
		for(String property : propertyOrder)
		{
			if(property.equals(CSVFeed.TIMESTAMP_COLUMN_NAME))
			{
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Resolve the order of the properties that appear in the CSV file, 
	 * from the first row of the CSV file.
	 * @param firstRow - the first record of the CSV file
	 * @param propertyTypes - describes the event to send into the EPRuntime
	 * @return the property names in the order in which they occur in the file
	 */
	public static String[] resolvePropertyOrder(String[] firstRow, Map<String, Class> propertyTypes)
	{
		String[] result = null;
		
		if(isValidTitleRow(firstRow, propertyTypes))
		{
			result = firstRow;
			log.debug(".resolvePropertyOrder using valid title row, propertyOrder==" + Arrays.asList(result));
		}
		else 
		{
			throw new EPException("Cannot resolve the order of properties in the CSV file");	
		}
		
		return result;
	}

	private static boolean isValidTitleRow(String[] row, Map<String, Class> propertyTypes)
	{
		return isValidRowLength(row, propertyTypes) && columnNamesAreValid(row, propertyTypes);
	}

	private static boolean columnNamesAreValid(String[] row, Map<String, Class> propertyTypes)
	{
		Set<String> properties = new HashSet<String>();
		for(String property : row)
		{
			if(!isValidColumnName(property, propertyTypes) || !properties.add(property))
			{
				return false;
			}
		}
		return true;
	}

	private static boolean isValidColumnName(String columnName, Map<String, Class> propertyTypes)
	{
		return propertyTypes.containsKey(columnName) || columnName.equals(CSVFeed.TIMESTAMP_COLUMN_NAME);
	}

	private static boolean isValidRowLength(String[] row, Map<String, Class> propertyTypes)
	{
		if(row == null)
		{
			return false;
		}
		return ( row.length == propertyTypes.size() ) || ( row.length == propertyTypes.size() + 1 );
	}

}
