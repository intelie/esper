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
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.agg.*;
import com.espertech.esper.type.MinMaxTypeEnum;
import com.espertech.esper.schedule.TimeProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
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

    /**
     * Ctor.
     * @param engineImportService is the engine imports
     * @param timeProvider returns time
     */
    public MethodResolutionServiceImpl(EngineImportService engineImportService,
                                       TimeProvider timeProvider)
	{
        this.engineImportService = engineImportService;
        this.timeProvider = timeProvider;
    }

    public boolean isUdfCache() {
        return engineImportService.isUdfCache();
    }

    public boolean isDuckType() {
        return engineImportService.isDuckType();
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

    public Constructor resolveCtor(Class clazz, Class[] paramTypes) throws EngineImportException {
        return engineImportService.resolveCtor(clazz, paramTypes);
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

    public AggregationMethod makeCountAggregator(boolean isIgnoreNull, boolean hasFilter)
    {
        if (!hasFilter) {
            if (isIgnoreNull) {
                return new NonNullCountAggregator();
            }
            return new CountAggregator();
        }
        else {
            if (isIgnoreNull) {
                return new NonNullCountFilterAggregator();
            }
            return new CountFilterAggregator();
        }
    }

    public AggregationSupport resolveAggregation(String functionName) throws EngineImportUndefinedException, EngineImportException
    {
        return engineImportService.resolveAggregation(functionName);
    }

    public Pair<Class, String> resolveSingleRow(String functionName) throws EngineImportUndefinedException, EngineImportException
    {
        return engineImportService.resolveSingleRow(functionName);
    }

    public AggregationMethod makeSumAggregator(Class type, boolean hasFilter)
    {
        if (!hasFilter) {
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
        else {
            if (type == BigInteger.class)
            {
                return new BigIntegerSumFilterAggregator();
            }
            if (type == BigDecimal.class)
            {
                return new BigDecimalSumFilterAggregator();
            }
            if ((type == Long.class) || (type == long.class))
            {
                return new LongSumFilterAggregator();
            }
            if ((type == Integer.class) || (type == int.class))
            {
                return new IntegerSumFilterAggregator();
            }
            if ((type == Double.class) || (type == double.class))
            {
                return new DoubleSumFilterAggregator();
            }
            if ((type == Float.class) || (type == float.class))
            {
                return new FloatSumFilterAggregator();
            }
            return new NumIntegerSumFilterAggregator();
        }
    }

    public Class getSumAggregatorType(Class type)
    {
        if (type == BigInteger.class)
        {
            return BigInteger.class;
        }
        if (type == BigDecimal.class)
        {
            return BigDecimal.class;
        }
        if ((type == Long.class) || (type == long.class))
        {
            return Long.class;
        }
        if ((type == Integer.class) || (type == int.class))
        {
            return Integer.class;
        }
        if ((type == Double.class) || (type == double.class))
        {
            return Double.class;
        }
        if ((type == Float.class) || (type == float.class))
        {
            return Float.class;
        }
        return Integer.class;
    }

    public AggregationMethod makeDistinctAggregator(AggregationMethod aggregationMethod, Class childType, boolean hasFilter)
    {
        if (hasFilter) {
            return new DistinctValueFilterAggregator(aggregationMethod, childType);
        }
        return new DistinctValueAggregator(aggregationMethod, childType);
    }

    public AggregationMethod makeAvgAggregator(Class type, boolean hasFilter)
    {
        if (hasFilter) {
            if ((type == BigDecimal.class) || (type == BigInteger.class))
            {
                return new BigDecimalAvgFilterAggregator();
            }
            return new AvgFilterAggregator();
        }
        if ((type == BigDecimal.class) || (type == BigInteger.class))
        {
            return new BigDecimalAvgAggregator();
        }
        return new AvgAggregator();
    }

    public Class getAvgAggregatorType(Class type)
    {
        if ((type == BigDecimal.class) || (type == BigInteger.class))
        {
            return BigDecimal.class;
        }
        return Double.class;
    }

    public AggregationMethod makeAvedevAggregator(boolean hasFilter)
    {
        if (!hasFilter) {
            return new AvedevAggregator();
        }
        else {
            return new AvedevFilterAggregator();
        }
    }

    public AggregationMethod makeMedianAggregator(boolean hasFilter)
    {
        if (!hasFilter) {
            return new MedianAggregator();
        }
        return new MedianFilterAggregator();
    }

    public AggregationMethod makeMinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Class targetType, boolean isHasDataWindows, boolean hasFilter)
    {
        if (!hasFilter) {
            if (!isHasDataWindows) {
                return new MinMaxEverAggregator(minMaxTypeEnum, targetType);
            }
            return new MinMaxAggregator(minMaxTypeEnum, targetType);
        }
        else {
            if (!isHasDataWindows) {
                return new MinMaxEverFilterAggregator(minMaxTypeEnum, targetType);
            }
            return new MinMaxFilterAggregator(minMaxTypeEnum, targetType);
        }
    }

    public AggregationMethod makeStddevAggregator(boolean hasFilter)
    {
        if (!hasFilter) {
            return new StddevAggregator();
        }
        return new StddevFilterAggregator();
    }

    public AggregationMethod makeFirstEverValueAggregator(Class type, boolean hasFilter) {
        if (hasFilter) {
            return new FirstEverValueFilterAggregator(type);
        }
        return new FirstEverValueAggregator(type);
    }

    public AggregationMethod makeLastEverValueAggregator(Class type, boolean hasFilter) {
        if (hasFilter) {
            return new LastEverValueFilterAggregator(type);
        }
        return new LastEverValueAggregator(type);
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

    public long getCurrentRowCount(AggregationMethod[] aggregators, AggregationAccess[] groupAccesses)
    {
        return 0;   // since the aggregators are always fresh ones 
    }

    public void removeAggregators(MultiKeyUntyped groupKey)
    {
        // To be overridden by implementations that care when aggregators get removed
    }

    public AggregationAccess makeAccessStreamId(boolean isJoin, int streamId, MultiKeyUntyped groupKey)
    {
        if (isJoin) {
            return new AggregationAccessJoinImpl(streamId);
        }
        else {
            return new AggregationAccessImpl(streamId);
        }
    }    
}
