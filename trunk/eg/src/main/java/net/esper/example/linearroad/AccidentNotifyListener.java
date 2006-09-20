package net.esper.example.linearroad;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import java.util.LinkedList;

public class AccidentNotifyListener implements UpdateListener
{
    private List<AccidentNotifyResult> newEventList = new LinkedList<AccidentNotifyResult>();
    private List<AccidentNotifyResult> oldEventList = new LinkedList<AccidentNotifyResult>();

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (newData != null)
        {
            process("new", newData, newEventList);
        }

        if (oldData != null)
        {
            process("old", oldData, oldEventList);
        }
    }

    private static void process(String description, EventBean[] events, List<AccidentNotifyResult> store)
    {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < events.length; i++)
        {
            CarLocEvent curCarSeg = (CarLocEvent) events[i].get("curCarSeg");

            int expressway = (Integer) curCarSeg.getExpressway();
            int direction = (Integer) curCarSeg.getDirection();
            int segment = (Integer) curCarSeg.getSegment();
            
            /*
            int expressway = (Integer) curCarSeg.get("expressway");
            int direction = (Integer) curCarSeg.get("direction");
            int segment = (Integer) curCarSeg.get("segment");
            */

            AccidentNotifyResult result = new AccidentNotifyResult(expressway, direction, segment);
            store.add(result);

            buffer.append("event#" + i + " " + result + "\n");
        }

        log.warn(".update " + description + "...\n" + buffer.toString());
    }

    private static final Log log = LogFactory.getLog(AccidentNotifyListener.class);
}
