package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestAudit extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    // TODO: @Audit("property, expression, view, pattern, dot, subquery, output, input, namedwindow, lookup, aggregate, sql, methodjoin, matchrecognize, expressiondef, lambda")
    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testAudit() {

        /**
         * TODO
        // property
        EPStatement stmtOne = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit select intPrimitive from SupportBean");
        stmtOne.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        stmtOne.destroy();
         */

        // expression
        epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('expr') select intPrimitive*100 as val0 from SupportBean").addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
    }
}
