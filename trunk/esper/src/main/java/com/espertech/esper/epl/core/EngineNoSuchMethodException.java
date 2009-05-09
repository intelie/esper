package com.espertech.esper.epl.core;

import java.lang.reflect.Method;

public class EngineNoSuchMethodException extends Exception
{
    private Method nearestMissMethod;

    public EngineNoSuchMethodException(String message, Method nearestMissMethod)
    {
        super(message);
        this.nearestMissMethod = nearestMissMethod;
    }

    public Method getNearestMissMethod()
    {
        return nearestMissMethod;
    }
}
