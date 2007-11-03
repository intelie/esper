package net.esper.eql.named;

import net.esper.core.EPStatementHandle;
import net.esper.eql.spec.OnDeleteDesc;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.Viewable;
import net.esper.view.StatementStopService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamedWindowServiceImpl implements NamedWindowService
{
    private Map<String, NamedWindowDeltaView> views;

    private ThreadLocal<List<NamedWindowConsumerDispatchUnit>> threadLocal = new ThreadLocal<List<NamedWindowConsumerDispatchUnit>>()
    {
        protected synchronized List<NamedWindowConsumerDispatchUnit> initialValue()
        {
            return new ArrayList<NamedWindowConsumerDispatchUnit>(100);
        }
    };

    public NamedWindowServiceImpl()
    {
        this.views = new HashMap<String, NamedWindowDeltaView>();
    }

    public NamedWindowDeleteView addDeleter(OnDeleteDesc onDeleteDesc)
    {
        NamedWindowDeltaView deltaView = views.get(onDeleteDesc.getWindowName());
        if (deltaView == null)
        {
            throw new RuntimeException("XXX"); // TODO
        }
        return deltaView.addDeleter(onDeleteDesc);        
    }

    public NamedWindowDeltaView addNamed(String name, Viewable eventStream, StatementStopService statementStopService)
    {
        if (views.containsKey(name))
        {
            throw new RuntimeException("XXX"); // TODO
        }
        NamedWindowDeltaView deltaView = new NamedWindowDeltaView(eventStream.getEventType(), this, eventStream, statementStopService);
        views.put(name, deltaView);
        return deltaView;
    }

    public EventType getNamedWindowType(String eventName)
    {
        NamedWindowDeltaView deltaView = views.get(eventName);
        if (deltaView == null)
        {
            return null;
        }
        return deltaView.getEventType();
    }

    public NamedWindowConsumerView addConsumer(String windowName, EPStatementHandle statementHandle, StatementStopService statementStopService)
    {
        NamedWindowDeltaView deltaView = views.get(windowName);
        if (deltaView == null)
        {
            throw new RuntimeException("XXX"); // TODO
        }

        return deltaView.addConsumer(statementHandle, statementStopService);
    }

    public void addDispatch(NamedWindowDeltaData delta, Map<EPStatementHandle, List<NamedWindowConsumerView>> consumers)
    {
        NamedWindowConsumerDispatchUnit unit = new NamedWindowConsumerDispatchUnit(delta, consumers);
        threadLocal.get().add(unit);
    }

    public boolean dispatch()
    {
        List<NamedWindowConsumerDispatchUnit> dispatches = threadLocal.get();
        if (dispatches.isEmpty())
        {
            return false;
        }

        if (dispatches.size() == 1)
        {
            NamedWindowConsumerDispatchUnit unit = dispatches.get(0);
            EventBean[] newData = unit.getDeltaData().getNewData();
            EventBean[] oldData = unit.getDeltaData().getOldData();

            for (Map.Entry<EPStatementHandle, List<NamedWindowConsumerView>> entry : unit.getDispatchTo().entrySet())
            {
                EPStatementHandle handle = entry.getKey();
                handle.getStatementLock().acquireLock(null); // TODO: statement lock factory
                try
                {
                    for (NamedWindowConsumerView consumerView : entry.getValue())
                    {
                        consumerView.update(newData, oldData);
                    }

                    // internal join processing, if applicable
                    handle.internalDispatch();
                }
                finally
                {
                    handle.getStatementLock().releaseLock(null);
                }
            }
        }
        // TODO: prove dispatches for more then one result is needed

        dispatches.clear();
        
        return true;
    }
}
