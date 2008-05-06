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
import com.espertech.esper.view.StatementStopCallback;
import com.espertech.esper.view.StatementStopService;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.client.ConfigurationRevisionEventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class RevisionProcessorMerge implements RevisionProcessor
{
    private static final Log log = LogFactory.getLog(RevisionProcessorMerge.class);

    private final RevisionSpec spec;
    private final String revisionEventTypeAlias;
    private final RevisionEventType revisionEventType;
    private final Map<EventType, RevisionTypeDescMerge> infoPerDeltaType;
    private final RevisionTypeDescMerge infoFullType;
    private final Map<MultiKeyUntyped, RevisionStateMerge> statePerKey;
    private final UpdateStrategy updateStrategy;

    public RevisionProcessorMerge(String revisionEventTypeAlias, RevisionSpec spec, StatementStopService statementStopService)
    {
        // on statement stop, remove versions
        statementStopService.addSubscriber(new StatementStopCallback() {
            public void statementStopped()
            {
                statePerKey.clear();
            }
        });

        this.revisionEventTypeAlias = revisionEventTypeAlias;
        this.statePerKey = new HashMap<MultiKeyUntyped, RevisionStateMerge>();
        this.spec = spec;

        // For all changeset properties, add type descriptors (property number, getter etc)
        Map<String, RevisionPropertyTypeDesc> propertyDesc = new HashMap<String, RevisionPropertyTypeDesc>();
        int count = 0;

        for (String property : spec.getChangesetPropertyNames())
        {
            EventPropertyGetter fullGetter = spec.getFullEventType().getGetter(property);
            int propertyNumber = count;
            final RevisionGetterParameters params = new RevisionGetterParameters(property, propertyNumber, fullGetter, null);

            // if there are no groups (full event property only), then simply use the full event getter
            EventPropertyGetter revisionGetter = new EventPropertyGetter() {
                    public Object get(EventBean eventBean) throws PropertyAccessException
                    {
                        RevisionEventBeanMerge riv = (RevisionEventBeanMerge) eventBean;
                        return riv.getVersionedValue(params);
                    }

                    public boolean isExistsProperty(EventBean eventBean)
                    {
                        return true;
                    }
                };

            Class type = spec.getFullEventType().getPropertyType(property);
            if (type == null)
            {
                for (EventType deltaType : spec.getDeltaTypes())
                {
                    Class dtype = deltaType.getPropertyType(property);
                    if (dtype != null)
                    {
                        type = dtype;
                        break;
                    }
                }
            }
            RevisionPropertyTypeDesc propertyTypeDesc = new RevisionPropertyTypeDesc(revisionGetter, params, type);
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
                    RevisionEventBeanMerge riv = (RevisionEventBeanMerge) eventBean;
                    return riv.getKey().getKeys()[keyPropertyNumber];
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true;
                }
            };

            Class type = spec.getFullEventType().getPropertyType(property);
            if (type == null)
            {
                for (EventType deltaType : spec.getDeltaTypes())
                {
                    Class dtype = deltaType.getPropertyType(property);
                    if (dtype != null)
                    {
                        type = dtype;
                        break;
                    }
                }
            }
            RevisionPropertyTypeDesc propertyTypeDesc = new RevisionPropertyTypeDesc(revisionGetter, null, type);
            propertyDesc.put(property, propertyTypeDesc);
            count++;
        }

        // compile for each event type a list of getters and indexes within the overlay
        infoPerDeltaType = new HashMap<EventType, RevisionTypeDescMerge>();
        for (EventType deltaType : spec.getDeltaTypes())
        {
            RevisionTypeDescMerge typeDesc = makeTypeDesc(deltaType, spec.getPropertyRevision());
            infoPerDeltaType.put(deltaType, typeDesc);
        }
        infoFullType = makeTypeDesc(spec.getFullEventType(), spec.getPropertyRevision());

        // how to handle updates to a full event
        if (spec.getPropertyRevision() == ConfigurationRevisionEventType.PropertyRevision.MERGE_DECLARED)
        {
            updateStrategy = new UpdateStrategyDeclared(spec);
        }
        else if (spec.getPropertyRevision() == ConfigurationRevisionEventType.PropertyRevision.MERGE_NON_NULL)
        {
            updateStrategy = new UpdateStrategyNonNull(spec);
        }         
        else if (spec.getPropertyRevision() == ConfigurationRevisionEventType.PropertyRevision.MERGE_EXISTS)
        {
            updateStrategy = new UpdateStrategyExists(spec);
        }
        else
        {
            throw new IllegalArgumentException("Unknown revision type '" + spec.getPropertyRevision() + "'");
        }

        revisionEventType = new RevisionEventType(propertyDesc);
    }

    public RevisionEventType getEventType()
    {
        return revisionEventType;
    }

    public boolean validateRevisionableEventType(EventType eventType)
    {
        if (eventType == spec.getFullEventType())
        {
            return true;
        }
        if (infoPerDeltaType.containsKey(eventType))
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
            if (type == spec.getFullEventType())
            {
                return true;
            }
            if (infoPerDeltaType.containsKey(type))
            {
                return true;
            }
        }

        return false;
    }

    public EventBean getRevision(EventBean event)
    {
        return new RevisionEventBeanMerge(revisionEventType, event);
    }

    public void onUpdate(EventBean[] newData, EventBean[] oldData, NamedWindowRootView namedWindowRootView, NamedWindowIndexRepository indexRepository)
    {
        // If new data is filled, it is not a delete
        if ((newData == null) || (newData.length == 0))
        {
            // we are removing an event
            RevisionEventBeanMerge revisionEvent = (RevisionEventBeanMerge) oldData[0];
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

        RevisionEventBeanMerge revisionEvent = (RevisionEventBeanMerge) newData[0];
        EventBean underlyingEvent = revisionEvent.getUnderlyingFullOrDelta();
        EventType underyingEventType = underlyingEvent.getEventType();

        // obtain key values
        MultiKeyUntyped key = null;
        RevisionTypeDescMerge typesDesc = null;
        boolean isFullEventType = false;
        if (underyingEventType == spec.getFullEventType())
        {
            typesDesc = infoFullType;
            key = getKeys(underlyingEvent, infoFullType.getKeyPropertyGetters());
            isFullEventType = true;
        }
        else
        {
            typesDesc = infoPerDeltaType.get(underyingEventType);

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
                        if (superType == spec.getFullEventType())
                        {
                            typesDesc = infoFullType;
                            key = getKeys(underlyingEvent, infoFullType.getKeyPropertyGetters());
                            isFullEventType = true;
                            break;
                        }
                        typesDesc = infoPerDeltaType.get(superType);
                        if (typesDesc != null)
                        {
                            infoPerDeltaType.put(underyingEventType, typesDesc);
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
        RevisionStateMerge revisionState = statePerKey.get(key);

        // Delta event and no full
        if ((!isFullEventType) && (revisionState == null))
        {
            return; // Ignore the event, its a delta and we don't currently have a full event for it
        }

        // New full event
        if (revisionState == null)
        {
            revisionState = new RevisionStateMerge(underlyingEvent, null, null);
            statePerKey.put(key, revisionState);

            // prepare revison event
            revisionEvent.setLastFullEvent(underlyingEvent);
            revisionEvent.setKey(key);
            revisionEvent.setOverlay(null);
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

        // handle update, changing revision state and event as required
        updateStrategy.handleUpdate(isFullEventType, revisionState, revisionEvent, typesDesc);

        // prepare revision event
        revisionEvent.setLastFullEvent(revisionState.getFullEventUnderlying());
        revisionEvent.setOverlay(revisionState.getOverlays());
        revisionEvent.setKey(key);
        revisionEvent.setLatest(true);

        // get prior event
        RevisionEventBeanMerge lastEvent = revisionState.getLastEvent();
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
                RevisionEventBeanMerge fullRevision = (RevisionEventBeanMerge) it.next();
                MultiKeyUntyped key = fullRevision.getKey();
                RevisionStateMerge state = statePerKey.get(key);
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
        for (EventBean anOldData : oldData)
        {
            RevisionEventBeanMerge event = (RevisionEventBeanMerge) anOldData;

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

    private RevisionTypeDescMerge makeTypeDesc(EventType eventType, ConfigurationRevisionEventType.PropertyRevision propertyRevision)
    {
        EventPropertyGetter[] keyPropertyGetters = PropertyGroupBuilder.getGetters(eventType, spec.getKeyPropertyNames());

        int len = spec.getChangesetPropertyNames().length;
        List<EventPropertyGetter> listOfGetters = new ArrayList<EventPropertyGetter>();
        List<Integer> listOfIndexes = new ArrayList<Integer>();

        for (int i = 0; i < len; i++)
        {
            String propertyName = spec.getChangesetPropertyNames()[i];
            EventPropertyGetter getter = null;

            if (propertyRevision != ConfigurationRevisionEventType.PropertyRevision.MERGE_EXISTS)
            {
                getter = eventType.getGetter(spec.getChangesetPropertyNames()[i]);
            }
            else
            {
                // only declared properties may be used a dynamic properties to avoid confusion of properties suddenly appearing
                for (String propertyNamesDeclared : eventType.getPropertyNames())
                {
                    if (propertyNamesDeclared.equals(propertyName))
                    {
                        // use dynamic properties
                        getter = eventType.getGetter(spec.getChangesetPropertyNames()[i] + "?");
                        break;
                    }
                }
            }
            
            if (getter != null)
            {
                listOfGetters.add(getter);
                listOfIndexes.add(i);
            }
        }

        EventPropertyGetter[] changesetPropertyGetters = listOfGetters.toArray(new EventPropertyGetter[listOfGetters.size()]);
        int[] changesetPropertyIndex = new int[listOfIndexes.size()];
        for (int i = 0; i < listOfIndexes.size(); i++)
        {
            changesetPropertyIndex[i] = listOfIndexes.get(i);
        }

        return new RevisionTypeDescMerge(keyPropertyGetters, changesetPropertyGetters, changesetPropertyIndex);
    }    
}
