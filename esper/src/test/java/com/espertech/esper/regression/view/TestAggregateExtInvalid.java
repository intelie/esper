package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
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
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        tryInvalid("select rate(10) from SupportBean",
                "Error starting statement: Unknown single-row function, aggregation function or mapped or indexed property named 'rate' could not be resolved [select rate(10) from SupportBean]");
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