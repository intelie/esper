package com.espertech.esper.epl.variable;

/**
 * A callback interface for indicating a change in variable value.
 */
public interface VariableChangeCallback
{
    /**
     * Indicate a change in variable value.
     * @param newValue new value
     * @param oldValue old value
     */
    public void update(Object newValue, Object oldValue);
}
