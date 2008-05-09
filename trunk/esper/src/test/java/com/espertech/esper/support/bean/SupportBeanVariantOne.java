package com.espertech.esper.support.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class SupportBeanVariantOne implements Serializable
{
    private ISupportB p0;
    private ISupportAImplSuperG p1;
    private ArrayList p2;

    public ISupportB getP0()
    {
        return new ISupportABCImpl("a", "b", "baseAB", "c");
    }

    public ISupportAImplSuperG getP1()
    {
        return new ISupportAImplSuperGImpl("g","a","baseAB");
    }

    public ArrayList getP2()
    {
        return p2;
    }
}
