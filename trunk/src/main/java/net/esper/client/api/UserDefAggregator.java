package net.esper.client.api;

import net.esper.eql.core.Aggregator;

public interface UserDefAggregator
{
    public void validate();
    public void enter(Object value);
    public void leave(Object value);
    public Object getValue();
    public Class getValueType();
    public Aggregator newAggregator();
}
