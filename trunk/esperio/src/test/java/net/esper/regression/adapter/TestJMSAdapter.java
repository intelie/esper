package net.esper.regression.adapter;

import junit.framework.*;
import net.esper.adapter.*;
import net.esper.adapter.jms.*;
import net.esper.client.*;
import net.esper.event.*;
import org.apache.commons.logging.*;

import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA. User: MYSELF Date: Nov 14, 2006 Time: 1:30:01 PM To
 * change this template use File | Settings | File Templates.
 */
public class TestJMSAdapter extends TestCase
{

  private Configuration config = new Configuration();
  private EPServiceProvider epService;
  EPAdministrator administrator;
  String statementText;
  private EPStatement statement;
  List<AdapterLoader> adapterLoaders;
  private SpringContextLoader scl;
  private static final String ESPER_TEST_CONFIG =
    "esper.outputadapter.test.readconfig.cfg.xml";
  private File configFile =
    new File("F:\\Esper\\jmsadapter\\trunk\\etc\\" + ESPER_TEST_CONFIG);
  private final Log log = LogFactory.getLog(getClass());

  protected void setUp()
  {
    config.configure(configFile);
    epService = EPServiceProviderManager.getProvider("OutputAdapter", config);
    administrator = epService.getEPAdministrator();
    adapterLoaders = config.getAdapterLoaders();
    scl = (SpringContextLoader)adapterLoaders.get(0);
  }

  public void testEvalEvents() throws Throwable
  {
    resetEventCounter(scl);
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString",
      "some string");
    assertEvent(
      "jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string",
      1, false);
    sendMapEventType("MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString","");
    assertEvent(
      "jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string",
      1, true);
    statementText = "select * from MyMapEvent.win:length(5)";
    statement = administrator.createEQL(statementText);
    administrator.createEQL(statementText);
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString",
      "some string");
    assertEvent(
      "jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string",
      1, false);
    statementText =
      "insert into myOutputStream select intPrimitive, doublePrimitive " +
        "from " + SupportBean.class.getName() + ".win:length(100)";
    administrator.createEQL(statementText);
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.1, "myString",
      "some string");
    assertEvent(
      "jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string",
      1, false);
  }

  public void testSubscription() throws Throwable
  {
    resetEventCounter(scl);
    sendMapEventType(
      "MyMapEvent", "myInt", 1, "myDouble", 1.0001, "myString",
      "subscription 1");
    assertEventSubscription(
      "jmsOutputAdapter", scl, "subscriptionOne", new Integer(1),
      new Double(1.0001), "subscription 1",
      1, false);
    sendMapEventType(
      "MyMapEventTwo", "aString", "subscription 2", "anInt", 2, null, null);
    assertEventSubscription(
      "jmsOutputAdapter", scl, "subscriptionTwo", "subscription 2",
      new Integer(2), null, 2, false);
  }

  public void testLateSubscriptionBidding() throws Throwable
  {
    SpringContextLoader scl = new SpringContextLoader();
    scl.configure("spring/jms-spring.xml", true);
    resetEventCounter(scl);
    OutputAdapter adapter = (OutputAdapter)getAdapter("jmsOutputAdapter", scl);
    JMSSubscription newSubscription =
      new JMSSubscription("newSubscription", adapter, "newMapEventType");
    adapter.addSubscription(newSubscription);
    epService = EPServiceProviderManager.getProvider(
      "testLateSubscriptionBidding",
      createMapEventTypeConfig(
        scl, "newMapEventType", "key1", Long.class, "key2", Double.class));
    sendMapEventType(
      "newMapEventType", "key1", (long)1, "key2", 1.1, null, null);
    assertEventSubscription(
      "jmsOutputAdapter", scl, "newSubscription", new Long(1), new Double(1.1),
      null, 1, false);
  }

  private Adapter getAdapter(String adapterAlias, SpringContextLoader scl)
  {
    return scl.getAdapter(adapterAlias);
  }

  private void resetEventCounter(SpringContextLoader scl)
  {
    OutputAdapter adapter = (OutputAdapter)getAdapter("jmsOutputAdapter", scl);
    adapter.getEventBeanListener().getAndResetEventCount();
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

  private void sendMapEventType(String eventTypeAlias, String key1,
    Object value1, String key2, Object value2, String key3, Object value3)
  {
    Map map = new HashMap<String, Object>();
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
    epService.getEPRuntime().sendEvent(map, eventTypeAlias);
  }

  private void assertEvent(String adapterAlias, SpringContextLoader scl,
    Object myInt, Object myDouble, Object myString, int count,
    boolean failed) throws Throwable
  {
    EventBeanListener eventBeanListener =
      ((OutputAdapter)scl.getAdapter(adapterAlias)).getEventBeanListener();
    EventBean event = eventBeanListener.getLastEvent();
    int eventCount = eventBeanListener.getAndResetEventCount();
    try
    {
      assertEquals(myInt, event.get("myInt"));
      assertEquals(myDouble, event.get("myDouble"));
      assertEquals(myString, event.get("myString"));
      assertEquals(count, eventCount);
    }
    catch (Throwable e)
    {
      if (failed)
      {
        assertTrue(true);
      }
      else
      {
        fail();
      }
    }
  }

  private void assertEventSubscription(String adapterAlias,
    SpringContextLoader scl, String subscription,
    Object value1, Object value2, Object value3, int count,
    boolean failed) throws Throwable
  {
    JMSOutputAdapter adapter = (JMSOutputAdapter)(scl.getAdapter(adapterAlias));

    EventBeanListener eventBeanListener =
      ((OutputAdapter)scl.getAdapter(adapterAlias)).getEventBeanListener();
    EventBean event = eventBeanListener.getLastEvent();
    try
    {
      JMSMessageMarshaler jmsAdapterMarshaler =
        adapter.getJmsMessageMarshaler();
      JMSMessageMarshaler jmsSubscriptionMarshaler =
        ((JMSSubscription)adapter.getSubscription(
          subscription)).getJmsMessageMarshaler();
      if (subscription.equals("subscriptionOne"))
      {
        assertEquals(value1, event.get("myInt"));
        assertEquals(value2, event.get("myDouble"));
        assertEquals(value3, event.get("myString"));
        assertNull(jmsSubscriptionMarshaler);
      }
      if (subscription.equals("subscriptionTwo"))
      {
        assertEquals(value1, event.get("aString"));
        assertEquals(value2, event.get("anInt"));
        assertNotSame(jmsAdapterMarshaler, jmsSubscriptionMarshaler);
      }
      if (subscription.equals("newSubscription"))
      {
        assertEquals(value1, event.get("key1"));
        assertEquals(value2, event.get("key2"));
        assertNull(jmsSubscriptionMarshaler);
      }
      assertEquals(count, eventBeanListener.getEventCount());
    }
    catch (Throwable e)
    {
      if (failed)
      {
        assertTrue(true);
      }
      else
      {
        fail();
      }
    }
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
