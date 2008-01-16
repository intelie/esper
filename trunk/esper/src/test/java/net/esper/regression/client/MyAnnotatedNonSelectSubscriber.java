package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.support.bean.SupportBean;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MyAnnotatedNonSelectSubscriber
{
    private ArrayList<Object[]> indicateIStream = new ArrayList<Object[]>();
    private ArrayList<Object[]> indicateRStream = new ArrayList<Object[]>();

    @EPSubscriberMethod(
        epl = "create window MyWindow.win:keepall() as select string, intPrimitive from SupportBean",
        name = "StmtA"
        )
    public void indCreateWindow(Object bean[])
    {
        indicateIStream.add(bean);
    }

    public List<Object[]> getResetIStream()
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
}
