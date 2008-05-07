package com.espertech.esper.support.bean;

public class SupportDeltaFive implements ISupportDeltaFive
{
    private final String k0;
    private final String p1;
    private final String p5;

    public SupportDeltaFive(String k0, String p1, String p5)
    {
        this.k0 = k0;
        this.p1 = p1;
        this.p5 = p5;
    }

    public String getK0()
    {
        return k0;
    }

    public String getP1()
    {
        return p1;
    }

    public String getP5()
    {
        return p5;
    }
}
