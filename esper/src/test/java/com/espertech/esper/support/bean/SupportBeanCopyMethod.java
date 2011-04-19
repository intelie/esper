package com.espertech.esper.support.bean;

public class SupportBeanCopyMethod
{
    private String valOne;
    private String valTwo;

    public SupportBeanCopyMethod(String valOne, String valTwo)
    {
        this.valOne = valOne;
        this.valTwo = valTwo;
    }

    public String getValOne()
    {
        return valOne;
    }

    public void setValOne(String valOne)
    {
        this.valOne = valOne;
    }

    public String getValTwo()
    {
        return valTwo;
    }

    public void setValTwo(String valTwo)
    {
        this.valTwo = valTwo;
    }

    public SupportBeanCopyMethod myCopyMethod()
    {
        return new SupportBeanCopyMethod(valOne, valTwo);
    }
}
