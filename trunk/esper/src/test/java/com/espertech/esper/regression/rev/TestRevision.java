package com.espertech.esper.regression.rev;

import com.espertech.esper.event.*;
import com.espertech.esper.event.rev.*;
import com.espertech.esper.regression.rev.*;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestRevision extends TestCase
{
    private final EventAdapterService eventSource = SupportEventAdapterService.getService();

    private EventType fullEventType;
    private EventType types[];
    private EventBean fullEventBean;
    private EventBean beans[];
    private String[] fields = "p0,p1,p2,p3,p4,p5".split(",");

    public void setUp()
    {
        fullEventType = eventSource.addBeanType("FullEvent", SupportRevisionFull.class);

        types = new EventType[5];
        types[0] = eventSource.addBeanType("D1", SupportDeltaOne.class);
        types[1] = eventSource.addBeanType("D2", SupportDeltaTwo.class);
        types[2] = eventSource.addBeanType("D3", SupportDeltaThree.class);
        types[3] = eventSource.addBeanType("D4", SupportDeltaFour.class);
        types[4] = eventSource.addBeanType("D5", SupportDeltaFive.class);

        beans = new EventBean[5];
        fullEventBean = eventSource.adapterForBean(new SupportRevisionFull("key", "f-p0", "f-p1", "f-p2", "f-p3", "f-p4", "f-p5"));
        beans[0] = eventSource.adapterForBean(new SupportDeltaOne("key", "d1-p1", "d1-p5"));
        beans[1] = eventSource.adapterForBean(new SupportDeltaTwo("key", "d2-p0", "d2-p2", "d2-p3"));
        beans[2] = eventSource.adapterForBean(new SupportDeltaThree("key", "d3-p0", "d3-p4"));
        beans[3] = eventSource.adapterForBean(new SupportDeltaFour("key", "d4-p0", "d4-p2", "d4-p5"));
        beans[4] = eventSource.adapterForBean(new SupportDeltaFive("key", "d5-p1", "d5-p5"));
    }

    public void testRevision()
    {
        String allPropertyNames[] = copyAndSort(fullEventType.getPropertyNames());
        PropertyGroupDesc groups[] = PropertyGroupBuilder.analyzeGroups(allPropertyNames, types, new String[] {"D1", "D2", "D3", "D4", "D5"});
        Map<String, int[]> propsPerGroup = PropertyGroupBuilder.getGroupsPerProperty(groups);
        
        Map<String, RevisionPropertyTypeDesc> propertyDesc = new HashMap<String, RevisionPropertyTypeDesc>();
        int count = 0;
        for (String property : allPropertyNames)
        {
            EventPropertyGetter fullGetter = fullEventType.getGetter(property);
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

        RevisionEventType revisionEventType = new RevisionEventType(fullEventType, propertyDesc);

        RevisionBeanHolder holders[] = new RevisionBeanHolder[4];
        EventBean f0 = new RevisionEventBean(revisionEventType, null, fullEventBean, holders);
        ArrayAssertionUtil.assertProps(f0, fields, new Object[] {"f-p0", "f-p1", "f-p2", "f-p3", "f-p4", "f-p5"});

        holders = arrayCopy(holders);
        holders[0] = new RevisionBeanHolder(2, beans[0], getGetters(beans[0], allPropertyNames));
        EventBean d1 = new RevisionEventBean(revisionEventType, null, fullEventBean, holders);
        ArrayAssertionUtil.assertProps(d1, fields, new Object[] {"f-p0", "d1-p1", "f-p2", "f-p3", "f-p4", "d1-p5"});

        holders = arrayCopy(holders);
        holders[1] = new RevisionBeanHolder(3, beans[1], getGetters(beans[1], allPropertyNames));
        EventBean d2 = new RevisionEventBean(revisionEventType, null, fullEventBean, holders);
        ArrayAssertionUtil.assertProps(d2, fields, new Object[] {"d2-p0", "d1-p1", "d2-p2", "d2-p3", "f-p4", "d1-p5"});

        holders = arrayCopy(holders);
        holders[2] = new RevisionBeanHolder(4, beans[2], getGetters(beans[2], allPropertyNames));
        EventBean d3 = new RevisionEventBean(revisionEventType, null, fullEventBean, holders);
        ArrayAssertionUtil.assertProps(d3, fields, new Object[] {"d3-p0", "d1-p1", "d2-p2", "d2-p3", "d3-p4", "d1-p5"});

        holders = arrayCopy(holders);
        holders[3] = new RevisionBeanHolder(5, beans[3], getGetters(beans[3], allPropertyNames));
        EventBean d4 = new RevisionEventBean(revisionEventType, null, fullEventBean, holders);
        ArrayAssertionUtil.assertProps(d4, fields, new Object[] {"d4-p0", "d1-p1", "d4-p2", "d2-p3", "d3-p4", "d4-p5"});

        holders = arrayCopy(holders);
        holders[0] = new RevisionBeanHolder(6, beans[4], getGetters(beans[4], allPropertyNames));
        EventBean d5 = new RevisionEventBean(revisionEventType, null, fullEventBean, holders);
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
