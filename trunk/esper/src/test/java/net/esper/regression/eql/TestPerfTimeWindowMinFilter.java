package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportMarketDataIDBean;
import net.esper.support.util.SupportUpdateListener;

public class TestPerfTimeWindowMinFilter extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.addEventTypeAlias("MD", SupportMarketDataIDBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void tearDown()
    {
        epService.initialize();
    }

    public void testPerf()
    {
        EPStatement[] statements = new EPStatement[100];
        SupportUpdateListener listeners[] = new SupportUpdateListener[statements.length];
        for (int i = 0; i < statements.length; i++)
        {
            int secondsWindowSpan = i % 30 + 1;
            double percent = 0.25 + i;
            int id = i % 5;

            String text = "select symbol, min(price) " +
                    "from MD(id='${id}').win:time(${secondsWindowSpan})\n" +
                    "having price >= min(price) * ${percent}";

            text = text.replace("${id}", Integer.toString(id));
            text = text.replace("${secondsWindowSpan}", Integer.toString(secondsWindowSpan));
            text = text.replace("${percent}", Double.toString(percent));

            statements[i] = epService.getEPAdministrator().createEQL(text);
            listeners[i] = new SupportUpdateListener();
            statements[i].addListener(listeners[i]);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            if (i % 10000 == 0)
            {
                long now = System.currentTimeMillis();
                double deltaSec = (now - start) / 1000.0;
                double throughput = 10000.0 / deltaSec;
                System.out.println("total=" + i + " deltaSec=" + deltaSec + " per sec:" + throughput);
                for (int j = 0; j < listeners.length; j++)
                {
                    listeners[j].reset();
                }
                start = now;
            }

            SupportMarketDataIDBean bean = new SupportMarketDataIDBean("IBM", Integer.toString(i % 5), 1);
            epService.getEPRuntime().sendEvent(bean);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("Delta=" + delta, delta < 2000);
    }
}
