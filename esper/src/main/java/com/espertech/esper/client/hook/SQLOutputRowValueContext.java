package com.espertech.esper.client.hook;

import java.util.Map;

/**
 * For use with {@link SQLOutputRowConversion}, context of row conversion.
 * <p>
 * Applications should not retain instances of this class as the engine may change and reuse values here.
 */
public class SQLOutputRowValueContext
{
    private int rowNum;
    private Map<String, Object> values;

    /**
     * Return row number, the number of the current output row.
     * @return row number
     */
    public int getRowNum()
    {
        return rowNum;
    }

    /**
     * Set the row number.
     * @param rowNum row number
     */
    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    /**
     * Returns column values.
     * @return values for all columns
     */
    public Map<String, Object> getValues()
    {
        return values;
    }

    /**
     * Set column values.
     * @param values for all columns
     */
    public void setValues(Map<String, Object> values)
    {
        this.values = values;
    }
}
