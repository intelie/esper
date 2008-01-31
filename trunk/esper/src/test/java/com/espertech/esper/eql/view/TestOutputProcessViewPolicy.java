package com.espertech.esper.eql.view;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.eql.SupportResultSetProcessor;
import com.espertech.esper.support.view.SupportSchemaNeutralView;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestOutputProcessViewPolicy extends TestCase
{
    private OutputProcessView outputProcessViewUpdate;
    private OutputProcessView outputProcessViewProcess;
    private SupportSchemaNeutralView childViewNoJoin;
    private SupportSchemaNeutralView childViewJoin;
    private SupportResultSetProcessor resultSetProcessor;

    public void setUp() throws Exception
    {
        OutputStrategy outputStrategy = new OutputStrategySimple();
        resultSetProcessor = new SupportResultSetProcessor();
        outputProcessViewUpdate = new OutputProcessViewPolicy(resultSetProcessor, outputStrategy, false, 1, null, SupportStatementContextFactory.makeContext());
        outputProcessViewProcess = new OutputProcessViewPolicy(resultSetProcessor, outputStrategy, false, 2, null, SupportStatementContextFactory.makeContext());
        
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
    }

    public void testProcess()
    {
        EventBean[] oldData = new EventBean[1];
        EventBean[] newData = new EventBean[1];
        oldData[0] = SupportEventBeanFactory.createObject(new SupportBean());
        newData[0] = SupportEventBeanFactory.createObject(new SupportBean());

        outputProcessViewProcess.process(makeEventSet(newData[0]), makeEventSet(oldData[0]));
    }

    private Set<MultiKey<EventBean>> makeEventSet(EventBean event)
    {
        Set<MultiKey<EventBean>> result = new HashSet<MultiKey<EventBean>>();
        result.add(new MultiKey<EventBean>(new EventBean[] { event}));
        return result;
    }
}
