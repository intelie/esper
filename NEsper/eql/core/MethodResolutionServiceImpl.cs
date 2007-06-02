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

	    public MethodInfo ResolveMethod(String classNameAlias, String methodName, Type[] paramTypes)
	    {
	        return engineImportService.ResolveMethod(classNameAlias, methodName, paramTypes);
		}

	    public AggregationMethod MakeCountAggregator(bool isIgnoreNull)
	    {
	        if (isIgnoreNull)
	        {
	            return new NonNullCountAggregator();
	        }
	        return new CountAggregator();
	    }

	    public AggregationSupport ResolveAggregation(String functionName)
	    {
	        return engineImportService.ResolveAggregation(functionName);
	    }

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

	    public AggregationMethod MakeDistinctAggregator(AggregationMethod aggregationMethod)
	    {
	        return new DistinctValueAggregator(aggregationMethod);
	    }

	    public AggregationMethod MakeAvgAggregator()
	    {
	        return new AvgAggregator();
	    }

	    public AggregationMethod MakeAvedevAggregator()
	    {
	        return new AvedevAggregator();
	    }

	    public AggregationMethod MakeMedianAggregator()
	    {
	        return new MedianAggregator();
	    }

	    public AggregationMethod MakeMinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Type targetType)
	    {
	        return new MinMaxAggregator(minMaxTypeEnum, targetType);
	    }

	    public AggregationMethod MakeStddevAggregator()
	    {
	        return new StddevAggregator();
	    }

	    public AggregationMethod[] NewAggregators(AggregationMethod[] prototypes, MultiKeyUntyped groupKey)
	    {
	        AggregationMethod[] row = new AggregationMethod[prototypes.length];
	        for (int i = 0; i < prototypes.length; i++)
	        {
	            row[i] = prototypes[i].NewAggregator(this);
	        }
	        return row;
	    }
	}
} // End of namespace
