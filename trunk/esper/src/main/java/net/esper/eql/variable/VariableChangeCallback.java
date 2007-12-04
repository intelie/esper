package net.esper.eql.variable;

public interface VariableChangeCallback
{
    public void update(Object newValue, Object oldValue);
}
