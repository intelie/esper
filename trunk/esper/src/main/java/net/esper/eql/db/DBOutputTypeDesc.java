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

    /**
     * Returns the SQL type of the output column.
     * @return sql type
     */
    public int getSqlType()
    {
        return sqlType;
    }

    /**
     * Returns the class name that getObject() on the output column produces.
     * @return class name from statement metadata
     */
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

