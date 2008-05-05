package com.espertech.esper.event.rev;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.view.StatementStopService;
import com.espertech.esper.view.StatementStopCallback;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.epl.named.NamedWindowRootView;
import com.espertech.esper.epl.named.NamedWindowIndexRepository;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.core.EPStatementHandle;

import java.util.*;

public class RevisionProcessorDeclared implements RevisionProcessor
{
    private static final Log log = LogFactory.getLog(RevisionProcessorDeclared.class);

    private final String revisionEventTypeAlias;
    private final RevisionEventType revisionEventType;
    private final PropertyGroupDesc groups[];
    private final Map<EventType, RevisionTypeDescDeclared> groupPerDeltaType;
    private final EventType fullEventType;
    private final EventPropertyGetter[] fullKeyGetters;

    private Map<MultiKeyUntyped, RevisionStateDeclared> statePerKey;

    public RevisionProcessorDeclared(String revisionEventTypeAlias, RevisionSpec spec, StatementStopService statementStopService)
    {
        // on statement stop, remove versions
        statementStopService.addSubscriber(new StatementStopCallback() {
            public void statementStopped()
            {
                statePerKey.clear();
            }
        });

        this.revisionEventTypeAlias = revisionEventTypeAlias;
        this.statePerKey = new HashMap<MultiKeyUntyped, RevisionStateDeclared>();
        this.fullEventType = spec.getFullEventType();
        this.fullKeyGetters = PropertyGroupBuilder.getGetters(fullEventType, spec.getKeyPropertyNames());

        // sort non-key properties, removing keys
        groups = PropertyGroupBuilder.analyzeGroups(spec.getChangesetPropertyNames(), spec.getDeltaTypes(), spec.getDeltaAliases());

        Map<String, int[]> propsPerGroup = PropertyGroupBuilder.getGroupsPerProperty(groups);
        Map<String, RevisionPropertyTypeDesc> propertyDesc = new HashMap<String, RevisionPropertyTypeDesc>();
        int count = 0;

        for (String property : spec.getChangesetPropertyNames())
        {
            EventPropertyGetter fullGetter = spec.getFullEventType().getGetter(property);
            int propertyNumber = count;
            int[] propGroupsProperty = propsPerGroup.get(property);
            final RevisionGetterParameters params = new RevisionGetterParameters(property, propertyNumber, fullGetter, propGroupsProperty);

            // if there are no groups (full event property only), then simply use the full event getter
            EventPropertyGetter revisionGetter = new EventPropertyGetter() {
                    public Object get(EventBean eventBean) throws PropertyAccessException
                    {
                        RevisionEventBeanDeclared riv = (RevisionEventBeanDeclared) eventBean;
                        return riv.getVersionedValue(params);
                    }

                    public boolean isExistsProperty(EventBean eventBean)
                    {
                        return true;
                    }
                };

            Class type = spec.getFullEventType().getPropertyType(property);
            RevisionPropertyTypeDesc propertyTypeDesc = new RevisionPropertyTypeDesc(revisionGetter, params, type);
            propertyDesc.put(property, propertyTypeDesc);
            count++;
        }

        for (String property : spec.getFullEventOnlyPropertyNames())
        {
            final EventPropertyGetter fullGetter = spec.getFullEventType().getGetter(property);

            // if there are no groups (full event property only), then simply use the full event getter
            EventPropertyGetter revisionGetter =  new EventPropertyGetter() {
                public Object get(EventBean eventBean) throws PropertyAccessException
                {
                    RevisionEventBeanDeclared riv = (RevisionEventBeanDeclared) eventBean;
                    return fullGetter.get(riv.getLastFullEvent());
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true;
                }
            };

            Class type = spec.getFullEventType().getPropertyType(property);
            RevisionPropertyTypeDesc propertyTypeDesc = new RevisionPropertyTypeDesc(revisionGetter, null, type);
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
                    RevisionEventBeanDeclared riv = (RevisionEventBeanDeclared) eventBean;
                    return riv.getKey().getKeys()[keyPropertyNumber];
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true;
                }
            };

