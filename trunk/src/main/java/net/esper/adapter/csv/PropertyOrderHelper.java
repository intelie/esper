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
	 * Resolve the order of the properties that appear in the CSV file, 
	 * from the first row of the CSV file.
	 * @param firstRow - the first record of the CSV file
	 * @param propertyTypes - describes the event to send into the EPRuntime
	 * @param timestampColumn - the name of the timestamp column, or null if no timestamp column
	 * @return the property names in the order in which they occur in the file
	 */
	public static String[] resolvePropertyOrder(String[] firstRow, Map<String, Class> propertyTypes, String timestampColumn)
	{
		log.debug(".resolvePropertyOrder firstRow==" + Arrays.asList(firstRow));
		String[] result = null;
		
		if(isValidTitleRow(firstRow, propertyTypes, timestampColumn))
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

	private static boolean isValidTitleRow(String[] row, Map<String, Class> propertyTypes, String timestampColumn)
	{
		if(propertyTypes == null)
		{
			return true;
		}
		else
		{
			return isValidRowLength(row, propertyTypes) && columnNamesAreValid(row, propertyTypes, timestampColumn);
		}
	}

	private static boolean columnNamesAreValid(String[] row, Map<String, Class> propertyTypes, String timestampColumn)
	{
		log.debug(".columnNamesAreValid");
		Set<String> properties = new HashSet<String>();
		for(String property : row)
		{
			if(!isValidColumnName(property, propertyTypes, timestampColumn) || !properties.add(property))
			{
				log.debug(".columnNamesAreValid invalid name==" + property);
				return false;
			}
		}
		return true;
	}

	private static boolean isValidColumnName(String columnName, Map<String, Class> propertyTypes, String timestampColumn)
	{
		return propertyTypes.containsKey(columnName) || columnName.equals(timestampColumn);
	}

	private static boolean isValidRowLength(String[] row, Map<String, Class> propertyTypes)
	{
		log.debug(".isValidRowLength");
		if(row == null)
		{
			return false;
		}
		return ( row.length == propertyTypes.size() ) || ( row.length == propertyTypes.size() + 1 );
	}

}
