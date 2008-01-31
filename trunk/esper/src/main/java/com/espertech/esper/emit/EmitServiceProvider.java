package com.espertech.esper.emit;

/**
 * Static factory for implementations of the EmitService interface.
 */
public final class EmitServiceProvider
{
    /**
     * Creates an implementation of the EmitService interface.
     * @return implementation
     */
    public static EmitService newService()
    {
        return new EmitServiceImpl();
    }
}
