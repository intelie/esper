/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.message.fix;

import junit.framework.TestCase;

import java.util.Map;
import java.util.LinkedHashMap;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.MapEventType;
import com.espertech.esper.event.MapEventBean;

public class TestFixMsgMarshaller extends TestCase
{
    private final String SAMPLE = "8=FIX4.2<SOH>9=114<SOH>34=251<SOH>35=S<SOH>49=Broker<SOH>52=2000072814:06:22<SOH>55=AA<SOH>56=Institution" +
            "<SOH>117=XXX<SOH>131=YYY<SOH>132=5.0<SOH>200=199901<SOH>201=1<SOH>202=25.0<SOH>10=103<SOH>";

    public void testMarshal() throws Exception
    {
        Map<String, Object> fixFields = new LinkedHashMap<String, Object>();
        fixFields.put("35", "S");
        fixFields.put("49", "Broker");
        fixFields.put("56", "Institution");
        fixFields.put("34", 251);
        fixFields.put("52", "2000072814:06:22");
        fixFields.put("117", "XXX");
        fixFields.put("131", "YYY");
        fixFields.put("55", "AA");
        fixFields.put("200", 199901);
        fixFields.put("202", 25.0);
        fixFields.put("201", 1);
        fixFields.put("132", 5.0);
        fixFields.put("131", "YYY");

        Map<String, Object> properties = new LinkedHashMap<String, Object>();
        for (int i = 1; i < 1000; i++)
        {
            properties.put(Integer.toString(i), String.class);
        }
        EventType eventType = new MapEventType(null, "fixtest", null, properties, null, null);
        EventBean event = new MapEventBean(fixFields, eventType);

        String msg = FixMsgMarshaller.marshalFix(event);
        String sample = TestFixMsgParser.replaceSOH(SAMPLE);
        assertEquals(sample, msg);

        // test parse
        Map<String, String> result = FixMsgParser.parse(msg);
        for (Map.Entry<String, Object> entry : fixFields.entrySet())
        {
            String value = result.get(entry.getKey());
            assertEquals(value.toString(), entry.getValue().toString());
        }
    }
}
