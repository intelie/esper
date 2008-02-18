package com.espertech.esper.core;

/**
 * Interface for statement-level dispatch.
 * <p>
 * Relevant when a statements callbacks have completed and the join processing must take place.
 */
public interface EPStatementDispatch
{
    /**
     * Execute dispatch.
     */
    public void execute();
}
