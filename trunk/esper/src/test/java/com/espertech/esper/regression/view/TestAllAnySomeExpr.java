package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBeanArrayCollMap;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.util.SerializableObjectCopier;

public class TestAllAnySomeExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    // TODO:
    // - test invalid when array selected as part of in (provide hint that ANY/SOME/ALL should be used instead)
    // - ANY
    //   Subselect+ANY/SOME:  SELECT s1 FROM t1 WHERE s1 > ANY (SELECT s1 FROM t2);
    //   Value+ANY/SOME:      select intValue = ANY (1, 2, 3)  (same as IN, also test >, <, <=, >=, !=, <>)
    //   Array+ANY/SOME:      select intArray = ANY (1, 2, 3, {3,4})  (also test !=; also test {1, 2}; invalid: >, <, >=, <=)
    // - ALL

    public void testSubselectAny()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String stmtText = "select intPrimitive = all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as result from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // TODO: confirm no rows returns false
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        assertFalse((Boolean) listener.assertOneGetNewAndReset().get("result"));

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        assertTrue((Boolean) listener.assertOneGetNewAndReset().get("result"));
    }

    public void testArrayProperty()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("ArrayBean", SupportBeanArrayCollMap.class);

        String stmtText = "select intArr = (1, 2, 3) as result from ArrayBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanArrayCollMap(new int[] {4, 5, 3}));
        assertTrue((Boolean) listener.assertOneGetNewAndReset().get("result"));
    }
}
