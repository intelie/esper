package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.support.bean.SupportBean;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MyAnnotatedArraySubscriber
{
    private ArrayList<Map> indicateMap = new ArrayList<Map>();
    private ArrayList<Object> indicateIStream = new ArrayList<Object>();
    private ArrayList<Object> indicateRStream = new ArrayList<Object>();

    @EPSubscriberMethod(
        epl = "select * from SupportBean(string='E1').win:length_batch(2)",
        name = "StmtA"
        )
    public void indWildcardBeanArrayObject(Object bean[])
    {
        indicateIStream.add(bean);
    }

    @EPSubscriberMethod(
        epl = "select * from SupportBean(string='E2').win:length_batch(2)",
        name = "StmtB"
        )
    public void indWildcardBeanArrayBean(SupportBean bean[])
    {
        indicateIStream.add(bean);
    }

    @EPSubscriberMethod(
        epl = "select * from SupportBean(string='E3').win:length_batch(2)",
        name = "StmtB"
        )
    public void indIRStream(SupportBean istream[], SupportBean rstream[])
    {
        indicateIStream.add(istream);
        indicateRStream.add(rstream);
    }

    public List<Object> getAndResetIndicateIStream()
    {
        List<Object> result = indicateIStream;
        indicateIStream = new ArrayList<Object>();
        return result;
    }

    public List<Object> getAndResetIndicateRStream()
    {
        List<Object> result = indicateRStream;
        indicateRStream = new ArrayList<Object>();
        return result;
    }
}
