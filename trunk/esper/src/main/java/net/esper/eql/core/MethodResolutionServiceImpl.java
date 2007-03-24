package net.esper.eql.core;

import net.esper.eql.agg.*;
import net.esper.type.MinMaxTypeEnum;
import net.esper.collection.MultiKeyUntyped;
import net.esper.client.EPException;

import java.lang.reflect.Method;

/**
 * Implements method resolution.
 */
public class MethodResolutionServiceImpl implements MethodResolutionService
{
	private final EngineImportService engineImportService;

    /**
     * Ctor.
     * @param engineImportService is the engine imports
     */
    public MethodResolutionServiceImpl(EngineImportService engineImportService)
	{
        this.engineImportService = engineImportService;
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

    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes)
			throws EngineImportException
    {
        return engineImportService.resolveMethod(classNameAlias, methodName, paramTypes);
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

    public AggregationMethod makeDistinctAggregator(AggregationMethod aggregationMethod)
    {
        return new DistinctValueAggregator(aggregationMethod);
    }

    public AggregationMethod makeAvgAggregator()
    {
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

    public AggregationMethod makeMinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Class targetType)
    {
        return new MinMaxAggregator(minMaxTypeEnum, targetType);
    }

    public AggregationMethod makeStddevAggregator()
    {
        return new StddevAggregator();
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
}
