package net.esper.regression.adapter;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.net.URL;

import net.esper.client.*;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 14, 2006
 * Time: 1:30:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJMSAdapter extends TestCase
{

    private EPServiceProvider epService;
    EPAdministrator administrator;
    String statementText;
    private EPStatement statement;
    private Configuration config = new Configuration();
    private static final String ESPER_TEST_CONFIG = "regression/esper.yves.test.readconfig.cfg.xml";

    protected void setUp()
    {
    }

    public void testInsert()
    {
        URL urlEsperConfig = this.getClass().getClassLoader().getResource(net.esper.regression.adapter.TestJMSAdapter.ESPER_TEST_CONFIG);
        config.configure(urlEsperConfig);
        URL urlAdapterConfig = getClass().getClassLoader().getResource("Spring/jms-spring.xml");
        config.setOuputAdapterConfiguration(urlAdapterConfig.toString());
        epService = EPServiceProviderManager.getProvider("testInsertTwo", config);
        administrator = epService.getEPAdministrator();
        statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time_batch(2).std:lastevent()";
        statement = administrator.createEQL(statementText);
        sendEvent(1,1.1,"some string");
        sendEvent(2,2.1,"some string 2");
        sleep(40000);
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
