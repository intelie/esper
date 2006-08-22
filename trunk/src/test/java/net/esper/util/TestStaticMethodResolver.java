package net.esper.util;

import java.lang.reflect.Method;

import net.esper.eql.expression.AutoImportService;
import net.esper.eql.expression.AutoImportServiceImpl;
import net.esper.util.StaticMethodResolver;

import junit.framework.TestCase;

public class TestStaticMethodResolver extends TestCase 
{
	private AutoImportService autoImportService;
	
	public void setUp()
	{
		autoImportService = new AutoImportServiceImpl(new String[] {"java.lang.*"});
	}
	
	public void testResolveMethod() throws Exception
	{
		String className = "Math";
		String methodName = "max";
		Class[] args = new Class[] { int.class, int.class };
		Method expected = Math.class.getMethod(methodName, args);
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
		
		args = new Class[] { long.class, long.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { int.class, long.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
		
		args = new Class[] { int.class, int.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { Integer.class, Integer.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
		
		args = new Class[] { long.class, long.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { Integer.class, Long.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
		
		args = new Class[] { float.class, float.class };
		expected = Math.class.getMethod(methodName, args);
		args = new Class[] { Integer.class, Float.class };
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
		
		className = "System";
		methodName = "currentTimeMillis";
		args = new Class[0];
		expected = System.class.getMethod(methodName, args);
		assertEquals(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
	}
	
	public void testResolveMethodNotFound() throws Exception
	{
		String className = "String";
		String methodName = "trim";
		Class[] args = null;
		try
		{
			StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
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
			StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
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
			StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
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
			StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
			fail();
		}
		catch(NoSuchMethodException e)
		{
			// Expected
		}
	}
}
