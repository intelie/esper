package net.esper.regression.pattern;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_B;
import net.esper.support.bean.SupportBean_C;
import net.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TestDeadPattern extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("A", SupportBean_A.class.getName());
        config.addEventTypeAlias("B", SupportBean_B.class.getName());
        config.addEventTypeAlias("C", SupportBean_C.class.getName());

        epService = EPServiceProviderManager.getProvider("TestDeadPattern", config);
        epService.initialize();
    }

    public void testDeadPattern()
    {
        String pattern = "(A() -> B()) and not C()";
        // Adjust to 20000 to better test the limit
        for (int i = 0; i < 1000; i++)
        {
            epService.getEPAdministrator().createPattern(pattern);
        }

        epService.getEPRuntime().sendEvent(new SupportBean_C("C1"));

        long startTime = System.currentTimeMillis();
        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        long delta =  System.currentTimeMillis() - startTime;

        log.info(".testDeadPattern delta=" + delta);
        assertTrue("performance: delta=" +  delta, delta < 20);
    }

    private static Log log = LogFactory.getLog(TestDeadPattern.class);
}
