package net.esper.core;

/**
 * Implementation that does not convert columns.
 */
public class DeliveryConvertorNull implements DeliveryConvertor
{
    public Object[] convertRow(Object[] columns) {
        return columns;
    }
}
