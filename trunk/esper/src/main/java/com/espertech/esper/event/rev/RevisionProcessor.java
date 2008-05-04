package com.espertech.esper.event.rev;

import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.named.NamedWindowIndexRepository;
import com.espertech.esper.epl.named.NamedWindowRootView;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class RevisionProcessor
{
    private static final Log log = LogFactory.getLog(RevisionProcessor.class);

    private final String namedWindowName;
    private final RevisionEventType revisionEventType;
    private final PropertyGroupDesc groups[];
    private final Map<EventType, RevisionEventTypeDesc> groupPerDeltaType;
    private final EventType fullEventType;
    private final EventPropertyGetter[] fullKeyGetters;

    private Map<MultiKeyUntyped, RevisionState> statePerKey;

    public RevisionProcessor(String namedWindowName, RevisionSpec spec)
    {
        this.namedWindowName = namedWindowName;
        this.statePerKey = new HashMap<MultiKeyUntyped, RevisionState>();
        this.fullEventType = spec.getFullEventType();

        // sort keys
        fullKeyGetters = PropertyGroupBuilder.getGetters(fullEventType, spec.getKeyPropertyNames());

        // sort non-key properties, removing keys
        String nonkeyPropertyNames[] = PropertyGroupBuilder.uniqueExclusiveSort(spec.getFullEventType().getPropertyNames(), spec.getKeyPropertyNames());
        groups = PropertyGroupBuilder.analyzeGroups(nonkeyPropertyNames, spec.getDeltaTypes(), spec.getDeltaAliases());
        
        Map<String, int[]> propsPerGroup = PropertyGroupBuilder.getGroupsPerProperty(groups);
        Map<String, RevisionPropertyTypeDesc> propertyDesc = new HashMap<String, RevisionPropertyTypeDesc>();
        int count = 0;

        for (String property : nonkeyPropertyNames)
        {
            EventPropertyGetter fullGetter = spec.getFullEventType().getGetter(property);
            int propertyNumber = count;
            int[] propGroupsProperty = propsPerGroup.get(property);
            final RevisionGetterParameters params = new RevisionGetterParameters(property, propertyNumber, fullGetter, propGroupsProperty);

            EventPropertyGetter revisionGetter = new EventPropertyGetter() {
                public Object get(EventBean eventBean) throws PropertyAccessException
                {
                    RevisionEventBean riv = (RevisionEventBean) eventBean;
                    return riv.getVersionedValue(params);
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

        count = 0;
        for (String property : spec.getKeyPropertyNames())
        {
            final int keyPropertyNumber = count;

            EventPropertyGetter revisionGetter = new EventPropertyGetter() {
                public Object get(EventBean eventBean) throws PropertyAccessException
                {
                    RevisionEventBean riv = (RevisionEventBean) eventBean;
                    return riv.getVersionFreeValue(keyPropertyNumber);
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true;
                }
            };

            RevisionPropertyTypeDesc propertyTypeDesc = new RevisionPropertyTypeDesc(revisionGetter, null);
            propertyDesc.put(property, propertyTypeDesc);
            count++;
        }

        groupPerDeltaType = PropertyGroupBuilder.getPerType(groups, nonkeyPropertyNames, spec.getKeyPropertyNames());
        revisionEventType = new RevisionEventType(spec.getFullEventType(), propertyDesc);
    }

    public RevisionEventType getEventType()
    {
        return revisionEventType;
    }

    public EventBean getRevision(EventBean event)
    {
        return new RevisionEventBean(revisionEventType, event);
    }

    public void onUpdate(EventBean[] newData, EventBean[] oldData, NamedWindowRootView namedWindowRootView, NamedWindowIndexRepository indexRepository)
    {
        // If new data is filled, it is not a delete
        if ((newData == null) || (newData.length == 0))
        {
            // we are removing an event
            RevisionEventBean revisionEvent = (RevisionEventBean) oldData[0];
            MultiKeyUntyped key = revisionEvent.getKey();
            statePerKey.remove(key);

            // TODO
            System.out.println("state per key after remove, size=" + statePerKey.size());

            // Insert into indexes for fast deletion, if there are any
            for (EventTable table : indexRepository.getTables())
            {
                // TODO
                log("remove", oldData[0]);
                table.remove(oldData);
            }

            // make as not the latest event since its due for removal
            revisionEvent.setLatest(false);

            namedWindowRootView.updateChildren(null, oldData);
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
            typesDesc = groupPerDeltaType.get(underyingEventType);
            key = getKeys(underlyingEvent, typesDesc.getKeyPropertyGetters());
        }

        // get the state for this key value
        RevisionState revisionState = statePerKey.get(key);

        // Delta event and no full
        if ((underyingEventType != fullEventType) && (revisionState == null))
        {
            return; // Ignore the event, its a delta and we don't currently have a full event for it
        }

        // New full event
        if (revisionState == null)
        {
            revisionState = new RevisionState(underlyingEvent, null, null);
            statePerKey.put(key, revisionState);

            // prepare revison event
            revisionEvent.setFullEvent(underlyingEvent);
            revisionEvent.setKey(key);
            revisionEvent.setHolders(null);
            revisionEvent.setLatest(true);

            // Insert into indexes for fast deletion, if there are any
            for (EventTable table : indexRepository.getTables())
            {
                // TODO
                log("add new full", newData[0]);
                table.add(newData);
            }

            // post to data window
            revisionState.setLastEvent(revisionEvent);
            namedWindowRootView.updateChildren(new EventBean[] {revisionEvent}, null);
            return;
        }

        // new version
        long versionNumber = revisionState.incRevisionNumber();

        // Previously-seen full event
        if (underyingEventType == fullEventType)
        {
            revisionState.setHolders(null);
            revisionState.setFullEventUnderlying(underlyingEvent);
        }
        // Delta event to existing full event
        else
        {
            int groupNum = typesDesc.getGroup().getGroupNum();
            RevisionBeanHolder[] holders = revisionState.getHolders();
            if (holders == null)    // optimization - the full event sets it to null, deltas all get a new one
            {
                holders = new RevisionBeanHolder[groups.length];
            }
            else
            {
                holders = arrayCopy(holders);   // preserve the last revisions
            }

            // add the new revision for a property group on top
            holders[groupNum] = new RevisionBeanHolder(versionNumber, underlyingEvent, typesDesc.getAllPropertyGetters());
            revisionState.setHolders(holders);
        }

        // prepare revision event
        revisionEvent.setFullEvent(revisionState.getFullEventUnderlying());
        revisionEvent.setHolders(revisionState.getHolders());
        revisionEvent.setKey(key);
        revisionEvent.setLatest(true);

        // get prior event
        RevisionEventBean lastEvent = revisionState.getLastEvent();
        lastEvent.setLatest(false);

        // data to post
        EventBean[] newDataPost = new EventBean[]{revisionEvent};
        EventBean[] oldDataPost = new EventBean[]{lastEvent};

        // update indexes
        for (EventTable table : indexRepository.getTables())
        {
            // TODO
            log("add new", newDataPost[0]);
            log("remove old", oldDataPost[0]);
            table.remove(oldDataPost);
            table.add(newDataPost);
        }

        // keep reference to last event
        revisionState.setLastEvent(revisionEvent);
        
        namedWindowRootView.updateChildren(newDataPost, oldDataPost);
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

    private RevisionBeanHolder[] arrayCopy(RevisionBeanHolder[] array)
    {
        if (array == null)
        {
            return null;
        }
        RevisionBeanHolder[] result = new RevisionBeanHolder[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public Collection<EventBean> getSnapshot(EPStatementHandle createWindowStmtHandle, Viewable parent)
    {
        createWindowStmtHandle.getStatementLock().acquireLock(null);
        try
        {
            Iterator<EventBean> it = parent.iterator();
            if (!it.hasNext())
            {
                return Collections.EMPTY_LIST;
            }
            ArrayDequeJDK6Backport<EventBean> list = new ArrayDequeJDK6Backport<EventBean>();
            while (it.hasNext())
            {
                RevisionEventBean fullRevision = (RevisionEventBean) it.next();
                MultiKeyUntyped key = fullRevision.getKey();
                RevisionState state = statePerKey.get(key);
                list.add(state.getLastEvent());
            }
            return list;
        }
        finally
        {
            createWindowStmtHandle.getStatementLock().releaseLock(null);
        }
    }

    public static void log(String text, EventBean event)
    {
        // TODO
        RevisionEventBean rev = (RevisionEventBean) event;
        System.out.println(text + " key=" + rev.getKey() + " hash=" + event.hashCode());
    }

    public void removeOldData(EventBean[] oldData, NamedWindowIndexRepository indexRepository)
    {
        for (int i = 0; i < oldData.length; i++)
        {
            RevisionEventBean event = (RevisionEventBean) oldData[i];

            // If the remove event is the latest event, remove from all caches
            if (event.isLatest())
            {
                MultiKeyUntyped key = event.getKey();
                statePerKey.remove(key);

                for (EventTable table : indexRepository.getTables())
                {
                    table.remove(oldData);
                }

                // TODO
                System.out.println("state per key after remove, size=" + statePerKey.size());
            }
        }
    }
}
