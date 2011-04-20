package com.espertech.esper.epl.core;

import java.lang.reflect.Constructor;

/**
 * Exception for resolution of a method failed.
 */
public class EngineNoSuchCtorException extends Exception
{
    private static final long serialVersionUID = 5903661121726479172L;

    private transient Constructor nearestMissCtor;

    /**
     * Ctor.
     * @param message message
     * @param nearestMissCtor best-match method
     */
    public EngineNoSuchCtorException(String message, Constructor nearestMissCtor)
    {
        super(message);
        this.nearestMissCtor = nearestMissCtor;
    }

    /**
     * Returns the best-match ctor.
     * @return ctor
     */
    public Constructor getNearestMissCtor()
    {
        return nearestMissCtor;
    }
}
