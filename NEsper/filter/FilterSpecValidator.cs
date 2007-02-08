using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.parse;
using net.esper.events;
using net.esper.util;

namespace net.esper.filter
{
    /// <summary>
    /// Utility class for validating filter specifications.
    /// </summary>

    public class FilterSpecValidator
    {
        /// <summary> Validates a filter specification.</summary>
        /// <param name="filterSpec">is the filter spec to validate
        /// </param>
        /// <param name="optionalTaggedEventTypes">is a optional list of result events and event types for filter specifications that
        /// contain references to result events
        /// </param>
        /// <throws>  ArgumentException to indicate validation errors </throws>

        public static void validate(FilterSpec filterSpec, EDictionary<String, EventType> optionalTaggedEventTypes)
        {
            EventType eventType = filterSpec.EventType;

            foreach (FilterSpecParam param in filterSpec.Parameters)
            {
                String property = param.PropertyName;
                FilterOperator _operator = param.FilterOperator;

                // Check valid
                if (!eventType.isProperty(property))
                {
                    throw new ASTFilterSpecValidationException("Invalid property named '" + property + "' for this event type");
                }

                Type type = eventType.GetPropertyType(property);

                // Check value not null
                if (param.getFilterValueClass(optionalTaggedEventTypes) == null)
                {
                    throw new ASTFilterSpecValidationException("Null filter-for value supplied for property named '" + property + "'");
                }

                // Check numeric
                if (FilterOperatorHelper.isComparisonOperator( _operator ) ||
                    FilterOperatorHelper.isRangeOperator( _operator ) )
                {
                    if (!(TypeHelper.IsNumeric(type)))
                    {
                        throw new ASTFilterSpecValidationException(
                    		"Invalid operator for non-numeric property named '" + property + 
                    		"'");
                    }
                }

                // Check type of value supplied if the type is supplied
                if (FilterOperatorHelper.isComparisonOperator( _operator ) || ( _operator == FilterOperator.EQUAL ) )
                {
                    Type boxedType = type;
                    Type filterValueClass = param.getFilterValueClass(optionalTaggedEventTypes);
                    Type boxedFilterValueClass = filterValueClass;
                    if (boxedType != boxedFilterValueClass)
                    {
                        throw new ASTFilterSpecValidationException(
                    		"Type mismatch for property named '" + property +
                    		"', supplied type of '" + param.getFilterValueClass(optionalTaggedEventTypes).Name +
                    		"' does not match property type '" + type.FullName + 
                    		"'");
                    }
                }
            }

            // Check for the same property mentioned twice
            String duplicateProperty = getDuplicateProperty(filterSpec);
            if (duplicateProperty != null)
            {
                throw new ASTFilterSpecValidationException("Property named '" + duplicateProperty + "' has been listed more than once as a filter parameter");
            }
        }

        /// <summary> Checks for duplicate property names within the parameters and returns the name
        /// of a duplicate property if found, or null if not.
        /// </summary>
        /// <param name="filterSpec">is the filter specification to check
        /// </param>
        /// <returns> name of property that was duplicate in the parameter list, or null if no duplicates exist
        /// </returns>

        private static String getDuplicateProperty(FilterSpec filterSpec)
        {
            EHashSet<String> properties = new EHashSet<String>();

            foreach (FilterSpecParam parameter in filterSpec.Parameters)
            {
                String property = parameter.PropertyName;

                if (properties.Contains(property))
                {
                    return property;
                }

                properties.Add(property);
            }

            return null;
        }
    }
}
