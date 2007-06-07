package net.esper.regression.adapter;

import junit.framework.TestCase;
import net.esper.adapter.SpringContext;
import net.esper.adapter.SpringContextLoader;
import net.esper.adapter.InputAdapter;
import net.esper.adapter.AdapterLoader;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportSerializableBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;
import net.esper.core.EPServiceProviderSPI;

import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

public class TestJMSSpringInputAdapter extends TestCase
{
    private SupportJMSSender jmsSender;

    public void setUp()
    {
        jmsSender = new SupportJMSSender();

    }

    public void tearDown()
    {
        jmsSender.destroy();
    }

    public void testSerializable() throws Exception
    {
        // define loader
        Configuration config = new Configuration();
        Properties props = new Properties();
        props.put(SpringContext.CLASSPATH_CONTEXT, "regression/jms_regression_input_spring.xml");
        config.addAdapterLoader("MyLoader", SpringContextLoader.class.getName(), props);
        EPServiceProvider service = EPServiceProviderManager.getProvider(this.getClass().getName() + "_testSerializable", config);

        EPStatement statement = service.getEPAdministrator().createEQL("select * from " + SupportSerializableBean.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        jmsSender.sendSerializable(new SupportSerializableBean("x1"));
        Thread.sleep(200);
        assertEquals("x1", listener.assertOneGetNewAndReset().get("string"));

        jmsSender.sendSerializable(new SupportSerializableBean("x2"));
        Thread.sleep(200);
        assertEquals("x2", listener.assertOneGetNewAndReset().get("string"));

        EPServiceProviderSPI spi = (EPServiceProviderSPI) service;
        AdapterLoader loader = (AdapterLoader) spi.getEnvContext().lookup("adapter-loader/MyLoader");
        loader.destroy();
    }

    public void testMap() throws Exception
    {
        Configuration config = new Configuration();

        // define loader
        Properties props = new Properties();
        props.put(SpringContext.CLASSPATH_CONTEXT, "regression/jms_regression_input_spring.xml");
        config.addAdapterLoader("MyLoader", SpringContextLoader.class.getName(), props);

        // define type
        Map<String, Class> typeProps = new HashMap<String, Class>();
        typeProps.put("prop1", String.class);
        typeProps.put("prop2", int.class);
        config.addEventTypeAlias("MyMapType", typeProps);

        EPServiceProvider service = EPServiceProviderManager.getProvider(this.getClass().getName() + "_testMap", config);

        EPStatement statement = service.getEPAdministrator().createEQL("select * from MyMapType");
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        jmsSender.sendMap(makeMap("MyMapType", "IBM", 100));
        Thread.sleep(500);
        EventBean received = listener.assertOneGetNewAndReset();
        assertEquals("IBM", received.get("prop1"));
        assertEquals(100, received.get("prop2"));

        // test some invalid types
        jmsSender.sendMap(makeMap(null, "IBM", 100));
        jmsSender.sendMap(makeMap("xxx", "IBM", 100));

        jmsSender.sendMap(makeMap("MyMapType", "CSCO", 200));
        Thread.sleep(200);
        received = listener.assertOneGetNewAndReset();
        assertEquals("CSCO", received.get("prop1"));
        assertEquals(200, received.get("prop2"));       
    }

    private Map<String, Object> makeMap(String type, String prop1, int prop2)
    {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("prop1", prop1);
        props.put("prop2", prop2);
        props.put(InputAdapter.ESPERIO_MAP_EVENT_TYPE, type);
        return props;
    }

}
