package com.espertech.esper.support.emit;

import com.espertech.esper.client.EmittedListener;

import java.util.LinkedList;
import java.util.List;

public class SupportEmittedListener implements EmittedListener
{
    List<Object> emittedObjects = new LinkedList<Object>();

    public void emitted(Object object)
    {
        emittedObjects.add(object);
    }

    public List<Object> getEmittedObjects()
    {
        return emittedObjects;
    }
}