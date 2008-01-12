package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;

import java.util.ArrayList;
import java.util.List;

public class MyAnnotatedSimpleSubscriber
{
    private ArrayList<Object[]> indicateSimple = new ArrayList<Object[]>();
    private ArrayList<Object[]> indicateInsertInto = new ArrayList<Object[]>();
    private ArrayList<Object[]> indicateIIIRStream = new ArrayList<Object[]>();
    private ArrayList<Object[]> indicateIIRIStream = new ArrayList<Object[]>();
    private ArrayList<Object[]> indicateIIRRStream = new ArrayList<Object[]>();
    private ArrayList<Object[]> indicateSelectRStream = new ArrayList<Object[]>();

    @EPSubscriberMethod(
        epl = "select string, intPrimitive from SupportBean(intPrimitive > 10)",
        name = "SimpleSelectStmt"
        )
    public void indicateSimple(String string, int intPrimitive)
    {
        indicateSimple.add(new Object[] {string, intPrimitive});
    }

    @EPSubscriberMethod(
        epl = "insert into MyStream select symbol, volume * 2 as volume from SupportMarketDataBean.std:unique(symbol)",
        name = "InsertIntoStmt"
        )
    public void indicateInsertInto(String symbol, long volume)
    {
        indicateInsertInto.add(new Object[] {symbol, volume});
    }

    @EPSubscriberMethod(
        epl = "select rstream symbol, volume from SupportMarketDataBean.std:unique(symbol)",
        name = "SelectRStream"
        )
    public void indicateSelectRStream(String symbol, long volume)
    {
        indicateSelectRStream.add(new Object[] {symbol, volume});
    }

    @EPSubscriberMethod(
        epl = "insert rstream into IIRRStream select rstream symbol, volume from SupportMarketDataBean.std:unique(symbol)",
        name = "IIRR"
        )
    public void indicateIIRR(String symbol, long volume)
    {
        indicateIIRRStream.add(new Object[] {symbol, volume});
    }

    @EPSubscriberMethod(
        epl = "insert rstream into IIIRStream select istream symbol, volume from SupportMarketDataBean.std:unique(symbol)",
        name = "IIIR"
        )
    public void indicateIIIR(String symbol, long volume)
    {
        indicateIIIRStream.add(new Object[] {symbol, volume});
    }

    @EPSubscriberMethod(
            epl = "insert istream into IIRIStream select rstream symbol, volume from SupportMarketDataBean.std:unique(symbol)",
            name = "IIRI"
    )
    public void indicateIIRI(String symbol, long volume)
    {
        indicateIIRIStream.add(new Object[] {symbol, volume});
    }

    public List<Object[]> getAndResetIIIR()
    {
        List<Object[]> result = indicateIIIRStream;
        indicateIIIRStream = new ArrayList<Object[]>();
        return result;
    }

    public List<Object[]> getAndResetIIRI()
    {
        List<Object[]> result = indicateIIRIStream;
        indicateIIRIStream = new ArrayList<Object[]>();
        return result;
    }

    public List<Object[]> getAndResetIIRR()
    {
        List<Object[]> result = indicateIIRRStream;
        indicateIIRRStream = new ArrayList<Object[]>();
        return result;
    }

    public List<Object[]> getAndResetIndicateInsertInto()
    {
        List<Object[]> result = indicateInsertInto;
        indicateInsertInto = new ArrayList<Object[]>();
        return result;
    }

    public List<Object[]> getAndResetIndicateSimple()
    {
        List<Object[]> result = indicateSimple;
        indicateSimple = new ArrayList<Object[]>();
        return result;
    }
}
