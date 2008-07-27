package com.espertech.esper.support.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;

public class SupportBeanNumeric implements Serializable
{
    private int intOne;
    private int intTwo;
    private BigInteger bigint;
    private BigDecimal bigdec;
    private double doubleOne;
    private double doubleTwo;

    public SupportBeanNumeric(int intOne, int intTwo, BigInteger bigint, BigDecimal bigdec, double doubleOne, double doubleTwo)
    {
        this.intOne = intOne;
        this.intTwo = intTwo;
        this.bigint = bigint;
        this.bigdec = bigdec;
        this.doubleOne = doubleOne;
        this.doubleTwo = doubleTwo;
    }

    public SupportBeanNumeric(int intOne, int intTwo)
    {
        this.intOne = intOne;
        this.intTwo = intTwo;
    }

    public SupportBeanNumeric(BigInteger bigint, BigDecimal bigdec)
    {
        this.bigint = bigint;
        this.bigdec = bigdec;
    }

    public int getIntOne()
    {
        return intOne;
    }

    public int getIntTwo()
    {
        return intTwo;
    }

    public BigInteger getBigint()
    {
        return bigint;
    }

    public BigDecimal getBigdec()
    {
        return bigdec;
    }

    public double getDoubleOne()
    {
        return doubleOne;
    }

    public double getDoubleTwo()
    {
        return doubleTwo;
    }

    public void setIntOne(int intOne)
    {
        this.intOne = intOne;
    }

    public void setIntTwo(int intTwo)
    {
        this.intTwo = intTwo;
    }

    public void setBigint(BigInteger bigint)
    {
        this.bigint = bigint;
    }

    public void setBigdec(BigDecimal bigdec)
    {
        this.bigdec = bigdec;
    }

    public void setDoubleOne(double doubleOne)
    {
        this.doubleOne = doubleOne;
    }

    public void setDoubleTwo(double doubleTwo)
    {
        this.doubleTwo = doubleTwo;
    }
}
