package net.esper.regression.client;

import net.esper.collection.UniformPair;

import java.util.ArrayList;
import java.util.List;

public class MySubscriberRowByRowFull
{
    private ArrayList<UniformPair<Integer>> indicateStart = new ArrayList<UniformPair<Integer>>();
    private ArrayList<Object> indicateEnd = new ArrayList<Object>();
    private ArrayList<Object[]> indicateIStream = new ArrayList<Object[]>();
    private ArrayList<Object[]> indicateRStream = new ArrayList<Object[]>();

    public void updateStart(int lengthIStream, int lengthRStream)
    {
        indicateStart.add(new UniformPair<Integer>(lengthIStream, lengthRStream));
    }

    public void update(String string, int intPrimitive)
    {
        indicateIStream.add(new Object[] {string, intPrimitive});
    }

    public void updateRStream(String string, int intPrimitive)
    {
        indicateRStream.add(new Object[] {string, intPrimitive});
    }

    public void updateEnd()
    {
        indicateEnd.add(this);
    }

    public List<UniformPair<Integer>> getAndResetIndicateStart()
    {
        List<UniformPair<Integer>> result = indicateStart;
        indicateStart = new ArrayList<UniformPair<Integer>>();
        return result;
    }

    public List<Object[]> getAndResetIndicateIStream()
    {
        List<Object[]> result = indicateIStream;
        indicateIStream = new ArrayList<Object[]>();
        return result;
    }

    public List<Object[]> getAndResetIndicateRStream()
    {
        List<Object[]> result = indicateRStream;
        indicateRStream = new ArrayList<Object[]>();
        return result;
    }

    public List<Object> getAndResetIndicateEnd()
    {
        List<Object> result = indicateEnd;
        indicateEnd = new ArrayList<Object>();
        return result;
    }

    public ArrayList<UniformPair<Integer>> getIndicateStart() {
        return indicateStart;
    }

    public ArrayList<Object> getIndicateEnd() {
        return indicateEnd;
    }

    public ArrayList<Object[]> getIndicateIStream() {
        return indicateIStream;
    }

    public ArrayList<Object[]> getIndicateRStream() {
        return indicateRStream;
    }
}
