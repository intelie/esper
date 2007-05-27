package net.esper.support.util;

public class SupportCtorIntObjectArray
{
    private Object[] arguments;
    private int someValue;

    public SupportCtorIntObjectArray(int someValue)
    {
        this.someValue = someValue;
    }

    public SupportCtorIntObjectArray(Object[] arguments)
    {
        this.arguments = arguments;
    }

    public Object[] getArguments()
    {
        return arguments;
    }

    public int getSomeValue()
    {
        return someValue;
    }
}
