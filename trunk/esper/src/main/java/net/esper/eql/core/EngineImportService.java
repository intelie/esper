package net.esper.eql.core;

import java.lang.reflect.Method;

public interface EngineImportService
{
    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes)
			throws ClassNotFoundException, NoSuchMethodException;
}
