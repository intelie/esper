/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.plan;

import net.esper.event.EventType;
import net.esper.type.OuterJoinType;
import net.esper.util.JavaClassHelper;

/**
 * Builds a query plan for the simple 2-stream scenario.
 */
public class TwoStreamQueryPlanBuilder
{
    /**
     * Build query plan.
     * @param queryGraph - navigability info
     * @param optionalOuterJoinType - outer join type, null if not an outer join
     * @return query plan
     */
    public static QueryPlan build(EventType[] typesPerStream, QueryGraph queryGraph, OuterJoinType optionalOuterJoinType)
    {
        QueryPlanIndex[] indexSpecs = new QueryPlanIndex[2];
        QueryPlanNode[] execNodeSpecs = new QueryPlanNode[2];

        TableLookupPlan lookupPlans[] = new TableLookupPlan[2];

        // Without navigability, use full set indexes and lookup without key use
        if (!queryGraph.isNavigable(0, 1))
        {
            String[][] unkeyedIndexProps = new String[][] {new String[0]};

            indexSpecs[0] = new QueryPlanIndex(unkeyedIndexProps, new Class[][] {null});
            indexSpecs[1] = new QueryPlanIndex(unkeyedIndexProps, new Class[][] {null});
            lookupPlans[0] = new FullTableScanLookupPlan(0, 1, 0);
            lookupPlans[1] = new FullTableScanLookupPlan(1, 0, 0);
        }
        else
        {
            String[] keyProps = queryGraph.getKeyProperties(0, 1);
            String[] indexProps = queryGraph.getIndexProperties(0, 1);
            Class[] coercionTypes = getCoercionTypes(typesPerStream, 0, 1, keyProps, indexProps);

            indexSpecs[0] = new QueryPlanIndex(new String[][] {queryGraph.getIndexProperties(1, 0)}, new Class[][] {coercionTypes});
            indexSpecs[1] = new QueryPlanIndex(new String[][] {queryGraph.getIndexProperties(0, 1)}, new Class[][] {coercionTypes});
            lookupPlans[0] = new IndexedTableLookupPlan(0, 1, 0, queryGraph.getKeyProperties(0, 1));
            lookupPlans[1] = new IndexedTableLookupPlan(1, 0, 0, queryGraph.getKeyProperties(1, 0));
        }

        execNodeSpecs[0] = new TableLookupNode(lookupPlans[0]);
        execNodeSpecs[1] = new TableLookupNode(lookupPlans[1]);

        if (optionalOuterJoinType != null)
        {
            if ( (optionalOuterJoinType.equals(OuterJoinType.LEFT)) ||
                 (optionalOuterJoinType.equals(OuterJoinType.FULL)) )
            {
                execNodeSpecs[0] = new TableOuterLookupNode(lookupPlans[0]);
            }
            if ( (optionalOuterJoinType.equals(OuterJoinType.RIGHT)) ||
                 (optionalOuterJoinType.equals(OuterJoinType.FULL)) )
            {
                execNodeSpecs[1] = new TableOuterLookupNode(lookupPlans[1]);
            }
        }

        return new QueryPlan(indexSpecs, execNodeSpecs);
    }

    protected static Class[] getCoercionTypes(EventType[] typesPerStream,
                                            int lookupStream,
                                            int indexedStream,
                                            String[] keyProps,
                                            String[] indexProps)
    {
        // Determine if any coercion is required
        if (indexProps.length != keyProps.length)
        {
            throw new IllegalStateException("Mismatch in the number of key and index properties");
        }

        Class[] coercionTypes = new Class[indexProps.length];
        boolean mustCoerce = false;
        for (int i = 0; i < keyProps.length; i++)
        {
            Class keyPropType = JavaClassHelper.getBoxedType(typesPerStream[lookupStream].getPropertyType(keyProps[i]));
            Class indexedPropType = JavaClassHelper.getBoxedType(typesPerStream[indexedStream].getPropertyType(indexProps[i]));
            Class coercionType = indexedPropType;
            if (keyPropType != indexedPropType)
            {
                coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, indexedPropType);
                mustCoerce = true;
            }
            coercionTypes[i] = coercionType;
        }
        if (!mustCoerce)
        {
            return null;
        }
        return coercionTypes;
    }
}
