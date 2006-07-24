package net.esper.pattern;

import junit.framework.*;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestEvalAndStateNode extends TestCase
{
    private Map<String, EventBean> events;

    public void setUp()
    {
        events = new HashMap<String, EventBean>();
        String[] ids = { "0", "a", "b", "c", "d", "e", "f" };
        for (String id : ids)
        {
            events.put(id, SupportEventBeanFactory.createObject(id));
        }
    }

    public void testGenerate() throws Exception
    {
        MatchedEventMap beginState = new MatchedEventMap();
        beginState.add("0", events.get("0"));

        Vector<List<MatchedEventMap>> listArray = new Vector<List<MatchedEventMap>>();
        listArray.add(0, makeList("a", "b"));
        listArray.add(1, makeList("c", "d"));
        listArray.add(2, makeList("e", "f"));

        List<MatchedEventMap> result = new LinkedList<MatchedEventMap>();
        EvalAndStateNode.generateMatchEvents(listArray, 0, result, beginState);

        assertTrue(result.size() == 8);
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                for (int k = 0; k < 2; k++)
                {
                    int index = i * 4 + j * 2 + k;
                    MatchedEventMap event = result.get(index);

                    log.debug(".testGenerate index=" + index + "  event=" + event);
                }
            }
        }

        assertTrue(result.get(0).getMatchingEvent("0") == events.get("0"));

        assertTrue(result.get(0).getMatchingEvent("a") == events.get("a"));
        assertTrue(result.get(0).getMatchingEvent("c") == events.get("c"));
        assertTrue(result.get(0).getMatchingEvent("e") == events.get("e"));

        assertTrue(result.get(1).getMatchingEvent("0") == events.get("0"));
        assertTrue(result.get(1).getMatchingEvent("a") == events.get("a"));
        assertTrue(result.get(1).getMatchingEvent("c") == events.get("c"));
        assertTrue(result.get(1).getMatchingEvent("f") == events.get("f"));

        assertTrue(result.get(7).getMatchingEvent("0") == events.get("0"));
        assertTrue(result.get(7).getMatchingEvent("b") == events.get("b"));
        assertTrue(result.get(7).getMatchingEvent("d") == events.get("d"));
        assertTrue(result.get(7).getMatchingEvent("f") == events.get("f"));
    }

    /**
     * Make a list of MatchEvents for testing each containing 2 entries in the list
     */
    private List<MatchedEventMap> makeList(String valueOne, String valueTwo)
    {
        List<MatchedEventMap> list = new LinkedList<MatchedEventMap>();

        MatchedEventMap event1 = new MatchedEventMap();
        event1.add(valueOne, events.get(valueOne));
        list.add(event1);

        MatchedEventMap event2 = new MatchedEventMap();
        event2.add(valueTwo, events.get(valueTwo));
        list.add(event2);

        return list;
    }

    private static final Log log = LogFactory.getLog(TestEvalAndStateNode.class);
}