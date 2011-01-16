package com.espertech.esper.client.hook;

public interface ConditionHandler {
    /**
     * Handle the engine condition as contained in the context object passed.
     * @param context the condition information
     */
    public void handle(ConditionHandlerContext context);
}
