/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.util.CollectionUtil;

import java.util.Arrays;

/**
 * Specifies an index to build as part of an overall query plan.
 */
public class QueryPlanIndexItem
{
    private String[] indexProps;
    private Class[] optIndexCoercionTypes;
    private String[] rangeProps;
    private Class[] optRangeCoercionTypes;

    /**
     * Ctor.
     * @param indexProps - array of property names with the first dimension suplying the number of
     * distinct indexes. The second dimension can be empty and indicates a full table scan.
     * @param optIndexCoercionTypes - array of coercion types for each index, or null entry for no coercion required
     */
    public QueryPlanIndexItem(String[] indexProps, Class[] optIndexCoercionTypes, String[] rangeProps, Class[] optRangeCoercionTypes) {
        this.indexProps = indexProps;
        this.optIndexCoercionTypes = optIndexCoercionTypes;
        this.rangeProps = rangeProps;
        this.optRangeCoercionTypes = optRangeCoercionTypes;
    }

    public String[] getIndexProps() {
        return indexProps;
    }

    public Class[] getOptIndexCoercionTypes() {
        return optIndexCoercionTypes;
    }

    public String[] getRangeProps() {
        return rangeProps;
    }

    public Class[] getOptRangeCoercionTypes() {
        return optRangeCoercionTypes;
    }

    public void setOptIndexCoercionTypes(Class[] optIndexCoercionTypes) {
        this.optIndexCoercionTypes = optIndexCoercionTypes;
    }

    @Override
    public String toString() {
        return "QueryPlanIndexItem{" +
                "indexProps=" + (indexProps == null ? null : Arrays.asList(indexProps)) +
                ", rangeProps=" + (rangeProps == null ? null : Arrays.asList(rangeProps)) +
                ", optIndexCoercionTypes=" + (optIndexCoercionTypes == null ? null : Arrays.asList(optIndexCoercionTypes)) +
                ", optRangeCoercionTypes=" + (optRangeCoercionTypes == null ? null : Arrays.asList(optRangeCoercionTypes)) +
                '}';
    }

    public boolean equalsCompareSortedProps(QueryPlanIndexItem other) {
        String[] otherIndexProps = CollectionUtil.copySortArray(other.getIndexProps());
        String[] thisIndexProps = CollectionUtil.copySortArray(this.getIndexProps());
        String[] otherRangeProps = CollectionUtil.copySortArray(other.getRangeProps());
        String[] thisRangeProps = CollectionUtil.copySortArray(this.getRangeProps());
        return CollectionUtil.compare(otherIndexProps, thisIndexProps) && CollectionUtil.compare(otherRangeProps, thisRangeProps);
    }
}
