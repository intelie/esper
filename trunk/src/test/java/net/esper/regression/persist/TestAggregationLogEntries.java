package net.esper.regression.persist;

import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProvider;
import net.esper.support.persist.SupportLogHandlerImpl;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.client.logstate.LogEntry;
import net.esper.client.logstate.LogEntrySerializer;
import net.esper.event.EventBean;
import junit.framework.TestCase;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestAggregationLogEntries extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerTwo;
    private SupportUpdateListener listenerOne;
    private SupportLogHandlerImpl logHandler;

    public void setUp()
    {
        logHandler = new SupportLogHandlerImpl();

        Configuration configuration = new Configuration();
        configuration.setLogEntryHandler(logHandler);

        listenerOne = new SupportUpdateListener();
        listenerTwo = new SupportUpdateListener();
        epService = EPServiceProviderManager.getProvider("TestAggregationLogEntries", configuration);
        epService.initialize();
    }

    public void testMemory() throws Exception
    {
        // Create 2 statements
        epService.startTransaction();

        String statementTextOne = "select avg(price) as avgPrice from " + SupportMarketDataBean.class.getName();
        EPStatement testView = epService.getEPAdministrator().createEQL(statementTextOne);
        testView.addListener(listenerOne);

        String statementTextTwo = "select avg(intPrimitive) as avgInt from " + SupportBean.class.getName()
                                        + " group by string";
        testView = epService.getEPAdministrator().createEQL(statementTextTwo);
        testView.addListener(listenerTwo);

        assertEquals(2, epService.getEPAdministrator().getStatements().size());
        epService.prepareCommit();

        // Assert statement information in logs
        assertEquals(1, logHandler.getReceived().size());
        LogEntry[] entries = logHandler.getReceived().get(0);
        assertEquals(4, entries.length);

        assertEquals(1, entries[0].getSeqNo());
        assertEquals("[1, 0, 0]", entries[0].getKey().toString());
        String stmtMemento = (String) entries[0].getState();
        assertEquals(statementTextOne, stmtMemento);

        assertEquals(2, entries[1].getSeqNo());
        assertEquals("[1, 1, 0]", entries[1].getKey().toString());

        assertEquals(3, entries[2].getSeqNo());
        assertEquals("[2, 0, 0]", entries[2].getKey().toString());
        stmtMemento = (String) entries[2].getState();
        assertEquals(statementTextTwo, stmtMemento);

        assertEquals(4, entries[3].getSeqNo());
        assertEquals("[2, 1, 0]", entries[3].getKey().toString());

        // Send some events
        epService.commit();
        logHandler.resetReceived();
        epService.startTransaction();

        sendMarketDataEvent(100);
        sendSupportBeanEvent(1, "DELL");
        sendMarketDataEvent(200);
        sendSupportBeanEvent(2, "IBM");

        epService.prepareCommit();

        // Assert new aggregate logged once
        assertEquals(1, logHandler.getReceived().size());
        entries = logHandler.getReceived().get(0);
        assertEquals(2, entries.length);

        assertEquals(5, entries[0].getSeqNo());
        assertEquals("[1, 2, 0]", entries[0].getKey().toString());

        assertEquals(6, entries[1].getSeqNo());
        assertEquals("[2, 3, 0]", entries[1].getKey().toString());

        epService.commit();
        logHandler.resetReceived();

        // Serialize logs
        LogEntry[] accumulatedLogs = logHandler.getHistorical();
        log.info(".testPersist accumulated logs=...\n" + LogEntry.print(accumulatedLogs));
        byte[] bytes = LogEntrySerializer.serialize(accumulatedLogs);
        LogEntry[] logs = LogEntrySerializer.deserialize(bytes);

        // Play back logs into engine
        epService = EPServiceProviderManager.getProvider("TestPersistenceNewEngine");
        epService.replayLogs(logs);

        // Statements should be available now
        List<EPStatement> statements = epService.getEPAdministrator().getStatements();
        assertEquals(2, statements.size());

        // Listeners should also have been added back
        assertEquals(1, statements.get(0).getListeners().size());
        listenerOne = (SupportUpdateListener) (statements.get(0).getListeners().iterator().next());
        assertEquals(1, statements.get(1).getListeners().size());
        listenerTwo = (SupportUpdateListener) (statements.get(1).getListeners().iterator().next());

        // Send next event, assert the average is right
        sendMarketDataEvent(150);
        EventBean newEvent = listenerOne.getLastNewData()[0];
        EventBean oldEvent = listenerOne.getLastOldData()[0];
        assertEquals((150 + 100 + 200) / 3.0, newEvent.get("avgPrice"));
        assertEquals((100 + 200) / 2.0, oldEvent.get("avgPrice"));

        sendSupportBeanEvent(-10, "DELL");
        newEvent = listenerTwo.getLastNewData()[0];
        oldEvent = listenerTwo.getLastOldData()[0];
        assertEquals((1 - 10) / 2.0, newEvent.get("avgInt"));
        assertEquals(1.0, oldEvent.get("avgInt"));
    }

    private void sendMarketDataEvent(double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("DELL", price, 0l, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendSupportBeanEvent(int intPrimitive, String string)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setString(string);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static Log log = LogFactory.getLog(TestAggregationLogEntries.class);
}
