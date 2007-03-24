package net.esper.eql.core;

import net.esper.eql.agg.*;
import net.esper.type.MinMaxTypeEnum;
import net.esper.collection.MultiKeyUntyped;

import java.lang.reflect.Method;

public class MethodResolutionServiceImpl implements MethodResolutionService
{
	private final EngineImportService engineImportService;

    public MethodResolutionServiceImpl(EngineImportService engineImportService)
	{
        this.engineImportService = engineImportService;
	}

    public AggregationSupport getPlugInAggregator(String functionName)
    {
        return null;
    }

    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes)
			throws ClassNotFoundException, NoSuchMethodException
    {
        return engineImportService.resolveMethod(classNameAlias, methodName, paramTypes);
	}       

    public AggregationMethod getCountAggregator(boolean isIgnoreNull)
    {
        if (isIgnoreNull)
        {
            return new NonNullCountAggregator();
        }
        return new CountAggregator();
    }

    public AggregationMethod getSumAggregator(Class type)
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

    public AggregationMethod getDistinctAggregator(AggregationMethod aggregationMethod)
    {
        return new DistinctValueAggregator(aggregationMethod);
    }

    public AggregationMethod getAvgAggregator()
    {
        return new AvgAggregator();
    }

    public AggregationMethod getAvedevAggregator()
    {
        return new AvedevAggregator();
    }

    public AggregationMethod getMedianAggregator()
    {
        return new MedianAggregator();
    }

    public AggregationMethod getMinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Class targetType)
    {
        return new MinMaxAggregator(minMaxTypeEnum, targetType);
    }

    public AggregationMethod getStddevAggregator()
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
