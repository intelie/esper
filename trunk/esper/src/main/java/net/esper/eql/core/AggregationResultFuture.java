package net.esper.eql.core;

/**
 * Interface for use by aggregate expression nodes representing aggregate functions such as 'sum' or 'avg' to use
 * to obtain the current value for the function at time of expression evaluation.
 */
public interface AggregationResultFuture
{
    /**
     * Returns current aggregation state, for use by expression node representing an aggregation function.
     * @param column is assigned to the aggregation expression node and passed as an column (index) into a row
     * @return current aggragation state
     */
    public Object getValue(int column);
}
