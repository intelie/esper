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
	        aggregationFunctions = new HashDictionary<String, String>();
	    }

	    public void AddImport(String importName)
	    {
	        if(!IsTypeNameOrNamespace(importName))
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
	        if(!IsTypeNameOrNamespace(aggregationClass))
	        {
	            throw new EngineImportException("Invalid class name for aggregation '" + aggregationClass + "'");
	        }
	        aggregationFunctions.Put(functionName.ToLower(), aggregationClass);
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

	        Type type;

	        try
	        {
	            type = TypeHelper.ResolveType(className);
	        }
	        catch (TypeLoadException ex)
	        {
	            throw new EngineImportException("Could not load aggregation class by name '" + className + "'", ex);
	        }

	        Object obj;
            try
            {
                obj = Activator.CreateInstance(type);
            }
            catch (TypeLoadException e)
            {
                throw new EngineImportException("Error instantiating aggregation class", e);
            }
            catch (MethodAccessException e)
            {
                throw new EngineImportException("Error instantiating aggregation class - Caller does not have permission to use constructor", e);
            }
            catch (ArgumentException e)
            {
                throw new EngineImportException("Error instantiating aggregation class - Type is not a RuntimeType", e);
            }

	        if (!(obj is AggregationSupport))
	        {
	            throw new EngineImportException("Aggregation class by name '" + className + "' does not subclass AggregationSupport");
	        }
	        return (AggregationSupport) obj;
	    }

	    public MethodInfo ResolveMethod(String classNameAlias, String methodName, Type[] paramTypes)
	    {
	        Type type;
	        try
	        {
	            type = ResolveType(classNameAlias);
	        }
	        catch (TypeLoadException e)
	        {
	            throw new EngineImportException("Could not load class by name '" + classNameAlias + "' ", e);
	        }

	        try
	        {
	            return StaticMethodResolver.ResolveMethod(type, methodName, paramTypes);
	        }
	        catch (MissingMethodException e)
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
	    public Type ResolveType(String className)
	    {
			// Attempt to retrieve the class with the name as-is
			try
			{
			    return TypeHelper.ResolveType(className);
			}
			catch(TypeLoadException){}

			// Try all the imports
			foreach (String importName in imports)
			{
				// Test as a class name
				if(importName.EndsWith(className))
				{
					Type type = TypeHelper.ResolveType(importName);
                    if ( type != null )
                    {
                        return type;
                    }
				}
				else
				{
					// Import is a namespace
					String prefixedClassName = importName + '.' + className;
					try
					{
                        Type type = TypeHelper.ResolveType(prefixedClassName);
                        if ( type != null )
                        {
                            return type;
                        }
					}
					catch(TypeLoadException){}
				}
			}

			// No import worked, the class isn't resolved
			throw new TypeLoadException("Unknown class " + className);
		}

	    /// <summary>For testing, returns imports.</summary>
	    /// <returns>returns auto-import list as array</returns>
	    public String[] Imports
		{
	    	get { return imports.ToArray(); }
		}

        private static readonly Regex functionRegEx = new Regex(@"^\w+$", RegexOptions.None);
        
        private static bool IsFunctionName(String functionName)
	    {
            return functionRegEx.IsMatch(functionName);
	    }

        private static readonly Regex typeNameRegEx = new Regex(@"^(\w+\.)*\w+$", RegexOptions.None);

		private static bool IsTypeNameOrNamespace(String importName)
		{
            return typeNameRegEx.IsMatch(importName);
		}
	}
} // End of namespace
