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
            propertyNames.add(propertyName);
            String failedMessage = "failed assertion for property '" + propertyName + "' ";

            // assert presence and type
            Assert.assertTrue(failedMessage, eventType.isProperty(propertyName));
            Assert.assertNotNull(failedMessage, eventType.getGetter(propertyName));
            Assert.assertSame(failedMessage, eventType.getPropertyType(propertyName), properties[i].getPropertyType());

            // test indexed property
            if (properties[i].isIndexed())
            {
                String propertyNameIndexed = propertyName + "[0]";
                Assert.assertNotNull(failedMessage, eventType.getPropertyType(propertyNameIndexed));
                Assert.assertNotNull(failedMessage, eventType.getGetter(propertyNameIndexed));
                Assert.assertTrue(failedMessage, eventType.isProperty(propertyName));
            }

            // test mapped property
            if (properties[i].isMapped())
            {
                String propertyNameMapped = propertyName + "('a')";
                Assert.assertNotNull(failedMessage, eventType.getPropertyType(propertyNameMapped));
                Assert.assertNotNull(failedMessage, eventType.getGetter(propertyNameMapped));
                Assert.assertTrue(failedMessage, eventType.isProperty(propertyName));
            }
        }

        // assert same property names
        ArrayAssertionUtil.assertEqualsAnyOrder(eventType.getPropertyNames(), propertyNames.toArray());
    }


}