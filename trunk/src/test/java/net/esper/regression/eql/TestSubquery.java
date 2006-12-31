package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.support.util.SupportUpdateListener;

public class TestSubquery extends TestCase
{
	private EPServiceProvider epService;
	private SupportUpdateListener listener;
	private EPStatement statement;
	private String enclosing;
	private String subquery;
	
	protected void setUp()
	{
		epService = EPServiceProviderManager.getDefaultProvider();
		listener = new SupportUpdateListener();
		enclosing = SupportBeanSimple.class.getName();
		subquery = SupportBean.class.getName();
//		System.setProperty("ENABLE_AST_DUMP", "true");
	}
	
	public void testAccepted()
	{
		String text = "select * from " + enclosing  + " where myInt in (select intPrimitive as int from " + subquery + ".win:length(3))";
		statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);
		runAssertion("myInt", "int");
		
		epService.initialize();
		
		text = "select * from " + enclosing  + " where myInt in (select intPrimitive as myInt from " + subquery + ".win:length(3))";
		statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);
		runAssertion("myInt", "myInt");
		
		text = "select * from " + enclosing  + " where 2*myInt - myInt in (select intPrimitive as int from " + subquery + ".win:length(3))";
		statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);	
		runAssertion("(2*myInt-myInt)", "int");
	}
	
	public void testSameEventType()
	{
		String text = "select * from " + enclosing  + " where myInt in (select myInt as int from " + enclosing + ".win:length(3))";
		statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);
		
		sendEnclosingEvent(1);
		assertAccepted("myInt", 1);
		
		sendEnclosingEvent(2);
		assertAccepted("myInt", 2);
		
		sendSubqueryEvent(2);
		assertNotAccepted();
	}
	
	public void testInvalidMultipleSelectedColumns()
	{
		String text = "select * from " + enclosing  + " where myInt in (select intPrimitive, intBoxed as int from " + subquery + ".win:length(3))";
		try
		{
			statement = epService.getEPAdministrator().createEQL(text);
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
	
	
	private void runAssertion(String outsideProperty, String insideProperty) 
	{
		sendSubqueryEvent(1);
		sendEnclosingEvent(1);
		assertAccepted(outsideProperty, 1);
		
		sendEnclosingEvent(2);
		assertNotAccepted();
		
		sendSubqueryEvent(2);
		sendEnclosingEvent(2);
		assertAccepted(outsideProperty, 2);

		sendSubqueryEvent(3);
		sendSubqueryEvent(4);
		sendEnclosingEvent(2);
		assertAccepted(outsideProperty, 2);
		
		sendSubqueryEvent(5);
		sendEnclosingEvent(2);
		assertNotAccepted();

		// Stop and start the statement
		statement.stop();

		sendSubqueryEvent(6);

		statement.start();

		// Events sent while the statement was stopped have no effect
		sendEnclosingEvent(3);
		assertAccepted(outsideProperty, 3);

		sendEnclosingEvent(6);
		assertNotAccepted();
		
		// The subquery should be active once again
		sendSubqueryEvent(6);

		sendEnclosingEvent(3);
		assertNotAccepted();
		
		sendEnclosingEvent(6);
		assertAccepted(outsideProperty, 6);
	}
	
	private void assertAccepted(String property, int value)
	{
		assertTrue(listener.getAndClearIsInvoked());
		assertEquals(1, listener.getLastNewData().length);
		assertEquals(value, listener.getLastNewData()[0].get(property));
	}
	
	private void assertNotAccepted()
	{
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	private void sendEnclosingEvent(int value)
	{
		Object event = new SupportBeanSimple("", value);
		epService.getEPRuntime().sendEvent(event);
	}
	
	private void sendSubqueryEvent(int value)
	{
		Object event = new SupportBean("", value);
		epService.getEPRuntime().sendEvent(event);
	}
}
