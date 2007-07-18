package net.esper.support.bean;

public class SupportBeanDupProperty
{
    private String myProperty;
    private String MyProperty;
    private String MYPROPERTY;
    private String myproperty;

    public SupportBeanDupProperty(String myProperty, String MyProperty, String MYPROPERTY, String myproperty)
    {
        this.myProperty = myProperty;
        this.MyProperty = MyProperty;
        this.MYPROPERTY = MYPROPERTY;
        this.myproperty = myproperty;
    }

    public String getmyProperty()
    {
        return myProperty;
    }

    public String getMyProperty()
    {
        return MyProperty;
    }

    public String getMYPROPERTY()
    {
        return MYPROPERTY;
    }

    public String getMyproperty()
    {
        return myproperty;
    }
}
