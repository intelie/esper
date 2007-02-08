using System;
using EventType = net.esper.events.EventType;
using TypeHelper = net.esper.util.TypeHelper;
namespace net.esper.view
{
	
	/// <summary> Utility class for checking in a schema if fields exist and/or have an expected type.</summary>
	public sealed class PropertyCheckHelper
	{
		/// <summary> Check if the field identified by the field name exists according to the schema.</summary>
		/// <param name="type">contains metadata about fields
		/// </param>
		/// <param name="fieldName">is the field's field name to test
		/// </param>
		/// <returns> a String error message if the field doesn't exist, or null to indicate success
		/// </returns>
		public static String exists(EventType type, String fieldName)
		{
			Type clazz = getClass(type, fieldName);
			
			if (clazz == null)
			{
				return "Parent view does not contain a field named '" + fieldName + "'";
			}
			
			return null;
		}
		
		/// <summary> Check if the fields identified by the field names both exists according to the schema.</summary>
		/// <param name="type">contains metadata about fields
		/// </param>
		/// <param name="fieldNameOne">is the first field's field name to test
		/// </param>
		/// <param name="fieldNameTwo">is the first field's field name to test
		/// </param>
		/// <returns> a String error message if either of the fields doesn't exist, or null to indicate success
		/// </returns>
		public static String exists(EventType type, String fieldNameOne, String fieldNameTwo)
		{
			Type clazz = getClass(type, fieldNameOne);
			
			if (clazz == null)
			{
				return "Parent view does not contain a field named '" + fieldNameOne + "'";
			}
			
			clazz = getClass(type, fieldNameTwo);
			
			if (clazz == null)
			{
				return "Parent view does not contain a field named '" + fieldNameTwo + "'";
			}
			
			return null;
		}
		
		/// <summary> Check if the field identified by the field name is a valid numeric field according to the schema.</summary>
		/// <param name="type">contains metadata about fields
		/// </param>
		/// <param name="numericFieldName">is the field's field name to test
		/// </param>
		/// <returns> a String error message if the field doesn't exist or is not numeric, or null to indicate success
		/// </returns>
		public static String checkNumeric(EventType type, String numericFieldName)
		{
			return checkFieldNumeric(type, numericFieldName);
		}
		
		/// <summary> Check if the fields identified by their field names are valid numeric field according to the schema.</summary>
		/// <param name="type">contains metadata about fields
		/// </param>
		/// <param name="numericFieldNameX">is the first field's field name to test
		/// </param>
		/// <param name="numericFieldNameY">is the second field's field name to test
		/// </param>
		/// <returns> a String error message if the field doesn't exist or is not numeric, or null to indicate success
		/// </returns>
		public static String checkNumeric(EventType type, String numericFieldNameX, String numericFieldNameY)
		{
			String error = checkFieldNumeric(type, numericFieldNameX);
			if (error != null)
			{
				return error;
			}
			
			return checkFieldNumeric(type, numericFieldNameY);
		}
		
		/// <summary> Check if the field identified by the field name is of type long according to the schema.</summary>
		/// <param name="type">contains metadata about fields
		/// </param>
		/// <param name="longFieldName">is the field's field name to test
		/// </param>
		/// <returns> a String error message if the field doesn't exist or is not a long, or null to indicate success
		/// </returns>
		public static String checkLong(EventType type, String longFieldName)
		{
			Type clazz = getClass(type, longFieldName);
			
			if (clazz == null)
			{
				return "Parent view does not contain a field named '" + longFieldName + "'";
			}
			
			if ((clazz != typeof(long)) && (clazz != typeof(long)))
			{
				return "Parent view field named '" + longFieldName + "' is not of type long";
			}
			
			return checkFieldNumeric(type, longFieldName);
		}
		
		/// <summary> Returns the class for the field as defined in the schema.</summary>
		/// <param name="type">contains metadata about fields
		/// </param>
		/// <param name="fieldName">is the field's name to return the type for
		/// </param>
		/// <returns> type of field.
		/// </returns>
		private static Type getClass(EventType type, String fieldName)
		{
			Type clazz = type.GetPropertyType(fieldName);
			return clazz;
		}
		
		// Perform the schema checking for if a field exists and is numeric
		private static String checkFieldNumeric(EventType type, String numericFieldName)
		{
			Type clazz = getClass(type, numericFieldName);
			
			if (clazz == null)
			{
				return "Parent view does not contain a field named '" + numericFieldName + "'";
			}
			
			if (!TypeHelper.IsNumeric(clazz))
			{
				return "Parent view field named '" + numericFieldName + "' is not a number";
			}
			
			return null;
		}
	}
}