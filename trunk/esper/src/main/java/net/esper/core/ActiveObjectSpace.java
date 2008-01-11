package net.esper.core;

public interface ActiveObjectSpace
{
    public Object getSubscriber(String subscriberId);
    public void write(Object activeObject);
    public void take(Object activeObject);
}
