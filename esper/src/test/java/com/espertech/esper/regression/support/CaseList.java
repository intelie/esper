package com.espertech.esper.regression.support;

import java.util.LinkedList;
import java.util.List;

public class CaseList
{
    private LinkedList<EventExpressionCase> results;

    public CaseList()
    {
        results = new LinkedList<EventExpressionCase>();
    }

    public void addTest(EventExpressionCase desc)
    {
        results.add(desc);
    }

    public int getNumTests()
    {
        return results.size();
    }

    public List<EventExpressionCase> getResults()
    {
        return results;
    }
}
