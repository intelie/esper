package com.espertech.esper.support.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.util.JavaClassHelper;
import junit.framework.Assert;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class EventTypeAssertionUtil
{
    public static void assertConsistency(EventBean eventBean)
    {
        assertConsistencyRecusive(eventBean);
    }

    public static void assertConsistency(EventType eventType)
    {
        assertConsistencyRecusive(eventType);
    }

    public static String print(EventBean event)
    {
        StringWriter writer = new StringWriter();
        print(event, writer, 0);
        return writer.toString();
    }

    private static void print(EventBean event, StringWriter writer, int indent)
    {
        writeIndent(writer, indent);
        writer.append("Properties : \n");
        printProperties(event, writer, indent + 2);

        writeIndent(writer, indent);
        writer.append("Fragments : \n");
        for (EventPropertyDescriptor desc : event.getEventType().getPropertyDescriptors())
        {
            if (!desc.isFragment())
            {
                continue;
            }

            writeIndent(writer, indent + 2);
            writer.append(desc.getPropertyName());
            writer.append(" : ");

            if (desc.isRequiresIndex())
            {
                writer.append("\n");
                int count = 0;
                while(true){
                    try
                    {
                        writeIndent(writer, indent + 2);
                        writer.append("#");
                        writer.append(Integer.toString(count));
                        EventBean result = (EventBean) event.getFragment(desc.getPropertyName() + "[" + count + "]");
                        if (result == null)
                        {
                            writer.append("(null EventBean)\n");
                        }
                        else
                        {
                            writer.append("\n");
                            print(result, writer, indent + 4);
                        }
                        count++;
                    }
                    catch (PropertyAccessException ex)
                    {
                        writer.append("-- no access --\n");
                        break;
                    }
                }
            }
            else
            {
                Object fragment = event.getFragment(desc.getPropertyName());
                if (fragment == null)
                {
                    writer.append("(null)\n");
                    continue;
                }

                if (fragment instanceof EventBean)
                {
                    writer.append("bean...\n");
                    print((EventBean)fragment, writer, indent + 4);
                }
                else
                {
                    EventBean[] events = (EventBean[]) fragment;
                    for (int i = 0; i < events.length; i++)
                    {
                        writer.append("bean # " + i + "...\n");
                        print(events[i], writer, indent + 4);
                    }
                }
            }
        }
    }

    private static void printProperties(EventBean eventBean, StringWriter writer, int indent)
    {
        EventPropertyDescriptor properties[] = eventBean.getEventType().getPropertyDescriptors();

        // write simple properties
        for (int i = 0; i < properties.length; i++)
        {
            String propertyName = properties[i].getPropertyName();

            if (properties[i].isIndexed() || properties[i].isMapped())
            {
                continue;
            }

            writeIndent(writer, indent);
            writer.append(propertyName);
            writer.append(" : ");

            Object resultGet = eventBean.get(propertyName);
            writeValue(writer, resultGet);
            writer.append("\n");
        }        

        // write indexed properties
        for (int i = 0; i < properties.length; i++)
        {
            String propertyName = properties[i].getPropertyName();

            if (!properties[i].isIndexed())
            {
                continue;
            }

            writeIndent(writer, indent);
            writer.append(propertyName);
            String type = "array";
            if (properties[i].isRequiresIndex())
            {
                type = type + " requires-index";
            }
            writer.append(" (" + type + ") : ");

            if (properties[i].isRequiresIndex())
            {
                int count = 0;
                writer.append("\n");
                while(true){
                    try
                    {
                        writeIndent(writer, indent + 2);
                        writer.append("#");
                        writer.append(Integer.toString(count));
                        writer.append(" ");
                        Object result = eventBean.get(propertyName + "[" + count + "]");
                        writeValue(writer, result);
                        writer.append("\n");
                        count++;
                    }
                    catch (PropertyAccessException ex)
                    {
                        writer.append("-- no access --\n");
                        break;
                    }
                }
            }
            else
            {
                Object result = eventBean.get(propertyName);
                writeValue(writer, result);
                writer.append("\n");
            }
        }

        // write mapped properties
        for (int i = 0; i < properties.length; i++)
        {
            String propertyName = properties[i].getPropertyName();

            if (!properties[i].isMapped())
            {
                continue;
            }

            writeIndent(writer, indent);
            writer.append(propertyName);
            String type = "mapped";
            if (properties[i].isRequiresMapkey())
            {
                type = type + " requires-mapkey";
            }
            writer.append(" (" + type + ") : ");

            if (!properties[i].isRequiresMapkey())
            {
                Object result = eventBean.get(propertyName);
                writeValue(writer, result);
                writer.append("\n");
            }
            else
            {
                writer.append("??map key unknown??\n");                
            }
        }
    }
    

    private static void assertConsistencyRecusive(EventBean eventBean)
    {
        assertConsistencyRecusive(eventBean.getEventType());

        EventPropertyDescriptor properties[] = eventBean.getEventType().getPropertyDescriptors();
        for (int i = 0; i < properties.length; i++)
        {
            String failedMessage = "failed assertion for property '" + properties[i].getPropertyName() + "' ";
            String propertyName = properties[i].getPropertyName();

            // assert getter
            if ((!properties[i].isRequiresIndex()) && (!properties[i].isRequiresMapkey()))
            {
                EventPropertyGetter getter = eventBean.getEventType().getGetter(propertyName);
                Object resultGetter = getter.get(eventBean);
                Assert.assertNotNull(failedMessage, resultGetter);         // expecting non-null values returned

                Object resultGet = eventBean.get(propertyName);
                Assert.assertSame(failedMessage, resultGet, resultGetter);

                Assert.assertTrue(failedMessage, JavaClassHelper.isSubclassOrImplementsInterface(resultGet.getClass(), properties[i].getPropertyType()));
            }

            // fragment
            if (!properties[i].isFragment())
            {
                Assert.assertNull(failedMessage, eventBean.getFragment(propertyName));
                continue;
            }

            Object fragment = eventBean.get(propertyName);
            Assert.assertNotNull(failedMessage, fragment);

            EventTypeFragment fragmentType = eventBean.getEventType().getFragmentType(propertyName);
            Assert.assertNotNull(failedMessage, fragmentType);

            if (fragmentType.isIndexed())
            {
                Assert.assertTrue(failedMessage, fragment instanceof EventBean);
                EventBean fragmentEvent = (EventBean) fragment;
                assertConsistencyRecusive(fragmentEvent);
            }
            else
            {
                Assert.assertTrue(failedMessage, fragment instanceof EventBean[]);
                EventBean[] events = (EventBean[]) fragment;
                Assert.assertTrue(failedMessage, events.length > 0);
                for (EventBean event : events)
                {
                    assertConsistencyRecusive(event);
                }
            }
        }
    }

    private static void assertConsistencyRecusive(EventType eventType)
    {
        assertConsistencyProperties(eventType);

        // test fragments
        for (EventPropertyDescriptor descriptor : eventType.getPropertyDescriptors())
        {
            String failedMessage = "failed assertion for property '" + descriptor.getPropertyName() + "' ";
            if (!descriptor.isFragment())
            {
                Assert.assertNull(failedMessage, eventType.getFragmentType(descriptor.getPropertyName()));
                continue;
            }

            EventTypeFragment fragment = eventType.getFragmentType(descriptor.getPropertyName());
            if (!descriptor.isRequiresIndex())
            {
                Assert.assertNotNull(failedMessage, fragment);
                if (fragment.isIndexed())
                {
                    Assert.assertTrue(descriptor.isIndexed());
                }
                assertConsistencyRecusive(fragment.getFragmentType());
            }
            else
            {
                fragment = eventType.getFragmentType(descriptor.getPropertyName() + "[0]");
                Assert.assertNotNull(failedMessage, fragment);
                Assert.assertTrue(descriptor.isIndexed());
                assertConsistencyRecusive(fragment.getFragmentType());
            }
        }
    }

    private static void assertConsistencyProperties(EventType eventType)
    {
        List<String> propertyNames = new ArrayList<String>();

        EventPropertyDescriptor properties[] = eventType.getPropertyDescriptors();
        for (int i = 0; i < properties.length; i++)
        {
            String propertyName = properties[i].getPropertyName();
            propertyNames.add(propertyName);
            String failedMessage = "failed assertion for property '" + propertyName + "' ";

            // assert presence of descriptor
            Assert.assertSame(properties[i], eventType.getPropertyDescriptor(propertyName));

            // test properties that can simply be in a property expression
            if ((!properties[i].isRequiresIndex()) && (!properties[i].isRequiresMapkey()))
            {
                Assert.assertTrue(failedMessage, eventType.isProperty(propertyName));
                Assert.assertSame(failedMessage, eventType.getPropertyType(propertyName), properties[i].getPropertyType());
                Assert.assertNotNull(failedMessage, eventType.getGetter(propertyName));
            }
            
            // test indexed property
            if (properties[i].isIndexed())
            {
                String propertyNameIndexed = propertyName + "[0]";
                Assert.assertTrue(failedMessage, eventType.isProperty(propertyNameIndexed));
                Assert.assertNotNull(failedMessage, eventType.getPropertyType(propertyNameIndexed));
                Assert.assertNotNull(failedMessage, eventType.getGetter(propertyNameIndexed));
            }

            // test mapped property
            if (properties[i].isRequiresMapkey())
            {
                String propertyNameMapped = propertyName + "('a')";
                Assert.assertTrue(failedMessage, eventType.isProperty(propertyNameMapped));
                Assert.assertNotNull(failedMessage, eventType.getPropertyType(propertyNameMapped));
                Assert.assertNotNull(failedMessage, eventType.getGetter(propertyNameMapped));
            }

            // consistent flags
            Assert.assertFalse(failedMessage, properties[i].isIndexed() && properties[i].isMapped());
            if (properties[i].isRequiresIndex())
            {
                Assert.assertTrue(failedMessage, properties[i].isIndexed());
            }
            if (properties[i].isRequiresMapkey())
            {
                Assert.assertTrue(failedMessage, properties[i].isMapped());
            }

            // fragments
            if (properties[i].isFragment())
            eventType.getFragmentType(propertyName);
        }

        // assert same property names
        ArrayAssertionUtil.assertEqualsAnyOrder(eventType.getPropertyNames(), propertyNames.toArray());
    }

    private static void writeIndent(StringWriter writer, int indent)
    {
        for (int i = 0; i < indent; i++)
        {
            writer.write(' ');
        }
    }

    private static void writeValue(StringWriter writer, Object result)
    {
        if (result == null)
        {
            writer.append("(null)");
        }
        else
        {
            writer.append(result.toString());
        }
    }
}