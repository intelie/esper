/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.support;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

public class ResultAssertTestResult
{
    private final String category;
    private final String title;
    private final String[] properties;

    private final TreeMap<Long, Map<Integer, StepDesc>> assertions;

    public ResultAssertTestResult(String category, String title, String[] properties) {
        this.category = category;
        this.title = title;
        this.properties = properties;

        assertions = new TreeMap<Long, Map<Integer, StepDesc>>();
    }

    public void addResultInsert(long time, int step, Object[][] newDataPerRow)
    {        
        addResultInsRem(time, step, newDataPerRow, null);
    }

    public void addResultRemove(long time, int step, Object[][] oldDataPerRow)
    {
        addResultInsRem(time, step, null, oldDataPerRow);
    }

    public void addResultInsRem(long time, int step, Object[][] newDataPerRow, Object[][] oldDataPerRow)
    {
        if (step >= 10)
        {
            throw new IllegalArgumentException("Step max value is 10 for any time slot");
        }
        Map<Integer, StepDesc> stepMap = assertions.get(time);
        if (stepMap == null)
        {
            stepMap = new HashMap<Integer, StepDesc>();
            assertions.put(time, stepMap);
        }

        if (stepMap.containsKey(step))
        {
            throw new IllegalArgumentException("Step already in map for time slot");
        }
        stepMap.put(step, new StepDesc(step, newDataPerRow, oldDataPerRow));
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String[] getProperties() {
        return properties;
    }

    public TreeMap<Long, Map<Integer, StepDesc>> getAssertions() {
        return assertions;
    }
}
