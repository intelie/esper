package net.esper.eql.db;

/**
 * Descriptor for SQL output columns.
 */
public class DBOutputTypeDesc
{
    private int sqlType;
    private String className;

    /**
     * Ctor.
     * @param sqlType the type of the column
     * @param className the Java class reflecting column type 
     */
    public DBOutputTypeDesc(int sqlType, String className)
    {
        this.sqlType = sqlType;
        this.className = className;
    }

    public int getSqlType()
    {
        return sqlType;
    }

    public String getClassName()
    {
        return className;
    }

    public String toString()
    {
        return "type=" + sqlType +
                " className=" + className;
    }
}

