package net.esper.eql.core;

import net.esper.type.MinMaxTypeEnum;
import net.esper.collection.MultiKeyUntyped;
import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.agg.AggregationSupport;

import java.lang.reflect.Method;

public interface MethodResolutionService
{
    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes)
			throws ClassNotFoundException, NoSuchMethodException;

    public AggregationSupport getPlugInAggregator(String name);

    public AggregationMethod getCountAggregator(boolean isIgnoreNull);
    public AggregationMethod getSumAggregator(Class type);
    public AggregationMethod getDistinctAggregator(AggregationMethod aggregationMethod);
    public AggregationMethod getAvgAggregator();
    public AggregationMethod getAvedevAggregator();
    public AggregationMethod getMedianAggregator();
    public AggregationMethod getMinMaxAggregator(MinMaxTypeEnum minMaxType, Class targetType);
    public AggregationMethod getStddevAggregator();

    public AggregationMethod[] newAggregators(AggregationMethod[] prototypes, MultiKeyUntyped groupKey);
}