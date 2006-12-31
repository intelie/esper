package net.esper.eql.expression;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.eql.core.StreamTypeService;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;

public class TestExprInStmtNode extends TestCase
{
	private EPServiceProvider epService;
	private ExprNode inNode;
	private ExprNode identChildNode;
	private ExprNode mathChildNode;
	private EPStatement beanStatement;
	private EPStatement mapStatement;
	private EPStatement invalidStatement;
	
	protected void setUp() throws Exception
	{
		Map<String, Class> typeMap = new HashMap<String, Class>();
		typeMap.put("myInt", Integer.class);
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias("mapEvent", typeMap);
		epService = EPServiceProviderManager.getProvider("ExprInSetNode provider", configuration);
		
		identChildNode = SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0");
		mathChildNode = SupportExprNodeFactory.makeMathNode();
		
		String beanText = "select myInt from " + SupportBeanSimple.class.getName() + ".win:length(2)";
		beanStatement = epService.getEPAdministrator().createEQL(beanText);
		
		String mapText = "select * from mapEvent.win:length(2)";
		mapStatement = epService.getEPAdministrator().createEQL(mapText);
	
		String invalidText = "select myInt, myString from " + SupportBeanSimple.class.getName() + ".win:length(2)";
		invalidStatement = epService.getEPAdministrator().createEQL(invalidText);
	}
	
	public void testValid() 
	{	
		runAssertIdent(false, beanStatement);
		runAssertIdent(true, beanStatement);
		runAssertIdent(false, mapStatement);
		
		runAssertMath(false, beanStatement);
		runAssertMath(true, beanStatement);
		runAssertMath(false, mapStatement);
	}
	
	public void testInvalid()
	{
		StreamTypeService streamTypeService = new SupportStreamTypeSvc3Stream();
		
		// 0 child nodes
		try
		{
			inNode = new ExprInStmtNode(false, beanStatement);
			inNode.validate(streamTypeService, null);
			fail();
		}
		catch(ExprValidationException ex)
		{
			// Expected
		}
		
		// 2 child nodes
		try
		{
			inNode = new ExprInStmtNode(false, beanStatement);
			inNode.addChildNode(identChildNode);
			inNode.addChildNode(identChildNode);
			inNode.validate(streamTypeService, null);
			fail();
		}
		catch(ExprValidationException ex)
		{
			// Expected
		}
		
		// Statement that selects more than 1 property
		try
		{
			inNode = new ExprInStmtNode(false, invalidStatement);
			fail();
		}
		catch(IllegalArgumentException ex)
		{
			// Expected
		}
	}
	
	public void testEqualsNode()
	{
		ExprNode other;
		
		// Same isNot in, same statement
		inNode = new ExprInStmtNode(false, beanStatement);
		other = new ExprInStmtNode(false, beanStatement);
		
		assertTrue(inNode.equalsNode(other));
		assertTrue(other.equalsNode(inNode));
		
		// Different isNot in, same statement
		inNode = new ExprInStmtNode(false, beanStatement);
		other = new ExprInStmtNode(true, beanStatement);
		
		assertFalse(inNode.equalsNode(other));
		assertFalse(other.equalsNode(inNode));
		
		// Same isNotIn, different statement
		inNode = new ExprInStmtNode(false, beanStatement);
		other = new ExprInStmtNode(false, mapStatement);
		
		assertFalse(inNode.equalsNode(other));
		assertFalse(other.equalsNode(inNode));
	}
	
	public void testToExpressionString() throws ExprValidationException
	{
		inNode = new ExprInStmtNode(false, beanStatement);
		inNode.addChildNode(identChildNode);
		
		assertEquals("s0.intPrimitive in (" + beanStatement.getText() + ")", inNode.toExpressionString());

		inNode = new ExprInStmtNode(true, beanStatement);
		inNode.addChildNode(identChildNode);
		assertEquals("s0.intPrimitive not in (" + beanStatement.getText() + ")", inNode.toExpressionString());
	}

	private void runAssertMath(boolean isNotIn, EPStatement statement) 
	{
		inNode = new ExprInStmtNode(isNotIn, statement);
		inNode.addChildNode(mathChildNode);
		
		sendEvent(1, statement);
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(0, 1)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(1, 1)));
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(2, 1)));
		
		sendEvent(2, statement);
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(0, 1)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(1, 1)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(2, 1)));
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(3, 1)));
		
		// The length of the subquery window is 2, the first 
		// event therefore falls off
		sendEvent(3, statement);
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(1, 1)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(2, 1)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(3, 1)));
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(4, 1)));
	}

	private void runAssertIdent(boolean isNotIn, EPStatement statement) 
	{
		inNode = new ExprInStmtNode(isNotIn, statement);
		inNode.addChildNode(identChildNode);
		
		sendEvent(1, statement);
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(0)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(1)));
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(2)));
		
		sendEvent(2, statement);
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(0)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(1)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(2)));
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(3)));
		
		// The length of the subquery window is 2, the first 
		// event therefore falls off
		sendEvent(3, statement);
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(1)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(2)));
		assertTrue(isNotIn ^ (Boolean) inNode.evaluate(createEvent(3)));
		assertFalse(isNotIn ^ (Boolean) inNode.evaluate(createEvent(4)));
	}
	
	private EventBean[] createEvent(int intPrimitive)
	{
		Object object = new SupportBean("", intPrimitive);
		EventBean event = SupportEventAdapterService.getService().adapterForBean(object);
		return new EventBean[] { event };
	}
	
	private EventBean[] createEvent(int intPrimitive, Integer intBoxed)
	{
		SupportBean object = new SupportBean("", intPrimitive);
		object.setIntBoxed(intBoxed);
		EventBean event = SupportEventAdapterService.getService().adapterForBean(object);
		return new EventBean[] { event };
	}
	
	public void sendEvent(int myInt, EPStatement statement)
	{
		if(statement == beanStatement)
		{
			Object bean = new SupportBeanSimple("", myInt);
			epService.getEPRuntime().sendEvent(bean);
		}
		else
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("myInt", myInt);
			epService.getEPRuntime().sendEvent(map, "mapEvent");
		}
	}
}
