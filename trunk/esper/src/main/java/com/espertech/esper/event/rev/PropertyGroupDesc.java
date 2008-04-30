package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;

import java.util.Arrays;
import java.util.Map;

public class PropertyGroupDesc {

    private final int groupNum;
    private final Map<EventType, String> types;
    private final String[] properties;

    public PropertyGroupDesc(int groupNum, Map<EventType, String> aliasTypeSet, String[] properties) {
        this.groupNum = groupNum;
        this.types = aliasTypeSet;
        this.properties = properties;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public Map<EventType, String> getTypes() {
        return types;
    }

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
