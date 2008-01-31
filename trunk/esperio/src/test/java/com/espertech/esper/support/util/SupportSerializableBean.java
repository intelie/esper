package com.espertech.esper.support.util;

import java.io.Serializable;

public class SupportSerializableBean implements Serializable
{
    private String string;

    public SupportSerializableBean(String string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }
} 