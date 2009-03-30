package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportBeanIterablePropsContainer implements Serializable
{
    private SupportBeanIterableProps contained;

    public SupportBeanIterablePropsContainer(SupportBeanIterableProps inner)
    {
        this.contained = inner;
    }

    public static SupportBeanIterablePropsContainer makeDefaultBean()
	{
        return new SupportBeanIterablePropsContainer(SupportBeanIterableProps.makeDefaultBean());
	}

    public SupportBeanIterableProps getContained()
    {
        return contained;
    }
}
