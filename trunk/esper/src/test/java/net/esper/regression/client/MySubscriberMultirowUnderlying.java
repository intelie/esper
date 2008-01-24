package net.esper.regression.client;

import net.esper.collection.UniformPair;
import net.esper.support.bean.SupportBean;

import java.util.ArrayList;

public class MySubscriberMultirowUnderlying
{
    private ArrayList<UniformPair<SupportBean[]>> indicateArr = new ArrayList<UniformPair<SupportBean[]>>();

    public void update(SupportBean[] newEvents, SupportBean[] oldEvents)
    {
        indicateArr.add(new UniformPair<SupportBean[]>(newEvents, oldEvents));
    }

    public ArrayList<UniformPair<SupportBean[]>> getIndicateArr()
    {
        return indicateArr;
    }

    public ArrayList<UniformPair<SupportBean[]>> getAndResetIndicateArr()
    {
        ArrayList<UniformPair<SupportBean[]>> result = indicateArr;
        indicateArr = new ArrayList<UniformPair<SupportBean[]>>();
        return result;
    }
}
