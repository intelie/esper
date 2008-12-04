package com.espertech.esper.epl.join.table;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.epl.join.table.UnindexedEventTable;
import junit.framework.TestCase;

public class TestUnindexedEventTable extends TestCase
{
    public void testFlow()
    {
        UnindexedEventTable rep = new UnindexedEventTable(1);

        EventBean[] addOne = SupportEventBeanFactory.makeEvents(new String[] {"a", "b"});
        rep.add(addOne);
        rep.remove(new EventBean[] {addOne[0]});
    }
}
