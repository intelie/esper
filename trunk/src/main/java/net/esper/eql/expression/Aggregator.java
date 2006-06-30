package net.esper.eql.expression;

/**
 * Maintains aggregation state applying values as entering and leaving the state.
 * <P>Implementations must also act as a factory for further independent copies of aggregation states such that
 * new aggregation state holders and be created from a prototype.
 */
public interface Aggregator
{
    /**
     * Apply the value as entering aggregation (entering window).
     * <p>The value can be null since 'null' values may be counted as unique separate values.
     * @param value to add to aggregate
     */
    public void enter(Object value);

    /**
     * Apply the value as leaving aggregation (leaving window).
     * <p>The value can be null since 'null' values may be counted as unique separate values.
     * @param value to remove from aggregate
     */
    public void leave(Object value);

    /**
     * Returns the current value held.
     * @return current value
     */
    public Object getValue();

    /**
     * Returns the type of the current value.
     * @return type of values held
     */
    public Class getValueType();

    /**
     * Make a new, initalized aggregation state.
     * @return initialized copy of the aggregator
     */
    public Aggregator newAggregator();
}
