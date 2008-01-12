package net.esper.eql.view;

import net.esper.dispatch.DispatchService;
import net.esper.eql.core.NaturalEventBean;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.event.EventBean;
import net.esper.view.View;
import net.sf.cglib.reflect.FastMethod;

public class OutputStrategyNatural implements OutputStrategy
{
    private final SelectClauseStreamSelectorEnum selectStreamDirEnum;
    private final DispatchService dispatchService;
    private final OutputStrategy syntheticStrategy;
    private final NaturalDispatchable naturalDispatchable;

    private ThreadLocal<Boolean> isDispatchWaiting = new ThreadLocal<Boolean>() {
        protected synchronized Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public OutputStrategyNatural(SelectClauseStreamSelectorEnum selectStreamDirEnum, DispatchService dispatchService, OutputStrategy syntheticStrategy, Object target, FastMethod method)
    {
        this.selectStreamDirEnum = selectStreamDirEnum;
        this.dispatchService = dispatchService;
        this.syntheticStrategy = syntheticStrategy;
        naturalDispatchable = new NaturalDispatchable(target, method, isDispatchWaiting);
    }

    public void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents, View postProcessView)
    {
        // TODO: second strategy that doesn't route and just dispatches for speed
        if ((selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH) ||
            (selectStreamDirEnum == SelectClauseStreamSelectorEnum.ISTREAM_ONLY))
        {
            if (newEvents != null)
            {
                for (EventBean newEvent : newEvents)
                {
                    NaturalEventBean natural = (NaturalEventBean) newEvent;
                    naturalDispatchable.addParameters(natural.getNatural());
                }

                if (!isDispatchWaiting.get())
                {
                    dispatchService.addExternal(naturalDispatchable);
                }
            }
        }
        else if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ONLY)
        {
            if (oldEvents != null)
            {
                for (EventBean oldEvent : oldEvents)
                {
                    NaturalEventBean natural = (NaturalEventBean) oldEvent;
                    naturalDispatchable.addParameters(natural.getNatural());
                }

                if (!isDispatchWaiting.get())
                {
                    dispatchService.addExternal(naturalDispatchable);
                }
            }
        }
        else
        {
            throw new IllegalStateException("Unknown stream selector " + selectStreamDirEnum);
        }

        // Invoke the synthetic output strategy - there may be listeners or insert-into
        EventBean[] unpackedNew = unpack(newEvents);
        EventBean[] unpackedOld = unpack(oldEvents);
        if ((unpackedNew != null) || (unpackedOld != null) || (forceUpdate))
        {
            syntheticStrategy.output(forceUpdate, unpackedNew, unpackedOld, postProcessView);
        }
    }

    private EventBean[] unpack(EventBean[] events)
    {
        if ((events == null) || (events.length == 0))
        {
            return events;
        }
        EventBean[] unpacked = new EventBean[events.length];
        for (int i = 0; i < unpacked.length; i++)
        {
            NaturalEventBean natural = (NaturalEventBean) events[i];
            if (natural.getOptionalSynthetic() == null)
            {
                return null; // no events
            }
            if (unpacked == null)
            {
                unpacked = new EventBean[events.length];
            }
            unpacked[i] = natural.getOptionalSynthetic();
        }
        return unpacked;
    }
}
