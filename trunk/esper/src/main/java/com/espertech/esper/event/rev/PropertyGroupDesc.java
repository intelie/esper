package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;

import java.util.Arrays;
import java.util.Map;

/**
 * For use with building groups of event properties to reduce overhead in maintaining versions.
 */
public class PropertyGroupDesc {

    private final int groupNum;
    private final Map<EventType, String> types;
    private final String[] properties;

    /**
     * Ctor.
     * @param groupNum the group number
     * @param aliasTypeSet the event types and their aliases whose totality of properties fully falls within this group.
     * @param properties is the properties in the group
     */
    public PropertyGroupDesc(int groupNum, Map<EventType, String> aliasTypeSet, String[] properties) {
        this.groupNum = groupNum;
        this.types = aliasTypeSet;
        this.properties = properties;
    }

    /**
     * Returns the group number.
     * @return group number
     */
    public int getGroupNum() {
        return groupNum;
    }

    /**
     * Returns the types.
     * @return types
     */
    public Map<EventType, String> getTypes() {
        return types;
    }

    /**
     * Returns the properties.
     * @return properties
     */
    public String[] getProperties() {
        return properties;
    }

    public String toString()
    {
        return "groupNum=" + groupNum +
               " properties=" + Arrays.toString(properties) +
               " aliasTypes=" + types.toString();
    }
}
