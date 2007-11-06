package net.esper.eql.named;

import net.esper.core.EPStatementHandle;
import net.esper.event.EventBean;
import net.esper.event.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamedWindowServiceImpl implements NamedWindowService
{
    private Map<String, NamedWindowProcessor> processors;

    private ThreadLocal<List<NamedWindowConsumerDispatchUnit>> threadLocal = new ThreadLocal<List<NamedWindowConsumerDispatchUnit>>()
    {
        protected synchronized List<NamedWindowConsumerDispatchUnit> initialValue()
        {
            return new ArrayList<NamedWindowConsumerDispatchUnit>(100);
        }
    };

    public NamedWindowServiceImpl()
    {
        this.processors = new HashMap<String, NamedWindowProcessor>();
    }

    public boolean isNamedWindow(String name)
    {
        return processors.containsKey(name);
    }

    public NamedWindowProcessor getProcessor(String name)
    {
        NamedWindowProcessor processor = processors.get(name);
        if (processor == null)
        {
            throw new RuntimeException("XXX"); // TODO
        }
        return processor;
    }

    public NamedWindowProcessor addProcessor(String name, EventType eventType)
    {
        if (processors.containsKey(name))
        {
            throw new RuntimeException("XXX"); // TODO
        }

        NamedWindowProcessor processor = new NamedWindowProcessor(this, name, eventType);
        processors.put(name, processor);
        
        return processor;
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
