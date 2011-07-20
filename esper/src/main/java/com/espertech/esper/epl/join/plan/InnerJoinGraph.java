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

package com.espertech.esper.epl.join.plan;

import com.espertech.esper.collection.InterchangeablePair;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.type.OuterJoinType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InnerJoinGraph {

    private final int numStreams;
    private final boolean isAllInnerJoin;
    private final Set<InterchangeablePair<Integer, Integer>> innerJoins;

    public InnerJoinGraph(int numStreams, Set<InterchangeablePair<Integer, Integer>> innerJoins) {
        this.numStreams = numStreams;
        this.isAllInnerJoin = false;
        this.innerJoins = innerJoins;
    }

    public InnerJoinGraph(int numStreams, boolean isAllInnerJoin) {
        this.numStreams = numStreams;
        this.isAllInnerJoin = isAllInnerJoin;
        this.innerJoins = null;
    }

    public boolean isAllInnerJoin() {
        return isAllInnerJoin;
    }

    public boolean isEmpty() {
        if (isAllInnerJoin) {
            return false;
        }
        return innerJoins.isEmpty();
    }

    public boolean hasInnerJoin(int toStream) {
        if (isAllInnerJoin) {
            return true;
        }
        boolean hasInnerJoin = false;
        for (InterchangeablePair<Integer, Integer> pair : innerJoins)
        {
            if (pair.getFirst() == toStream)
            {
                hasInnerJoin = true;
            }
            if (pair.getSecond() == toStream)
            {
                hasInnerJoin = true;
            }
        }
        return hasInnerJoin;
    }

    public static InnerJoinGraph graphInnerJoins(int numStreams, List<OuterJoinDesc> outerJoinDescList)
    {
        if ((outerJoinDescList.size() + 1) != numStreams)
        {
            throw new IllegalArgumentException("Number of outer join descriptors and number of streams not matching up");
        }

        Set<InterchangeablePair<Integer, Integer>> graph = new HashSet<InterchangeablePair<Integer, Integer>>();

        boolean allInnerJoin = true;
        for (int i = 0; i < outerJoinDescList.size(); i++)
        {
            OuterJoinDesc desc = outerJoinDescList.get(i);
            int streamMax = i + 1;       // the outer join must references streams less then streamMax

            // Check outer join
            int streamOne = desc.getLeftNode().getStreamId();
            int streamTwo = desc.getRightNode().getStreamId();

            if ((streamOne > streamMax) || (streamTwo > streamMax) ||
                (streamOne == streamTwo))
            {
                throw new IllegalArgumentException("Outer join descriptors reference future streams, or same streams");
            }

            if (desc.getOuterJoinType() == OuterJoinType.INNER)
            {
                graph.add(new InterchangeablePair<Integer, Integer>(streamOne, streamTwo));
            }
            else {
                allInnerJoin = false;
            }
        }

        if (allInnerJoin) {
            return new InnerJoinGraph(numStreams, true);
        }
        return new InnerJoinGraph(numStreams, graph);
    }

    public void addRequiredStreams(int streamNum, Set<Integer> requiredStreams, Set<Integer> completedStreams) {
        if (isAllInnerJoin) {
            for (int i = 0; i < numStreams; i++) {
                if (!completedStreams.contains(i)) {
                    requiredStreams.add(i);
                }
            }
            return;
        }

        for (InterchangeablePair<Integer, Integer> pair : innerJoins)
        {
            if (pair.getFirst() == streamNum)
            {
                if (!completedStreams.contains(pair.getSecond()))
                {
                    requiredStreams.add(pair.getSecond());
                }
            }
            if (pair.getSecond() == streamNum)
            {
                if (!completedStreams.contains(pair.getFirst()))
                {
                    requiredStreams.add(pair.getFirst());
                }
            }
        }
    }
}
