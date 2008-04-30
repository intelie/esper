package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;

import java.util.Map;
import java.util.HashMap;

public class RevisionProcessor
{
    private final String namedWindowName;
    private final RevisionEventType revisionEventType;

    public RevisionProcessor(String namedWindowName, RevisionSpec spec)
    {
        this.namedWindowName = namedWindowName;

        String allPropertyNames[] = PropertyGroupBuilder.copyAndSort(spec.getFullEventType().getPropertyNames());
        PropertyGroupDesc groups[] = PropertyGroupBuilder.analyzeGroups(allPropertyNames, spec.getDeltaTypes(), spec.getDeltaAliases());
        
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

        revisionEventType = new RevisionEventType(spec.getFullEventType(), propertyDesc);
    }

    public RevisionEventType getEventType()
    {
        return revisionEventType;
    }
}
