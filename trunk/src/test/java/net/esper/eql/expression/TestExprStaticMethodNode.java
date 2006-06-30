package net.esper.eql.expression;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.esper.support.eql.SupportStreamTypeSvc1Stream;

public class TestExprStaticMethodNode extends TestCase 
{
	ExprNode intThree;
	ExprNode intFive;
	ExprNode shortNine;
	ExprNode doubleFour;
	ExprNode doubleEight;
	ExprNode stringTen;
	
	protected void setUp() throws Exception 
	{
		intThree = new ExprConstantNode(3);
		intFive = new ExprConstantNode(5);
		short nine = 9;
		shortNine = new ExprConstantNode(nine);
		doubleFour = new ExprConstantNode(4d);
		doubleEight = new ExprConstantNode(8d);
		stringTen = new ExprConstantNode("10");
	}
	
	public void testMaxIntInt() throws Exception
	{
		Method staticMethod = Math.class.getMethod("max", new Class[] { int.class, int.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
		root.addChildNode(intThree);
		root.addChildNode(intFive);
		
		Integer result = Math.max(3,5);
		assertEquals(result, root.evaluate(null));
	}
	
	public void testIntegerInt() throws Exception
	{
		Method parentMethod = this.getClass().getMethod("staticIntMethod", Integer.class);
		ExprNode parent = new ExprStaticMethodNode(parentMethod);
		Method childMethod = Math.class.getMethod("max", new Class[] { int.class, int.class });
		ExprNode child = new ExprStaticMethodNode(childMethod);
		child.addChildNode(intThree);
		child.addChildNode(intFive);
		parent.addChildNode(child);

		int result = Math.max(3, 5);
		assertEquals(result, parent.evaluate(null));
	}
	
	public void testMaxIntShort() throws Exception
	{
		Method staticMethod = Math.class.getMethod("max", new Class[] { int.class, int.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
		root.addChildNode(intThree);
		root.addChildNode(shortNine);
		
		short nine = 9;
		Integer result = Math.max(3,nine);
		assertEquals(result, root.evaluate(null));
	}
	
	public void testMaxDoubleInt() throws Exception
	{
		Method staticMethod = Math.class.getMethod("max", new Class[] { double.class, double.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
		root.addChildNode(doubleEight);
		root.addChildNode(intFive);
		
		Double result = Math.max(8d,5);
		assertEquals(result, root.evaluate(null));
	}
	
	public void testMaxDoubleDouble() throws Exception
	{
		Method staticMethod = Math.class.getMethod("max", new Class[] { double.class, double.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
		root.addChildNode(doubleEight);
		root.addChildNode(doubleFour);
		
		Double result = Math.max(8d,4d);
		assertEquals(result, root.evaluate(null));
	}
	
	public void testPowDoubleDouble() throws Exception
	{
		Method staticMethod = Math.class.getMethod("pow", new Class[] { double.class, double.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
		root.addChildNode(doubleEight);
		root.addChildNode(doubleFour);
		
		Double result = Math.pow(8d,4d);
		assertEquals(result, root.evaluate(null));
	}
	
	public void testValueOfInt() throws Exception
	{
		Method staticMethod = Integer.class.getMethod("valueOf", new Class[] { String.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
		root.addChildNode(stringTen);
		
		Integer result = Integer.valueOf("10");
		assertEquals(result, root.evaluate(null));
	}
	
	public void testConstructor() throws Exception
	{
		ExprStaticMethodNode root;
		
		try 
		{
			root = new ExprStaticMethodNode(null);
			fail();
		} 
		catch (NullPointerException e) 
		{
			// Expected
		}
		
		try 
		{
			root = new ExprStaticMethodNode(this.getClass().getMethod("nonstaticMethod", null));
			fail();
		} 
		catch (IllegalArgumentException e) 
		{
			// Expected
		}
		
		root = new ExprStaticMethodNode(this.getClass().getMethod("staticMethod", null));
		assertEquals(this.getClass().getMethod("staticMethod", null), root.getStaticMethod());
	}
	
	public void testEqualsNode() throws Exception
	{
		Method staticMethodOne = Math.class.getMethod("max", new Class[] { int.class, int.class });
		Method staticMethodTwo = Math.class.getMethod("max", new Class[] { double.class, double.class });		
		
		ExprNode rootOne = new ExprStaticMethodNode(staticMethodOne);
		ExprNode rootTwo = new ExprStaticMethodNode(staticMethodOne);
		ExprNode rootThree = new ExprStaticMethodNode(staticMethodTwo);
		ExprNode rootFour = new ExprConstantNode(3d);

		assertTrue(rootOne.equalsNode(rootTwo));
		assertFalse(rootOne.equalsNode(rootThree));
		assertFalse(rootOne.equalsNode(rootFour));
		assertFalse(rootOne.equalsNode(null));
	}
	
	public void testGetType() throws Exception
	{
		Method staticMethod = Math.class.getMethod("max", new Class[] { int.class, int.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
		assertEquals(staticMethod.getReturnType(), root.getType());
	}
	
	public void testValidate() throws Exception
	{
		Method staticMethod = Math.class.getMethod("max", new Class[] { int.class, int.class });
	    StreamTypeService streamTypeService = new SupportStreamTypeSvc1Stream();
	    
		ExprNode root = new ExprStaticMethodNode(staticMethod);	    
		root.addChildNode(intFive);
	    root.addChildNode(intThree);
	    root.validate(streamTypeService);
	    
	    root = new ExprStaticMethodNode(staticMethod);	    
		root.addChildNode(intFive);
	    root.addChildNode(shortNine);
	    root.validate(streamTypeService);
	}
	
	public void testValidateInvalid() throws Exception
	{
		Method staticMethod = Math.class.getMethod("max", new Class[] { int.class, int.class });
		ExprNode root = new ExprStaticMethodNode(staticMethod);
	    StreamTypeService streamTypeService = new SupportStreamTypeSvc1Stream();

	    root.addChildNode(intFive);
	    try
		{
			root.validate(streamTypeService);
			fail();
		}
		catch(ExprValidationException e)
		{
			// Expected
		}
		
		root.addChildNode(doubleFour);
	    try
		{
			root.validate(streamTypeService);
			fail();
		}
		catch(ExprValidationException e)
		{
			// Expected
		}
		
	    root.addChildNode(intFive);
	    try
		{
			root.validate(streamTypeService);
			fail();
		}
		catch(ExprValidationException e)
		{
			// Expected
		}
	}
	
	public void nonstaticMethod(){}
	
	public static void staticMethod(){}
	
	public static int staticIntMethod(Integer param){return param;}
}
