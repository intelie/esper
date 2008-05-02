package com.espertech.esper.event.rev;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.named.NamedWindowIndexRepository;
import com.espertech.esper.epl.named.NamedWindowRootView;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.view.View;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class RevisionProcessor
{
    private static final Log log = LogFactory.getLog(RevisionProcessor.class);

    private final String namedWindowName;
    private final RevisionEventType revisionEventType;
    private final PropertyGroupDesc groups[];
    private final Map<EventType, RevisionEventTypeDesc> groupPerDeltaType;
    private final EventType fullEventType;
    private final EventPropertyGetter[] fullKeyGetters;

    private View tailView;
    private long currentRevisionNum;
    private Map<MultiKeyUntyped, RevisionState> lastFullPerKey;

    public RevisionProcessor(String namedWindowName, RevisionSpec spec)
    {
        this.namedWindowName = namedWindowName;
        this.lastFullPerKey = new HashMap<MultiKeyUntyped, RevisionState>();
        this.fullEventType = spec.getFullEventType();
        fullKeyGetters = PropertyGroupBuilder.getGetters(fullEventType, spec.getKeyPropertyNames());

        String allPropertyNames[] = PropertyGroupBuilder.copyAndSort(spec.getFullEventType().getPropertyNames());
        groups = PropertyGroupBuilder.analyzeGroups(allPropertyNames, spec.getDeltaTypes(), spec.getDeltaAliases());
        
        Map<String, int[]> propsPerGroup = PropertyGroupBuilder.getGroupsPerProperty(groups);
        Map<String, RevisionPropertyTypeDesc> propertyDesc = new HashMap<String, RevisionPropertyTypeDesc>();
        int count = 0;
        for (String property : allPropertyNames)
        {
            EventPropertyGetter fullGetter = spec.getFullEventType().getGetter(property);
            int propertyNumber = count;
            int[] propGroupsProperty = propsPerGroup.get(property);
            final RevisionGetterParameters params = new RevisionGetterParameters(propertyNumber, fullGetter, propGroupsProperty);

            EventPropertyGetter revisionGetter = new EventPropertyGetter() {
                public Object get(EventBean eventBean) throws PropertyAccessException
                {
                    RevisionEventBean riv = (RevisionEventBean) eventBean;
                    return riv.getValue(params);
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true;
                }
            };

            RevisionPropertyTypeDesc propertyTypeDesc = new RevisionPropertyTypeDesc(revisionGetter, params);
            propertyDesc.put(property, propertyTypeDesc);
            count++;
        }

        groupPerDeltaType = PropertyGroupBuilder.getPerType(groups, allPropertyNames, spec.getKeyPropertyNames());
        revisionEventType = new RevisionEventType(spec.getFullEventType(), propertyDesc);
    }

    public void setOutputView(View tailView)
    {
        this.tailView = tailView;
    }

    public RevisionEventType getEventType()
    {
        return revisionEventType;
    }

    public EventBean getRevision(EventBean event)
    {
        return new RevisionEventBean(revisionEventType, event);
    }

    /*
        lock.lock();
        try
        {
            long revision = currentRevisionNum++;

            if (event.getEventType() == fullEventType)
            {
                MultiKeyUntyped mk = getKeys(event, fullKeyGetters);
                Pair<EventBean, RevisionBeanHolder[]> pair = lastFullPerKey.get(mk);

                // full event has not been seen
                if (pair == null)
                {
                    pair = new Pair<EventBean, RevisionBeanHolder[]>(event, new RevisionBeanHolder[groups.length]);
                    lastFullPerKey.put(mk, pair);
                }
                else
                {
                    pair.setFirst(event);
                    pair.setSecond(null);
                }

                return new RevisionEventBean(revisionEventType, mk, event, null);
            }

            RevisionEventTypeDesc typeDesc = groupPerType.get(event.getEventType());
            if (typeDesc == null)
            {
                log.warn(""); // TODO: handle subtypes
            }

            // establish type's key values
            MultiKeyUntyped mk = getKeys(event, typeDesc.getKeyPropertyGetters());

            Pair<EventBean, RevisionBeanHolder[]> pair = lastFullPerKey.get(mk); // TODO: handle error

            // not seen this key before
            if (pair == null)
            {
                return new RevisionEventBean(revisionEventType, mk, null, null);
            }

            RevisionBeanHolder holderSnapshot[] = pair.getSecond();
            if (holderSnapshot == null)
            {
                holderSnapshot = new RevisionBeanHolder[groups.length];
            }
            else
            {
                holderSnapshot = arrayCopy(pair.getSecond());
            }

            // apply to type's property group
            int groupNum = typeDesc.getGroup().getGroupNum();
            holderSnapshot[groupNum] = new RevisionBeanHolder(revision, event, typeDesc.getAllPropertyGetters());
            pair.setSecond(holderSnapshot); // store back, keeping the last one produced

            return new RevisionEventBean(revisionEventType, mk, pair.getFirst(), holderSnapshot);
        }
        catch (RuntimeException ex)
        {
            // TODO error handling
            throw ex;
        }
        finally {
            lock.unlock();
        }
    }

    private RevisionBeanHolder[] arrayCopy(RevisionBeanHolder[] array)
    {
        RevisionBeanHolder[] result = new RevisionBeanHolder[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }
    */

    public void onUpdate(EventBean[] newData, EventBean[] oldData, NamedWindowRootView namedWindowRootView, NamedWindowIndexRepository indexRepository)
    {
        // If new data is filled, it is not a delete
        if (newData.length == 0)
        {
            return;
        }

        RevisionEventBean revisionEvent = (RevisionEventBean) newData[0];
        EventBean underlyingEvent = revisionEvent.getUnderlyingFullOrDelta();
        EventType underyingEventType = underlyingEvent.getEventType();

        // obtain key values
        MultiKeyUntyped key;
        RevisionEventTypeDesc typesDesc;
        if (underyingEventType == fullEventType)
        {
            typesDesc = null;
            key = getKeys(underlyingEvent, fullKeyGetters);
        }
        else
        {
            typesDesc = groupPerDeltaType.get(underlyingEvent.getEventType());
            key = getKeys(revisionEvent, typesDesc.getKeyPropertyGetters());
        }

        // get the state for this key value
        RevisionState revisionState = lastFullPerKey.get(key);

        // Delta event and no full
        if ((underlyingEvent.getEventType() != fullEventType) && (revisionState == null))
        {
            return; // Ignore the event, its a delta and we don't currently have a full event for it
        }

        // New full event
        if (revisionState == null)
        {
            revisionState = new RevisionState(underlyingEvent, new RevisionBeanHolder[groups.length], null);
            lastFullPerKey.put(key, revisionState);
            revisionEvent.setFullEvent(underlyingEvent);

            // Update indexes for fast deletion, if there are any
            for (EventTable table : indexRepository.getTables())
            {
                table.add(newData);
            }

            // post to data window
            namedWindowRootView.updateChildren(new EventBean[] {revisionEvent}, null);
            return;
        }

        // Delta event to existing full event
        int groupNum = typesDesc.getGroup().getGroupNum();

        // apply to type's property group
        long version = revisionState.incRevisionNumber();
        revisionState.getHolders()[groupNum] = new RevisionBeanHolder(version, underlyingEvent, typesDesc.getAllPropertyGetters());        
        tailView.update(new EventBean[]{revisionEvent}, new EventBean[] {revisionState.getLastEvent()});
    }

    private MultiKeyUntyped getKeys(EventBean event, EventPropertyGetter[] keyPropertyGetters)
    {
        Object[] keys = new Object[keyPropertyGetters.length];
        for (int i = 0; i < keys.length; i++)
        {
            keys[i] = keyPropertyGetters[i].get(event);
        }
        return new MultiKeyUntyped(keys);
    }
}
