package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestAggregateExtInvalid extends TestCase {

    private EPServiceProvider epService;

    public void testInvalid()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getExpression().setExtendedAggregation(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        tryInvalid("select rate(10) from SupportBean",
                "Error in expression: Unknown single-row function or aggregation function named 'rate' could not be resolved [select rate(10) from SupportBean]");
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