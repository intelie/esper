package com.espertech.esper.event.property;

import junit.framework.TestCase;
import com.espertech.esper.client.ConfigurationEventTypeLegacy;
import com.espertech.esper.event.InternalEventPropDescriptor;
import com.espertech.esper.event.EventPropertyType;
import com.espertech.esper.support.bean.SupportLegacyBean;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.List;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPropertyListBuilderPublic extends TestCase
{
    private PropertyListBuilderPublic builder;

    public void setUp()
    {
        ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();

        // add 2 explicit properties, also supported
        config.addFieldProperty("x", "fieldNested");
        config.addMethodProperty("y", "readLegacyBeanVal");
        
        builder = new PropertyListBuilderPublic(config);
    }

    public void testBuildPropList() throws Exception
    {
        List<InternalEventPropDescriptor> descList = builder.assessProperties(SupportLegacyBean.class);

        List<InternalEventPropDescriptor> expected = new LinkedList<InternalEventPropDescriptor>();
        expected.add(new InternalEventPropDescriptor("fieldLegacyVal", "fieldLegacyVal", SupportLegacyBean.class.getField("fieldLegacyVal"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("fieldStringArray", "fieldStringArray", SupportLegacyBean.class.getField("fieldStringArray"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("fieldMapped", "fieldMapped", SupportLegacyBean.class.getField("fieldMapped"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("fieldNested", "fieldNested", SupportLegacyBean.class.getField("fieldNested"), EventPropertyType.SIMPLE));

        expected.add(new InternalEventPropDescriptor("readLegacyBeanVal", "readLegacyBeanVal", SupportLegacyBean.class.getMethod("readLegacyBeanVal"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("readStringArray", "readStringArray", SupportLegacyBean.class.getMethod("readStringArray"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("readStringIndexed", "readStringIndexed", SupportLegacyBean.class.getMethod("readStringIndexed", new Class[] {int.class}), EventPropertyType.INDEXED));
        expected.add(new InternalEventPropDescriptor("readMapByKey", "readMapByKey", SupportLegacyBean.class.getMethod("readMapByKey", new Class[] {String.class}), EventPropertyType.MAPPED));
        expected.add(new InternalEventPropDescriptor("readMap", "readMap", SupportLegacyBean.class.getMethod("readMap"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("readLegacyNested", "readLegacyNested", SupportLegacyBean.class.getMethod("readLegacyNested"), EventPropertyType.SIMPLE));

        expected.add(new InternalEventPropDescriptor("x", "x", SupportLegacyBean.class.getField("fieldNested"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("y", "y", SupportLegacyBean.class.getMethod("readLegacyBeanVal"), EventPropertyType.SIMPLE));
        ArrayAssertionUtil.assertEqualsAnyOrder(expected.toArray(), descList.toArray());
    }

    private static Log log = LogFactory.getLog(TestPropertyListBuilderPublic.class);
}
