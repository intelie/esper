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
import net.esper.adapter.OutputAdapterServiceImpl;
import net.esper.adapter.AdapterRole;
import net.esper.core.EPServiceProviderSPI;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventType;

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
    private SupportUpdateListener listener;
    private Configuration config = new Configuration();
    private long currentTime;
    private static final String ESPER_TEST_CONFIG = "esper.yves.test.readconfig.cfg.xml";

    protected void setUp()
    {
        URL urlEsperConfig = this.getClass().getClassLoader().getResource(net.esper.regression.adapter.TestJMSAdapter.ESPER_TEST_CONFIG);
        config.configure(urlEsperConfig);
        epService = (EPServiceProviderSPI) EPServiceProviderManager.getProvider("testJMSAdapter", config);
        administrator = epService.getEPAdministrator();
        // Set the clock to 0
        currentTime = 0;
        sendTimeEvent(0);
        // Turn off external clocking
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testEPService()
    {
        /* Map<String, Class> propertyTypes = new HashMap<String, Class>();
        propertyTypes.put("myInt", Integer.class);
        propertyTypes.put("myDouble", Double.class);
        propertyTypes.put("myString", String.class);
        String eventTypeAlias = "MyMapEvent";
        config.addEventTypeAlias(eventTypeAlias, propertyTypes); */

        statementText = "select * from MyMapEvent.win:length(5)";
        EPStatement statement = administrator.createEQL(statementText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);
        sendEvent(1,1.1,"some string");
    }

    public void testInsert()
    {
        statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:length(1)";
        EPStatement stmt = administrator.createEQL(statementText);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);
        sendEvent(1,1.1,"some string");
    }

    public void testAdapter()
    {
        URL urlAdapterConfig = getClass().getClassLoader().getResource("Spring/jms-spring.xml");
        OutputAdapterService adapterService = OutputAdapterServiceProvider.newService(urlAdapterConfig.toString());
        epService.setOuputAdapterService(adapterService);
        JMSAdapter inputAdapter = ((OutputAdapterServiceImpl) adapterService).getJMSAdapter("jmsInputAdapter", AdapterRole.RECEIVER);
        inputAdapter.setUsingEngineThread(true);
        inputAdapter.setEPService(epService);
        inputAdapter.start();
        EventType eventType = ((OutputAdapterServiceImpl)adapterService).getEventType("jmsInputAdapterEventType");
        config.addEventTypeAlias("testInputAdapter", buildPropertyMap(eventType));
        //statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time_batch(2).std:lastevent()";
        statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time(10 sec)";
        //statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:length(5)";
        administrator.createEQL(statementText);
        //statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time_batch(2).std:lastevent()";
        //administrator.createEQL(statementText);
        sendEvent(1,1.1,"some string");
        //sleep(100);
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

    private Map buildPropertyMap(EventType eventType)
    {
        Map<String, Class> mapType = new HashMap<String, Class>();
        for (String prop :eventType.getPropertyNames())
        {
            mapType.put(prop, eventType.getPropertyType(prop));
        }
        return mapType;
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
