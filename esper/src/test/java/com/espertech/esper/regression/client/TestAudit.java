package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.AuditPath;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestAudit extends TestCase {

    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("SupportBean", SupportBean.class);
        configuration.addEventType("SupportBean_ST0", SupportBean_ST0.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testAudit() {

        // pattern
        auditLog.info("*** Pattern: ");
        EPStatement stmtPattern = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('pattern') select a.intPrimitive as val0 from pattern [a=SupportBean -> b=SupportBean_ST0]");
        stmtPattern.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E2", 2));
        assertEquals(1, listener.assertOneGetNewAndReset().get("val0"));
        stmtPattern.destroy();

        // view
        auditLog.info("*** View: ");
        EPStatement stmtView = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('view') select intPrimitive from SupportBean.std:lastevent()");
        stmtView.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        assertEquals(50, listener.assertOneGetNewAndReset().get("intPrimitive"));
        stmtView.destroy();

        // expression
        auditLog.info("*** Expression: ");
        EPStatement stmtExpr = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('expr') select intPrimitive*100 as val0, sum(intPrimitive) as val1 from SupportBean");
        stmtExpr.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        assertEquals(5000, listener.assertOneGetNew().get("val0"));
        assertEquals(50, listener.assertOneGetNewAndReset().get("val1"));
        stmtExpr.destroy();

        // property
        auditLog.info("*** Property: ");
        EPStatement stmtProp = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('property') select intPrimitive from SupportBean");
        stmtProp.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        assertEquals(50, listener.assertOneGetNewAndReset().get("intPrimitive"));
        stmtProp.destroy();
    }
}
