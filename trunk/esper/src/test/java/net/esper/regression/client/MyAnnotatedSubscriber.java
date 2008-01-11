package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.support.bean.SupportBean;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class MyAnnotatedSubscriber
{
    private ArrayList<Object[]> indications = new ArrayList<Object[]>();

    @EPSubscriberMethod(
        epl = "select string, intPrimitive from SupportBean(intPrimitive > 10)"
        )
    public void indicate(String string, int intPrimitive)
    {
        indications.add(new Object[] {string, intPrimitive});
    }

    public List<Object[]> getAndReset()
    {
        List<Object[]> result = indications;
        indications = new ArrayList<Object[]>();
        return result;
    }
}
