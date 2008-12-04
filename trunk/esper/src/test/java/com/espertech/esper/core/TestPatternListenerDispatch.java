package com.espertech.esper.core;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;

import java.util.Set;
import java.util.HashSet;

import junit.framework.TestCase;

public class TestPatternListenerDispatch extends TestCase
{
    private PatternListenerDispatch dispatch;

    private EventBean eventOne = SupportEventBeanFactory.createObject("a");
    private EventBean eventTwo = SupportEventBeanFactory.createObject("b");

    private SupportUpdateListener listener = new SupportUpdateListener();

    public void setUp()
    {
        Set<UpdateListener> listeners = new HashSet<UpdateListener>();
        listeners.add(listener);
        dispatch = new PatternListenerDispatch(listeners);
    }

    public void testSingle()
    {
        listener.reset();

        assertFalse(dispatch.hasData());
        dispatch.add(eventOne);
        assertTrue(dispatch.hasData());

        dispatch.execute();

        assertFalse(dispatch.hasData());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(eventOne, listener.getLastNewData()[0]);
    }

    public void testTwo()
    {
        listener.reset();
        assertFalse(dispatch.hasData());

        dispatch.add(eventOne);
        dispatch.add(eventTwo);
        assertTrue(dispatch.hasData());

        dispatch.execute();

        assertFalse(dispatch.hasData());
        assertEquals(2, listener.getLastNewData().length);
        assertEquals(eventOne, listener.getLastNewData()[0]);
        assertEquals(eventTwo, listener.getLastNewData()[1]);
    }
}
