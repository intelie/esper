package com.espertech.esper.regression.db;

import com.espertech.esper.client.hook.SQLColumnTypeConversion;
import com.espertech.esper.client.hook.SQLColumnTypeContext;
import com.espertech.esper.client.hook.SQLColumnValueContext;
import com.espertech.esper.client.hook.SQLInputParameterContext;

import java.util.ArrayList;
import java.util.List;

public class SupportSQLColumnTypeConversion implements SQLColumnTypeConversion
{
    private static List<SQLColumnTypeContext> typeContexts;
    private static List<SQLColumnValueContext> valueContexts;
    private static List<SQLInputParameterContext> paramContexts;

    static {
        reset();
    }

    public static void reset() {
        typeContexts = new ArrayList<SQLColumnTypeContext>();
        valueContexts = new ArrayList<SQLColumnValueContext>();
        paramContexts = new ArrayList<SQLInputParameterContext>();
    }

    public static List<SQLColumnTypeContext> getTypeContexts()
    {
        return typeContexts;
    }

    public static List<SQLColumnValueContext> getValueContexts()
    {
        return valueContexts;
    }

    public static List<SQLInputParameterContext> getParamContexts()
    {
        return paramContexts;
    }

    public Class getColumnType(SQLColumnTypeContext sqlColumnTypeContext)
    {
        typeContexts.add(sqlColumnTypeContext);
        return Boolean.class;
    }

    public Object getColumnValue(SQLColumnValueContext valueContext)
    {
        valueContexts.add(valueContext);
        return ((Integer) valueContext.getColumnValue()) >= 50;
    }

    public Object getParameterValue(SQLInputParameterContext inputParameterContext)
    {
        paramContexts.add(inputParameterContext);
        if (inputParameterContext.getParameterValue() instanceof String) {
            return Integer.parseInt(inputParameterContext.getParameterValue().toString().substring(1));
        }
        return inputParameterContext.getParameterValue();
    }
}
