package net.esper.core;

public interface ActiveObjectSpace
{
    public void write(Object activeObject);
    public void take(Object activeObject);
}
