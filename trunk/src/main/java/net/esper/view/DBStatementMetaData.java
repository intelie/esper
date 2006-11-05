package net.esper.view;

import net.esper.util.SQLTypeMapUtil;

import java.util.*;

public class DBStatementMetaData
{
    private List<DBInputDesc> inputPropertyTypes;
    private Map<String, DBOutputDesc> propertiesOut;

    public DBStatementMetaData()
    {
        inputPropertyTypes = new LinkedList<DBInputDesc>();
        propertiesOut = new HashMap<String, DBOutputDesc>();
    }

    public void addInputParam(String eventPropertyName, int sqlType)
    {
        inputPropertyTypes.add(new DBInputDesc(eventPropertyName, sqlType));
    }

    public void addOutputParam(String columnName, int sqlType, String className)
    {
        propertiesOut.put(columnName, new DBOutputDesc(sqlType, className));
    }

    public Map<String, Class> getOutputEventType()
    {
        Map<String, Class> eventType = new HashMap<String, Class>();
        for (String name : propertiesOut.keySet())
        {
            DBOutputDesc dbOutputDesc = propertiesOut.get(name);
            Class clazz = SQLTypeMapUtil.sqlTypeToClass(dbOutputDesc.getSqlType(), dbOutputDesc.getClassName());
            eventType.put(name, clazz);
        }
        return eventType;
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Input:");
        buffer.append(inputPropertyTypes.toString());
        buffer.append(" Output:");
        buffer.append(propertiesOut.toString());
        return buffer.toString();
    }

    public class DBInputDesc
    {
        private String eventPropName;
        private int sqlType;

        public DBInputDesc(String eventPropName, int sqlType)
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

    public class DBOutputDesc
    {
        private int sqlType;
        private String className;

        public DBOutputDesc(int sqlType, String className)
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
    }
}
