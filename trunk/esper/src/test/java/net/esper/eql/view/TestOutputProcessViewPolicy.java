package net.esper.eql.view;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import net.esper.collection.MultiKey;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportResultSetProcessor;
import net.esper.support.view.SupportSchemaNeutralView;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.support.event.SupportEventBeanFactory;

public class TestOutputProcessViewPolicy extends TestCase
{
    private OutputProcessView outputProcessViewUpdate;
    private OutputProcessView outputProcessViewProcess;
    private SupportSchemaNeutralView childViewNoJoin;
    private SupportSchemaNeutralView childViewJoin;
    private SupportResultSetProcessor resultSetProcessor;

    public void setUp() throws Exception
    {
        resultSetProcessor = new SupportResultSetProcessor();
        outputProcessViewUpdate = new OutputProcessViewPolicy(resultSetProcessor, 1, null, SupportStatementContextFactory.makeContext());
        outputProcessViewProcess = new OutputProcessViewPolicy(resultSetProcessor, 2, null, SupportStatementContextFactory.makeContext());
        
        childViewNoJoin = new SupportSchemaNeutralView();
        outputProcessViewUpdate.addView(childViewNoJoin);
        childViewJoin = new SupportSchemaNeutralView();
        outputProcessViewProcess.addView(childViewJoin);        
    }

    public void testGetEventType()
    {
        assertSame(resultSetProcessor.getResultEventType(), outputProcessViewUpdate.getEventType());
    }

    public void testUpdate()
    {
        EventBean[] oldData = new EventBean[1];
        EventBean[] newData = new EventBean[1];
        oldData[0] = SupportEventBeanFactory.createObject(new SupportBean());
        newData[0] = SupportEventBeanFactory.createObject(new SupportBean());

        outputProcessViewUpdate.update(newData, oldData);

        assertSame(newData[0], childViewNoJoin.getLastNewData()[0]);
        assertSame(oldData[0], childViewNoJoin.getLastOldData()[0]);
    }

    public void testProcess()
    {
        EventBean[] oldData = new EventBean[1];
        EventBean[] newData = new EventBean[1];
        oldData[0] = SupportEventBeanFactory.createObject(new SupportBean());
        newData[0] = SupportEventBeanFactory.createObject(new SupportBean());

        outputProcessViewProcess.process(makeEventSet(newData[0]), makeEventSet(oldData[0]));

        assertSame(newData[0], childViewJoin.getLastNewData()[0]);
        assertSame(oldData[0], childViewJoin.getLastOldData()[0]);
    }

    private Set<MultiKey<EventBean>> makeEventSet(EventBean event)
    {
        Set<MultiKey<EventBean>> result = new HashSet<MultiKey<EventBean>>();
        result.add(new MultiKey<EventBean>(new EventBean[] { event}));
        return result;
    }
}
