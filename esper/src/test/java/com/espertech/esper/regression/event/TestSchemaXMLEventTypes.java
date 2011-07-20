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

package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.event.EventTypeAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestSchemaXMLEventTypes extends TestCase
{
    private static String CLASSLOADER_SCHEMA_URI = "regression/typeTestSchema.xsd";

    private EPServiceProvider epService;

    public void testSchemaXMLTypes() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("typesEvent");
        String schemaUri = TestSchemaXMLEvent.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
        eventTypeMeta.setSchemaResource(schemaUri);
        configuration.addEventType("TestTypesEvent", eventTypeMeta);

        epService = EPServiceProviderManager.getProvider("TestSchemaXML", configuration);
        epService.initialize();

        String stmtSelectWild = "select * from TestTypesEvent";
        EPStatement wildStmt = epService.getEPAdministrator().createEPL(stmtSelectWild);
        EventType type = wildStmt.getEventType();
        EventTypeAssertionUtil.assertConsistency(type);

        Object[][] types = new Object[][] {
                {"attrNonPositiveInteger", Integer.class},
                {"attrNonNegativeInteger", Integer.class},
                {"attrNegativeInteger", Integer.class},
                {"attrPositiveInteger", Integer.class},
                {"attrLong", Long.class},
                {"attrUnsignedLong", Long.class},
                {"attrInt", Integer.class},
                {"attrUnsignedInt", Integer.class},
                {"attrDecimal", Double.class},
                {"attrInteger", Integer.class},
                {"attrFloat", Float.class},
                {"attrDouble", Double.class},
                {"attrString", String.class},
                {"attrShort", Short.class},
                {"attrUnsignedShort", Short.class},
                {"attrByte", Byte.class},
                {"attrUnsignedByte", Byte.class},
                {"attrBoolean", Boolean.class},
                {"attrDateTime", String.class},
                {"attrDate", String.class},
                {"attrTime", String.class}};
        
        for (int i = 0; i < types.length; i++)
        {
            String name = types[i][0].toString();
            EventPropertyDescriptor desc = type.getPropertyDescriptor(name);
            Class expected = (Class) types[i][1];
            assertEquals("Failed for " + name, expected, desc.getPropertyType());
        }
    }

    private static final Log log = LogFactory.getLog(TestSchemaXMLEvent.class);
}