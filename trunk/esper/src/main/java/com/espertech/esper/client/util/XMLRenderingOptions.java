package com.espertech.esper.client.util;

public class XMLRenderingOptions
{
    private boolean preventLooping;
    private boolean defaultAsElement;
    private boolean defaultAsAttribute;

    public XMLRenderingOptions()
    {
        preventLooping = true;
        defaultAsElement = true;
        defaultAsAttribute = false;
    }

    public boolean isPreventLooping()
    {
        return preventLooping;
    }

    public boolean isDefaultAsAttribute()
    {
        return defaultAsAttribute;
    }

    public boolean isDefaultAsElement()
    {
        return defaultAsElement;
    }

    public XMLRenderingOptions setPreventLooping(boolean preventLooping)
    {
        this.preventLooping = preventLooping;
        return this;
    }

    public XMLRenderingOptions setDefaultAsAttribute(boolean defaultAsAttribute)
    {
        this.defaultAsAttribute = defaultAsAttribute;
        return this;
    }

    public XMLRenderingOptions setDefaultAsElement(boolean defaultAsElement)
    {
        this.defaultAsElement = defaultAsElement;
        return this;
    }
}
