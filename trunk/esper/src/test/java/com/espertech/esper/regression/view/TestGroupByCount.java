package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.SerializableObjectCopier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestGroupByCount extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testCountOneViewOM() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create().setStreamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH)
                .add("symbol")
                .add(Expressions.countStar(), "countAll")
                .add(Expressions.countDistinct("volume"), "countDistVol")
                .add(Expressions.count("volume"), "countVol") );
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarketDataBean.class.getName()).addView("win", "length", 3)));
        model.setWhereClause(Expressions.or()
                .add(Expressions.eq("symbol", "DELL"))
                .add(Expressions.eq("symbol", "IBM"))
                .add(Expressions.eq("symbol", "GE")) );
        model.setGroupByClause(GroupByClause.create("symbol"));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String viewExpr = "select irstream symbol, " +
                                  "count(*) as countAll, " +
                                  "count(distinct volume) as countDistVol, " +
                                  "count(volume) as countVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where ((symbol = \"DELL\")) or ((symbol = \"IBM\")) or ((symbol = \"GE\")) " +
                          "group by symbol";
        assertEquals(viewExpr, model.toEPL());

        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(testListener);

        runAssertion();
    }

    public void testCountOneViewCompile() throws Exception
    {
        String viewExpr = "select irstream symbol, " +
                                  "count(*) as countAll, " +
                                  "count(distinct volume) as countDistVol, " +
                                  "count(volume) as countVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where ((symbol = \"DELL\")) or ((symbol = \"IBM\")) or ((symbol = \"GE\")) " +
                          "group by symbol";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(viewExpr);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(viewExpr, model.toEPL());

        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(testListener);

        runAssertion();
    }

    public void testCountOneView()
    {
        String viewExpr = "select irstream symbol, " +
                                  "count(*) as countAll," +
                                  "count(distinct volume) as countDistVol," +
                                  "count(all volume) as countVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        runAssertion();
    }

    public void testCountJoin()
    {
        String viewExpr = "select irstream symbol, " +
                                  "count(*) as countAll," +
                                  "count(distinct volume) as countDistVol," +
                                  "count(volume) as countVol " +
                          " from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "  and one.string = two.symbol " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

        runAssertion();
    }

    private void runAssertion()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("countAll"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("countDistVol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("countVol"));

        sendEvent(SYMBOL_DELL, 50L);
        assertEvents(SYMBOL_DELL, 0L, 0L, 0L,
                SYMBOL_DELL, 1L, 1L, 1L
                );

        sendEvent(SYMBOL_DELL, null);
        assertEvents(SYMBOL_DELL, 1L, 1L, 1L,
                SYMBOL_DELL, 2L, 1L, 1L
                );

        sendEvent(SYMBOL_DELL, 25L);
        assertEvents(SYMBOL_DELL, 2L, 1L, 1L,
                SYMBOL_DELL, 3L, 2L, 2L
                );

        sendEvent(SYMBOL_DELL, 25L);
        assertEvents(SYMBOL_DELL, 3L, 2L, 2L,
                SYMBOL_DELL, 3L, 1L, 2L
                );

        sendEvent(SYMBOL_DELL, 25L);
        assertEvents(SYMBOL_DELL, 3L, 1L, 2L,
                SYMBOL_DELL, 3L, 1L, 3L
                );

        sendEvent(SYMBOL_IBM, 1L);
        sendEvent(SYMBOL_IBM, null);
        sendEvent(SYMBOL_IBM, null);
        sendEvent(SYMBOL_IBM, null);
        assertEvents(SYMBOL_IBM, 3L, 1L, 1L,
                SYMBOL_IBM, 3L, 0L, 0L
                );
    }

    private void assertEvents(String symbolOld, Long countAllOld, Long countDistVolOld, Long countVolOld,
                              String symbolNew, Long countAllNew, Long countDistVolNew, Long countVolNew)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbolOld, oldData[0].get("symbol"));
        assertEquals(countAllOld, oldData[0].get("countAll"));
        assertEquals(countDistVolOld, oldData[0].get("countDistVol"));
        assertEquals(countVolOld, oldData[0].get("countVol"));

        assertEquals(symbolNew, newData[0].get("symbol"));
        assertEquals(countAllNew, newData[0].get("countAll"));
        assertEquals(countDistVolNew, newData[0].get("countDistVol"));
        assertEquals(countVolNew, newData[0].get("countVol"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, Long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestGroupByCount.class);
}
