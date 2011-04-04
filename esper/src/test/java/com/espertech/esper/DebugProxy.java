package com.espertech.esper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// TODO - remove me
public class DebugProxy implements java.lang.reflect.InvocationHandler {

    private Object obj;

    public static Object newInstance(Object obj) {
        Class[] interfaces = obj.getClass().getInterfaces();
        return java.lang.reflect.Proxy.newProxyInstance(
            obj.getClass().getClassLoader(),
            interfaces,
            new DebugProxy(obj));
    }

    private DebugProxy(Object obj) {
	this.obj = obj;
    }

    public Object invoke(Object proxy, Method m, Object[] args)
	throws Throwable
    {
        Object result;
	try {
	    System.out.println("before method " + m.getName());
	    result = m.invoke(obj, args);
        } catch (InvocationTargetException e) {
	    throw e.getTargetException();
        } catch (Exception e) {
	    throw new RuntimeException("unexpected invocation exception: " +
				       e.getMessage());
	} finally {
	    System.out.println("after method " + m.getName());
	}
	return result;
    }
}
