package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

public class PropertySelect implements Serializable
{
    private static final long serialVersionUID = 0L;

    private SelectClause selectClause;
    private String propertyName;
    private String propertyAsName;
    private Expression whereClause;

    public PropertySelect(String propertyName, String propertyAsName, SelectClause selectClause, Expression whereClause)
    {
        this.propertyName = propertyName;
        this.propertyAsName = propertyAsName;
        this.selectClause = selectClause;
        this.whereClause = whereClause;
    }

    public String getPropertyAsName()
    {
        return propertyAsName;
    }

    public void setPropertyAsName(String propertyAsName)
    {
        this.propertyAsName = propertyAsName;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public SelectClause getSelectClause()
    {
        return selectClause;
    }

    public void setSelectClause(SelectClause selectClause)
    {
        this.selectClause = selectClause;
    }

    public Expression getWhereClause()
    {
        return whereClause;
    }

    public void setWhereClause(Expression whereClause)
    {
        this.whereClause = whereClause;
    }

    public void toEPL(StringWriter writer)
    {
        if (selectClause != null)
        {
            selectClause.toEPL(writer);
            writer.write("from ");
        }
        writer.write(propertyName);
        if (propertyAsName != null)
        {
            writer.write(" as ");
            writer.write(propertyAsName);
        }
        if (whereClause != null)
        {
            whereClause.toEPL(writer);
        }
    }
}
