package com.espertech.esper.client.hook;

public class SQLColumnValueContext
{
    private String columnName;
    private int columnNumber;
    private Object columnValue;

    public SQLColumnValueContext()
    {
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public int getColumnNumber()
    {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber)
    {
        this.columnNumber = columnNumber;
    }

    public Object getColumnValue()
    {
        return columnValue;
    }

    public void setColumnValue(Object columnValue)
    {
        this.columnValue = columnValue;
    }
}
