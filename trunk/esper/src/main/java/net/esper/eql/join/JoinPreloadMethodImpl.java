package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.view.internal.BufferView;

public class JoinPreloadMethodImpl implements JoinPreloadMethod
{
    private final int numStreams;
    private final BufferView[] bufferViews;
    private final JoinSetComposer joinSetComposer;

    public JoinPreloadMethodImpl(int numStreams, JoinSetComposer joinSetComposer)
    {
        this.numStreams = numStreams;
        this.bufferViews = new BufferView[numStreams];
        this.joinSetComposer = joinSetComposer;
    }

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
}
