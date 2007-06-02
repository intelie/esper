///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Reflection;

using net.esper.eql.agg;

namespace net.esper.eql.core
{
	/// <summary>
	/// Service for engine-level resolution of static methods and aggregation methods.
	/// </summary>
	public interface EngineImportService
	{
	    /// <summary>
	    /// Add an import, such as "com.mypackage.*" or "com.mypackage.MyClass".
	    /// </summary>
	    /// <param name="importName">is the import to add</param>
	    /// <throws>EngineImportException if the information or format is invalid</throws>
	    void AddImport(String importName);

	    /// <summary>Add an aggregation function.</summary>
	    /// <param name="functionName">is the name of the function to make known.</param>
	    /// <param name="aggregationClass">is the class that provides the aggregator</param>
	    /// <throws>EngineImportException throw if format or information is invalid</throws>
	    void AddAggregation(String functionName, String aggregationClass);

	    /// <summary>
	    /// Used at statement compile-time to try and resolve a given function name into an
	    /// aggregation method. Matches function name case-neutral.
	    /// </summary>
	    /// <param name="functionName">is the function name</param>
	    /// <returns>aggregation provider</returns>
	    /// <throws>
	    /// EngineImportUndefinedException if the function is not a configured aggregation function
	    /// </throws>
	    /// <throws>
	    /// EngineImportException if the aggregation providing class could not be loaded or doesn't match
	    /// </throws>
	    AggregationSupport ResolveAggregation(String functionName);

	    /// <summary>
	    /// Resolves a given class, method and list of parameter types to a static method.
	    /// </summary>
	    /// <param name="classNameAlias">is the class name to use</param>
	    /// <param name="methodName">is the method name</param>
	    /// <param name="paramTypes">is parameter types match expression sub-nodes</param>
	    /// <returns>method this resolves to</returns>
	    /// <throws>
	    /// EngineImportException if the method cannot be resolved to a visible static method
	    /// </throws>
	    MethodInfo ResolveMethod(String classNameAlias, String methodName, Type[] paramTypes);
	}
} // End of namespace
