package com.espertech.esper.client.hook;

import java.util.Map;

public class SQLOutputRowTypeContext
{
    private final String db;
    private final String sql;
    private final Map<String, Object> fields;

    public SQLOutputRowTypeContext(String db, String sql, Map<String, Object> fields)
    {
        this.db = db;
        this.sql = sql;
        this.fields = fields;
    }

    public String getDb()
    {
        return db;
    }

    public String getSql()
    {
        return sql;
    }

    public Map<String, Object> getFields()
    {
        return fields;
    }
}
