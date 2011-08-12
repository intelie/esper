/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event.bean;

import com.espertech.esper.client.ConfigurationEventTypeLegacy;
import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.event.bean.InternalEventPropDescriptor;
import com.espertech.esper.event.EventPropertyType;
import com.espertech.esper.event.bean.PropertyListBuilderExplicit;
import com.espertech.esper.support.bean.SupportLegacyBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.List;
import java.util.LinkedList;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPropertyListBuilderExplicit extends TestCase
{
    private PropertyListBuilderExplicit builder;

    public void setUp()
    {
        ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
        config.addFieldProperty("f_legVal", "fieldLegacyVal");
        config.addFieldProperty("f_strArr", "fieldStringArray");
        config.addFieldProperty("f_strMap", "fieldMapped");
        config.addFieldProperty("f_legNested", "fieldNested");

        config.addMethodProperty("m_legVal", "readLegacyBeanVal");
        config.addMethodProperty("m_strArr", "readStringArray");
        config.addMethodProperty("m_strInd", "readStringIndexed");
        config.addMethodProperty("m_strMapKeyed", "readMapByKey");
        config.addMethodProperty("m_strMap", "readMap");
        config.addMethodProperty("m_legNested", "readLegacyNested");

        builder = new PropertyListBuilderExplicit(config);
    }

    public void testBuildPropList() throws Exception
    {
        List<InternalEventPropDescriptor> descList =  builder.assessProperties(SupportLegacyBean.class);

        List<InternalEventPropDescriptor> expected = new LinkedList<InternalEventPropDescriptor>();
        expected.add(new InternalEventPropDescriptor("f_legVal", SupportLegacyBean.class.getField("fieldLegacyVal"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("f_strArr", SupportLegacyBean.class.getField("fieldStringArray"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("f_strMap", SupportLegacyBean.class.getField("fieldMapped"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("f_legNested", SupportLegacyBean.class.getField("fieldNested"), EventPropertyType.SIMPLE));

        expected.add(new InternalEventPropDescriptor("m_legVal", SupportLegacyBean.class.getMethod("readLegacyBeanVal"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("m_strArr", SupportLegacyBean.class.getMethod("readStringArray"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("m_strInd", SupportLegacyBean.class.getMethod("readStringIndexed", new Class[] {int.class}), EventPropertyType.INDEXED));
        expected.add(new InternalEventPropDescriptor("m_strMapKeyed", SupportLegacyBean.class.getMethod("readMapByKey", new Class[] {String.class}), EventPropertyType.MAPPED));
        expected.add(new InternalEventPropDescriptor("m_strMap", SupportLegacyBean.class.getMethod("readMap"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("m_legNested", SupportLegacyBean.class.getMethod("readLegacyNested"), EventPropertyType.SIMPLE));

        ArrayAssertionUtil.assertEqualsAnyOrder(expected.toArray(), descList.toArray());
    }

    public void testInvalid()
    {
        tryInvalidField("x", SupportBean.class);
        tryInvalidField("intPrimitive", SupportBean.class);

        tryInvalidMethod("x", SupportBean.class);
        tryInvalidMethod("intPrimitive", SupportBean.class);
    }

    private void tryInvalidMethod(String methodName, Class clazz)
    {
        ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
        config.addMethodProperty("name", methodName);
        builder = new PropertyListBuilderExplicit(config);

        try
        {
            builder.assessProperties(clazz);
        }
        catch (ConfigurationException ex)
        {
            // expected
            log.debug(ex);
        }
    }

    private void tryInvalidField(String fieldName, Class clazz)
    {
        ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
        config.addFieldProperty("name", fieldName);
        builder = new PropertyListBuilderExplicit(config);

        try
        {
            builder.assessProperties(clazz);
        }
        catch (ConfigurationException ex)
        {
            // expected
            log.debug(ex);
        }
    }

    private static Log log = LogFactory.getLog(TestPropertyListBuilderExplicit.class);
}
