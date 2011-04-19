/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.util.UuidGenerator;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Specifies an index to build as part of an overall query plan.
 */
public class QueryPlanIndex
{
    private Map<String, QueryPlanIndexItem> items;

    public QueryPlanIndex(Map<String, QueryPlanIndexItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("Null value not allowed for items");
        }
        this.items = items;
    }

    public Map<String, QueryPlanIndexItem> getItems() {
        return items;
    }

    public static QueryPlanIndex makeIndex(QueryPlanIndexItem ... items) {
        Map<String, QueryPlanIndexItem> result = new LinkedHashMap<String, QueryPlanIndexItem>();
        for (QueryPlanIndexItem item : items) {
            result.put(UuidGenerator.generate(), item);            
        }
        return new QueryPlanIndex(result);
    }

    public static QueryPlanIndex makeIndex(List<QueryPlanIndexItem> indexesSet) {
        Map<String, QueryPlanIndexItem> items = new LinkedHashMap<String, QueryPlanIndexItem>();
        for (QueryPlanIndexItem item : indexesSet) {
            items.put(UuidGenerator.generate(), item);
        }
        return new QueryPlanIndex(items);
    }

    /**
     * Find a matching index for the property names supplied.
     * @param indexProps - property names to search for
     * @return -1 if not found, or offset within indexes if found
     */
    protected String getIndexNum(String[] indexProps, String[] rangeProps)
    {
        QueryPlanIndexItem proposed = new QueryPlanIndexItem(indexProps, null, rangeProps, null);
        for (Map.Entry<String, QueryPlanIndexItem> entry : items.entrySet()) {
            if (entry.getValue().equalsCompareSortedProps(proposed)) {
                return entry.getKey();
            }
        }

        return null;
    }

    protected String getFirstIndexNum() {
        return items.keySet().iterator().next();
    }

    /**
     * Add an index specification element.
     * @param indexProperties - list of property names to index
     * @param coercionTypes - list of coercion types if required, or null if no coercion required
     * @return number indicating position of index that was added
     */
    public String addIndex(String[] indexProperties, Class[] coercionTypes)
    {
        String uuid = UuidGenerator.generate();
        items.put(uuid, new QueryPlanIndexItem(indexProperties, coercionTypes, null, null));
        return uuid;
    }

    /**
     * For testing - Returns property names of all indexes.
     * @return property names array
     */
    public String[][] getIndexProps()
    {
        String[][] arr = new String[items.size()][];
        int count = 0;
        for (Map.Entry<String, QueryPlanIndexItem> entry : items.entrySet()) {
            arr[count] = entry.getValue().getIndexProps();
            count++;
        }
        return arr;
    }


    /**
     * Returns a list of coercion types for a given index.
     * @param indexProperties is the index field names
     * @return coercion types, or null if no coercion is required
     */
    public Class[] getCoercionTypes(String[] indexProperties)
    {
        for (Map.Entry<String, QueryPlanIndexItem> entry : items.entrySet())
        {
            if (Arrays.deepEquals(entry.getValue().getIndexProps(), indexProperties))
            {
                return entry.getValue().getOptIndexCoercionTypes();
            }
        }
        throw new IllegalArgumentException("Index properties not found");
    }

    /**
     * Sets the coercion types for a given index.
     * @param indexProperties is the index property names
     * @param coercionTypes is the coercion types
     */
    public void setCoercionTypes(String[] indexProperties, Class[] coercionTypes)
    {
        boolean found = false;
        for (Map.Entry<String, QueryPlanIndexItem> entry : items.entrySet())
        {
            if (Arrays.deepEquals(entry.getValue().getIndexProps(), indexProperties))
            {
                entry.getValue().setOptIndexCoercionTypes(coercionTypes);
                found = true;
            }
        }
        if (!found)
        {
            throw new IllegalArgumentException("Index properties not found");
        }
    }

    public String toString()
    {
        if (items.isEmpty()) {
            return "    (none)";
        }
        StringBuilder buf = new StringBuilder();
        String delimiter = "";
        for (Map.Entry<String, QueryPlanIndexItem> entry : items.entrySet())
        {
            buf.append(delimiter);
            buf.append("    item " + entry.getKey() + " : " + entry.getValue());
            delimiter = "\n";
        }
        return buf.toString();
    }

    /**
     * Print index specifications in readable format.
     * @param indexSpecs - define indexes
     * @return readable format of index info
     */
    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend"})
    public static String print(QueryPlanIndex[] indexSpecs)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("QueryPlanIndex[]\n");

        String delimiter = "";
        for (int i = 0; i < indexSpecs.length; i++)
        {
            buffer.append(delimiter);
            buffer.append("  index spec stream " + i + " : \n" + (indexSpecs[i] == null ? "    null" : indexSpecs[i]));
            delimiter = "\n";
        }

        return buffer.toString() + "\n";
    }
}
