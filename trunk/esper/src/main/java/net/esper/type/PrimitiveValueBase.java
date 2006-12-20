package net.esper.type;

/**
 * Abstract class for literal values supplied in an event expression string and prepared expression values supplied
 * by set methods.
 */
public abstract class PrimitiveValueBase implements PrimitiveValue
{
    public void parse(String[] values)
    {
        parse(values[0]);
    }

    public void setBoolean(boolean x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public void setByte(byte x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public void setDouble(double x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public void setFloat(float x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public void setInt(int x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public void setLong(long x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public void setShort(short x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public void setString(String x) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}
