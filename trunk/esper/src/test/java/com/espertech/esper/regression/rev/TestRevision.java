package com.espertech.esper.regression.rev;

import junit.framework.TestCase;
import com.espertech.esper.event.*;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.lang.reflect.Array;

public class TestRevision extends TestCase
{
    private final EventAdapterService eventSource = SupportEventAdapterService.getService();

    private static final Map<String, int[]> propertyGroups = new HashMap<String, int[]>();
    static
    {
        propertyGroups.put("p0", new int[] {1, 2, 3});
        propertyGroups.put("p1", new int[] {0});
        propertyGroups.put("p2", new int[] {1, 3});
        propertyGroups.put("p3", new int[] {1});
        propertyGroups.put("p4", new int[] {2});
        propertyGroups.put("p5", new int[] {0, 3});
    }

    private static String[] fields = "p0,p1,p2,p3,p4,p5".split(",");

    public void testRevision()
    {
        EventType fullEventType = eventSource.addBeanType("FullEvent", SupportRevisionFull.class);
        EventBean fullEvent = eventSource.adapterForBean(new SupportRevisionFull("key", "f-p0", "f-p1", "f-p2", "f-p3", "f-p4", "f-p5"));
        EventBean d1Event = eventSource.adapterForBean(new SupportDeltaOne("key", "d1-p1", "d1-p5"));
        EventBean d2Event = eventSource.adapterForBean(new SupportDeltaTwo("key", "d2-p0", "d2-p2", "d2-p3"));
        EventBean d3Event = eventSource.adapterForBean(new SupportDeltaThree("key", "d3-p0", "d3-p4"));
        EventBean d4Event = eventSource.adapterForBean(new SupportDeltaFour("key", "d4-p0", "d4-p2", "d4-p5"));
        EventBean d5Event = eventSource.adapterForBean(new SupportDeltaFive("key", "d5-p1", "d5-p5"));

        String allPropertyNames[] = copyAndSort(fullEventType.getPropertyNames());
        Map<String, RevisionPropertyTypeDesc> propertyDesc = new HashMap<String, RevisionPropertyTypeDesc>();
        int count = 0;
        for (String property : allPropertyNames)
        {
            EventPropertyGetter fullGetter = fullEventType.getGetter(property);
            int propertyNumber = count;
            int[] propGroupsProperty = propertyGroups.get(property);
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

        RevisionEventType revisionEventType = new RevisionEventType(fullEventType, propertyDesc);

        RevisionBeanHolder holders[] = new RevisionBeanHolder[4];
        EventBean f0 = new RevisionEventBean(revisionEventType, fullEvent, holders);
        ArrayAssertionUtil.assertProps(f0, fields, new Object[] {"f-p0", "f-p1", "f-p2", "f-p3", "f-p4", "f-p5"});

        holders = arrayCopy(holders);
        holders[0] = new RevisionBeanHolder(2, d1Event, getGetters(d1Event, allPropertyNames));
        EventBean d1 = new RevisionEventBean(revisionEventType, fullEvent, holders);
        ArrayAssertionUtil.assertProps(d1, fields, new Object[] {"f-p0", "d1-p1", "f-p2", "f-p3", "f-p4", "d1-p5"});

        holders = arrayCopy(holders);
        holders[1] = new RevisionBeanHolder(3, d2Event, getGetters(d2Event, allPropertyNames));
        EventBean d2 = new RevisionEventBean(revisionEventType, fullEvent, holders);
        ArrayAssertionUtil.assertProps(d2, fields, new Object[] {"d2-p0", "d1-p1", "d2-p2", "d2-p3", "f-p4", "d1-p5"});

        holders = arrayCopy(holders);
        holders[2] = new RevisionBeanHolder(4, d3Event, getGetters(d3Event, allPropertyNames));
        EventBean d3 = new RevisionEventBean(revisionEventType, fullEvent, holders);
        ArrayAssertionUtil.assertProps(d3, fields, new Object[] {"d3-p0", "d1-p1", "d2-p2", "d2-p3", "d3-p4", "d1-p5"});

        holders = arrayCopy(holders);
        holders[3] = new RevisionBeanHolder(5, d4Event, getGetters(d4Event, allPropertyNames));
        EventBean d4 = new RevisionEventBean(revisionEventType, fullEvent, holders);
        ArrayAssertionUtil.assertProps(d4, fields, new Object[] {"d4-p0", "d1-p1", "d4-p2", "d2-p3", "d3-p4", "d4-p5"});

        holders = arrayCopy(holders);
        holders[0] = new RevisionBeanHolder(6, d5Event, getGetters(d5Event, allPropertyNames));
        EventBean d5 = new RevisionEventBean(revisionEventType, fullEvent, holders);
        ArrayAssertionUtil.assertProps(d5, fields, new Object[] {"d4-p0", "d5-p1", "d4-p2", "d2-p3", "d3-p4", "d5-p5"});
    }

    private RevisionBeanHolder[] arrayCopy(RevisionBeanHolder[] array)
    {
        RevisionBeanHolder[] result = new RevisionBeanHolder[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    private EventPropertyGetter[] getGetters(EventBean event, String[] allPropertyNames)
    {
        EventPropertyGetter[] getters = new EventPropertyGetter[allPropertyNames.length];
        for (int i = 0; i < getters.length; i++)
        {
            getters[i] = event.getEventType().getGetter(allPropertyNames[i]);
        }
        return getters;
    }

    private String[] copyAndSort(String[] input)
    {
        String[] result = new String[input.length];
        System.arraycopy(input, 0, result, 0, input.length);
        Arrays.sort(result);
        return result;
    }
}
