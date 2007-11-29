package net.esper.eql.variable;

public class VersionedValue<T>
{
    private int version;
    private T value;

    public VersionedValue(int version, T value)
    {
        this.version = version;
        this.value = value;
    }

    public int getVersion()
    {
        return version;
    }

    public T getValue()
    {
        return value;
    }

    public String toString()
    {
        return value + "@" + version;
    }
}