/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

import com.espertech.esper.client.EPException;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.agg.*;
import com.espertech.esper.type.MinMaxTypeEnum;
import com.espertech.esper.schedule.TimeProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Implements method resolution.
 */
public class MethodResolutionServiceImpl implements MethodResolutionService
{
    private static final Log log = LogFactory.getLog(MethodResolutionServiceImpl.class);
	private final EngineImportService engineImportService;
    private final TimeProvider timeProvider;
    private final boolean isUdfCache;

    /**
     * Ctor.
     * @param engineImportService is the engine imports
     * @param isUdfCache returns true to cache UDF results for constant parameter sets
     */
    public MethodResolutionServiceImpl(EngineImportService engineImportService,
                                       TimeProvider timeProvider,
                                       boolean isUdfCache)
	{
        this.engineImportService = engineImportService;
        this.timeProvider = timeProvider;
        this.isUdfCache = isUdfCache;
    }

    public boolean isUdfCache()
    {
        return isUdfCache;
    }

    public AggregationSupport makePlugInAggregator(String functionName)
    {
        try
        {
            return engineImportService.resolveAggregation(functionName);
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

    public Method resolveMethod(String className, String methodName, Class[] paramTypes)
			throws EngineImportException
    {
        return engineImportService.resolveMethod(className, methodName, paramTypes);
	}

    public Method resolveMethod(String className, String methodName)
			throws EngineImportException
    {
        return engineImportService.resolveMethod(className, methodName);
	}

    public Class resolveClass(String className)
			throws EngineImportException
    {
        return engineImportService.resolveClass(className);
	}

    public Method resolveMethod(Class clazz, String methodName, Class[] paramTypes) throws EngineImportException
    {
        return engineImportService.resolveMethod(clazz, methodName, paramTypes);
    }

    public AggregationMethod makeCountAggregator(boolean isIgnoreNull)
    {
        if (isIgnoreNull)
        {
            return new NonNullCountAggregator();
        }
        return new CountAggregator();
    }

    public AggregationSupport resolveAggregation(String functionName) throws EngineImportUndefinedException, EngineImportException
    {
        return engineImportService.resolveAggregation(functionName);
    }

    public AggregationMethod makeSumAggregator(Class type)
    {
        if (type == BigInteger.class)
        {
            return new BigIntegerSumAggregator();
        }
        if (type == BigDecimal.class)
        {
            return new BigDecimalSumAggregator();
        }
        if ((type == Long.class) || (type == long.class))
        {
            return new LongSumAggregator();
        }
        if ((type == Integer.class) || (type == int.class))
        {
            return new IntegerSumAggregator();
        }
        if ((type == Double.class) || (type == double.class))
        {
            return new DoubleSumAggregator();
        }
        if ((type == Float.class) || (type == float.class))
        {
            return new FloatSumAggregator();
        }
        return new NumIntegerSumAggregator();
    }

    public AggregationMethod makeDistinctAggregator(AggregationMethod aggregationMethod, Class childType)
    {
        return new DistinctValueAggregator(aggregationMethod, childType);
    }

    public AggregationMethod makeAvgAggregator(Class type)
    {
        if ((type == BigDecimal.class) || (type == BigInteger.class))
        {
            return new BigDecimalAvgAggregator();
        }
        return new AvgAggregator();
    }

    public AggregationMethod makeAvedevAggregator()
    {
        return new AvedevAggregator();
    }

    public AggregationMethod makeMedianAggregator()
    {
        return new MedianAggregator();
    }

    public AggregationMethod makeMinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Class targetType, boolean isHasDataWindows)
    {
        if (!isHasDataWindows) {
            return new MinMaxEverAggregator(minMaxTypeEnum, targetType);
        }
        return new MinMaxAggregator(minMaxTypeEnum, targetType);
    }

    public AggregationMethod makeStddevAggregator()
    {
        return new StddevAggregator();
    }

    public AggregationMethod makeFirstValueAggregator(Class type) {
        return new FirstValueAggregator(type);
    }

    public AggregationMethod makeLastValueAggregator(Class type) {
        return new LastValueAggregator(type);
    }

    public AggregationMethod makeRateAggregator() {
        return new RateAggregator();
    }

    public AggregationMethod makeRateEverAggregator(long interval) {
        return new RateEverAggregator(interval, timeProvider);
    }

    public AggregationMethod makeNthAggregator(Class returnType, int size) {
        return new NthAggregator(returnType, size);
    }

    public AggregationMethod makeLeavingAggregator() {
        return new LeavingAggregator();
    }

    public void setGroupKeyTypes(Class[] groupKeyTypes)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Group key typed are " + Arrays.toString(groupKeyTypes));
        }
    }

    public AggregationMethod[] newAggregators(AggregationMethod[] prototypes, MultiKeyUntyped groupKey)
    {
        AggregationMethod row[] = new AggregationMethod[prototypes.length];
        for (int i = 0; i < prototypes.length; i++)
        {
            row[i] = prototypes[i].newAggregator(this);
        }
        return row;
    }

    public long getCurrentRowCount(AggregationMethod[] aggregators)
    {
        return 0;   // since the aggregators are always fresh ones 
    }

    public void removeAggregators(MultiKeyUntyped groupKey)
    {
        // To be overridden by implementations that care when aggregators get removed
    }
}
