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

import junit.framework.TestCase;
import com.espertech.esper.client.ConfigurationEventTypeLegacy;
import com.espertech.esper.event.bean.InternalEventPropDescriptor;
import com.espertech.esper.event.EventPropertyType;
import com.espertech.esper.event.bean.PropertyListBuilderJavaBean;
import com.espertech.esper.support.bean.SupportLegacyBean;
import com.espertech.esper.support.util.ArrayAssertionUtil;

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
        List<InternalEventPropDescriptor> descList = builder.assessProperties(SupportLegacyBean.class);

        List<InternalEventPropDescriptor> expected = new LinkedList<InternalEventPropDescriptor>();
        expected.add(new InternalEventPropDescriptor("x", SupportLegacyBean.class.getField("fieldNested"), EventPropertyType.SIMPLE));
        expected.add(new InternalEventPropDescriptor("y", SupportLegacyBean.class.getMethod("readLegacyBeanVal"), EventPropertyType.SIMPLE));
        ArrayAssertionUtil.assertEqualsAnyOrder(expected.toArray(), descList.toArray());
    }

    private static Log log = LogFactory.getLog(TestPropertyListBuilderJavaBean.class);
}
