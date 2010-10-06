package com.espertech.esper.regression.rowrecog;

public class SupportRecogBean
{
    private String string;
    private int value;
    private String cat;

    public SupportRecogBean(String string, int value)
    {
        this.string = string;
        this.value = value;
    }

    public SupportRecogBean(String string, String cat, int value)
    {
        this.string = string;
        this.cat = cat;
        this.value = value;
    }

    public String getString()
    {
        return string;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public void setString(String string)
    {
        this.string = string;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public String toString()
    {
        return string;
    }
}
