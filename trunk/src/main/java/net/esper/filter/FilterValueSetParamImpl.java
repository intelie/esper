package net.esper.filter;

/**
 * Filter parameter value defining the event property to filter, the filter operator, and the filter value.
 */
public class FilterValueSetParamImpl implements FilterValueSetParam
{
    private final String propertyName;
    private final FilterOperator filterOperator;
    private final Object filterValue;

    /**
     * Ctor.
     * @param propertyName - property to interrogate
     * @param filterOperator - operator to apply
     * @param filterValue - value to look for
     */
    public FilterValueSetParamImpl(String propertyName, FilterOperator filterOperator, Object filterValue)
    {
        this.propertyName = propertyName;
        this.filterOperator = filterOperator;
        this.filterValue = filterValue;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public FilterOperator getFilterOperator()
    {
        return filterOperator;
    }

    public Object getFilterForValue()
    {
        return filterValue;
    }
}
