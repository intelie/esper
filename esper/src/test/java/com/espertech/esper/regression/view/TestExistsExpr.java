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

package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.SerializableObjectCopier;

public class TestExistsExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testExistsSimple()
    {
        String stmtText = "select exists(string) as t0, " +
                          " exists(intBoxed?) as t1, " +
                          " exists(dummy?) as t2, " +
                          " exists(intPrimitive?) as t3, " +
                          " exists(intPrimitive) as t4 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(stmtText);
        selectTestCase.addListener(listener);

        for (int i = 0; i < 5; i++)
        {
            assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t" + i));            
        }

        SupportBean bean = new SupportBean("abc", 100);
        bean.setFloatBoxed(9.5f);
        bean.setIntBoxed(3);
        epService.getEPRuntime().sendEvent(bean);
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {true, true, false, true, true});
    }

    public void testExistsInner()
    {
        String stmtText = "select exists(item?.id) as t0, " +
                          " exists(item?.id?) as t1, " +
                          " exists(item?.item.intBoxed) as t2, " +
                          " exists(item?.indexed[0]?) as t3, " +
                          " exists(item?.mapped('keyOne')?) as t4, " +
                          " exists(item?.nested?) as t5, " +
                          " exists(item?.nested.nestedValue?) as t6, " +
                          " exists(item?.nested.nestedNested?) as t7, " +
                          " exists(item?.nested.nestedNested.nestedNestedValue?) as t8, " +
                          " exists(item?.nested.nestedNested.nestedNestedValue.dummy?) as t9, " +
                          " exists(item?.nested.nestedNested.dummy?) as t10 " +
                          " from " + SupportMarkerInterface.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(stmtText);
        selectTestCase.addListener(listener);

        for (int i = 0; i < 11; i++)
        {
            assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t" + i));
        }

        // cannot exists if the inner is null
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, false, false, false, false, false, false, false, false, false});

        // try nested, indexed and mapped
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(SupportBeanComplexProps.makeDefaultBean()));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, false, true, true, true, true, true, true, false, false});

        // try nested, indexed and mapped
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(SupportBeanComplexProps.makeDefaultBean()));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, false, true, true, true, true, true, true, false, false});

        // try a boxed that returns null but does exists
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBeanDynRoot(new SupportBean())));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, true, false, false, false, false, false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean_A("10")));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {true, true, false, false, false, false, false, false, false, false, false});
    }

    public void testCastDoubleAndNull_OM() throws Exception
    {
        String stmtText = "select exists(item?.intBoxed) as t0 " +
                          "from " + SupportMarkerInterface.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create().add(Expressions.existsProperty("item?.intBoxed"), "t0"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarkerInterface.class.getName())));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEPL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(listener);

        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean()));
        assertEquals(true, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot("abc"));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));
    }

    public void testCastStringAndNull_Compile() throws Exception
    {
        String stmtText = "select exists(item?.intBoxed) as t0 " +
                          "from " + SupportMarkerInterface.class.getName();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEPL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(listener);

        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean()));
        assertEquals(true, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot("abc"));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));
    }

    private void assertResults(EventBean event, boolean[] result)
    {
        for (int i = 0; i < result.length; i++)
        {
            assertEquals("failed for index " + i, result[i], event.get("t" + i));
        }
    }
}
