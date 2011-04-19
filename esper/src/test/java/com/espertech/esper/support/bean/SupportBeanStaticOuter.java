package com.espertech.esper.support.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SupportBeanStaticOuter
{
    private SupportBeanStaticInner inside;

    public SupportBeanStaticOuter() {
        this.inside = new SupportBeanStaticInner();
    }

    public SupportBeanStaticInner getInside() {
        return inside;
    }

    public void setInside(SupportBeanStaticInner inside) {
        this.inside = inside;
    }
}
