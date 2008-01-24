package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.collection.Pair;
import net.esper.collection.UniformPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySubscriberMultirowMap
{
    private ArrayList<UniformPair<Map[]>> indicateMap = new ArrayList<UniformPair<Map[]>>();

    public void update(Map[] newEvents, Map[] oldEvents)
    {
        indicateMap.add(new UniformPair<Map[]>(newEvents, oldEvents));
    }

    public ArrayList<UniformPair<Map[]>> getIndicateMap()
    {
        return indicateMap;
    }

    public ArrayList<UniformPair<Map[]>> getAndResetIndicateMap()
    {
        ArrayList<UniformPair<Map[]>> result = indicateMap;
        indicateMap = new ArrayList<UniformPair<Map[]>>();
        return result;
    }
}
