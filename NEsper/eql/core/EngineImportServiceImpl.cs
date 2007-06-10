///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;

using net.esper.compat;
using net.esper.eql.agg;
using net.esper.util;

namespace net.esper.eql.core
{
	/// <summary>Implementation for engine-level imports.</summary>
	public class EngineImportServiceImpl : EngineImportService
	{
		private readonly List<String> imports;
	    private readonly EDictionary<String, String> aggregationFunctions;

		/// <summary>Ctor.</summary>
		public EngineImportServiceImpl()
	    {
	        imports = new List<String>();
	        aggregationFunctions = new EHashDictionary<String, String>();
	    }

	    public void AddImport(String importName)
	    {
	        if(!isClassName(importName) && !isPackageName(importName))
	        {
	            throw new EngineImportException("Invalid import name '" + importName + "'");
	        }

	        imports.Add(importName);
	    }

	    public void AddAggregation(String functionName, String aggregationClass)
	    {
	        String existing = aggregationFunctions.Fetch(functionName);
	        if (existing != null)
	        {
	            throw new EngineImportException("Aggregation function by name '" + functionName + "' is already defined");
	        }
	        if(!IsFunctionName(functionName))
	        {
	            throw new EngineImportException("Invalid aggregation function name '" + functionName + "'");
	        }
	        if(!IsClassName(aggregationClass))
	        {
	            throw new EngineImportException("Invalid class name for aggregation '" + aggregationClass + "'");
	        }
	        aggregationFunctions.Put(functionName.ToLowerCase(), aggregationClass);
	    }

	    public AggregationSupport ResolveAggregation(String name)
	    {
            String className = aggregationFunctions.Fetch(name);
	        if (className == null)
	        {
                className = aggregationFunctions.Fetch(name.ToLower());
	        }
	        if (className == null)
	        {
	            throw new EngineImportUndefinedException("Aggregation function named '" + name + "' is not defined");
	        }

	        Type clazz;
	        try
	        {
	            clazz = Type.GetType(className);
	        }
	        catch (TypeLoadException ex)
	        {
	            throw new EngineImportException("Could not load aggregation class by name '" + className + "'", ex);
	        }

	        Object obj = null;
	        try
	        {
	            obj = Activator.CreateInstance(clazz);
	        }
	        catch (InstantiationException e)
	        {
	            throw new EngineImportException("Error instantiating aggregation class by name '" + className + "'", e);
	        }
	        catch (IllegalAccessException e)
	        {
	            throw new EngineImportException("Illegal access instatiating aggregation class by name '" + className + "'", e);
	        }

	        if (!(obj is AggregationSupport))
	        {
	            throw new EngineImportException("Aggregation class by name '" + className + "' does not subclass AggregationSupport");
	        }
	        return (AggregationSupport) obj;
	    }

	    public MethodInfo ResolveMethod(String classNameAlias, String methodName, Type[] paramTypes)
	    {
	        Type type = null;
	        try
	        {
	            type = ResolveClass(classNameAlias);
	        }
	        catch (ClassNotFoundException e)
	        {
	            throw new EngineImportException("Could not load class by name '" + classNameAlias + "' ", e);
	        }

	        try
	        {
	            return StaticMethodResolver.ResolveMethod(clazz, methodName, paramTypes);
	        }
	        catch (NoSuchMethodException e)
	        {
	            throw new EngineImportException("Could not find method named '" + methodName + "' in class '" + classNameAlias + "' ", e);
	        }
	    }

	    /// <summary>
	    /// Finds a class by class name using the auto-import information provided.
	    /// </summary>
	    /// <param name="className">is the class name to find</param>
	    /// <returns>class</returns>
	    /// <throws>ClassNotFoundException if the class cannot be loaded</throws>
	    protected Type ResolveClass(String className)
	    {
			// Attempt to retrieve the class with the name as-is
			try
			{
				return Type.GetType(className);
			}
			catch(TypeLoadException e){}

			// Try all the imports
			foreach (String importName in imports)
			{
				bool isClassName = IsClassName(importName);

				// Import is a class name
				if(isClassName)
				{
					if(importName.EndsWith(className))
					{
						return Type.GetType(importName);
					}
				}
				else
				{
					// Import is a package name
					String prefixedClassName = GetPackageName(importName) + '.' + className;
					try
					{
						return Type.GetType(prefixedClassName);
					}
					catch(TypeLoadException e){}
				}
			}

			// No import worked, the class isn't resolved
			throw new ClassNotFoundException("Unknown class " + className);
		}

	    /// <summary>For testing, returns imports.</summary>
	    /// <returns>returns auto-import list as array</returns>
	    protected String[] GetImports()
		{
			return imports.ToArray();
		}

	    private static bool IsFunctionName(String functionName)
	    {
	        String classNameRegEx = "\\w+";
	        return functionName.Matches(classNameRegEx);
	    }

		private static bool IsClassName(String importName)
		{
			String classNameRegEx = "(\\w+\\.)*\\w+";
			return importName.Matches(classNameRegEx);
		}

		private static bool IsPackageName(String importName)
		{
			String classNameRegEx = "(\\w+\\.)+\\*";
			return importName.Matches(classNameRegEx);
		}

		// Strip off the trailing ".*"
		private static String GetPackageName(String importName)
		{
			return importName.Substring(0, importName.Length - 2);
		}
	}
} // End of namespace
