package net.esper.filter;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventType;
import net.esper.eql.parse.ASTFilterSpecValidationException;

import java.util.HashSet;
import java.util.Map;

/**
 * Utility class for validating filter specifications.
 */
public class FilterSpecValidator
{
    /**
     * Validates a filter specification.
     * @param filterSpec is the filter spec to validate
     * @param optionalTaggedEventTypes is a optional list of result events and event types for filter specifications that
     * contain references to result events
     * @throws IllegalArgumentException to indicate validation errors
     * @throws ASTFilterSpecValidationException to indicate filter validation problem
     */
    public static void validate(FilterSpec filterSpec, Map<String, EventType> optionalTaggedEventTypes)
        throws ASTFilterSpecValidationException
    {
        EventType eventType = filterSpec.getEventType();

        for (FilterSpecParam param : filterSpec.getParameters())
        {
            String property = param.getPropertyName();
            FilterOperator operator = param.getFilterOperator();

            // Check valid
            if (!eventType.isProperty(property))
            {
                throw new ASTFilterSpecValidationException("Invalid property named '" + property + "' for this event type");
            }

            Class type = JavaClassHelper.getBoxedType(eventType.getPropertyType(property));

            // Check value not null
            if (param.getFilterValueClass(optionalTaggedEventTypes) == null)
            {
                throw new ASTFilterSpecValidationException("Null filter-for value supplied for property named '" + property + '\'');
            }

            // Check numeric
            if ( (operator.isComparisonOperator()) || (operator.isRangeOperator()) )
            {
                if (!(JavaClassHelper.isNumeric(type)))
                {
                    throw new ASTFilterSpecValidationException("Invalid operator for non-numeric property named '" + property + '\'');
                }
            }

            // Check type of value supplied if the type is supplied
            if (operator.isComparisonOperator() || operator == FilterOperator.EQUAL)
            {
                Class boxedType = JavaClassHelper.getBoxedType(type);
                Class filterValueClass = param.getFilterValueClass(optionalTaggedEventTypes);
                Class boxedFilterValueClass = JavaClassHelper.getBoxedType(filterValueClass);
                if (boxedType != boxedFilterValueClass)
                {
                    throw new ASTFilterSpecValidationException("Type mismatch for property named '" + property +
                            "', supplied type of '" + param.getFilterValueClass(optionalTaggedEventTypes).getName() +
                            "' does not match property type '" + type.getName() + '\'');
                }
            }
        }

        // Check for the same property mentioned twice
        String duplicateProperty = getDuplicateProperty(filterSpec);
        if (duplicateProperty != null)
        {
            throw new ASTFilterSpecValidationException("Property named '" + duplicateProperty +
                    "' has been listed more than once as a filter parameter");
        }
    }

    /**
     * Checks for duplicate property names within the parameters and returns the name
     * of a duplicate property if found, or null if not.
     * @param filterSpec is the filter specification to check
     * @return name of property that was duplicate in the parameter list, or null if no duplicates exist
     */
    private static final String getDuplicateProperty(FilterSpec filterSpec)
    {
        HashSet<String> properties = new HashSet<String>();

        for (FilterSpecParam parameter : filterSpec.getParameters())
        {
            String property = parameter.getPropertyName();

            if (properties.contains(property))
            {
                return property;
            }

            properties.add(property);
        }

        return null;
    }
}
