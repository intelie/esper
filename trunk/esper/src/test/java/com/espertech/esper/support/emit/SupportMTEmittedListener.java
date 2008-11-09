package com.espertech.esper.support.emit;

import com.espertech.esper.client.EmittedListener;

import java.util.List;
import java.util.LinkedList;

public class SupportMTEmittedListener implements EmittedListener
{
    private List<Object> emittedObjects = new LinkedList<Object>();

    public void emitted(Object object)
    {
        emittedObjects.add(object);
    }

    public Object[] getEmittedObjects()
    {
        return emittedObjects.toArray();
    }

    public void reset()
    {
        emittedObjects.clear();
    }
}
