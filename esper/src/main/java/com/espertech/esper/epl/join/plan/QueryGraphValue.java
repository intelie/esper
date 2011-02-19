/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Property lists stored as a value for each stream-to-stream relationship, for use by {@link QueryGraph}.
 */
public class QueryGraphValue
{
    private List<String> propertiesValue;
    private List<String> propertiesKey;
    private List<QueryGraphValueRange> rangeEntries;

    /**
     * Ctor.
     */
    public QueryGraphValue()
    {
        propertiesValue = new LinkedList<String>();
        propertiesKey = new LinkedList<String>();
        rangeEntries = new ArrayList<QueryGraphValueRange>();
    }

    public List<QueryGraphValueRange> getRangeEntries() {
        return rangeEntries;
    }

    /**
     * Add key and index property.
     * @param keyProperty - key property
     * @param indexProperty - index property
     * @return true if added and either property did not exist, false if either already existed
     */
    public boolean add(String indexProperty, String keyProperty)
    {
        if (propertiesValue.contains(keyProperty))
        {
            return false;
        }
        if (propertiesKey.contains(indexProperty))
        {
            return false;
        }
        propertiesValue.add(keyProperty);
        propertiesKey.add(indexProperty);
        return true;
    }

    /**
     * Returns property names for left stream.
     * @return property names
     */
    public List<String> getPropertiesValue()
    {
        return propertiesValue;
    }

    /**
     * Returns property names for right stream.
     * @return property names
     */
    public List<String> getPropertiesKey()
    {
        return propertiesKey;
    }

    public String toString()
    {
        return "QueryGraphValue " +
                " propertiesValue=" + Arrays.toString(propertiesValue.toArray()) +
                " propertiesKey=" + Arrays.toString(propertiesKey.toArray()) +
                " rangeEntries=" + Arrays.toString(rangeEntries.toArray());
    }

    public void addRange(QueryGraphRangeEnum rangeType, String propertyStart, String propertyEnd, String propertyValue) {
        if (!rangeType.isRange()) {
            throw new IllegalArgumentException("Expected range type, received " + rangeType);
        }

        // duplicate can be removed right away
        for (QueryGraphValueRange entry : rangeEntries) {
            if (!entry.getPropertyValue().equals(propertyValue)) {
                continue;
            }
            if (entry.getType() == rangeType) {
                QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) entry;
                if (in.getPropertyStart().equals(propertyStart) &&
                    in.getPropertyEnd().equals(propertyEnd)) {
                    return;
                }
            }
        }

        rangeEntries.add(new QueryGraphValueRangeIn(rangeType, propertyStart, propertyEnd, propertyValue, true));
    }

    public void addRelOp(String propertyKey, QueryGraphRangeEnum op, String propertyValue, boolean isBetweenOrIn) {

        // Note: Read as follows:
        // System.out.println("If I have an index on '" + propertyValue + "' I'm evaluating " + propertyKey + " and finding all values of " + propertyValue + " " + op + " then " + propertyKey);

        // Check if there is an opportunity to convert this to a range or remove an earlier specification
        List<QueryGraphValueRange> matches = new ArrayList<QueryGraphValueRange>();
        for (QueryGraphValueRange entry : rangeEntries) {
            if (!entry.getPropertyValue().equals(propertyValue)) {
                continue;
            }
            // duplicate can be removed right away
            if (entry.getType() == op) {
                QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) entry;
                if (relOp.getPropertyKey().equals(propertyKey)) {
                    return;
                }
            }
            matches.add(entry);
        }

        if (matches.isEmpty()) {
            rangeEntries.add(new QueryGraphValueRangeRelOp(op, propertyKey, propertyValue, isBetweenOrIn));
            return;
        }

        // consolidate with an existing entry
        List<QueryGraphValueRange> deletes = new ArrayList<QueryGraphValueRange>();
        for (QueryGraphValueRange match : matches) {

            if (match.getType().isRange()) {
                continue;
            }

            if (match instanceof QueryGraphValueRangeRelOp) {
                QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) match;
                QueryGraphRangeConsolidateDesc opsDesc = QueryGraphRangeUtil.getCanConsolidate(op, match.getType());
                if (opsDesc != null) {
                    String start = !opsDesc.isReverse() ? relOp.getPropertyKey() : propertyKey;
                    String end = !opsDesc.isReverse() ?  propertyKey : relOp.getPropertyKey();
                    addRange(opsDesc.getType(), start, end, propertyValue);
                    deletes.add(match);
                }
            }
        }

        if (deletes.isEmpty()) {
            rangeEntries.add(new QueryGraphValueRangeRelOp(op, propertyKey, propertyValue, isBetweenOrIn));
        }
        else {
            rangeEntries.removeAll(deletes);
        }
    }

    public String[] getRangeEntriesValueProperties() {
        if (rangeEntries.isEmpty()) {
            return null;
        }
        String[] props = new String[rangeEntries.size()];
        for (int i = 0; i < rangeEntries.size(); i++) {
            props[i] = rangeEntries.get(i).getPropertyValue();
        }
        return props;
    }
}

