package com.espertech.esper.support.bean;

public class SupportChainChildTwo
{
    private String text;
    private int value;

    public SupportChainChildTwo(String text, int value)
    {
        this.text = text;
        this.value = value;
    }

    public String getText()
    {
        return text;
    }

    public int getValue()
    {
        return value;
    }
}
