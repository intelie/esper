package com.espertech.esper.event.rev;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class PropertyGroupBuilder
{
    private static final Log log = LogFactory.getLog(PropertyGroupBuilder.class);

    public static Map<EventType, RevisionEventTypeDesc> getPerType(PropertyGroupDesc[] groups, String[] allProperties, String[] keyProperties)
    {
        Map<EventType, RevisionEventTypeDesc> perType = new HashMap<EventType, RevisionEventTypeDesc>();
        for (PropertyGroupDesc group : groups)
        {
            for (EventType type : group.getTypes().keySet())
            {
                EventPropertyGetter[] allGetters = getGetters(type, allProperties);
                EventPropertyGetter[] keyGetters = getGetters(type, keyProperties);
                RevisionEventTypeDesc pair = new RevisionEventTypeDesc(keyGetters, allGetters, group);
                perType.put(type, pair);
            }
        }
        return perType;
    }

    public static Map<String, int[]> getGroupsPerProperty(PropertyGroupDesc[] groups)
    {
        Map<String, int[]> groupsNumsPerProp = new HashMap<String, int[]>();
        for (PropertyGroupDesc group : groups)
        {
            for (String property : group.getProperties())
            {
                int[] value = groupsNumsPerProp.get(property);
                if (value == null)
                {
                    value = new int[1];
                    groupsNumsPerProp.put(property, value);
                    value[0] = group.getGroupNum();
                }
                else
                {
                    int[] copy = new int[value.length + 1];
                    System.arraycopy(value, 0, copy, 0, value.length);
                    copy[value.length] = group.getGroupNum();
                    Arrays.sort(copy);
                    groupsNumsPerProp.put(property, copy);
                }                
            }
        }
        return groupsNumsPerProp;
    }

    public static PropertyGroupDesc[] analyzeGroups(String[] allProperties, EventType[] deltaEventTypes, String[] aliases)
    {
        if (deltaEventTypes.length != aliases.length)
        {
            throw new IllegalArgumentException("Delta event type number and alias number of elements don't match");
        }
        allProperties = copyAndSort(allProperties);

        Map<MultiKey<String>, PropertyGroupDesc> result = new LinkedHashMap<MultiKey<String>, PropertyGroupDesc>();
        int currentGroupNum = 0;

        for (int i = 0; i < deltaEventTypes.length; i++)
        {
            MultiKey<String> props = getPropertiesContributed(deltaEventTypes[i], allProperties);
            if (props.getArray().length == 0)
            {
                log.warn("Event type alias '" + aliases[i] + "' does not contribute (or override) any properties of the revision event type");
                continue;
            }

            PropertyGroupDesc propertyGroup = result.get(props);
            Map<EventType, String> typesForGroup = null;
            if (propertyGroup == null)
            {
                typesForGroup = new HashMap<EventType, String>();
                propertyGroup = new PropertyGroupDesc(currentGroupNum++, typesForGroup, props.getArray());
                result.put(props, propertyGroup);
            }
            else
            {
                typesForGroup = propertyGroup.getTypes();
            }
            typesForGroup.put(deltaEventTypes[i], aliases[i]);
        }

        Collection<PropertyGroupDesc> out = result.values();
        PropertyGroupDesc[] array = out.toArray(new PropertyGroupDesc[out.size()]);

        if (log.isDebugEnabled())
        {
            log.debug(".analyzeGroups " + Arrays.toString(array));            
        }
        return array;
    }

    private static MultiKey<String> getPropertiesContributed(EventType deltaEventType, String[] allPropertiesSorted) {

        TreeSet<String> props = new TreeSet<String>();
        for (String property : deltaEventType.getPropertyNames())
        {
            for (String propInAll : allPropertiesSorted)
            {
                if (propInAll.equals(property))
                {
                    props.add(property);
                    break;
                }
            }
        }
        return new MultiKey<String>(props.toArray(new String[props.size()]));
    }

    protected static String[] copyAndSort(String[] input)
    {
        String[] result = new String[input.length];
        System.arraycopy(input, 0, result, 0, input.length);
        Arrays.sort(result);
        return result;
    }

    protected static EventPropertyGetter[] getGetters(EventType eventType, String[] propertyNames)
    {
        EventPropertyGetter[] getters = new EventPropertyGetter[propertyNames.length];
        for (int i = 0; i < getters.length; i++)
        {
            getters[i] = eventType.getGetter(propertyNames[i]);
        }
        return getters;
    }
}
