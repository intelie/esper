/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

/**
 * Default pattern state factory.
 */
public class PatternStateFactoryImpl implements PatternStateFactory
{
    public EvalStateNode makeGuardState(Evaluator parentNode, EvalGuardNode evalGuardNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalGuardStateNode(parentNode, evalGuardNode, beginState, stateNodeId);
    }

    public EvalStateNode makeOrState(Evaluator parentNode, EvalOrNode evalOrNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalOrStateNode(parentNode, evalOrNode, beginState);
    }

    public EvalStateNode makeEveryStateNode(Evaluator parentNode, EvalEveryNode evalEveryNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalEveryStateNode(parentNode, evalEveryNode, beginState);
    }

    public EvalStateNode makeEveryDistinctStateNode(Evaluator parentNode, EvalEveryDistinctNode evalEveryNode, MatchedEventMap beginState, Object stateNodeId)
    {
        if (evalEveryNode.getMsecToExpire() == null) {
            return new EvalEveryDistinctStateNode(parentNode, evalEveryNode, beginState);
        }
        else {
            return new EvalEveryDistinctStateExpireKeyNode(parentNode, evalEveryNode, beginState);
        }
    }

    public EvalStateNode makeNotNode(Evaluator parentNode, EvalNotNode evalNotNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalNotStateNode(parentNode, evalNotNode, beginState);
    }

    public EvalStateNode makeAndStateNode(Evaluator parentNode, EvalAndNode evalAndNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalAndStateNode(parentNode, evalAndNode, beginState);
    }

    public EvalStateNode makeRootNode(EvalNode evalChildNode, MatchedEventMap beginState, PatternContext context)
    {
        return new EvalRootStateNode(evalChildNode, beginState, context);
    }

    public EvalStateNode makeObserverNode(Evaluator parentNode, EvalObserverNode evalObserverNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalObserverStateNode(parentNode, evalObserverNode, beginState);
    }

    public EvalStateNode makeFollowedByState(Evaluator parentNode, EvalFollowedByNode evalFollowedByNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalFollowedByStateNode(parentNode, evalFollowedByNode, beginState);
    }

    public EvalStateNode makeMatchUntilState(Evaluator parentNode, EvalMatchUntilNode evalMatchUntilNode, MatchedEventMap beginState, Object stateObjectId)       
    {
        return new EvalMatchUntilStateNode(parentNode, evalMatchUntilNode, beginState);
    }

    public EvalStateNode makeFilterStateNode(Evaluator parentNode, EvalFilterNode evalFilterNode, MatchedEventMap beginState, Object stateNodeId)
    {
        return new EvalFilterStateNode(parentNode, evalFilterNode, beginState);
    }
}
