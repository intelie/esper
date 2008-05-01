package com.espertech.esper.event.rev;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RevisionProcessor
{
    private static final Log log = LogFactory.getLog(RevisionProcessor.class);

    private final String namedWindowName;
    private final RevisionEventType revisionEventType;
    private final PropertyGroupDesc groups[];
    private final Map<EventType, RevisionEventTypeDesc> groupPerType;
    private final EventType fullEventType;
    private final EventPropertyGetter[] fullKeyGetters;

    private Lock lock;
    private long currentRevisionNum;
    private Map<MultiKeyUntyped, Pair<EventBean, RevisionBeanHolder[]>> lastFullPerKey;

    public RevisionProcessor(String namedWindowName, RevisionSpec spec)
    {
        this.namedWindowName = namedWindowName;
        this.lock = new ReentrantLock();
        this.lastFullPerKey = new HashMap<MultiKeyUntyped, Pair<EventBean, RevisionBeanHolder[]>>();
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

        groupPerType = PropertyGroupBuilder.getPerType(groups, allPropertyNames, spec.getKeyPropertyNames());
        revisionEventType = new RevisionEventType(spec.getFullEventType(), propertyDesc);
    }

    public RevisionEventType getEventType()
    {
        return revisionEventType;
    }

    public EventBean getRevision(EventBean event)
    {
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
        RevisionBeanHolder[] result = new RevisionBeanHolder[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }    
}
