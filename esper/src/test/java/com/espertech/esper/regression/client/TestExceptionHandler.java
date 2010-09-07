package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.hook.ExceptionHandler;
import com.espertech.esper.client.hook.ExceptionHandlerContext;
import com.espertech.esper.client.hook.ExceptionHandlerFactoryContext;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.StatementLifecycleEvent;
import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.*;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class TestExceptionHandler extends TestCase
{
    private EPServiceProvider epService;

    public void testHandler()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        // add same factory twice
        config.getEngineDefaults().getExceptionHandling().addClass(SupportExceptionHandlerFactory.class);
        config.getEngineDefaults().getExceptionHandling().addClass(SupportExceptionHandlerFactory.class);
        config.addEventType("SupportBean", SupportBean.class);
        config.addPlugInAggregationFunction("myinvalidagg", InvalidAggTest.class.getName());

        epService = EPServiceProviderManager.getDefaultProvider(config);
        SupportExceptionHandlerFactory.getFactoryContexts().clear();
        SupportExceptionHandlerFactory.getHandlers().clear();
        epService.initialize();

        String epl = "@Name('ABCName') select myinvalidagg() from SupportBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);

        List<ExceptionHandlerFactoryContext> contexts = SupportExceptionHandlerFactory.getFactoryContexts();
        assertEquals(2, contexts.size());
        assertEquals(epService.getURI(), contexts.get(0).getEngineURI());
        assertEquals(epService.getURI(), contexts.get(1).getEngineURI());

        SupportExceptionHandlerFactory.SupportExceptionHandler handlerOne = SupportExceptionHandlerFactory.getHandlers().get(0);
        SupportExceptionHandlerFactory.SupportExceptionHandler handlerTwo = SupportExceptionHandlerFactory.getHandlers().get(1);
        epService.getEPRuntime().sendEvent(new SupportBean());

        assertEquals(1, handlerOne.getContexts().size());
        assertEquals(1, handlerTwo.getContexts().size());
        ExceptionHandlerContext ehc = handlerOne.getContexts().get(0);
        assertEquals(epService.getURI(), ehc.getEngineURI());
        assertEquals(epl, ehc.getEpl());
        assertEquals("ABCName", ehc.getStatementName());
        assertEquals("Sample exception", ehc.getThrowable().getMessage());
    }

    public static class InvalidAggTest extends AggregationSupport {

        @Override
        public void validate(Class childNodeType) {
        }

        @Override
        public void enter(Object value) {
            throw new RuntimeException("Sample exception");
        }

        @Override
        public void leave(Object value) {
        }

        @Override
        public Object getValue() {
            return null;
        }

        @Override
        public Class getValueType() {
            return null;
        }

        @Override
        public void clear() {
        }
    }

}