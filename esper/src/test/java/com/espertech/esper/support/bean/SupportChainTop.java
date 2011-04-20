package com.espertech.esper.support.bean;

public class SupportChainTop
{
    public static SupportChainTop make() {
        return new SupportChainTop();
    }

    public SupportChainChildOne getChildOne(String text, int value)
    {
        return new SupportChainChildOne(text, value);
    }
}
