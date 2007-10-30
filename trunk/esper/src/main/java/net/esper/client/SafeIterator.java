package net.esper.client;

import java.util.Iterator;

public interface SafeIterator<E> extends Iterator<E>
{
    public void close();
}
