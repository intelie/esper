package net.esper.eql.db;

/**
 * Descriptor for input parameters to SQL statement execution.
 */
public class DBInputParameterDesc
{
    private String eventPropName;
    private int sqlType;

    /**
     * Ctor.
     * @param eventPropName is the stream-name and property-name (complex) is the SQL string
     * @param sqlType is the type returned by the statement metadata for this parameter
     */
    public DBInputParameterDesc(String eventPropName, int sqlType)
    {
        this.eventPropName = eventPropName;
        this.sqlType = sqlType;
    }

    public String getEventPropName()
    {
        return eventPropName;
    }

    public int getSqlType()
    {
        return sqlType;
    }

    public String toString()
    {
        return "eventPropName=" + eventPropName +
                " sqlType=" + sqlType;
    }
}
