package net.esper.regression.view;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.eql.SupportStaticMethodLib;
import net.esper.support.util.SupportUpdateListener;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

public class TestStaticFunctions extends TestCase 
{
	 private EPServiceProvider epService;
	private String stream;
	private String statementText;
	private EPStatement statement;
	private SupportUpdateListener listener;
	
	protected void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider();
	    epService.initialize();
	    stream = " from " + SupportMarketDataBean.class.getName() +".win:length(5) ";
	}
	
	public void testRuntimeException()
	{
		String className = SupportStaticMethodLib.class.getName();
		statementText = "select price, " + className + ".throwException() " + stream;
		try
		{
			createStatementAndGetProperty("price");
			fail();
		}
		catch(EPException e)
		{
			// Expected
		}
	}
	
	public void testAutoImports()
	{
		Configuration configuration = new Configuration();
		configuration.addImport("mull");
		epService = EPServiceProviderManager.getProvider("1", configuration);
		
		statementText = "select Integer.toBinaryString(7) " + stream;
		try
		{
			createStatementAndGetProperty("Integer.toBinaryString(7)");
			fail();
		}
		catch(EPStatementException e)
		{
			// expected
		}
		
		configuration.addImport("java.lang.*");
		epService = EPServiceProviderManager.getProvider("2", configuration);
		
		Object result = createStatementAndGetProperty("Integer.toBinaryString(7)");
		assertEquals(Integer.toBinaryString(7), result);
	}
	
	public void testNoParameters()
	{
		Long startTime = System.currentTimeMillis();
		statementText = "select System.currentTimeMillis() " + stream;
		Long result = (Long)createStatementAndGetProperty("System.currentTimeMillis()");
		Long finishTime = System.currentTimeMillis();
		assertTrue(startTime <= result);
		assertTrue(result <= finishTime);
		
		statementText = "select java.lang.ClassLoader.getSystemClassLoader() " + stream;
		Object expected = ClassLoader.getSystemClassLoader();
		Object resultTwo = createStatementAndGetProperty("java.lang.ClassLoader.getSystemClassLoader()");
		assertEquals(expected, resultTwo);
		
		statementText = "select UnknownClass.invalidMethod() " + stream;
		try
		{
			createStatementAndGetProperty("invalidMethod()");
			fail();
		}
		catch(EPStatementException e)
		{
			// Expected
		}
	}
	
	public void testSingleParameter()
	{
		statementText = "select Integer.toBinaryString(7) " + stream;
		Object result = createStatementAndGetProperty("Integer.toBinaryString(7)");
		assertEquals(Integer.toBinaryString(7), result);
		
		statementText = "select Integer.valueOf(\"6\") " + stream;
		result = createStatementAndGetProperty("Integer.valueOf(\"6\")");
		assertEquals(Integer.valueOf("6"), result);
		
		statementText = "select java.lang.String.valueOf(\'a\') " + stream;
		result = createStatementAndGetProperty("java.lang.String.valueOf(\"a\")");
		assertEquals(String.valueOf('a'), result);
	}
	
	public void testTwoParameters()
	{
		statementText = "select Math.max(2, 3) " + stream;
		assertEquals(3, createStatementAndGetProperty("Math.max(2, 3)"));

		statementText = "select java.lang.Math.max(2, 3d) " + stream;
		assertEquals(3d, createStatementAndGetProperty("java.lang.Math.max(2, 3.0)"));

		statementText = "select Long.parseLong(\"123\", 10)" + stream;
		Object expected = Long.parseLong("123", 10);
		assertEquals(expected, createStatementAndGetProperty("Long.parseLong(\"123\", 10)"));
	}
	
	public void testUserDefined()
	{
		String className = SupportStaticMethodLib.class.getName();
		statementText = "select " + className + ".staticMethod(2)" + stream;
		assertEquals(2, createStatementAndGetProperty(className + ".staticMethod(2)"));
	}
	
	public void testComplexParameters()
	{
		statementText = "select String.valueOf(price) " + stream;
		Object result = createStatementAndGetProperty("String.valueOf(price)");
		assertEquals(String.valueOf(10d), result);
		
		statementText = "select String.valueOf(2 + 3*5) " + stream;
		result = createStatementAndGetProperty("String.valueOf((2+(3*5)))");
		assertEquals(String.valueOf(17), result);
		
		statementText = "select String.valueOf(price*volume +volume) " + stream;
		result = createStatementAndGetProperty("String.valueOf(((price*volume)+volume))");
		assertEquals(String.valueOf(44d), result);

		statementText = "select String.valueOf(Math.pow(price, Integer.valueOf(\"2\"))) " + stream;
		result = createStatementAndGetProperty("String.valueOf(Math.pow(price, Integer.valueOf(\"2\")))");
		assertEquals(String.valueOf(100d), result);
	}
	
	public void testMultipleMethodInvocations()
	{
		statementText = "select Math.max(2d, price), Math.max(volume, 4d)" + stream;
		Object[] props = createStatementAndGetProperty("Math.max(2.0, price)", "Math.max(volume, 4.0)");
		assertEquals(10d, props[0]);
		assertEquals(4d, props[1]);
	}
	
	public void testOtherClauses()
	{
		// where
		statementText = "select *" + stream + "where Math.pow(price, .5) > 2";
		assertEquals("IBM", createStatementAndGetProperty("symbol"));
		sendEvent("CAT", 4d, 100);
		assertNull(getProperty("symbol"));
		
		// group-by
		statementText = "select symbol, sum(price)" + stream + "group by String.valueOf(symbol)";
		assertEquals(10d, createStatementAndGetProperty("sum(price)"));
		sendEvent("IBM", 4d, 100);
		assertEquals(14d, getProperty("sum(price)"));
		
		epService.initialize();
		
		// having
		statementText = "select symbol, sum(price)" + stream + "having Math.pow(sum(price), .5) > 10";
		assertEquals(null, createStatementAndGetProperty("sum(price)"));
		sendEvent("IBM", 100d, 100);
		assertEquals(110d, getProperty("sum(price)"));
		
		// order-by
		statementText = "select symbol, price" + stream + "output every 3 events order by Math.pow(price, 2)";
		assertEquals(null, createStatementAndGetProperty("symbol"));
		sendEvent("CAT", 10d, 0L);
		sendEvent("MAT", 3d, 0L);
		
		EventBean[] newEvents = listener.getAndResetLastNewData();
		assertTrue(newEvents.length == 3);
		assertEquals("MAT", newEvents[0].get("symbol"));
		assertEquals("IBM", newEvents[1].get("symbol"));
		assertEquals("CAT", newEvents[2].get("symbol"));
	}
	
	private Object createStatementAndGetProperty(String propertyName)
	{
		statement = epService.getEPAdministrator().createEQL(statementText);
		listener = new SupportUpdateListener();
		statement.addListener(listener);
		epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 10d, 4l, ""));
		return getProperty(propertyName);
	}
	
	private Object getProperty(String propertyName)
	{
		EventBean[] newData = listener.getAndResetLastNewData();
		if(newData == null || newData.length == 0)
		{
			return null;
		}
		else
		{
			return newData[0].get(propertyName);
		}
	}
	
	private Object[] createStatementAndGetProperty(String... propertyNames)
	{
		statement = epService.getEPAdministrator().createEQL(statementText);
		listener = new SupportUpdateListener();
		statement.addListener(listener);
		sendEvent("IBM", 10d, 4l);
	
		List<Object> properties = new ArrayList<Object>();
		EventBean event = listener.getAndResetLastNewData()[0];
		for(String propertyName : propertyNames)
		{
			properties.add(event.get(propertyName));
		}
		return properties.toArray(new Object[0]);
	}
	
	private void sendEvent(String symbol, double price, long volume)
	{
		epService.getEPRuntime().sendEvent(new SupportMarketDataBean(symbol, price, volume, ""));
	}
	
}
