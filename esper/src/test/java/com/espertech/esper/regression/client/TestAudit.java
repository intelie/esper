package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.core.EPRuntimeImpl;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.bean.SupportBean_ST1;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.AuditPath;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;

public class TestAudit extends TestCase {

    private static final Log log = LogFactory.getLog(EPRuntimeImpl.class);
    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("SupportBean", SupportBean.class);
        configuration.addEventType("SupportBean_ST0", SupportBean_ST0.class);
        configuration.addEventType("SupportBean_ST1", SupportBean_ST1.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testDocSample() {
        epService.getEPAdministrator().createEPL("create schema OrderEvent(price double)");

        String epl = "@Name('All-Order-Events') @Audit('stream,property') select price from OrderEvent";
        epService.getEPAdministrator().createEPL(epl).addListener(listener);
        epService.getEPRuntime().sendEvent(Collections.singletonMap("price", 100d), "OrderEvent");
    }

    public void testAudit() {

        // stream
        auditLog.info("*** Stream: ");
        EPStatement stmtInput = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('stream') select * from SupportBean(string = 'E1')");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        stmtInput.destroy();
        
        // named window stream: this is not yet enabled
        EPStatement stmtNW = epService.getEPAdministrator().createEPL("create window WinOne.win:keepall() as SupportBean");
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into WinOne select * from SupportBean");
        EPStatement stmtConsume = epService.getEPAdministrator().createEPL("@Audit select * from WinOne");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        stmtNW.destroy();
        stmtInsert.destroy();
        stmtConsume.destroy();

        auditLog.info("*** Schedule: ");
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        EPStatement stmtSchedule = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('schedule') select irstream * from SupportBean.win:time(1 sec)");
        stmtSchedule.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        listener.reset();
        log.info("Sending time");
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2000));
        assertTrue(listener.isInvoked());
        listener.reset();
        stmtSchedule.destroy();

        // exprdef-instances
        auditLog.info("*** Expression-Def: ");
        EPStatement stmtExprDef = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('exprdef') " +
                "expression DEF { 1 } " +
                "expression INN {  x => x.string }" +
                "expression OUT { x => INN(x) } " +
                "select DEF(), OUT(sb) from SupportBean sb");
        stmtExprDef.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(1, listener.assertOneGetNewAndReset().get("DEF()"));
        stmtExprDef.destroy();

        // pattern-instances
        auditLog.info("*** Pattern-Lifecycle: ");
        EPStatement stmtPatternLife = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('pattern-instances') select a.intPrimitive as val0 from pattern [every a=SupportBean -> (b=SupportBean_ST0 and not SupportBean_ST1)]");
        stmtPatternLife.addListener(listener);
        log.info("Sending E1");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        log.info("Sending E2");
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        log.info("Sending E3");
        epService.getEPRuntime().sendEvent(new SupportBean_ST1("E3", 3));
        stmtPatternLife.destroy();

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
        EPStatement stmtExpr = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('expression') select intPrimitive*100 as val0, sum(intPrimitive) as val1 from SupportBean");
        stmtExpr.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        assertEquals(5000, listener.assertOneGetNew().get("val0"));
        assertEquals(50, listener.assertOneGetNewAndReset().get("val1"));
        stmtExpr.destroy();

        // expression-detail
        auditLog.info("*** Expression-Nested: ");
        EPStatement stmtExprNested = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('expression-nested') select ('A'||string)||'X' as val0 from SupportBean");
        stmtExprNested.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        assertEquals("AE1X", listener.assertOneGetNewAndReset().get("val0"));
        stmtExprNested.destroy();

        // property
        auditLog.info("*** Property: ");
        EPStatement stmtProp = epService.getEPAdministrator().createEPL("@Name('ABC') @Audit('property') select intPrimitive from SupportBean");
        stmtProp.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        assertEquals(50, listener.assertOneGetNewAndReset().get("intPrimitive"));
        stmtProp.destroy();
    }
}
