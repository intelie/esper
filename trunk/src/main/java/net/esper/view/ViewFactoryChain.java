package net.esper.view;

import net.esper.event.EventType;

import java.util.List;

/**
 * Holder for the logical chain of view fact 
 */
public class ViewFactoryChain
{
    private List<ViewFactory> viewFactoryChain;
    private EventType streamEventType;

    public ViewFactoryChain(EventType streamEventType, List<ViewFactory> viewFactoryChain)
    {
        this.streamEventType = streamEventType;
        this.viewFactoryChain = viewFactoryChain;
    }

    public EventType getEventType()
    {
        if (viewFactoryChain.size() == 0)
        {
            return streamEventType;
        }
        else
        {
            return viewFactoryChain.get(viewFactoryChain.size() - 1).getEventType();
        }
    }

    public List<ViewFactory> getViewFactoryChain()
    {
        return viewFactoryChain;
    }
}
