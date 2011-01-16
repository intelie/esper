package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.client.hook.ConditionHandlerContext;
import com.espertech.esper.client.hook.ConditionHandlerFactoryContext;
import com.espertech.esper.client.hook.ConditionPatternSubexpressionMax;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.core.EPStatementSPI;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConditionHandlerFactory;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestFollowedByMaxOperator extends TestCase implements SupportBeanConstants
{
    // TODO test timer:within expiring subnodes (permanently false)
    // TODO test node quit behavior
    // TODO separate followed-by state node
    // TODO test mixed form "A -> B -[20]> C -> D"
    // TODO EHA catch up
    // TODO test invalid: expression contains properties

    public void testSingleMaxSimple()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean_A", SupportBean_A.class);
        config.addEventType("SupportBean_B", SupportBean_B.class);
        config.getEngineDefaults().getConditionHandling().addClass(SupportConditionHandlerFactory.class);
        
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        ConditionHandlerFactoryContext context = SupportConditionHandlerFactory.getFactoryContexts().get(0);
        assertEquals(epService.getURI(), context.getEngineURI());
        SupportConditionHandlerFactory.SupportConditionHandler handler = SupportConditionHandlerFactory.getLastHandler();

        String expression = "select a.id as a, b.id as b from pattern [every a=SupportBean_A -[2]> b=SupportBean_B]";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        runAssertion(epService, statement, handler);
        statement.destroy();

        // test SODA
        EPStatementObjectModel model =  epService.getEPAdministrator().compileEPL(expression);
        assertEquals(expression, model.toEPL());
        statement = epService.getEPAdministrator().create(model);
        assertEquals(statement.getText(), model.toEPL());
        runAssertion(epService, statement, handler);
        statement.destroy();
        
        // test variable
        epService.getEPAdministrator().createEPL("create variable int myvar=3");
        expression = "select a.id as a, b.id as b from pattern [every a=SupportBean_A -[myvar-1]> b=SupportBean_B]";
        statement = epService.getEPAdministrator().createEPL(expression);
        runAssertion(epService, statement, handler);
    }

    private void runAssertion(EPServiceProvider epService, EPStatement stmt, SupportConditionHandlerFactory.SupportConditionHandler handler) {

        String fields[] = new String[] {"a", "b"};
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));

        handler.getContexts().clear();
        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        assertContext(epService, stmt, handler.getContexts());

        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A1","B1"}, {"A2","B1"}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("A4"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B2"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A4","B2"}});
        assertTrue(handler.getContexts().isEmpty());

        for (int i = 5; i < 9; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean_A("A" + i));
            if (i >= 7) {
                assertContext(epService, stmt, handler.getContexts());
            }
        }

        epService.getEPRuntime().sendEvent(new SupportBean_B("B3"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A5","B3"}, {"A6","B3"}});

        epService.getEPRuntime().sendEvent(new SupportBean_B("B4"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_A("A20"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A21"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B5"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A20","B5"}, {"A21","B5"}});
        assertTrue(handler.getContexts().isEmpty());
    }

    private void assertContext(EPServiceProvider epService, EPStatement stmt, List<ConditionHandlerContext> contexts) {
        assertEquals(1, contexts.size());
        ConditionHandlerContext context = contexts.get(0);
        assertEquals(epService.getURI(), context.getEngineURI());
        assertEquals(stmt.getText(), context.getEpl());
        assertEquals(stmt.getName(), context.getStatementName());
        ConditionPatternSubexpressionMax max = (ConditionPatternSubexpressionMax) context.getEngineCondition();
        assertEquals(2, max.getMax());
        contexts.clear();
    }

    private static Log log = LogFactory.getLog(TestFollowedByMaxOperator.class);
}
