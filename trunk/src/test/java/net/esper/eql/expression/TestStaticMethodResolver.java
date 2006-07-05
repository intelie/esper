package net.esper.eql.expression;

import java.lang.reflect.Method;
import java.util.LinkedList;

import junit.framework.TestCase;

public class TestStaticMethodResolver extends TestCase 
{
	public void testResolveClass() throws Exception
	{
		String className = "java.lang.Math";
		Class expected = Math.class;
		assertEquals(expected, StaticMethodResolver.resolveClass(className));
		
		className = "Math";
		assertEquals(expected, StaticMethodResolver.resolveClass(className));
		
		className = "Integer";
		expected = Integer.class;
		assertEquals(expected, StaticMethodResolver.resolveClass(className));
		
		expected = LinkedList.class;
		className = "java.util.LinkedList";
		assertEquals(expected, StaticMethodResolver.resolveClass(className));
		
		expected = Math.class;
		className = "java.lang.Math";
		assertEquals(expected, StaticMethodResolver.resolveClass(className));
	}
	
	public void testResolveClassNotFound() throws Exception
	{
		String className = "LinkedList";
		try
		{
			StaticMethodResolver.resolveClass(className);
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
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args));
		
		args = new Class[] { long.class, long.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { int.class, long.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args));
		
		className = "System";
		methodName = "currentTimeMillis";
		args = new Class[0];
		expected = System.class.getMethod(methodName, args);
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args));
	}
	
	public void testResolveMethodNotFound() throws Exception
	{
		String className = "String";
		String methodName = "trim";
		Class[] args = null;
		try
		{
			StaticMethodResolver.resolveMethod(className, methodName, args);
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
			StaticMethodResolver.resolveMethod(className, methodName, args);
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
			StaticMethodResolver.resolveMethod(className, methodName, args);
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
			StaticMethodResolver.resolveMethod(className, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
	}
}
