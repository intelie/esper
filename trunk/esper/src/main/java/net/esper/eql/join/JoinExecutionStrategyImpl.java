package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.collection.UniformPair;

import java.util.Set;

/**
 * Join execution strategy based on a 3-step getSelectListEvents of composing a join set, filtering the join set and
 * indicating.
 */
public class JoinExecutionStrategyImpl implements JoinExecutionStrategy
{
    private final JoinSetComposer composer;
    private final JoinSetProcessor filter;
    private final JoinSetProcessor indicator;

    /**
     * Ctor.
     * @param composer - determines join tuple set
     * @param filter - for filtering among tuples
     * @param indicator - for presenting the info to a view
     */
    public JoinExecutionStrategyImpl(JoinSetComposer composer, JoinSetProcessor filter, JoinSetProcessor indicator)
    {
        this.composer = composer;
        this.filter = filter;
        this.indicator = indicator;
    }

    public void join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
    {
        UniformPair<Set<MultiKey<EventBean>>> joinSet = composer.join(newDataPerStream, oldDataPerStream);

        filter.process(joinSet.getFirst(), joinSet.getSecond());

        if ( (!joinSet.getFirst().isEmpty()) || (!joinSet.getSecond().isEmpty()) )
        {
            indicator.process(joinSet.getFirst(), joinSet.getSecond());
        }
    }

}
