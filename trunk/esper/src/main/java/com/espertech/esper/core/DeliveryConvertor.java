package com.espertech.esper.core;

/**
 * Converts a row of column selection results into a result for dispatch to a method.
 */
public interface DeliveryConvertor
{
    /**
     * Convert result row to dispatchable.
     * @param row to convert
     * @return converted row
     */
    public Object[] convertRow(Object[] row);
}
