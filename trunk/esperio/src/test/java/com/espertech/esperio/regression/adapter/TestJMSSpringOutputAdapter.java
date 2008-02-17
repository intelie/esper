package com.espertech.esperio.regression.adapter;

import junit.framework.TestCase;
import com.espertech.esperio.SpringContext;
import com.espertech.esperio.SpringContextLoader;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esperio.support.util.SupportSerializableBean;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestJMSSpringOutputAdapter extends TestCase
{
    private SupportJMSReceiver jmsReceiver;

    public void setUp()
    {
        jmsReceiver = new SupportJMSReceiver();
    }

    public void tearDown()
    {
        jmsReceiver.destroy();
    }

    public void testOutputAdapter() throws Exception
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        // define output type
        Map<String, Class> typeProps = new HashMap<String, Class>();
        typeProps.put("prop1", String.class);
        typeProps.put("prop2", String.class);
        config.addEventTypeAlias("MyOutputStream", typeProps);

        // define loader
        Properties props = new Properties();
        props.put(SpringContext.CLASSPATH_CONTEXT, "regression/jms_regression_output_spring.xml");
        config.addPluginLoader("MyLoader", SpringContextLoader.class.getName(), props);
        EPServiceProvider service = EPServiceProviderManager.getProvider(this.getClass().getName() + "_testOutputAdapter", config);

        service.getEPAdministrator().createEPL(
                "insert into MyOutputStream " +
                "select string as prop1, '>' || string || '<' as prop2 from " + SupportSerializableBean.class.getName());

        service.getEPRuntime().sendEvent(new SupportSerializableBean("x1"));
        Message result = jmsReceiver.receiveMessage();
        assertNotNull(result);
        MapMessage mapMsg = (MapMessage) result;
        assertEquals("x1", mapMsg.getObject("prop1"));
        assertEquals(">x1<", mapMsg.getObject("prop2"));

        service.getEPRuntime().sendEvent(new SupportSerializableBean("x2"));
        result = jmsReceiver.receiveMessage();
        assertNotNull(result);
        mapMsg = (MapMessage) result;
        assertEquals("x2", mapMsg.getObject("prop1"));
        assertEquals(">x2<", mapMsg.getObject("prop2"));
    }
}
