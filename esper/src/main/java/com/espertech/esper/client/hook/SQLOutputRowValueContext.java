package com.espertech.esper.client.hook;

import java.util.Map;

public class SQLOutputRowValueContext
{
    private int rowNum;
    private Map<String, Object> values;

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    public Map<String, Object> getValues()
    {
        return values;
    }

    public void setValues(Map<String, Object> values)
    {
        this.values = values;
    }
}
