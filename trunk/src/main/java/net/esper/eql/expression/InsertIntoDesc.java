package net.esper.eql.expression;

import java.util.List;
import java.util.LinkedList;

public class InsertIntoDesc
{
    private final boolean isIStream;
    private final String eventTypeAlias;
    private List<String> columnNames;

    public InsertIntoDesc(boolean isIStream, String eventTypeAlias)
    {
        this.isIStream = isIStream;
        this.eventTypeAlias = eventTypeAlias;
        columnNames = new LinkedList<String>();
    }

    public boolean isIStream()
    {
        return isIStream;
    }

    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }

    public List<String> getColumnNames()
    {
        return columnNames;
    }

    public void add(String columnName)
    {
        columnNames.add(columnName);
    }
}
