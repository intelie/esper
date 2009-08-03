package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestSubselectOrderOfEval extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void testOrderOfEvaluationSubselectFirst()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getExpression().setSelfSubselectPreeval(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String viewExpr = "select * from SupportBean(intPrimitive<10) where intPrimitive not in (select intPrimitive from SupportBean.std:unique(intPrimitive))";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(viewExpr);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertFalse(listener.getAndClearIsInvoked());

        stmtOne.destroy();

        String viewExprTwo = "select * from SupportBean where intPrimitive not in (select intPrimitive from SupportBean(intPrimitive<10).std:unique(intPrimitive))";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(viewExprTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertFalse(listener.getAndClearIsInvoked());
    }

    public void testOrderOfEvaluationSubselectLast()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getExpression().setSelfSubselectPreeval(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String viewExpr = "select * from SupportBean(intPrimitive<10) where intPrimitive not in (select intPrimitive from SupportBean.std:unique(intPrimitive))";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(viewExpr);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertTrue(listener.getAndClearIsInvoked());

        stmtOne.destroy();

        String viewExprTwo = "select * from SupportBean where intPrimitive not in (select intPrimitive from SupportBean(intPrimitive<10).std:unique(intPrimitive))";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(viewExprTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertTrue(listener.getAndClearIsInvoked());
    }
}