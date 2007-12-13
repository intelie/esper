package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean_S1;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

public class TestEQLComments extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testComment()
    {
        String lineSeparator = System.getProperty("line.separator");
        String statement = "select string, /* this is my string */\n" +
                "intPrimitive, // same line comment\n" +
                "/* comment taking one line */\n" +
                "// another comment taking a line\n" +
                "intPrimitive as /* rename */ myPrimitive\n" +
                "from " + SupportBean.class.getName() + lineSeparator +
                " where /* inside a where */ intPrimitive /* */ = /* */ 100";

        EPStatement stmt = epService.getEPAdministrator().createEQL(statement);
        stmt.addListener(updateListener);

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 100));

        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("e1", event.get("string"));
        assertEquals(100, event.get("intPrimitive"));
        assertEquals(100, event.get("myPrimitive"));
        updateListener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("e1", -1));
        assertFalse(updateListener.getAndClearIsInvoked());
    }
}
