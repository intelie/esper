package net.esper.view.internal;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.view.*;
import net.esper.view.window.RelativeAccessByEventNIndex;
import net.esper.collection.*;

import java.util.*;

public class PriorEventViewFactory implements ViewFactory
{
    private TreeMap<Integer, List<ViewResourceCallback>> callbacksPerIndex = new TreeMap<Integer, List<ViewResourceCallback>>();
    private EventType eventType;
    private final boolean isUnbound;

    public PriorEventViewFactory(boolean unbound)
    {
        isUnbound = unbound;
    }

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        throw new UnsupportedOperationException("View not available through EQL");
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapPriorEventAccess)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }

        // Get the index requested, such as the 8th prior event
        ViewCapPriorEventAccess requested = (ViewCapPriorEventAccess) viewCapability;
        int reqIndex = requested.getIndexConstant();

        // Store in a list per index such that we can consolidate this into a single buffer
        List<ViewResourceCallback> callbackList = callbacksPerIndex.get(reqIndex);
        if (callbackList == null)
        {
            callbackList = new LinkedList<ViewResourceCallback>();
            callbacksPerIndex.put(reqIndex, callbackList);
        }
        callbackList.add(resourceCallback);
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        ViewUpdatedCollection viewUpdatedCollection = null;

        if (callbacksPerIndex.size() == 0)
        {
            throw new IllegalStateException("No resources requested");
        }

        // Construct an array of requested prior-event indexes (such as 10th prior event, 8th prior = {10, 8})
        int[] requested = new int[callbacksPerIndex.size()];
        int count = 0;
        for (int reqIndex : callbacksPerIndex.keySet())
        {
            requested[count++] = reqIndex;
        }


        // For unbound streams the buffer is strictly rolling new events
        if (isUnbound)
        {
            viewUpdatedCollection = new PriorEventBufferUnbound(callbacksPerIndex.lastKey());
        }
        // For bound streams (with views posting old and new data), and if only one prior index requested
        else if (requested.length == 1)
        {
            viewUpdatedCollection = new PriorEventBufferSingle(requested[0]);
        }
        else
        {
            // For bound streams (with views posting old and new data)
            // Multiple prior event indexes requested, such as "prior(2, price), prior(8, price)"
            // Sharing a single viewUpdatedCollection for multiple prior-event indexes
            viewUpdatedCollection = new PriorEventBufferMulti(requested);
        }

        // Since an expression such as "prior(2, price), prior(8, price)" translates
        // into {2, 8} the relative index is {0, 1}.
        // Map the expression-supplied index to a relative viewUpdatedCollection-known index via wrapper
        int relativeIndex = 0;
        for (int reqIndex : callbacksPerIndex.keySet())
        {
            List<ViewResourceCallback> callbacks = callbacksPerIndex.get(reqIndex);
            for (ViewResourceCallback callback : callbacks)
            {
                if (viewUpdatedCollection instanceof RelativeAccessByEventNIndex)
                {
                    RelativeAccessByEventNIndex relativeAccess = (RelativeAccessByEventNIndex) viewUpdatedCollection;
                    callback.setViewResource(new RelativeAccessImpl(relativeAccess, relativeIndex));
                }
                else
                {
                    callback.setViewResource(viewUpdatedCollection);
                }
            }
            relativeIndex++;
        }

        PriorEventView priorEventView = new PriorEventView(viewUpdatedCollection);
        return priorEventView;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        return false;
    }

    public class RelativeAccessImpl implements RelativeAccessByEventNIndex
    {
        private final RelativeAccessByEventNIndex buffer;
        private final int relativeIndex;

        public RelativeAccessImpl(RelativeAccessByEventNIndex buffer, int relativeIndex)
        {
            this.buffer = buffer;
            this.relativeIndex = relativeIndex;
        }

        public EventBean getRelativeToEvent(EventBean event, int prevIndex)
        {
            return buffer.getRelativeToEvent(event, relativeIndex);
        }
    }
}
