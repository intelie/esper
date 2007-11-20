package net.esper.support.bean;

public class SupportBeanDynRoot implements SupportMarkerInterface
{
    private Object inner;

    public SupportBeanDynRoot(Object inner)
    {
        this.inner = inner;
    }

    public Object getInner()
    {
        return inner;
    }
}
