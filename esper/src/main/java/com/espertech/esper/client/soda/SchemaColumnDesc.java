package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

public class SchemaColumnDesc implements Serializable
{
    private static final long serialVersionUID = 5068685531968720148L;

    private String name;
    private String type;
    private boolean array;

    public SchemaColumnDesc() {
    }

    public SchemaColumnDesc(String name, String type, boolean array)
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

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setArray(boolean array)
    {
        this.array = array;
    }

    public void toEPL(StringWriter writer) {
        writer.write(name);
        writer.write(' ');
        writer.write(type);
        if (array) {
            writer.write("[]");
        }
    }

}
