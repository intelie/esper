package net.esper.eql.core;

import net.esper.util.StaticMethodResolver;
import net.esper.eql.agg.AggregationMethod;
import net.esper.client.ConfigurationPlugInAggregationFunction;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Method;

public class EngineImportServiceImpl implements EngineImportService
{
	private final List<String> imports = new ArrayList<String>();
    private final Map<String, String> aggregationFunctions = new HashMap<String, String>();

	/**
	 * Ctor.
	 * @param imports - the package and class names that will be used to resolve partial class names
	 */
	public EngineImportServiceImpl(String[] imports, List<ConfigurationPlugInAggregationFunction> aggregationFunctions)
	{
		if(imports == null)
		{
			throw new NullPointerException("Array of auto imports cannot be null");
		}

		for(String importName : imports)
		{
			addImport(importName);
		}

        if (aggregationFunctions != null)
        {
            for(ConfigurationPlugInAggregationFunction function : aggregationFunctions)
            {
                addAggregationFunction(function.getName(), function.getFunctionClassName());
            }
        }
    }

    /**
     * Ctor.
     */
    public EngineImportServiceImpl()
    {
    }

    public AggregationMethod resolveAggregationFunction(String name)
    {
        String className = aggregationFunctions.get(name);
        if (className == null)
        {
            return null;
        }

        Class clazz;
        try
        {
            clazz = Class.forName(className);
        }
        catch(ClassNotFoundException e)
        {
            return null; // TODO
        }
        return null; // TODO
    }

    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes)
			throws ClassNotFoundException, NoSuchMethodException
    {
        Class clazz = resolveClass(classNameAlias);
        return StaticMethodResolver.resolveMethod(clazz, methodName, paramTypes);
	}

    protected Class resolveClass(String className)
			throws ClassNotFoundException
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

    /**
     * Add a package to the auto-import list, for testing.
     * @param importName to add
     */
    protected void addImport(String importName)
	{
		if(!isClassName(importName) && !isPackageName(importName))
		{
			throw new IllegalArgumentException("Invalid import name " + importName);
		}
		imports.add(importName);
	}

    protected void addAggregationFunction(String functionName, String className)
    {
        if(!isFunctionName(functionName))
        {
            throw new IllegalArgumentException("Invalid function name " + functionName);
        }
        if(!isClassName(className))
        {
            throw new IllegalArgumentException("Invalid class name " + className);
        }
    }

    private static boolean isFunctionName(String functionName)
    {
        String classNameRegEx = "\\w";
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
