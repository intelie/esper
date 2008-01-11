package net.esper.eql.view;

import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import net.esper.dispatch.DispatchService;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.eql.core.NaturalEventBean;
import net.sf.cglib.reflect.FastMethod;

public class OutputStrategyNatural implements OutputStrategy
{
    private final boolean isRoute;
    private final SelectClauseStreamSelectorEnum selectStreamDirEnum;
    private final DispatchService dispatchService;
    private final OutputStrategy syntheticStrategy;
    private final NaturalDispatchable naturalDispatchable;

    private ThreadLocal<Boolean> isDispatchWaiting = new ThreadLocal<Boolean>() {
        protected synchronized Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public OutputStrategyNatural(boolean isRoute, SelectClauseStreamSelectorEnum selectStreamDirEnum, DispatchService dispatchService, OutputStrategy syntheticStrategy, Object target, FastMethod method)
    {
        this.selectStreamDirEnum = selectStreamDirEnum;
        this.isRoute = isRoute;
        this.dispatchService = dispatchService;
        this.syntheticStrategy = syntheticStrategy;
        naturalDispatchable = new NaturalDispatchable(target, method, isDispatchWaiting);
    }

    public void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents, ViewSupport finalView)
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

        // if there are listeners, invoke the synthetic output strategy
        if (finalView.hasViews() || isRoute)
        {
            syntheticStrategy.output(forceUpdate, newEvents, oldEvents, finalView);
        }        
    }
}
