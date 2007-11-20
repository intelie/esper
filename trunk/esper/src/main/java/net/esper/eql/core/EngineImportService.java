package net.esper.eql.core;

import net.esper.eql.agg.AggregationSupport;

import java.lang.reflect.Method;

/**
 * Service for engine-level resolution of static methods and aggregation methods.
 */
public interface EngineImportService
{
    /**
     * Add an import, such as "com.mypackage.*" or "com.mypackage.MyClass".
     * @param importName is the import to add
     * @throws EngineImportException if the information or format is invalid
     */
    public void addImport(String importName) throws EngineImportException;

    /**
     * Add an aggregation function.
     * @param functionName is the name of the function to make known.
     * @param aggregationClass is the class that provides the aggregator
     * @throws EngineImportException throw if format or information is invalid
     */
    public void addAggregation(String functionName, String aggregationClass) throws EngineImportException;

    /**
     * Used at statement compile-time to try and resolve a given function name into an
     * aggregation method. Matches function name case-neutral.
     * @param functionName is the function name
     * @return aggregation provider
     * @throws EngineImportUndefinedException if the function is not a configured aggregation function
     * @throws EngineImportException if the aggregation providing class could not be loaded or doesn't match
     */
    public AggregationSupport resolveAggregation(String functionName) throws EngineImportUndefinedException, EngineImportException;

    /**
     * Resolves a given class, method and list of parameter types to a static method.
     * @param classNameAlias is the class name to use
     * @param methodName is the method name
     * @param paramTypes is parameter types match expression sub-nodes
     * @return method this resolves to
     * @throws EngineImportException if the method cannot be resolved to a visible static method
     */
    public Method resolveMethod(String classNameAlias, String methodName, Class[] paramTypes) throws EngineImportException;
}
