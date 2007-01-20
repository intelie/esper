package net.esper.regression.adapter;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.io.File;

import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.adapter.jms.JMSAdapter;
import net.esper.adapter.*;
import net.esper.core.EPServiceProviderSPI;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 14, 2006
 * Time: 1:30:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJMSAdapter extends TestCase
{

  private Configuration config = new Configuration();
  private String eventTypeAlias;
  private EPServiceProvider epService;
  EPAdministrator administrator;
  String statementText;
  private EPStatement statement;
  private SupportUpdateListener listener;
  private Map<String, Class> propertyTypes;
  private long currentTime;
  private static final String ESPER_TEST_CONFIG ="esper.yves.test.readconfig.cfg.xml";
  private File configFile = new File("F:\\Esper\\jmsadapter\\trunk\\etc\\"+ESPER_TEST_CONFIG);
  private final Log log = LogFactory.getLog(this.getClass());

  protected void setUp()
  {
    config.configure(configFile);
    epService = EPServiceProviderManager.getProvider("OutputAdapter", config);
    //administrator = epService.getEPAdministrator();
    // Set the clock to 0
    //currentTime = 0;
    //sendTimeEvent(0);
    // Turn off external clocking
    //epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
  }

  public void testSpringLoaderConfiguration()
  {
    String resource = "spring/jms-spring.xml";
    SpringContextLoader scl = new SpringContextLoader();
    scl.configure(config, resource);        
  }

  public void testEPService()
  {
    statementText = "select * from MyMapEvent.win:length(5)";
    EPStatement statement = administrator.createEQL(statementText);
    listener = new SupportUpdateListener();
    statement.addListener(listener);
    sendEvent("jmsOuputAdapter", "MyMapEvent", 1, 1.1, "some string");
  }

  public void testInsert()
  {
    statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:length(1)";
    EPStatement stmt = administrator.createEQL(statementText);
    listener = new SupportUpdateListener();
    stmt.addListener(listener);
    sendEvent("jmsOuputAdapter", "MyMapEvent", 1, 1.1, "some string");
  }

  public void testOneEvent()
  {
    sendEvent("jmsOuputAdapter", "MyMapEvent", 1, 1.1, "some string");
    assertEvent("jmsOuputAdapter", new Integer(1), new Double(1.1), "some string", 1);
  }

  public void testAdapter()
  {
    URL urlAdapterConfig = getClass().getClassLoader().getResource("Spring/jms-spring.xml");
    OutputAdapterService adapterService = OutputAdapterServiceProvider.newService(urlAdapterConfig.toString());
    //epService.setOuputAdapterService(adapterService);
    JMSAdapter inputAdapter = ((OutputAdapterServiceImpl) adapterService).getJMSAdapter("jmsInputAdapter", AdapterRole.RECEIVER);
    inputAdapter.setUsingEngineThread(true);
    inputAdapter.setEPService(epService);
    inputAdapter.start();
    EventType eventType = ((OutputAdapterServiceImpl) adapterService).getEventType("jmsInputAdapterEventType");
    config.addEventTypeAlias("testInputAdapter", buildPropertyMap(eventType));
    //statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time_batch(2).std:lastevent()";
    //statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time(10 sec)";
    statementText = "insert into myOutputStream select intPrimitive, doublePrimitive " +
      "from " + SupportBean.class.getName() + ".win:length(100)";
    //statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:length(5)";
    administrator.createEQL(statementText);
    //statementText = "insert into myOutputStream select myInt, myDouble, myString from MyMapEvent.win:time_batch(2).std:lastevent()";
    //administrator.createEQL(statementText);
    //sendEvent(1,1.1,"some string");
    sendEvent(1, 1.1);
    sleep(10000);
  }

  private void sendTimeEvent(int timeIncrement)
  {
    currentTime += timeIncrement;
    CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
    epService.getEPRuntime().sendEvent(event);
  }

  private Adapter getAdapter(String adapterAlias)
  {
    Object o = config.getSpringContextLoaderReference();
    if (! (o instanceof SpringContextLoader))
    {
      return null;
    }
    return ((SpringContextLoader) o).getAdapter(adapterAlias);
  }

  private void sendEvent(String adapterAlias, String eventTypeAlias, Integer myInt, Double myDouble, String myString)
  {
    Map map = new HashMap<String, Object>();
    map.put("myInt", myInt);
    map.put("myDouble", myDouble);
    map.put("myString", myString);
    ((OutputAdapter) getAdapter(adapterAlias)).setEPServiceProvider(epService);
    epService.getEPRuntime().sendEvent(map, eventTypeAlias);
  }

  private void sendEvent(int intPrimitive, double doublePrimitive)
  {
    SupportBean bean = new SupportBean();
    bean.setIntPrimitive(intPrimitive);
    bean.setDoublePrimitive(doublePrimitive);
    epService.getEPRuntime().sendEvent(bean);
  }

  private void assertEvent(String adapterAlias, Object myInt, Object myDouble, Object myString, int count)
  {
    EventBean event = ((OutputAdapter) getAdapter(adapterAlias)).getLastEvent();
    int eventCount = ((OutputAdapter) getAdapter(adapterAlias)).getAndResetEventCount();
    assertEquals(myInt, event.get("myInt"));
    assertEquals(myDouble, event.get("myDouble"));
    assertEquals(myString, event.get("myString"));
    assertEquals(count, eventCount);
  }

  private Map buildPropertyMap(EventType eventType)
  {
    Map<String, Class> mapType = new HashMap<String, Class>();
    for (String prop : eventType.getPropertyNames())
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
