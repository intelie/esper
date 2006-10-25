package net.esper.persist;

import junit.framework.TestCase;
import net.esper.support.persist.SupportLogContextNodeFactory;
import net.esper.client.logstate.LogEntryType;
import net.esper.client.logstate.LogKey;
import net.esper.client.logstate.LogEntry;

import java.util.Map;
import java.io.Serializable;

public class TestLogServiceUtil extends TestCase
{
    private LogContextNode root;
    private LogContextNode child;
    private LogContextNode childChild;
    private LogKey logKeyOne = makeLogKey(new int[] {1, 0, 0});
    private LogKey logKeyTwo = makeLogKey(new int[] {1, 1, 0});

    public void setUp()
    {
        root = SupportLogContextNodeFactory.createChild("a");
        child = root.createChild(LogEntryType.GROUP_AGG_STATE, "b");
        childChild = child.createChild(LogEntryType.GROUP_AGG_STATE, "c");
    }

    public void testGetLogKey()
    {
        assertEquals(logKeyOne, LogServiceUtil.getLogKeyForNode(child));
        assertEquals(logKeyTwo, LogServiceUtil.getLogKeyForNode(childChild));
    }

    public void testGetDeepNodeMap()
    {
        Map<LogKey, LogContextNode> result = LogServiceUtil.getDeepNodeMap(root);
        assertEquals(2, result.size());
        assertEquals("b", result.get(logKeyOne).getState());
        assertEquals("c", result.get(logKeyTwo).getState());
    }

    public void testGetStateMap()
    {
        LogEntry[] entries = new LogEntry[2];
        entries[0] = new LogEntry(1, logKeyOne, LogEntryType.ENGINE, "a");
        entries[1] = new LogEntry(1, logKeyTwo, LogEntryType.ENGINE, "b");

        Map<LogKey, Serializable> result = LogServiceUtil.getStateMap(entries);
        assertEquals(2, result.size());
        assertEquals("a", result.get(logKeyOne));
        assertEquals("b", result.get(logKeyTwo));
    }

    private LogKey makeLogKey(int[] keys)
    {
        return new LogKey(keys);
    }
}
