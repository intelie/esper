package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;

import java.util.ArrayList;
import java.util.List;

public class MySimpleSubscriber
{
    private ArrayList<Object[]> indicateSimple = new ArrayList<Object[]>();

    public void update(String string, int intPrimitive)
    {
        indicateSimple.add(new Object[] {string, intPrimitive});
    }

    public List<Object[]> getAndResetIndicateSimple()
    {
        List<Object[]> result = indicateSimple;
        indicateSimple = new ArrayList<Object[]>();
        return result;
    }
}
