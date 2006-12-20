package net.esper.event.property;

import junit.framework.TestCase;
import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.event.EventPropertyDescriptor;
import net.esper.event.EventPropertyType;
import net.esper.support.bean.SupportLegacyBean;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.List;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPropertyListBuilderJavaBean extends TestCase
{
    private PropertyListBuilderJavaBean builder;

    public void setUp()
    {
        ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();

        // add 2 explicit properties, also supported
        config.addFieldProperty("x", "fieldNested");
        config.addMethodProperty("y", "readLegacyBeanVal");

        builder = new PropertyListBuilderJavaBean(config);
    }

    public void testBuildPropList() throws Exception
    {
        List<EventPropertyDescriptor> descList = builder.assessProperties(SupportLegacyBean.class);

        List<EventPropertyDescriptor> expected = new LinkedList<EventPropertyDescriptor>();
        expected.add(new EventPropertyDescriptor("x", "x", SupportLegacyBean.class.getField("fieldNested"), EventPropertyType.SIMPLE));
        expected.add(new EventPropertyDescriptor("y", "y", SupportLegacyBean.class.getMethod("readLegacyBeanVal"), EventPropertyType.SIMPLE));
        ArrayAssertionUtil.assertEqualsAnyOrder(expected.toArray(), descList.toArray());
    }

    private static Log log = LogFactory.getLog(TestPropertyListBuilderJavaBean.class);
}
