package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;

public class PropertyEvalSpec implements MetaDefItem
{
    private String[] propertyNames;

    public PropertyEvalSpec(String[] propertyNames)
    {
        this.propertyNames = propertyNames;
    }

    public String[] getPropertyNames()
    {
        return propertyNames;
    }
}
