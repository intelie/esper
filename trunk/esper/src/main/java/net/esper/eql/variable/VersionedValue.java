package net.esper.eql.variable;

import java.util.Date;

public class VersionedValue<T>
{
    private int version;
    private T value;
    private long timestamp;

    public VersionedValue(int version, T value, long timestamp)
    {
        this.version = version;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getVersion()
    {
        return version;
    }

    public T getValue()
    {
        return value;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public String toString()
    {
        return value + "@" + version + "@" + (new Date(timestamp));
    }
}