package com.espertech.esper.util;

public class TypeWidenerBoxedNumeric implements TypeWidener
{
    private final SimpleNumberCoercer coercer;

    /**
     * Ctor.
     * @param coercer the coercer
     */
    public TypeWidenerBoxedNumeric(SimpleNumberCoercer coercer)
    {
        this.coercer = coercer;
    }

    public Object widen(Object input)
    {
       return coercer.coerceBoxed((Number) input);
    }
}
