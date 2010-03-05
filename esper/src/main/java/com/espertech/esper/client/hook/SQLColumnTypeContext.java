package com.espertech.esper.client.hook;

public class SQLColumnTypeContext
{
    private final String db;
    private final String sql;
    private final String columnName;
    private final Class columnClassType;
    private final int columnSqlType;
    private final int columnNumber;

    public SQLColumnTypeContext(String db, String sql, String columnName, Class columnClassType, int columnSqlType, int columnNumber)
    {
        this.db = db;
        this.sql = sql;
        this.columnName = columnName;
        this.columnClassType = columnClassType;
        this.columnSqlType = columnSqlType;
        this.columnNumber = columnNumber;
    }

    public String getDb()
    {
        return db;
    }

    public String getSql()
    {
        return sql;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public Class getColumnClassType()
    {
        return columnClassType;
    }

    public int getColumnSqlType()
    {
        return columnSqlType;
    }

    public int getColumnNumber()
    {
        return columnNumber;
    }
}
