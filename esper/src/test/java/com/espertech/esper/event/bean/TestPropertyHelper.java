package com.espertech.esper.event.bean;

import com.espertech.esper.event.bean.PropertyHelper;
import com.espertech.esper.event.bean.InternalEventPropDescriptor;
import com.espertech.esper.support.bean.SupportBeanPropertyNames;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import net.sf.cglib.reflect.FastClass;

import java.util.List;
import java.util.LinkedList;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPropertyHelper extends TestCase
{
    public void testAddMappedProperties()
    {
    	List<InternalEventPropDescriptor> result = new LinkedList<InternalEventPropDescriptor>();
        PropertyHelper.addMappedProperties(SupportBeanPropertyNames.class, result);

        for (InternalEventPropDescriptor desc : result)
        {
            log.debug("desc=" + desc.getPropertyName());
        }

        assertEquals(6, result.size());
        assertEquals("a", result.get(0).getPropertyName());
        assertEquals("AB", result.get(1).getPropertyName());
        assertEquals("ABC", result.get(2).getPropertyName());
        assertEquals("ab", result.get(3).getPropertyName());
        assertEquals("abc", result.get(4).getPropertyName());
        assertEquals("fooBah", result.get(5).getPropertyName());
    }

    public void testAddIntrospectProperties() throws Exception
    {
    	List<InternalEventPropDescriptor> result = new LinkedList<InternalEventPropDescriptor>();
        PropertyHelper.addIntrospectProperties(SupportBeanPropertyNames.class, result);

        for (InternalEventPropDescriptor desc : result)
        {
            log.debug("desc=" + desc.getPropertyName());
        }

        assertEquals(9, result.size()); // for "class" is also in there
        assertEquals("indexed", result.get(8).getPropertyName());
        assertNotNull(result.get(8).getReadMethod());
    }

    public void testRemoveDuplicateProperties()
    {
        List<InternalEventPropDescriptor> result = new LinkedList<InternalEventPropDescriptor>();
        result.add(new InternalEventPropDescriptor("x", (Method) null, null));
        result.add(new InternalEventPropDescriptor("x", (Method) null, null));
        result.add(new InternalEventPropDescriptor("y", (Method) null, null));

        PropertyHelper.removeDuplicateProperties(result);

        assertEquals(2, result.size());
        assertEquals("x", result.get(0).getPropertyName());
        assertEquals("y", result.get(1).getPropertyName());
    }

    public void testRemoveJavaProperties()
    {
        List<InternalEventPropDescriptor> result = new LinkedList<InternalEventPropDescriptor>();
        result.add(new InternalEventPropDescriptor("x", (Method) null, null));
        result.add(new InternalEventPropDescriptor("class", (Method) null, null));
        result.add(new InternalEventPropDescriptor("hashCode", (Method) null, null));
        result.add(new InternalEventPropDescriptor("toString", (Method) null, null));
        result.add(new InternalEventPropDescriptor("getClass", (Method) null, null));

        PropertyHelper.removeJavaProperties(result);

        assertEquals(1, result.size());
        assertEquals("x", result.get(0).getPropertyName());
    }

    public void testIntrospect()
    {
        PropertyDescriptor desc[] = PropertyHelper.introspect(SupportBeanPropertyNames.class);
        assertTrue(desc.length > 5);
    }

    public void testGetGetter() throws Exception
    {
        FastClass fastClass = FastClass.create(Thread.currentThread().getContextClassLoader(), SupportBeanPropertyNames.class);
        EventBean bean = SupportEventBeanFactory.createObject(new SupportBeanPropertyNames());
        Method method = SupportBeanPropertyNames.class.getMethod("getA", new Class[0]);
        EventPropertyGetter getter = PropertyHelper.getGetter(method, fastClass, SupportEventAdapterService.getService());
        assertEquals("", getter.get(bean));
    }

    private static final Log log = LogFactory.getLog(TestPropertyHelper.class);
}
