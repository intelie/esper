package com.espertech.esper.regression.epl;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBeanCombinedProps;
import com.espertech.esper.support.bean.SupportBeanSimple;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.util.SerializableObjectCopier;


public class TestModifiedWildcardSelect extends TestCase
{
	private EPServiceProvider epService;
	private SupportUpdateListener listener;
	private SupportUpdateListener insertListener;
	private Map<String, Object> properties;

	protected void setUp()
	{
		epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
		epService.initialize();
		listener = new SupportUpdateListener();
		insertListener = new SupportUpdateListener();
		properties = new HashMap<String, Object>();
	}

    public void testSingleOM() throws Exception
    {
        String eventName = SupportBeanSimple.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard().add(Expressions.concat("myString", "myString"), "concat"));
        model.setFromClause(FromClause.create(FilterStream.create(eventName).addView(View.create("win", "length", 5))));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String text = "select *, myString || myString as concat from " + eventName + ".win:length(5)";
        assertEquals(text, model.toEPL());

        EPStatement statement = epService.getEPAdministrator().create(model);
        statement.addListener(listener);
        assertSimple();
    }

	public void testSingle() throws Exception
	{
		String eventName = SupportBeanSimple.class.getName();
		String text = "select *, myString||myString as concat from " + eventName + ".win:length(5)";

		EPStatement statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);
		assertSimple();
	}

	public void testSingleInsertInto() throws InterruptedException
	{
		String eventName = SupportBeanSimple.class.getName();
		String text = "insert into someEvent select *, myString||myString as concat from " + eventName + ".win:length(5)";
		String textTwo = "select * from someEvent.win:length(5)";

		EPStatement statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);

		statement = epService.getEPAdministrator().createEPL(textTwo);
		statement.addListener(insertListener);
		assertSimple();
		assertProperties(insertListener);
	}

	public void testJoinInsertInto() throws InterruptedException
	{
		String eventNameOne = SupportBeanSimple.class.getName();
		String eventNameTwo = SupportMarketDataBean.class.getName();
		String text = "insert into someJoinEvent select *, myString||myString as concat " +
				"from " + eventNameOne + ".win:length(5) as eventOne, "
				+ eventNameTwo + ".win:length(5) as eventTwo";
		String textTwo = "select * from someJoinEvent.win:length(5)";

		EPStatement statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);

		statement = epService.getEPAdministrator().createEPL(textTwo);
		statement.addListener(insertListener);

		assertNoCommonProperties();
		assertProperties(insertListener);
	}

	public void testJoinNoCommonProperties() throws Exception
	{
		String eventNameOne = SupportBeanSimple.class.getName();
		String eventNameTwo = SupportMarketDataBean.class.getName();
		String text = "select *, myString||myString as concat " +
				"from " + eventNameOne + ".win:length(5) as eventOne, "
				+ eventNameTwo + ".win:length(5) as eventTwo";

		EPStatement statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);

		assertNoCommonProperties();

		listener.reset();
		epService.initialize();

		text = "select *, myString||myString as concat " +
		"from " + eventNameOne + ".win:length(5) as eventOne, " +
				eventNameTwo + ".win:length(5) as eventTwo " +
				"where eventOne.myString = eventTwo.symbol";

		statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);

		assertNoCommonProperties();
	}

	public void testJoinCommonProperties() throws Exception
	{
		String eventNameOne = SupportBean_A.class.getName();
		String eventNameTwo = SupportBean_B.class.getName();
		String text = "select *, eventOne.id||eventTwo.id as concat " +
				"from " + eventNameOne + ".win:length(5) as eventOne, " +
						eventNameTwo + ".win:length(5) as eventTwo ";

		EPStatement statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);

		assertCommonProperties();

		listener.reset();
		epService.initialize();

		text = "select *, eventOne.id||eventTwo.id as concat " +
			"from " + eventNameOne + ".win:length(5) as eventOne, " +
				eventNameTwo + ".win:length(5) as eventTwo " +
				"where eventOne.id = eventTwo.id";

		statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);

		assertCommonProperties();
	}

	public void testCombinedProperties() throws InterruptedException
	{
		String eventName = SupportBeanCombinedProps.class.getName();
		String text = "select *, indexed[0].mapped('0ma').value||indexed[0].mapped('0mb').value as concat from " + eventName + ".win:length(5)";

		EPStatement statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);
		assertCombinedProps();
	}

	public void testMapEvents()
	{
		Configuration configuration = SupportConfigFactory.getConfiguration();
		Map<String, Class> typeMap = new HashMap<String, Class>();
		typeMap.put("int", Integer.class);
		typeMap.put("string", String.class);
		configuration.addEventTypeAlias("mapEvent", typeMap);
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
		epService = EPServiceProviderManager.getProvider("wildcard map event", configuration);

		String text = "select *, string||string as concat from mapEvent.win:length(5)";

		EPStatement statement = epService.getEPAdministrator().createEPL(text);
		statement.addListener(listener);

		// The map to send into the runtime
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("int", 1);
		props.put("string", "xx");
		epService.getEPRuntime().sendEvent(props, "mapEvent");

		// The map of expected results
		properties.put("int", 1);
		properties.put("string", "xx");
		properties.put("concat", "xxxx");

		assertProperties(listener);
	}

	public void testInvalidRepeatedProperties() throws InterruptedException
	{
		String eventName = SupportBeanSimple.class.getName();
		String text = "select *, myString||myString as myString from " + eventName + ".win:length(5)";

		try
		{
			epService.getEPAdministrator().createEPL(text);
			fail();
		}
		catch(EPException ex)
		{
			//Expected
		}
	}

	private void assertNoCommonProperties() throws InterruptedException
	{
		SupportBeanSimple eventSimple = sendSimpleEvent("string");
		SupportMarketDataBean eventMarket = sendMarketEvent("string");

		EventBean event = listener.getLastNewData()[0];
		properties.put("concat", "stringstring");
		assertProperties(listener);
		assertSame(eventSimple, event.get("eventOne"));
		assertSame(eventMarket, event.get("eventTwo"));
	}

	private void assertSimple() throws InterruptedException
	{
		SupportBeanSimple event = sendSimpleEvent("string");

        assertEquals("stringstring", listener.getLastNewData()[0].get("concat"));
		properties.put("concat", "stringstring");
		properties.put("myString", "string");
		properties.put("myInt", 0);
		assertProperties(listener);

        assertEquals(Pair.class, listener.getLastNewData()[0].getEventType().getUnderlyingType());
        assertTrue(listener.getLastNewData()[0].getUnderlying() instanceof Pair);
        Pair pair = (Pair) listener.getLastNewData()[0].getUnderlying();
        assertEquals(event, pair.getFirst());
        assertEquals("stringstring", ((Map)pair.getSecond()).get("concat"));
    }

	private void assertCommonProperties() throws InterruptedException
	{
		sendABEvents("string");
		EventBean event = listener.getLastNewData()[0];
		properties.put("concat", "stringstring");
		assertProperties(listener);
		assertNotNull(event.get("eventOne"));
		assertNotNull(event.get("eventTwo"));
	}

	private void assertCombinedProps() throws InterruptedException
	{
		sendCombinedProps();
		EventBean eventBean = listener.getLastNewData()[0];

        assertEquals("0ma0", eventBean.get("indexed[0].mapped('0ma').value"));
        assertEquals("0ma1", eventBean.get("indexed[0].mapped('0mb').value"));
        assertEquals("1ma0", eventBean.get("indexed[1].mapped('1ma').value"));
        assertEquals("1ma1", eventBean.get("indexed[1].mapped('1mb').value"));

        assertEquals("0ma0", eventBean.get("array[0].mapped('0ma').value"));
        assertEquals("1ma1", eventBean.get("array[1].mapped('1mb').value"));

        assertEquals("0ma00ma1", eventBean.get("concat"));
	}

	private void assertProperties(SupportUpdateListener listener)
	{
		EventBean event = listener.getLastNewData()[0];
		for(String property : properties.keySet())
		{
			assertEquals(properties.get(property), event.get(property));
		}
	}

	private SupportBeanSimple sendSimpleEvent(String s)
	{
	    SupportBeanSimple bean = new SupportBeanSimple(s, 0);
	    epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

	private SupportMarketDataBean sendMarketEvent(String symbol)
	{
		SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0.0, 0L, null);
		epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

	private void sendABEvents(String id)
	{
		SupportBean_A beanOne = new SupportBean_A(id);
		SupportBean_B beanTwo = new SupportBean_B(id);
		epService.getEPRuntime().sendEvent(beanOne);
		epService.getEPRuntime().sendEvent(beanTwo);
	}

	private void sendCombinedProps()
	{
		epService.getEPRuntime().sendEvent(SupportBeanCombinedProps.makeDefaultBean());
	}
}
