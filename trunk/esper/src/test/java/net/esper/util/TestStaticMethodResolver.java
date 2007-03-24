package net.esper.util;

import junit.framework.TestCase;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.MethodResolutionServiceImpl;

import java.lang.reflect.Method;

public class TestStaticMethodResolver extends TestCase 
{		
	public void testResolveMethod() throws Exception
	{
        Class declClass = Math.class;
		String methodName = "max";
		Class[] args = new Class[] { int.class, int.class };
		Method expected = Math.class.getMethod(methodName, args);
		assertEquals(expected, StaticMethodResolver.resolveMethod(declClass, methodName, args));
		
		args = new Class[] { long.class, long.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { int.class, long.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(declClass, methodName, args));
		
		args = new Class[] { int.class, int.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { Integer.class, Integer.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(declClass, methodName, args));
		
		args = new Class[] { long.class, long.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { Integer.class, Long.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(declClass, methodName, args));
		
		args = new Class[] { float.class, float.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { Integer.class, Float.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(declClass, methodName, args));
		
        declClass = System.class;
		methodName = "currentTimeMillis";
		args = new Class[0];
		expected = System.class.getMethod(methodName, args);
		assertEquals(expected, StaticMethodResolver.resolveMethod(declClass, methodName, args));
	}
	
	public void testResolveMethodNotFound() throws Exception
	{
        Class declClass = String.class;
		String methodName = "trim";
		Class[] args = null;
		try
		{
			StaticMethodResolver.resolveMethod(declClass, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
		
		
		declClass = Math.class;
		methodName = "moox";
		args = new Class[] { int.class, int.class };
		try
		{
			StaticMethodResolver.resolveMethod(declClass, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
		
		methodName = "max";
		args = new Class[] { boolean.class, boolean.class };
		try
		{
			StaticMethodResolver.resolveMethod(declClass, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
		
		methodName = "max";
		args = new Class[] { int.class, int.class, boolean.class };
		try
		{
			StaticMethodResolver.resolveMethod(declClass, methodName, args);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
	}
}
