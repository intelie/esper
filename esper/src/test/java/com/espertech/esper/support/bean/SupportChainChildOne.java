package com.espertech.esper.support.bean;

public class SupportChainChildOne
{
    private String text;
    private int value;

    public SupportChainChildOne(String text, int value)
    {
        this.text = text;
        this.value = value;
    }

    public SupportChainChildTwo getChildTwo(String append) {
        return new SupportChainChildTwo(text + append, 1 + value);
    }
}
