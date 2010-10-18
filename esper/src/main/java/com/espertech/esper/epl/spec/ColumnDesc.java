package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

public class ColumnDesc implements MetaDefItem, Serializable
{
    private static final long serialVersionUID = -3508097717971934622L;
    
    private final String name;
    private final String type;
    private final boolean array;

    public ColumnDesc(String name, String type, boolean array)
    {
        this.name = name;
        this.type = type;
        this.array = array;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public boolean isArray()
    {
        return array;
    }
}
