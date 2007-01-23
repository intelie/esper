package net.esper.regression.adapter;

import junit.framework.TestCase;
import net.esper.adapter.Adapter;
import net.esper.adapter.OutputAdapter;
import net.esper.adapter.SpringContextLoader;
import net.esper.client.*;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.EventTypeListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 14, 2006
 * Time: 1:30:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJMSAdapter extends TestCase {

  private Configuration config = new Configuration();
  private String eventTypeAlias;
  private EPServiceProvider epService;
  EPAdministrator administrator;
  String statementText;
  private EPStatement statement;
  private static final String ESPER_TEST_CONFIG = "esper.yves.test.readconfig.cfg.xml";
  private File configFile = new File("F:\\Esper\\jmsadapter\\trunk\\etc\\" + ESPER_TEST_CONFIG);
  private final Log log = LogFactory.getLog(this.getClass());

  protected void setUp() {
    config.configure(configFile);
    epService = EPServiceProviderManager.getProvider("OutputAdapter", config);
    administrator = epService.getEPAdministrator();
  }

  public void testEvalEvents() throws Throwable
  {
    SpringContextLoader scl = (SpringContextLoader) config.getSpringContextLoaderReference();
    sendEvent("MyMapEvent", 1, 1.1, "some string");
    assertEvent("jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string", 1, false);
    sendEvent("MyMapEvent", 1, 1.1, "");
    assertEvent("jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string", 1, true);
    statementText = "select * from MyMapEvent.win:length(5)";
    statement = administrator.createEQL(statementText);
    administrator.createEQL(statementText);
    sendEvent("MyMapEvent", 1, 1.1, "some string");
    assertEvent("jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string", 1, false);
    statementText = "insert into myOutputStream select intPrimitive, doublePrimitive " +
      "from " + SupportBean.class.getName() + ".win:length(100)";
    administrator.createEQL(statementText);
    sendEvent("MyMapEvent", 1, 1.1, "some string");
    assertEvent("jmsOutputAdapter", scl, new Integer(1), new Double(1.1), "some string", 1, false);
  }

  public void testLateEventTypeBidding() throws Throwable {
    SpringContextLoader scl = new SpringContextLoader();
    scl.configure("spring/jms-spring.xml");
    Adapter adapter = getAdapter("jmsOutputAdapter", scl);
    ((OutputAdapter) adapter).setEventTypeAlias("newMapEventType");
    epService = EPServiceProviderManager.getProvider("testLateEventTypeBidding",
                createMapEventTypeConfig(scl, "newMapEventType", "key1", Long.class, "key2", Double.class));
    sendMapEventType("newMapEventType", "key1", (long)1, "key2", 1.1);
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
    config.addSpringLoaderContextReference(scl);
    //OutputAdapter adapter = (OutputAdapter) getAdapter("jmsOutputAdapter", scl);
    //config.registerInterest(eventTypeAlias, adapter.getEventTypeListener());
    return config;
  }

  private void sendEvent(String eventTypeAlias, Integer myInt, Double myDouble, String myString) {
    Map map = new HashMap<String, Object>();
    map.put("myInt", myInt);
    map.put("myDouble", myDouble);
    map.put("myString", myString);
    epService.getEPRuntime().sendEvent(map, eventTypeAlias);
  }

  private void sendMapEventType(String eventTypeAlias, String key1, Object value1,
                                String key2, Object value2) {
    Map map = new HashMap<String, Object>();
    map.put(key1, value1);
    map.put(key2, value2);
    epService.getEPRuntime().sendEvent(map, eventTypeAlias);
  }

  private void assertEvent(String adapterAlias, SpringContextLoader scl,
                           Object myInt, Object myDouble, Object myString, int count,
                           boolean failed) throws Throwable {
    EventTypeListener eventTypeListener = ((OutputAdapter) scl.getAdapter(adapterAlias)).getEventTypeListener();
    EventBean event = eventTypeListener.getLastEvent();
    int eventCount = eventTypeListener.getAndResetEventCount();
    try {
      assertEquals(myInt, event.get("myInt"));
      assertEquals(myDouble, event.get("myDouble"));
      assertEquals(myString, event.get("myString"));
      assertEquals(count, eventCount);
    }
    catch (Throwable e) {
      if (failed) {
        assertTrue(true);
      } else {
        fail();
      }
    }
  }

  private class SupportBean {
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

    public SupportBean() {
    }

    public SupportBean(String string, int intPrimitive) {
      this.string = string;
      this.intPrimitive = intPrimitive;
    }

    public String getString() {
      return string;
    }

    public boolean isBoolPrimitive() {
      return boolPrimitive;
    }

    public int getIntPrimitive() {
      return intPrimitive;
    }

    public long getLongPrimitive() {
      return longPrimitive;
    }

    public char getCharPrimitive() {
      return charPrimitive;
    }

    public short getShortPrimitive() {
      return shortPrimitive;
    }

    public byte getBytePrimitive() {
      return bytePrimitive;
    }

    public float getFloatPrimitive() {
      return floatPrimitive;
    }

    public double getDoublePrimitive() {
      return doublePrimitive;
    }

    public Boolean getBoolBoxed() {
      return boolBoxed;
    }

    public Integer getIntBoxed() {
      return intBoxed;
    }

    public Long getLongBoxed() {
      return longBoxed;
    }

    public Character getCharBoxed() {
      return charBoxed;
    }

    public Short getShortBoxed() {
      return shortBoxed;
    }

    public Byte getByteBoxed() {
      return byteBoxed;
    }

    public Float getFloatBoxed() {
      return floatBoxed;
    }

    public Double getDoubleBoxed() {
      return doubleBoxed;
    }

    public void setString(String string) {
      this.string = string;
    }

    public void setBoolPrimitive(boolean boolPrimitive) {
      this.boolPrimitive = boolPrimitive;
    }

    public void setIntPrimitive(int intPrimitive) {
      this.intPrimitive = intPrimitive;
    }

    public void setLongPrimitive(long longPrimitive) {
      this.longPrimitive = longPrimitive;
    }

    public void setCharPrimitive(char charPrimitive) {
      this.charPrimitive = charPrimitive;
    }

    public void setShortPrimitive(short shortPrimitive) {
      this.shortPrimitive = shortPrimitive;
    }

    public void setBytePrimitive(byte bytePrimitive) {
      this.bytePrimitive = bytePrimitive;
    }

    public void setFloatPrimitive(float floatPrimitive) {
      this.floatPrimitive = floatPrimitive;
    }

    public void setDoublePrimitive(double doublePrimitive) {
      this.doublePrimitive = doublePrimitive;
    }

    public void setBoolBoxed(Boolean boolBoxed) {
      this.boolBoxed = boolBoxed;
    }

    public void setIntBoxed(Integer intBoxed) {
      this.intBoxed = intBoxed;
    }

    public void setLongBoxed(Long longBoxed) {
      this.longBoxed = longBoxed;
    }

    public void setCharBoxed(Character charBoxed) {
      this.charBoxed = charBoxed;
    }

    public void setShortBoxed(Short shortBoxed) {
      this.shortBoxed = shortBoxed;
    }

    public void setByteBoxed(Byte byteBoxed) {
      this.byteBoxed = byteBoxed;
    }

    public void setFloatBoxed(Float floatBoxed) {
      this.floatBoxed = floatBoxed;
    }

    public void setDoubleBoxed(Double doubleBoxed) {
      this.doubleBoxed = doubleBoxed;
    }

    public SupportEnum getEnumValue() {
      return enumValue;
    }

    public void setEnumValue(SupportEnum enumValue) {
      this.enumValue = enumValue;
    }
  }

  private enum SupportEnum {
    ENUM_VALUE_1,
    ENUM_VALUE_2,
    ENUM_VALUE_3;

    public static SupportEnum getValueForEnum(int count) {
      return SupportEnum.values()[count];
    }

  }

}
