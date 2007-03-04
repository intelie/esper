package net.esper.regression.adapter;

import junit.framework.*;
import net.esper.adapter.*;
import net.esper.adapter.jms.*;
import net.esper.client.*;
import net.esper.support.util.*;
import org.apache.commons.logging.*;
import org.springframework.jms.core.*;

import javax.jms.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created for ESPER.
 */
public class TestJMSAdapter extends TestCase
  implements ProducerCallback, MessageListener, ExceptionListener
{

  private Configuration config = new Configuration();
  private EPServiceProvider epService;
  EPAdministrator administrator;
  String statementText;
  private EPStatement statement;
  List<AdapterLoader> adapterLoaders;
  private SpringContextLoader scl;
  private ArrayBlockingQueue<HashMap<String, Object>> expectedEvents =
    new ArrayBlockingQueue<HashMap<String, Object>>(10, true);
  private SupportUpdateListener listener;
  private Boolean destinationSet;

  private static final String ESPER_TEST_CONFIG =
    "esper.outputadapter.test.readconfig.cfg.xml";
  private static File configFile =
    new File("F:\\Esper\\jmsadapter\\trunk\\etc\\" + ESPER_TEST_CONFIG);
  private final Log log = LogFactory.getLog(getClass());

  public TestJMSAdapter()
  {
  }

  protected void setUp()
  {
    config.configure(configFile);
    epService = EPServiceProviderManager.getProvider("OutputAdapter", config);
    administrator = epService.getEPAdministrator();
    adapterLoaders = config.getAdapterLoaders();
    scl = (SpringContextLoader)adapterLoaders.get(0);
    listener = new SupportUpdateListener();
  }

  public void testEvalEvents() throws Throwable
  {
    expectedEvents.clear();
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString",
      "some string", true);
    statementText = "select * from MyMapEvent.win:length(5)";
    statement = administrator.createEQL(statementText);
    administrator.createEQL(statementText);
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString",
      "some string", true);
    statementText =
      "insert into myOutputStream select intPrimitive, doublePrimitive " +
        "from " + SupportBean.class.getName() + ".win:length(100)";
    administrator.createEQL(statementText);
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString",
      "some string", true);
    scl.close();
  }

  public void testSubscription() throws Throwable
  {
    expectedEvents.clear();
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.0001, "myString",
      "subscription 1", true);
    sendMapEventType(
      "MyMapEventTwo", "aString", "subscription 2", "anInt", 2, null, null,
      true);
    scl.close();
  }

  public void testLateSubscriptionBidding() throws Throwable
  {
    expectedEvents.clear();
    scl.close();
    scl = new SpringContextLoader();
    scl.configure("spring/jms-spring.xml", true);
    OutputAdapter adapter = (OutputAdapter)getAdapter("jmsOutputAdapter", scl);
    JMSSubscription newSubscription =
      new JMSSubscription("newSubscription", adapter, "newMapEventType");
    adapter.addSubscription(newSubscription);
    epService = EPServiceProviderManager.getProvider(
      "testLateSubscriptionBidding",
      createMapEventTypeConfig(
        scl, "newMapEventType", "key1", Long.class, "key2", Double.class));
    sendMapEventType(
      "newMapEventType", "key1", (long)1, "key2", 1.1, null, null, true);
    scl.close();
  }

  public void testInputAdapter()
  {
    // We need to configure another engine than the one used for the output adapter
    // to avoid an endless loop.
    // The listener will be hooked to the second engine.
    EPServiceProvider inputAdapterEPService =
      EPServiceProviderManager.getProvider(
        "testInputAdapter",
        createMapEventTypeConfig(
          "MyMapEvent", "myInt", Integer.class, "myDouble", Double.class,
          "myString", String.class));
    Adapter adapter = scl.getAdapter("jmsInputAdapter");
    adapter.setEPServiceProvider(inputAdapterEPService);
    administrator = inputAdapterEPService.getEPAdministrator();
    statementText = "select * from MyMapEvent.win:length(5)";
    statement = administrator.createEQL(statementText);
    statement.addListener(listener);
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString",
      "some string", false);
    while (!listener.getAndClearIsInvoked())
    {

    }
    assertEquals(1, listener.getLastNewData().length);
    assertEquals(1.1, listener.getLastNewData()[0].get("myDouble"));
    scl.close();
  }

  private Adapter getAdapter(String adapterAlias, SpringContextLoader scl)
  {
    return scl.getAdapter(adapterAlias);
  }

  private Configuration createMapEventTypeConfig(SpringContextLoader scl,
    String eventTypeAlias,
    String key1, Class class1,
    String key2, Class class2)
  {
    Configuration config = new Configuration();
    Map<String, Class> props = new HashMap<String, Class>();
    props.put(key1, class1);
    props.put(key2, class2);
    config.addEventTypeAlias(eventTypeAlias, props);
    config.addAdapterLoader(scl);
    return config;
  }

  private Configuration createMapEventTypeConfig(String eventTypeAlias,
    String key1, Class class1,
    String key2, Class class2,
    String key3, Class class3)
  {
    Configuration config = new Configuration();
    Map<String, Class> props = new HashMap<String, Class>();
    props.put(key1, class1);
    props.put(key2, class2);
    props.put(key3, class3);
    config.addEventTypeAlias(eventTypeAlias, props);
    return config;
  }

  private void sendMapEventType(String eventTypeAlias, String key1,
    Object value1, String key2, Object value2, String key3, Object value3,
    boolean checkInBroker)
  {
    HashMap<String, Object> map = new HashMap<String, Object>();
    if (key1 != null)
    {
      map.put(key1, value1);
    }
    if (key2 != null)
    {
      map.put(key2, value2);
    }
    if (key3 != null)
    {
      map.put(key3, value3);
    }
    try
    {
      expectedEvents.put(map);
    }
    catch (InterruptedException e)
    {
    }
    if (checkInBroker)
    {
      destinationSet = false;
      springJmsExecute();
      while (!destinationSet)
      {
      }
      epService.getEPRuntime().sendEvent(map, eventTypeAlias);
      destinationSet = false;
    }
    else
    {
      epService.getEPRuntime().sendEvent(map, eventTypeAlias);
    }
  }

  private void springJmsExecute()
  {
    JmsTemplate jmsTemplate = ((SpringJMSTemplateOutputAdapter)scl.getAdapter(
      "jmsOutputAdapter")).getJmsTemplate();
    jmsTemplate.execute(this);
  }

  public Object doInJms(Session session, MessageProducer producer)
  {
    ObjectMessage message = null;
    try
    {
      if (expectedEvents.isEmpty())
      {
        return null;
      }
      HashMap<String, Object> mapEvents = expectedEvents.take();
      message = session.createObjectMessage();
      message.setObject(mapEvents);
      Destination destination = session.createQueue("ESPER.TEST.QUEUE");
      SpringJMSTemplateOutputAdapter adapter =
        (SpringJMSTemplateOutputAdapter)getAdapter("jmsOutputAdapter", scl);
      adapter.setDestination(destination);
      destinationSet = true;
      producer = session.createProducer(destination);
      producer.send(message);
    }
    catch (InterruptedException e)
    {
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
    return message;
  }

  private synchronized void assertEvent(Map<String, Object> rcvEvent)
  {
    try
    {
      assertTrue(expectedEvents.size() != 0);
      assertTrue(rcvEvent.size() != 0);
      HashMap<String, Object> expEvent = expectedEvents.take();
      Iterator<Map.Entry<String, Object>> itRcvEvent =
        rcvEvent.entrySet().iterator();
      Iterator<Map.Entry<String, Object>> itExpEvent =
        expEvent.entrySet().iterator();
      while (itExpEvent.hasNext())
      {
        Map.Entry<String, Object> rcvEntry = itRcvEvent.next();
        Map.Entry<String, Object> expEntry = itExpEvent.next();
        assertEquals(expEntry.getKey(), rcvEntry.getKey());
        assertEquals(expEntry.getValue(), rcvEntry.getValue());
      }
    }
    catch (InterruptedException ex)
    {
    }
  }

  public void onMessage(Message message)
  {
    try
    {
      message.acknowledge();
      if (message instanceof ObjectMessage)
      {
        HashMap<String, Object> mapEvent =
          (HashMap<String, Object>)((ObjectMessage)message).getObject();
        expectedEvents.put(mapEvent);
      }
      if (message instanceof MapMessage)
      {
        MapMessage mapMsg = (MapMessage)message;
        mapMsg.getMapNames();
        Enumeration en = mapMsg.getMapNames();
        Map<String, Object> mapEvent = new HashMap<String, Object>();
        while (en.hasMoreElements())
        {
          String property = (String)en.nextElement();
          Object mapObject = mapMsg.getObject(property);
          log.debug("Property " + property + " Received: " + mapObject);
          mapEvent.put(property, mapObject);
        }
        assertEvent(mapEvent);
      }
    }
    catch (InterruptedException ex)
    {
      log.debug("Interrupted!");
    }
    catch (JMSException e)
    {
      log.error("Caught: " + e);
      e.printStackTrace();
    }
  }

  synchronized public void onException(JMSException ex)
  {
    log.error("JMS Exception occured.  Shutting down client.");
  }

  public void destroy()
  {
    log.debug("destroy()");
  }

  private class SupportBean
  {
    private String string;

    private boolean boolPrimitive;
    private int intPrimitive;
    private long longPrimitive;
    private char charPrimitive;
    private short shortPrimitive;
    private byte bytePrimitive;
    private float floatPrimitive;
    private double doublePrimitive;

    private Boolean boolBoxed;
    private Integer intBoxed;
    private Long longBoxed;
    private Character charBoxed;
    private Short shortBoxed;
    private Byte byteBoxed;
    private Float floatBoxed;
    private Double doubleBoxed;

    private SupportEnum enumValue;

    public SupportBean()
    {
    }

    public SupportBean(String string, int intPrimitive)
    {
      this.string = string;
      this.intPrimitive = intPrimitive;
    }

    public String getString()
    {
      return string;
    }

    public boolean isBoolPrimitive()
    {
      return boolPrimitive;
    }

    public int getIntPrimitive()
    {
      return intPrimitive;
    }

    public long getLongPrimitive()
    {
      return longPrimitive;
    }

    public char getCharPrimitive()
    {
      return charPrimitive;
    }

    public short getShortPrimitive()
    {
      return shortPrimitive;
    }

    public byte getBytePrimitive()
    {
      return bytePrimitive;
    }

    public float getFloatPrimitive()
    {
      return floatPrimitive;
    }

    public double getDoublePrimitive()
    {
      return doublePrimitive;
    }

    public Boolean getBoolBoxed()
    {
      return boolBoxed;
    }

    public Integer getIntBoxed()
    {
      return intBoxed;
    }

    public Long getLongBoxed()
    {
      return longBoxed;
    }

    public Character getCharBoxed()
    {
      return charBoxed;
    }

    public Short getShortBoxed()
    {
      return shortBoxed;
    }

    public Byte getByteBoxed()
    {
      return byteBoxed;
    }

    public Float getFloatBoxed()
    {
      return floatBoxed;
    }

    public Double getDoubleBoxed()
    {
      return doubleBoxed;
    }

    public void setString(String string)
    {
      this.string = string;
    }

    public void setBoolPrimitive(boolean boolPrimitive)
    {
      this.boolPrimitive = boolPrimitive;
    }

    public void setIntPrimitive(int intPrimitive)
    {
      this.intPrimitive = intPrimitive;
    }

    public void setLongPrimitive(long longPrimitive)
    {
      this.longPrimitive = longPrimitive;
    }

    public void setCharPrimitive(char charPrimitive)
    {
      this.charPrimitive = charPrimitive;
    }

    public void setShortPrimitive(short shortPrimitive)
    {
      this.shortPrimitive = shortPrimitive;
    }

    public void setBytePrimitive(byte bytePrimitive)
    {
      this.bytePrimitive = bytePrimitive;
    }

    public void setFloatPrimitive(float floatPrimitive)
    {
      this.floatPrimitive = floatPrimitive;
    }

    public void setDoublePrimitive(double doublePrimitive)
    {
      this.doublePrimitive = doublePrimitive;
    }

    public void setBoolBoxed(Boolean boolBoxed)
    {
      this.boolBoxed = boolBoxed;
    }

    public void setIntBoxed(Integer intBoxed)
    {
      this.intBoxed = intBoxed;
    }

    public void setLongBoxed(Long longBoxed)
    {
      this.longBoxed = longBoxed;
    }

    public void setCharBoxed(Character charBoxed)
    {
      this.charBoxed = charBoxed;
    }

    public void setShortBoxed(Short shortBoxed)
    {
      this.shortBoxed = shortBoxed;
    }

    public void setByteBoxed(Byte byteBoxed)
    {
      this.byteBoxed = byteBoxed;
    }

    public void setFloatBoxed(Float floatBoxed)
    {
      this.floatBoxed = floatBoxed;
    }

    public void setDoubleBoxed(Double doubleBoxed)
    {
      this.doubleBoxed = doubleBoxed;
    }

    public SupportEnum getEnumValue()
    {
      return enumValue;
    }

    public void setEnumValue(SupportEnum enumValue)
    {
      this.enumValue = enumValue;
    }
  }

  private enum SupportEnum
  {
    ENUM_VALUE_1,
    ENUM_VALUE_2,
    ENUM_VALUE_3;

    public static SupportEnum getValueForEnum(int count)
    {
      return SupportEnum.values()[count];
    }

  }

}
