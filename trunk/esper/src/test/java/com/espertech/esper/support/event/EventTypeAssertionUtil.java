package com.espertech.esper.support.event;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;

import junit.framework.Assert;

public class EventTypeAssertionUtil
{
    public static String browse(EventType eventType)
    {
        StringWriter writer = new StringWriter();
        //browse(eventType, new Stack<String>(), writer);
        return writer.toString();
    }

    public static void assertConsistency(EventType eventType)
    {
        assertConsistencyInternal(eventType);

        // test fragments
        //for (int i = 0; i < eventTy)
    }

    private static void assertConsistencyInternal(EventType eventType)
    {
        List<String> propertyNames = new ArrayList<String>();

        EventPropertyDescriptor properties[] = eventType.getPropertyDescriptors();
        for (int i = 0; i < properties.length; i++)
        {
            // assert property name expansion
            String propertyName = properties[i].getPropertyName();
            if (properties[i].isRequiresIndex())
            {
                propertyNames.add(propertyName + "[]");
            }
            if (properties[i].isRequiresMapkey())
            {
                propertyNames.add(propertyName + "()");
            }

            // assert type
            Assert.assertSame("failed assertion for property '" + propertyName + "' ", eventType.getPropertyType(propertyName), properties[i].getPropertyType());

            // test indexed property
            if (properties[i].isIndexed())
            {
                Assert.assertNotNull(eventType.getPropertyType(propertyName));
                Assert.assertNotNull(eventType.getPropertyType(propertyName + "[0]"));
            }
        }

        // assert same names
        ArrayAssertionUtil.assertEqualsAnyOrder(eventType.getPropertyNames(), propertyNames.toArray());
    }


}