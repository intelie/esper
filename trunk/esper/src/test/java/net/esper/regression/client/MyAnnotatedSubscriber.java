package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.support.bean.SupportBean;

import java.util.ArrayList;
import java.util.List;

public class MyAnnotatedSubscriber
{
    private List<Object> indications = new ArrayList<Object>();

    @EPSubscriberMethod(
        epl       = "SupportBean(intPrimitive > 10)"
        )
    public void indicate(SupportBean bean)
    {
        indications.add(bean);
    }

    public List<Object> getAndReset()
    {
        List<Object> result = indications;
        indications = new ArrayList<Object>();
        return result;
    }
}
