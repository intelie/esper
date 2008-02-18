package com.espertech.esper.core;

/**
 * Implementation of a convertor for column results that renders the result as an object array itself.
 */
public class DeliveryConvertorObjectArr implements DeliveryConvertor
{
    public Object[] convertRow(Object[] columns) {
        return new Object[] {columns};
    }
}
