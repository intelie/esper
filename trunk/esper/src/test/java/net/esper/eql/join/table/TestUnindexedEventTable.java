package net.esper.eql.join.table;

import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.eql.join.table.UnindexedEventTable;
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
