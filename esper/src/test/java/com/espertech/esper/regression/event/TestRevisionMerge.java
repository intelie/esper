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
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class TestRevisionMerge extends TestCase
{
    private static final Log log = LogFactory.getLog(TestRevisionMerge.class);
    private EPServiceProvider epService;
    private SupportUpdateListener listenerOne;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerOne = new SupportUpdateListener();
    }

    public void testMergeDeclared()
    {
        Map<String, Object> fullType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pf", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("FullType", fullType);

        Map<String, Object> deltaType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pd", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("DeltaType", deltaType);

        ConfigurationRevisionEventType revEvent = new ConfigurationRevisionEventType();
        revEvent.addNameBaseEventType("FullType");
        revEvent.addNameDeltaEventType("DeltaType");
        revEvent.setPropertyRevision(ConfigurationRevisionEventType.PropertyRevision.MERGE_DECLARED);
        revEvent.setKeyPropertyNames(new String[] {"p1"});
        epService.getEPAdministrator().getConfiguration().addRevisionEventType("MyExistsRevision", revEvent);

        epService.getEPAdministrator().createEPL("create window MyWin.win:time(10 sec) as select * from MyExistsRevision");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from FullType");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from DeltaType");

        String[] fields = "p1,p2,p3,pf,pd".split(",");
        EPStatement consumerOne = epService.getEPAdministrator().createEPL("select irstream * from MyWin");
        consumerOne.addListener(listenerOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(consumerOne.getEventType().getPropertyNames(), fields);

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf","10,20,30,f0"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"10", "20", "30", "f0", null});

        epService.getEPRuntime().sendEvent(makeMap("p1,p2","10,21"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "20", "30", "f0", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", null, "f0", null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pf","10,32,f1"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", null, "f0", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, "32", "f1", null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd","10,33,pd3"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", null, "32", "f1", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, "33", "f1", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf","10,22,34,f2"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", null, "33", "f1", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1","10"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, null, null, "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf,pd", "10,23,35,pfx,pd4"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", null, null, null, "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "23", "35", null, "pd4"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2", "10,null"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "23", "35", null, "pd4"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, null, null, null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd,pf","10,36,pdx,f4"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", null, null, null, null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, "36", "f4", null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd", "10,null,pd5"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", null, "36", "f4", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, null, "f4", "pd5"});
        listenerOne.reset();
    }

    public void testMergeNonNull()
    {
        Map<String, Object> fullType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pf", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("FullType", fullType);

        Map<String, Object> deltaType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pd", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("DeltaType", deltaType);

        ConfigurationRevisionEventType revEvent = new ConfigurationRevisionEventType();
        revEvent.addNameBaseEventType("FullType");
        revEvent.addNameDeltaEventType("DeltaType");
        revEvent.setPropertyRevision(ConfigurationRevisionEventType.PropertyRevision.MERGE_NON_NULL);
        revEvent.setKeyPropertyNames(new String[] {"p1"});
        epService.getEPAdministrator().getConfiguration().addRevisionEventType("MyExistsRevision", revEvent);

        epService.getEPAdministrator().createEPL("create window MyWin.win:time(10 sec) as select * from MyExistsRevision");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from FullType");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from DeltaType");

        String[] fields = "p1,p2,p3,pf,pd".split(",");
        EPStatement consumerOne = epService.getEPAdministrator().createEPL("select irstream * from MyWin");
        consumerOne.addListener(listenerOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(consumerOne.getEventType().getPropertyNames(), fields);

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf","10,20,30,f0"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"10", "20", "30", "f0", null});

        epService.getEPRuntime().sendEvent(makeMap("p1,p2","10,21"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "20", "30", "f0", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", "30", "f0", null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pf","10,32,f1"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", "30", "f0", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", "32", "f1", null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd","10,33,pd3"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", "32", "f1", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", "33", "f1", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf","10,22,34,f2"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", "33", "f1", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1","10"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf,pd", "10,23,35,pfx,pd4"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "23", "35", "f2", "pd4"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2", "10,null"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "23", "35", "f2", "pd4"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "23", "35", "f2", "pd4"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd,pf","10,36,pdx,f4"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "23", "35", "f2", "pd4"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "23", "36", "f4", "pd4"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd", "10,null,pd5"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "23", "36", "f4", "pd4"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "23", "36", "f4", "pd5"});
        listenerOne.reset();
    }

    public void testMergeExists()
    {
        Map<String, Object> fullType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pf", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("FullType", fullType);

        Map<String, Object> deltaType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pd", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("DeltaType", deltaType);

        ConfigurationRevisionEventType revEvent = new ConfigurationRevisionEventType();
        revEvent.addNameBaseEventType("FullType");
        revEvent.addNameDeltaEventType("DeltaType");
        revEvent.setPropertyRevision(ConfigurationRevisionEventType.PropertyRevision.MERGE_EXISTS);
        revEvent.setKeyPropertyNames(new String[] {"p1"});
        epService.getEPAdministrator().getConfiguration().addRevisionEventType("MyExistsRevision", revEvent);

        epService.getEPAdministrator().createEPL("create window MyWin.win:time(10 sec) as select * from MyExistsRevision");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from FullType");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from DeltaType");

        String[] fields = "p1,p2,p3,pf,pd".split(",");
        EPStatement consumerOne = epService.getEPAdministrator().createEPL("select irstream * from MyWin");
        consumerOne.addListener(listenerOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(consumerOne.getEventType().getPropertyNames(), fields);

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf","10,20,30,f0"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"10", "20", "30", "f0", null});

        epService.getEPRuntime().sendEvent(makeMap("p1,p2","10,21"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "20", "30", "f0", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", "30", "f0", null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pf","10,32,f1"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", "30", "f0", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", "32", "f1", null});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd","10,33,pd3"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", "32", "f1", null});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", "33", "f1", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf","10,22,34,f2"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", "33", "f1", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1","10"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf,pd", "10,23,35,pfx,pd4"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "22", "34", "f2", "pd3"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "23", "35", "f2", "pd4"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p2", "10,null"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "23", "35", "f2", "pd4"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, "35", "f2", "pd4"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd,pf","10,36,pdx,f4"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", null, "35", "f2", "pd4"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, "36", "f4", "pd4"});
        listenerOne.reset();

        epService.getEPRuntime().sendEvent(makeMap("p1,p3,pd", "10,null,pd5"), "DeltaType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", null, "36", "f4", "pd4"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", null, null, "f4", "pd5"});
        listenerOne.reset();
    }

    public void testNestedPropertiesNoDelta()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("Nested", SupportBeanComplexProps.class);

        ConfigurationRevisionEventType revEvent = new ConfigurationRevisionEventType();
        revEvent.addNameBaseEventType("Nested");
        revEvent.setPropertyRevision(ConfigurationRevisionEventType.PropertyRevision.MERGE_DECLARED);
        revEvent.setKeyPropertyNames(new String[] {"simpleProperty"});
        epService.getEPAdministrator().getConfiguration().addRevisionEventType("NestedRevision", revEvent);

        epService.getEPAdministrator().createEPL("create window MyWin.win:time(10 sec) as select * from NestedRevision");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from Nested");

        String[] fields = "key,f1".split(",");
        String stmtText = "select irstream simpleProperty as key, nested.nestedValue as f1 from MyWin";
        EPStatement consumerOne = epService.getEPAdministrator().createEPL(stmtText);
        consumerOne.addListener(listenerOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(consumerOne.getEventType().getPropertyNames(), fields);

        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"simple", "nestedValue"});

        SupportBeanComplexProps bean = SupportBeanComplexProps.makeDefaultBean();
        bean.getNested().setNestedValue("val2");
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"simple", "nestedValue"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"simple", "val2"});
        listenerOne.reset();
    }

    private Map<String, Object> makeMap(Object[][] entries)
    {
        Map result = new HashMap<String, Object>();
        for (int i = 0; i < entries.length; i++)
        {
            result.put(entries[i][0], entries[i][1]);
        }
        return result;
    }

    private Map<String, Object> makeMap(String keysList, String valuesList)
    {
        String[] keys = keysList.split(",");
        String[] values = valuesList.split(",");

        Map result = new HashMap<String, Object>();
        for (int i = 0; i < keys.length; i++)
        {
            if (values[i].equals("null"))
            {
                result.put(keys[i], null);
            }
            else
            {
                result.put(keys[i], values[i]);
            }
        }
        return result;
    }
}
