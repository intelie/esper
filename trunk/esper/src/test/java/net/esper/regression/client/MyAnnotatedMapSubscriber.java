package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAnnotatedMapSubscriber
{
    private ArrayList<Map> indicateMap = new ArrayList<Map>();
    private ArrayList<Object[]> indicateObjectArray = new ArrayList<Object[]>();

    @EPSubscriberMethod(
        epl = "select string, intPrimitive from SupportBean.std:unique(string)",
        name = "StmtA"
        )
    public void indicateMap(Map row)
    {
        indicateMap.add(row);
    }

    @EPSubscriberMethod(
        epl = "select string, intPrimitive from SupportBean.std:unique(string)",
        name = "StmtB"
        )
    public void indicateObjectVarargs(Object... values)
    {
        indicateObjectArray.add(values);
    }

    @EPSubscriberMethod(
        epl = "select symbol, volume from SupportMarketDataBean",
        name = "StmtC"
        )
    public void indicateObjectArray(Object[] values)
    {
        indicateObjectArray.add(values);
    }

    public ArrayList<Map> getAndResetIndicateMap()
    {
        ArrayList<Map> result = indicateMap;
        indicateMap = new ArrayList<Map>();
        return result;
    }

    public List<Object[]> getAndResetIndicateObjectVarags()
    {
        List<Object[]> result = indicateObjectArray;
        indicateObjectArray = new ArrayList<Object[]>();
        return result;
    }

}
