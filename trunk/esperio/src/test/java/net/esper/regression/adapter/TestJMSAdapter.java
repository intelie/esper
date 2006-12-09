package net.esper.regression.adapter;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.net.URL;

import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.adapter.jms.JMSAdapter;
import net.esper.adapter.OutputAdapterServiceProvider;
import net.esper.adapter.OutputAdapterService;
import net.esper.core.EPServiceProviderSPI;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 14, 2006
 * Time: 1:30:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJMSAdapter extends TestCase
{

    private EPServiceProviderSPI epService;
    EPAdministrator administrator;
    String statementText;
    private EPStatement statement;
    private Configuration config = new Configuration();
    private long currentTime;
    private static final String ESPER_TEST_CONFIG = "esper.yves.test.readconfig.cfg.xml";

    protected void setUp()
    {
    }

    public void testEvent()
    {
        Configuration configuration = new Configuration();
        // Set the clock to 0
        currentTime = 0;
        sendTimeEvent(0);
        // Turn off external clocking
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testInsert()
    {
        URL urlEsperConfig = this.getClass().getClassLoader().getResource(net.esper.regression.adapter.TestJMSAdapter.ESPER_TEST_CONFIG);
        config.configure(urlEsperConfig);
        URL urlAdapterConfig = getClass().getClassLoader().getResource("Spring/jms-spring.xml");
        OutputAdapterService adapterService = OutputAdapterServiceProvider.newService(urlAdapterConfig.toString());
        epService = (EPServiceProviderSPI) EPServiceProviderManager.getProvider("testInsert", config);
        epService.setOuputAdapterService(adapterService);
        administrator = epService.getEPAdministrator();
        statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time_batch(2).std:lastevent()";
        statement = administrator.createEQL(statementText);
        sendEvent(1,1.1,"some string");
        sendEvent(2,2.1,"some string 2");
        sleep(40000);
    }

    private void sendTimeEvent(int timeIncrement){
        currentTime += timeIncrement;
        CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendEvent(Integer myInt, Double myDouble, String myString)
    {
        Map map = new HashMap<String, Object>();
        map.put("myInt", myInt);
        map.put("myDouble", myDouble);
        map.put("myString", myString);
        epService.getEPRuntime().sendEvent(map, "MyMapEvent");
    }

    private void sleep(int msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch (InterruptedException e)
        {
        }
    }

}
