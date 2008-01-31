package com.espertech.esper.eql.join;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.view.internal.BufferView;
import com.espertech.esper.eql.core.ResultSetProcessor;
import com.espertech.esper.collection.MultiKey;

import java.util.Set;
import java.util.HashSet;

/**
 * Implements a method for pre-loading (initializing) join indexes from a filled buffer.
 */
public class JoinPreloadMethodImpl implements JoinPreloadMethod
{
    private final int numStreams;
    private final BufferView[] bufferViews;
    private final JoinSetComposer joinSetComposer;

    /**
     * Ctor.
     * @param numStreams number of streams
     * @param joinSetComposer the composer holding stream indexes
     */
    public JoinPreloadMethodImpl(int numStreams, JoinSetComposer joinSetComposer)
    {
        this.numStreams = numStreams;
        this.bufferViews = new BufferView[numStreams];
        this.joinSetComposer = joinSetComposer;
    }

    /**
     * Sets the buffer for a stream to preload events from.
     * @param view buffer
     * @param stream the stream number for the buffer
     */
    public void setBuffer(BufferView view, int stream)
    {
        bufferViews[stream] = view;
    }

    public void preloadFromBuffer(int stream)
    {
        EventBean[] preloadEvents = bufferViews[stream].getNewDataBuffer().getAndFlush();
        EventBean[][] eventsPerStream = new EventBean[numStreams][];
        eventsPerStream[stream] = preloadEvents;
        joinSetComposer.init(eventsPerStream);
    }

    public void preloadAggregation(ResultSetProcessor resultSetProcessor)
    {
        Set<MultiKey<EventBean>> newEvents = joinSetComposer.staticJoin();
        Set<MultiKey<EventBean>> oldEvents = new HashSet<MultiKey<EventBean>>();
        resultSetProcessor.processJoinResult(newEvents, oldEvents, false);
    }
}
