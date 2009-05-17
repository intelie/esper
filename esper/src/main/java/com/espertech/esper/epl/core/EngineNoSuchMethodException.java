package com.espertech.esper.epl.core;

import java.lang.reflect.Method;

/**
 * Exception for resolution of a method failed.
 */
public class EngineNoSuchMethodException extends Exception
{
    private Method nearestMissMethod;

    /**
     * Ctor.
     * @param message message
     * @param nearestMissMethod best-match method
     */
    public EngineNoSuchMethodException(String message, Method nearestMissMethod)
    {
        super(message);
        this.nearestMissMethod = nearestMissMethod;
    }

    /**
     * Returns the best-match method.
     * @return method
     */
    public Method getNearestMissMethod()
    {
        return nearestMissMethod;
    }
}
