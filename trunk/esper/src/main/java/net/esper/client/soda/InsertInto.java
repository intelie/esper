package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertInto implements Serializable
{
    private final boolean isIStream;
    private final String streamName;
    private List<String> columnNames;

    public static InsertInto create(String streamName)
    {
        return new InsertInto(streamName);
    }

    public static InsertInto create(String streamName, String ...columns)
    {
        return new InsertInto(streamName, columns);
    }

    public static InsertInto create(String streamName, String[] columns, StreamSelector streamSelector)
    {
        if (streamSelector == StreamSelector.RSTREAM_ISTREAM_BOTH)
        {
            throw new IllegalArgumentException("Insert into only allows istream or rstream selection, not both");
        }
        return new InsertInto(streamName, Arrays.asList(columns), streamSelector != StreamSelector.RSTREAM_ONLY);
    }

    public InsertInto(String streamName)
    {
        this.isIStream = true;
        this.streamName = streamName;
        this.columnNames = new ArrayList<String>();
    }

    public InsertInto(String streamName, String[] columnNames)
    {
        this.isIStream = true;
        this.streamName = streamName;
        this.columnNames = Arrays.asList(columnNames);
    }

    public InsertInto(String streamName, List<String> columnNames, boolean isIStream)
    {
        this.isIStream = isIStream;
        this.streamName = streamName;
        this.columnNames = columnNames;
    }

    /**
     * Returns true if insert (new data) events are fed, or false for remove (old data) events are fed.
     * @return true for insert stream, false for remove stream
     */
    public boolean isIStream()
    {
        return isIStream;
    }

    /**
     * Returns name of stream name to use for insert-into stream.
     * @return stream name
     */
    public String getStreamName()
    {
        return streamName;
    }

    /**
     * Returns a list of column names specified optionally in the insert-into clause, or empty if none specified.
     * @return column names or empty list if none supplied
     */
    public List<String> getColumnNames()
    {
        return columnNames;
    }

    /**
     * Add a column name to the insert-into clause.
     * @param columnName to add
     */
    public void add(String columnName)
    {
        columnNames.add(columnName);
    }


    public void toEQL(StringWriter writer)
    {
        writer.write("insert ");
        if (!isIStream)
        {
            writer.write("rstream ");
        }

        writer.write("into ");
        writer.write(streamName);

        if (columnNames.size() > 0)
        {
            writer.write("(");
            String delimiter = "";
            for (String name : columnNames)
            {
                writer.write(delimiter);
                writer.write(name);
                delimiter = ", ";
            }
            writer.write(")");
        }
        writer.write(' ');
    }
}
