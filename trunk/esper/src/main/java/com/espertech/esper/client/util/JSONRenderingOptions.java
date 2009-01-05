package com.espertech.esper.client.util;

public class JSONRenderingOptions
{
    private boolean preventLooping;

    public JSONRenderingOptions()
    {
        preventLooping = true;
    }

    public boolean isPreventLooping()
    {
        return preventLooping;
    }

    public JSONRenderingOptions setPreventLooping(boolean preventLooping)
    {
        this.preventLooping = preventLooping;
        return this;
    }
}
