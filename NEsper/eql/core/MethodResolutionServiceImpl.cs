///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Reflection;

using net.esper.client;
using net.esper.collection;
using net.esper.eql.agg;
using net.esper.type;

namespace net.esper.eql.core
{
	/// <summary>
    /// Implements method resolution.
    /// </summary>
	
    public class MethodResolutionServiceImpl : MethodResolutionService
	{
		private readonly EngineImportService engineImportService;

	    /// <summary>Ctor.</summary>
	    /// <param name="engineImportService">is the engine imports</param>
	    public MethodResolutionServiceImpl(EngineImportService engineImportService)
		{
	        this.engineImportService = engineImportService;
		}

        /// <summary>
        /// Makes the plug in aggregator.
        /// </summary>
        /// <param name="functionName">Name of the function.</param>
        /// <returns></returns>
	    public AggregationSupport MakePlugInAggregator(String functionName)
	    {
	        try
	        {
	            return engineImportService.ResolveAggregation(functionName);
	        }
	        catch (EngineImportUndefinedException e)
	        {
	            throw new EPException("Failed to make new aggregation function instance for '" + functionName + "'", e);
	        }
	        catch (EngineImportException e)
	        {
	            throw new EPException("Failed to make new aggregation function instance for '" + functionName + "'", e);
	        }
	    }

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
	    public MethodInfo ResolveMethod(String classNameAlias, String methodName, Type[] paramTypes)
	    {
	        return engineImportService.ResolveMethod(classNameAlias, methodName, paramTypes);
		}

        /// <summary>
        /// Makes a new count-aggregator.
        /// </summary>
        /// <param name="isIgnoreNull">is true to ignore nulls, or false to count nulls</param>
        /// <returns>aggregator</returns>
	    public AggregationMethod MakeCountAggregator(bool isIgnoreNull)
	    {
	        if (isIgnoreNull)
	        {
	            return new NonNullCountAggregator();
	        }
	        return new CountAggregator();
	    }

        /// <summary>
        /// Returns a plug-in aggregation method for a given configured aggregation function name.
        /// </summary>
        /// <param name="functionName">is the aggregation function name</param>
        /// <returns>aggregation-providing class</returns>
        /// <throws>EngineImportUndefinedException is the function name cannot be found</throws>
        /// <throws>
        /// EngineImportException if there was an error resolving class information
        /// </throws>
	    public AggregationSupport ResolveAggregation(String functionName)
	    {
	        return engineImportService.ResolveAggregation(functionName);
	    }

        /// <summary>
        /// Makes a new sum-aggregator.
        /// </summary>
        /// <param name="type">is the type to be summed up, i.e. float, long etc.</param>
        /// <returns>aggregator</returns>
	    public AggregationMethod MakeSumAggregator(Type type)
	    {
	        if ((type == typeof(long?)) || (type == typeof(long)))
	        {
	            return new LongSumAggregator();
	        }
	        if ((type == typeof(int?)) || (type == typeof(int)))
	        {
	            return new IntegerSumAggregator();
	        }
	        if ((type == typeof(double?)) || (type == typeof(double)))
	        {
	            return new DoubleSumAggregator();
	        }
	        if ((type == typeof(float?)) || (type == typeof(float)))
	        {
	            return new FloatSumAggregator();
	        }
	        return new NumIntegerSumAggregator();
	    }

        /// <summary>
        /// Makes a new distinct-value-aggregator.
        /// </summary>
        /// <param name="aggregationMethod">is the inner aggregation method</param>
        /// <returns>aggregator</returns>
	    public AggregationMethod MakeDistinctAggregator(AggregationMethod aggregationMethod)
	    {
	        return new DistinctValueAggregator(aggregationMethod);
	    }

        /// <summary>
        /// Makes a new avg-aggregator.
        /// </summary>
        /// <returns>aggregator</returns>
	    public AggregationMethod MakeAvgAggregator()
	    {
	        return new AvgAggregator();
	    }

        /// <summary>
        /// Makes a new avedev-aggregator.
        /// </summary>
        /// <returns>aggregator</returns>
	    public AggregationMethod MakeAvedevAggregator()
	    {
	        return new AvedevAggregator();
	    }

        /// <summary>
        /// Makes a new median-aggregator.
        /// </summary>
        /// <returns>aggregator</returns>
	    public AggregationMethod MakeMedianAggregator()
	    {
	        return new MedianAggregator();
	    }

        /// <summary>
        /// Makes the min max aggregator.
        /// </summary>
        /// <param name="minMaxTypeEnum">The min max type enum.</param>
        /// <param name="targetType">Type of the target.</param>
        /// <returns></returns>
	    public AggregationMethod MakeMinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Type targetType)
	    {
	        return new MinMaxAggregator(minMaxTypeEnum, targetType);
	    }

        /// <summary>
        /// Makes a new stddev-aggregator.
        /// </summary>
        /// <returns>aggregator</returns>
	    public AggregationMethod MakeStddevAggregator()
	    {
	        return new StddevAggregator();
	    }

        /// <summary>
        /// Returns a new set of aggregators given an existing prototype-set of aggregators for a given group key.
        /// </summary>
        /// <param name="prototypes">is the prototypes</param>
        /// <param name="groupKey">is the key to group-by for</param>
        /// <returns>new set of aggregators for this group</returns>
	    public AggregationMethod[] NewAggregators(AggregationMethod[] prototypes, MultiKeyUntyped groupKey)
	    {
	        AggregationMethod[] row = new AggregationMethod[prototypes.Length];
	        for (int i = 0; i < prototypes.Length; i++)
	        {
	            row[i] = prototypes[i].NewAggregator(this);
	        }
	        return row;
	    }
	}
} // End of namespace
