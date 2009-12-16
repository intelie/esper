package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.TimerTask;

public class TestAggregateExpandedInvalid extends TestCase {

    private EPServiceProvider epService;

    public void testInvalid()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getExpression().setExpandedAggregation(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        tryInvalid("select rate(10) from SupportBean",
                "Error in expression: Unknown method named 'rate' could not be resolved [select rate(10) from SupportBean]");
        tryInvalid("select last() from SupportBean",
                "");
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }
}