package com.espertech.esper.regression.support;

import com.espertech.esper.client.soda.EPStatementObjectModel;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class EventExpressionCase
{
    private String expressionText;
    private EPStatementObjectModel objectModel;
    private LinkedHashMap<String, LinkedList<EventDescriptor>> expectedResults;

    public EventExpressionCase(String expressionText)
    {
        this.expressionText = expressionText;
        this.expectedResults = new LinkedHashMap<String, LinkedList<EventDescriptor>>();
    }

    public EventExpressionCase(EPStatementObjectModel objectModel)
    {
        this.objectModel = objectModel;
        this.expectedResults = new LinkedHashMap<String, LinkedList<EventDescriptor>>();
    }

    public String getExpressionText()
    {
        return expressionText;
    }

    public EPStatementObjectModel getObjectModel()
    {
        return objectModel;
    }

    public LinkedHashMap<String, LinkedList<EventDescriptor>> getExpectedResults()
    {
        return expectedResults;
    }

    public void add(String expectedOnEventId)
    {
        addDesc(expectedOnEventId);
    }

    public void add(String expectedOnEventId, String tag, Object bean)
    {
        EventDescriptor desc = addDesc(expectedOnEventId);
        desc.put(tag, bean);
    }

    public void add(String expectedOnEventId, String tagOne, Object beanOne,
                    String tagTwo, Object beanTwo)
    {
        EventDescriptor desc = addDesc(expectedOnEventId);
        desc.put(tagOne, beanOne);
        desc.put(tagTwo, beanTwo);
    }

    public void add(String expectedOnEventId, String tagOne, Object beanOne,
                    String tagTwo, Object beanTwo,
                    String tagThree, Object beanThree)
    {
        EventDescriptor desc = addDesc(expectedOnEventId);
        desc.put(tagOne, beanOne);
        desc.put(tagTwo, beanTwo);
        desc.put(tagThree, beanThree);
    }

    public void add(String expectedOnEventId, String tagOne, Object beanOne,
                    String tagTwo, Object beanTwo,
                    String tagThree, Object beanThree,
                    String tagFour, Object beanFour)
    {
        EventDescriptor desc = addDesc(expectedOnEventId);
        desc.put(tagOne, beanOne);
        desc.put(tagTwo, beanTwo);
        desc.put(tagThree, beanThree);
        desc.put(tagFour, beanFour);
    }

    public void add(String expectedOnEventId, Object[][] tagsAndBeans)
    {
        EventDescriptor desc = addDesc(expectedOnEventId);
        for (int i = 0; i < tagsAndBeans.length; i++)
        {
            desc.put((String)tagsAndBeans[i][0], tagsAndBeans[i][1]);
        }
    }

    private EventDescriptor addDesc(String expectedOnEventId)
    {
        LinkedList<EventDescriptor> resultList = expectedResults.get(expectedOnEventId);

        if (resultList == null)
        {
            resultList = new LinkedList<EventDescriptor>();
            expectedResults.put(expectedOnEventId, resultList);
        }

        EventDescriptor eventDesc = new EventDescriptor();
        resultList.add(eventDesc);
        return eventDesc;
    }
}
