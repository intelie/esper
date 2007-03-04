package net.esper.filter;

/**
 * This interface represents one filter parameter in an {@link FilterValueSet} filter specification.
 * <p> Each filtering parameter has an property name and operator type, and a value to filter for.
 */
public interface FilterValueSetParam
{
    /**
     * Returns the property name for the filter parameter.
     * @return property name
     */
    public String getPropertyName();

    /**
     * Returns the filter operator type.
     * @return filter operator type
     */
    public FilterOperator getFilterOperator();

    /**
     * Return the filter parameter constant to filter for.
     * @return filter parameter constant's value
     */
    public Object getFilterForValue();
}
