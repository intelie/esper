package net.esper.eql.core;

import net.esper.type.MinMaxTypeEnum;
import net.esper.collection.MultiKeyUntyped;
import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.agg.AggregationSupport;

import java.lang.reflect.Method;

/**
 * Service for resolving methods and aggregation functions, and for creating managing aggregation instances.
 */
public interface MethodResolutionService
{
    /**
     * Resolves a given class, method and list of parameter types to a static method.
     * @param classNameAlias is the class name to use
     * @param methodName is the method name
     * @param paramTypes is parameter types match expression sub-nodes
     * @return method this resolves to
     * @throws EngineImportException if the method cannot be resolved to a visible static method
     */    
    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes) throws EngineImportException;

    /**
     * Returns a plug-in aggregation method for a given configured aggregation function name.
     * @param functionName is the aggregation function name
     * @return aggregation-providing class
     * @throws EngineImportUndefinedException is the function name cannot be found
     * @throws EngineImportException if there was an error resolving class information
     */
    public AggregationSupport resolveAggregation(String functionName) throws EngineImportUndefinedException, EngineImportException;

    /**
     * Makes a new plug-in aggregation instance by name.
     * @param name is the plug-in aggregation function name
     * @return new instance of plug-in aggregation method
     */
    public AggregationSupport makePlugInAggregator(String name);

    /**
     * Makes a new count-aggregator.
     * @param isIgnoreNull is true to ignore nulls, or false to count nulls
     * @return aggregator
     */
    public AggregationMethod makeCountAggregator(boolean isIgnoreNull);

    /**
     * Makes a new sum-aggregator.
     * @param type is the type to be summed up, i.e. float, long etc.
     * @return aggregator
     */
    public AggregationMethod makeSumAggregator(Class type);

    /**
     * Makes a new distinct-value-aggregator.
     * @param aggregationMethod is the inner aggregation method
     * @param childType is the return type of the inner expression to aggregate, if any
     * @return aggregator
     */
    public AggregationMethod makeDistinctAggregator(AggregationMethod aggregationMethod, Class childType);

    /**
     * Makes a new avg-aggregator.
     * @return aggregator
     */
    public AggregationMethod makeAvgAggregator();

    /**
     * Makes a new avedev-aggregator.
     * @return aggregator
     */
    public AggregationMethod makeAvedevAggregator();

    /**
     * Makes a new median-aggregator.
     * @return aggregator
     */
    public AggregationMethod makeMedianAggregator();

    /**
     * Makes a new min-max-aggregator.
     * @param minMaxType dedicates whether to do min or max
     * @param targetType is the type to max or min
     * @return aggregator
     */
    public AggregationMethod makeMinMaxAggregator(MinMaxTypeEnum minMaxType, Class targetType);

    /**
     * Makes a new stddev-aggregator.
     * @return aggregator
     */
    public AggregationMethod makeStddevAggregator();

    /**
     * Returns a new set of aggregators given an existing prototype-set of aggregators for a given group key.
     * @param prototypes is the prototypes
     * @param groupKey is the key to group-by for
     * @return new set of aggregators for this group
     */
    public AggregationMethod[] newAggregators(AggregationMethod[] prototypes, MultiKeyUntyped groupKey);
}