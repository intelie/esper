package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportBeanInt implements Serializable
{
    private String id;
    private int p00;
    private int p01;
    private int p02;
    private int p03;

    public SupportBeanInt(String id, int p00, int p01, int p02, int p03)
    {
        this.id = id;
        this.p00 = p00;
        this.p01 = p01;
        this.p02 = p02;
        this.p03 = p03;
    }

    public String getId()
    {
        return id;
    }

    public int getP00()
    {
        return p00;
    }

    public int getP01()
    {
        return p01;
    }

    public int getP02()
    {
        return p02;
    }

    public int getP03()
    {
        return p03;
    }
}
