package net.esper.eql.expression;

import java.lang.reflect.Method;
import java.util.LinkedList;

import junit.framework.TestCase;

public class testStaticMethodResolver extends TestCase 
{
	StaticMethodResolver resolver;
	
	public void setUp()
	{
		resolver = new StaticMethodResolver();
	}
	
	public void testIsValidParameterConversion()
	{
		assertTrue(StaticMethodResolver.isValidTypeConversion(double.class, Double.class));
		assertTrue(StaticMethodResolver.isValidTypeConversion(double.class, Byte.class));
		assertTrue(StaticMethodResolver.isValidTypeConversion(boolean.class, Boolean.class));
		assertTrue(StaticMethodResolver.isValidTypeConversion(Boolean.class, boolean.class));
	
		assertFalse(StaticMethodResolver.isValidTypeConversion(Boolean.class, double.class));
		assertFalse(StaticMethodResolver.isValidTypeConversion(double.class, Boolean.class));
		assertFalse(StaticMethodResolver.isValidTypeConversion(int.class, Long.class));
		assertFalse(StaticMethodResolver.isValidTypeConversion(int.class, long.class));
	
		assertTrue(StaticMethodResolver.isValidTypeConversion(ExprNode.class, ExprStaticMethodNode.class));
		assertFalse(StaticMethodResolver.isValidTypeConversion(ExprStaticMethodNode.class, ExprNode.class));
	}
	
	public void testResolveClass() throws Exception
	{
		String className = "java.lang.Math";
		Class expected = Math.class;
		assertEquals(expected, resolver.resolveClass(className));
		
		className = "Math";
		assertEquals(expected, resolver.resolveClass(className));
		
		className = "Integer";
		expected = Integer.class;
		assertEquals(expected, resolver.resolveClass(className));
		
		expected = LinkedList.class;
		className = "java.util.LinkedList";
		assertEquals(expected, resolver.resolveClass(className));
		
		resolver.setClassPath(new String[] { "java.lang", "java.util" });
		className = "LinkedList";
		assertEquals(expected, resolver.resolveClass(className));
		
		expected = Math.class;
		className = "java.lang.Math";
		assertEquals(expected, resolver.resolveClass(className));
	}
	
	public void testResolveClassNotFound() throws Exception
	{
		String className = "LinkedList";
		try
		{
			resolver.resolveClass(className);
			fail();
		}
		catch(ClassNotFoundException e)
		{
			// expected
		}
		
		resolver.setClassPath(new String[] { "java.util" });
		className = "Math";
		try
		{
			resolver.resolveClass(className);
			fail();
		}
		catch(ClassNotFoundException e)
		{
			// expected
		}
		
	}
	
	public void testResolveMethod() throws Exception
	{
		String className = "Math";
		String methodName = "max";
		Class[] args = new Class[] { int.class, int.class };
		Method expected = Math.class.getMethod(methodName, args);
		assertEquals(expected, resolver.resolveMethod(className, methodName, args));
		
		args = new Class[] { long.class, long.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { int.class, long.class };
		assertEquals(expected, resolver.resolveMethod(className, methodName, args));
		
		className = "System";
		methodName = "currentTimeMillis";
		args = new Class[0];
		expected = System.class.getMethod(methodName, args);
		assertEquals(expected, resolver.resolveMethod(className, methodName, args));
		assertEquals(expected, resolver.resolveMethod(className, methodName, null));
	}
	
	public void testResolveMethodNotFound() throws Exception
	{
		String className = "String";
		String methodName = "trim";
		Class[] args = null;
		try
		{
			resolver.resolveMethod(className, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
		
		
		className = "Math";
		methodName = "moox";
		args = new Class[] { int.class, int.class };
		try
		{
			resolver.resolveMethod(className, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
		
		className = "Math";
		methodName = "max";
		args = new Class[] { boolean.class, boolean.class };
		try
		{
			resolver.resolveMethod(className, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
		
		className = "Math";
		methodName = "max";
		args = new Class[] { int.class, int.class, boolean.class };
		try
		{
			resolver.resolveMethod(className, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
	}
}
