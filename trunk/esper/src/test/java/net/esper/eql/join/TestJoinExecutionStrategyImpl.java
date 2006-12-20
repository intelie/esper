package net.esper.eql.join;

import junit.framework.TestCase;
import net.esper.support.eql.SupportJoinSetComposer;
import net.esper.support.eql.SupportJoinSetProcessor;
import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.collection.UniformPair;

import java.util.Set;
import java.util.HashSet;

public class TestJoinExecutionStrategyImpl extends TestCase
{
    private JoinExecutionStrategyImpl join;
    private Set<MultiKey<EventBean>> oldEvents;
    private Set<MultiKey<EventBean>> newEvents;
    private SupportJoinSetProcessor filter;
    private SupportJoinSetProcessor indicator;

    public void setUp()
    {
        oldEvents = new HashSet<MultiKey<EventBean>>();
        newEvents = new HashSet<MultiKey<EventBean>>();

        JoinSetComposer composer = new SupportJoinSetComposer(new UniformPair<Set<MultiKey<EventBean>>>(newEvents, oldEvents));
        filter = new SupportJoinSetProcessor();
        indicator = new SupportJoinSetProcessor();

        join = new JoinExecutionStrategyImpl(composer, filter, indicator);
    }

    public void testJoin()
    {
        join.join(null, null);

        assertSame(newEvents, filter.getLastNewEvents());
        assertSame(oldEvents, filter.getLastOldEvents());
        assertNull(indicator.getLastNewEvents());
        assertNull(indicator.getLastOldEvents());
    }
}
