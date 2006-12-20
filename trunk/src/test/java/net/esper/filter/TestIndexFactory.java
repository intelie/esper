package net.esper.filter;

import junit.framework.TestCase;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.event.EventType;

public class TestIndexFactory extends TestCase
{
    EventType eventType;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public void testCreateIndex()
    {
        // Create a "greater" index
        FilterParamIndex index = IndexFactory.createIndex(eventType, "intPrimitive", FilterOperator.GREATER);

        assertTrue(index != null);
        assertTrue(index instanceof FilterParamIndexCompare);
        assertTrue(index.getPropertyName().equals("intPrimitive"));
        assertTrue(index.getFilterOperator() == FilterOperator.GREATER);

        // Create an "equals" index
        index = IndexFactory.createIndex(eventType, "string", FilterOperator.EQUAL);

        assertTrue(index != null);
        assertTrue(index instanceof FilterParamIndexEquals);
        assertTrue(index.getPropertyName().equals("string"));
        assertTrue(index.getFilterOperator() == FilterOperator.EQUAL);

        // Create an "not equals" index
        index = IndexFactory.createIndex(eventType, "string", FilterOperator.NOT_EQUAL);

        assertTrue(index != null);
        assertTrue(index instanceof FilterParamIndexNotEquals);
        assertTrue(index.getPropertyName().equals("string"));
        assertTrue(index.getFilterOperator() == FilterOperator.NOT_EQUAL);

        // Create a range index
        index = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.RANGE_CLOSED);
        assertTrue(index instanceof FilterParamIndexRange);

        // Create a in-index
        index = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.IN_LIST_OF_VALUES);
        assertTrue(index instanceof FilterParamIndexIn);
    }
}

