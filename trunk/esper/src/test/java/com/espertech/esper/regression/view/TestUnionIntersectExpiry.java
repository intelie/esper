package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.event.EventBean;

import java.util.Iterator;

public class TestUnionIntersectExpiry extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    // TODO: test allow multiple expiry config

    public void init(boolean isAllowMultipleDataWindows)
    {
        listener = new SupportUpdateListener();

        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(isAllowMultipleDataWindows);

        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("SupportBean", SupportBean.class);
    }

    // TODO: test group-by
    // TODO: same event entering and leaving all windows
    // TODO: named window delete event
    // TODO: revision event support
    // TODO: multiple data windows not just 2
    // TODO: subselect test
    // TODO: invalid tests: no views specified; derived-value views
    public void testUnion()
    {
        init(false);
        String[] fields = new String[] {"string"};

        EPStatement stmt = epService.getEPAdministrator().createEPL("select string from SupportBean.std:unique(intPrimitive).std:unique(intBoxed) retain-union");
        stmt.addListener(listener);
        
        sendEvent("E1", 1, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendEvent("E2", 2, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2"});

        sendEvent("E3", 1, 20);
        Object result = ArrayAssertionUtil.iteratorToArray(stmt.iterator());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2", "E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3"});

        sendEvent("E4", 1, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2", "E3", "E4"));
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"E4"});
        listener.reset();
    }

    private void sendEvent(String string, int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private Object[][] toArr(String ...values)
    {
        Object[][] arr = new Object[values.length][];
        for (int i = 0; i < values.length; i++)
        {
            arr[i] = new Object[] {values[i]};
        }
        return arr;
    }

    public void testInvalid()
    {
        init(false);
        String text = "select string from SupportBean.std:unique(string).std:unique(intPrimitive)";
        tryInvalid(text, "Error starting view: Multiple data window views are not allowed by default configuration, please use one of the retain keywords or the change configuration [select string from SupportBean.std:unique(string).std:unique(intPrimitive)]");
    }

    private void tryInvalid(String text, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(text);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }
}