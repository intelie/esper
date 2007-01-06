package net.esper.eql.join;

import net.esper.view.internal.BufferView;
import net.esper.event.EventBean;
import net.esper.support.eql.SupportJoinExecutionStrategy;
import net.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;

public class TestJoinExecStrategyDispatchable extends TestCase
{
    private JoinExecStrategyDispatchable dispatchable;
    private BufferView bufferViewOne;
    private BufferView bufferViewTwo;
    private SupportJoinExecutionStrategy joinExecutionStrategy;

    public void setUp()
    {
        bufferViewOne = new BufferView(0);
        bufferViewTwo = new BufferView(1);

        joinExecutionStrategy = new SupportJoinExecutionStrategy();

        this.dispatchable = new JoinExecStrategyDispatchable(joinExecutionStrategy, 2);

        bufferViewOne.setObserver(dispatchable);
        bufferViewTwo.setObserver(dispatchable);
    }

    public void testFlow()
    {
        EventBean[] oldDataOne = SupportEventBeanFactory.makeEvents(new String[] {"a"});
        EventBean[] newDataOne = SupportEventBeanFactory.makeEvents(new String[] {"b"});
        EventBean[] oldDataTwo = SupportEventBeanFactory.makeEvents(new String[] {"c"});
        EventBean[] newDataTwo = SupportEventBeanFactory.makeEvents(new String[] {"d"});

        bufferViewOne.update(newDataOne, oldDataOne);
        dispatchable.execute();
        assertEquals(1, joinExecutionStrategy.getLastNewDataPerStream()[0].length);
        assertSame(newDataOne[0], joinExecutionStrategy.getLastNewDataPerStream()[0][0]);
        assertSame(oldDataOne[0], joinExecutionStrategy.getLastOldDataPerStream()[0][0]);
        assertNull(joinExecutionStrategy.getLastNewDataPerStream()[1]);
        assertNull(joinExecutionStrategy.getLastOldDataPerStream()[1]);

        bufferViewOne.update(newDataTwo, oldDataTwo);
        bufferViewTwo.update(newDataOne, oldDataOne);
        dispatchable.execute();
        assertEquals(1, joinExecutionStrategy.getLastNewDataPerStream()[0].length);
        assertEquals(1, joinExecutionStrategy.getLastNewDataPerStream()[1].length);
        assertSame(newDataTwo[0], joinExecutionStrategy.getLastNewDataPerStream()[0][0]);
        assertSame(oldDataTwo[0], joinExecutionStrategy.getLastOldDataPerStream()[0][0]);
        assertSame(newDataOne[0], joinExecutionStrategy.getLastNewDataPerStream()[1][0]);
        assertSame(oldDataOne[0], joinExecutionStrategy.getLastOldDataPerStream()[1][0]);
    }
}
