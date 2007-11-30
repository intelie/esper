package net.esper.client;

import java.io.Serializable;

public class ConfigurationVariable implements Serializable
{
    private Class type;
    private Object initializationValue;

    public Class getType()
    {
        return type;
    }

    public void setType(Class type)
    {
        this.type = type;
    }

    public Object getInitializationValue()
    {
        return initializationValue;
    }

    public void setInitializationValue(Object initializationValue)
    {
        this.initializationValue = initializationValue;
    }
}
