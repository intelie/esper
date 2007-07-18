package net.esper.eql.core;

import net.esper.eql.agg.AggregationSupport;
import net.esper.util.StaticMethodResolver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for engine-level imports.
 */
public class EngineImportServiceImpl implements EngineImportService
{
	private final List<String> imports;
    private final Map<String, String> aggregationFunctions;

	/**
	 * Ctor.
	 */
	public EngineImportServiceImpl()
    {
        imports = new ArrayList<String>();
        aggregationFunctions = new HashMap<String, String>();
    }

    public void addImport(String importName) throws EngineImportException
    {
        if(!isClassName(importName) && !isPackageName(importName))
        {
            throw new EngineImportException("Invalid import name '" + importName + "'");
        }

        imports.add(importName);
    }

    public void addAggregation(String functionName, String aggregationClass) throws EngineImportException
    {
        String existing = aggregationFunctions.get(functionName);
        if (existing != null)
        {
            throw new EngineImportException("Aggregation function by name '" + functionName + "' is already defined");
        }
        if(!isFunctionName(functionName))
        {
            throw new EngineImportException("Invalid aggregation function name '" + functionName + "'");
        }
        if(!isClassName(aggregationClass))
        {
            throw new EngineImportException("Invalid class name for aggregation '" + aggregationClass + "'");
        }
        aggregationFunctions.put(functionName.toLowerCase(), aggregationClass);
    }

    public AggregationSupport resolveAggregation(String name) throws EngineImportException, EngineImportUndefinedException
    {
        String className = aggregationFunctions.get(name);
        if (className == null)
        {
            className = aggregationFunctions.get(name.toLowerCase());
        }
        if (className == null)
        {
            throw new EngineImportUndefinedException("Aggregation function named '" + name + "' is not defined");
        }

        Class clazz;
        try
        {
            clazz = Class.forName(className);
        }
        catch (ClassNotFoundException ex)
        {
            throw new EngineImportException("Could not load aggregation class by name '" + className + "'", ex);
        }

        Object object = null;
        try
        {
            object = clazz.newInstance();
        }
        catch (InstantiationException e)
        {
            throw new EngineImportException("Error instantiating aggregation class by name '" + className + "'", e);
        }
        catch (IllegalAccessException e)
        {
            throw new EngineImportException("Illegal access instatiating aggregation class by name '" + className + "'", e);
        }

        if (!(object instanceof AggregationSupport))
        {
            throw new EngineImportException("Aggregation class by name '" + className + "' does not subclass AggregationSupport");
        }
        return (AggregationSupport) object;
    }

    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes)
			throws EngineImportException
    {
        Class clazz = null;
        try
        {
            clazz = resolveClass(classNameAlias);
        }
        catch (ClassNotFoundException e)
        {
            throw new EngineImportException("Could not load class by name '" + classNameAlias + "' ", e);
        }

        try
        {
            return StaticMethodResolver.resolveMethod(clazz, methodName, paramTypes);
        }
        catch (NoSuchMethodException e)
        {
            throw new EngineImportException("Could not find method named '" + methodName + "' in class '" + classNameAlias + "' ", e);
        }
    }

    /**
     * Finds a class by class name using the auto-import information provided.
     * @param className is the class name to find
     * @return class
     * @throws ClassNotFoundException if the class cannot be loaded
     */
    protected Class resolveClass(String className) throws ClassNotFoundException
    {
		// Attempt to retrieve the class with the name as-is
		try
		{
			return Class.forName(className);
		}
		catch(ClassNotFoundException e){}

		// Try all the imports
		for(String importName : imports)
		{
			boolean isClassName = isClassName(importName);

			// Import is a class name
			if(isClassName)
			{
				if(importName.endsWith(className))
				{
					return Class.forName(importName);
				}
			}
			else
			{
				// Import is a package name
				String prefixedClassName = getPackageName(importName) + '.' + className;
				try
				{
					return Class.forName(prefixedClassName);
				}
				catch(ClassNotFoundException e){}
			}
		}

		// No import worked, the class isn't resolved
		throw new ClassNotFoundException("Unknown class " + className);
	}

    /**
     * For testing, returns imports.
     * @return returns auto-import list as array
     */
    protected String[] getImports()
	{
		return imports.toArray(new String[0]);
	}

    private static boolean isFunctionName(String functionName)
    {
        String classNameRegEx = "\\w+";
        return functionName.matches(classNameRegEx);
    }

	private static boolean isClassName(String importName)
	{
		String classNameRegEx = "(\\w+\\.)*\\w+";
		return importName.matches(classNameRegEx);
	}

	private static boolean isPackageName(String importName)
	{
		String classNameRegEx = "(\\w+\\.)+\\*";
		return importName.matches(classNameRegEx);
	}

	// Strip off the final ".*"
	private static String getPackageName(String importName)
	{
		return importName.substring(0, importName.length() - 2);
	}
}
