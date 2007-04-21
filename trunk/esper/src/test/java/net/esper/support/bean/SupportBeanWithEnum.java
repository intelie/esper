package net.esper.support.bean;

public class SupportBeanWithEnum
{
    private String string;
    private SupportEnum supportEnum;

    public SupportBeanWithEnum(String string, SupportEnum supportEnum)
    {
        this.string = string;
        this.supportEnum = supportEnum;
    }

    public String getString()
    {
        return string;
    }

    public SupportEnum getSupportEnum()
    {
        return supportEnum;
    }
}