            Class type = spec.getFullEventType().getPropertyType(property);
            RevisionPropertyTypeDesc propertyTypeDesc = new RevisionPropertyTypeDesc(revisionGetter, null, type);
            propertyDesc.put(property, propertyTypeDesc);
            count++;
        }

        groupPerDeltaType = PropertyGroupBuilder.getPerType(groups, spec.getChangesetPropertyNames(), spec.getKeyPropertyNames());
        revisionEventType = new RevisionEventType(propertyDesc);
    }

    public RevisionEventType getEventType()
    {
        return revisionEventType;
    }

    public boolean validateRevisionableEventType(EventType eventType)
    {
        if (eventType == fullEventType)
        {
            return true;
        }
        if (groupPerDeltaType.containsKey(eventType))
        {
            return true;
        }

        if (eventType == null)
        {
            return false;
        }

        // Check all the supertypes to see if one of the matches the full or delta types
        Iterator<EventType> deepSupers = eventType.getDeepSuperTypes();
        if (deepSupers == null)
        {
            return false;
        }

        EventType type;
        for (;deepSupers.hasNext();)
        {
            type = deepSupers.next();
            if (type == fullEventType)
            {
                return true;
            }
            if (groupPerDeltaType.containsKey(type))
            {
                return true;
            }
        }

        return false;
    }

    public EventBean getRevision(EventBean event)
    {
        return new RevisionEventBeanDeclared(revisionEventType, event);
    }

    public void onUpdate(EventBean[] newData, EventBean[] oldData, NamedWindowRootView namedWindowRootView, NamedWindowIndexRepository indexRepository)
    {
        // If new data is filled, it is not a delete
        if ((newData == null) || (newData.length == 0))
        {
            // we are removing an event
            RevisionEventBeanDeclared revisionEvent = (RevisionEventBeanDeclared) oldData[0];
            MultiKeyUntyped key = revisionEvent.getKey();
            statePerKey.remove(key);

            // Insert into indexes for fast deletion, if there are any
            for (EventTable table : indexRepository.getTables())
            {
                table.remove(oldData);
            }

            // make as not the latest event since its due for removal
            revisionEvent.setLatest(false);

            namedWindowRootView.updateChildren(null, oldData);
            return;
        }

        RevisionEventBeanDeclared revisionEvent = (RevisionEventBeanDeclared) newData[0];
        EventBean underlyingEvent = revisionEvent.getUnderlyingFullOrDelta();
        EventType underyingEventType = underlyingEvent.getEventType();

        // obtain key values
        MultiKeyUntyped key = null;
        RevisionTypeDescDeclared typesDesc = null;
        boolean isFullEventType = false;
        if (underyingEventType == fullEventType)
        {
            key = getKeys(underlyingEvent, fullKeyGetters);
            isFullEventType = true;
        }
        else
        {
            typesDesc = groupPerDeltaType.get(underyingEventType);

            // if this type cannot be found, check all supertypes, if any
            if (typesDesc == null)
            {
                Iterator<EventType> superTypes = underyingEventType.getDeepSuperTypes();
                if (superTypes != null)
                {
                    EventType superType;
                    for (;superTypes.hasNext();)
                    {
                        superType = superTypes.next();
                        if (superType == fullEventType)
                        {
                            key = getKeys(underlyingEvent, fullKeyGetters);
                            isFullEventType = true;
                            break;
                        }
                        typesDesc = groupPerDeltaType.get(superType);
                        if (typesDesc != null)
                        {
                            groupPerDeltaType.put(underyingEventType, typesDesc);
                            key = getKeys(underlyingEvent, typesDesc.getKeyPropertyGetters());
                            break;
                        }
                    }
                }
            }
            else
            {
                key = getKeys(underlyingEvent, typesDesc.getKeyPropertyGetters());
            }

            if (key == null)
            {
                log.warn("Ignoring event of event type '" + underyingEventType + "' for revision processing type '" + revisionEventTypeAlias);
                return;
            }
        }

        // get the state for this key value
        RevisionStateDeclared revisionState = statePerKey.get(key);

        // Delta event and no full
        if ((!isFullEventType) && (revisionState == null))
        {
            return; // Ignore the event, its a delta and we don't currently have a full event for it
        }

        // New full event
        if (revisionState == null)
        {
            revisionState = new RevisionStateDeclared(underlyingEvent, null, null);
            statePerKey.put(key, revisionState);

            // prepare revison event
            revisionEvent.setLastFullEvent(underlyingEvent);
            revisionEvent.setKey(key);
            revisionEvent.setHolders(null);
            revisionEvent.setLatest(true);

            // Insert into indexes for fast deletion, if there are any
            for (EventTable table : indexRepository.getTables())
            {
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
        if (isFullEventType)
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
            holders[groupNum] = new RevisionBeanHolder(versionNumber, underlyingEvent, typesDesc.getChangesetPropertyGetters());
            revisionState.setHolders(holders);
        }

        // prepare revision event
        revisionEvent.setLastFullEvent(revisionState.getFullEventUnderlying());
        revisionEvent.setHolders(revisionState.getHolders());
        revisionEvent.setKey(key);
        revisionEvent.setLatest(true);

        // get prior event
        RevisionEventBeanDeclared lastEvent = revisionState.getLastEvent();
        lastEvent.setLatest(false);

        // data to post
        EventBean[] newDataPost = new EventBean[]{revisionEvent};
        EventBean[] oldDataPost = new EventBean[]{lastEvent};

        // update indexes
        for (EventTable table : indexRepository.getTables())
        {
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
                RevisionEventBeanDeclared fullRevision = (RevisionEventBeanDeclared) it.next();
                MultiKeyUntyped key = fullRevision.getKey();
                RevisionStateDeclared state = statePerKey.get(key);
                list.add(state.getLastEvent());
            }
            return list;
        }
        finally
        {
            createWindowStmtHandle.getStatementLock().releaseLock(null);
        }
    }

    public void removeOldData(EventBean[] oldData, NamedWindowIndexRepository indexRepository)
    {
        for (int i = 0; i < oldData.length; i++)
        {
            RevisionEventBeanDeclared event = (RevisionEventBeanDeclared) oldData[i];

            // If the remove event is the latest event, remove from all caches
            if (event.isLatest())
            {
                MultiKeyUntyped key = event.getKey();
                statePerKey.remove(key);

                for (EventTable table : indexRepository.getTables())
                {
                    table.remove(oldData);
                }
            }
        }
    }

    public String getRevisionEventTypeAlias()
    {
        return revisionEventTypeAlias;
    }
}
