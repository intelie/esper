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
 * Factory for pattern state object implementations.
 */
public interface PatternStateFactory
{
    /**
     * Makes a root state node.
     * @param evalChildNode is the first child node of the root node
     * @param beginState is the state node's begin state
     * @return root state node
     */
    public EvalStateNode makeRootNode(EvalNode evalChildNode, MatchedEventMap beginState, PatternContext context);

    /**
     * Makes a followed-by state node.
     * @param parentNode is the parent evaluator
     * @param evalFollowedByNode is the factory node
     * @param beginState is the begin state
     * @param stateObjectId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeFollowedByState(Evaluator parentNode, EvalFollowedByNode evalFollowedByNode, MatchedEventMap beginState, Object stateObjectId);

    /**
     * Makes a match-until state node.
     * @param parentNode is the parent evaluator
     * @param evalMatchUntilNode is the factory node
     * @param beginState is the begin state
     * @param stateObjectId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeMatchUntilState(Evaluator parentNode, EvalMatchUntilNode evalMatchUntilNode, MatchedEventMap beginState, Object stateObjectId);

    /**
     * Makes a followed-by state node.
     * @param parentNode is the parent evaluator
     * @param evalFilterNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeFilterStateNode(Evaluator parentNode, EvalFilterNode evalFilterNode, MatchedEventMap beginState, Object stateNodeId);

    /**
     * Makes a followed-by state node.
     * @param parentNode is the parent evaluator
     * @param evalObserverNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeObserverNode(Evaluator parentNode, EvalObserverNode evalObserverNode, MatchedEventMap beginState, Object stateNodeId);

    /**
     * Makes a followed-by state node.
     * @param parentNode is the parent evaluator
     * @param evalAndNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeAndStateNode(Evaluator parentNode, EvalAndNode evalAndNode, MatchedEventMap beginState, Object stateNodeId);

    /**
     * Makes a followed-by state node.
     * @param parentNode is the parent evaluator
     * @param evalNotNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeNotNode(Evaluator parentNode, EvalNotNode evalNotNode, MatchedEventMap beginState, Object stateNodeId);

    /**
     * Makes a every-state node.
     * @param parentNode is the parent evaluator
     * @param evalEveryNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeEveryStateNode(Evaluator parentNode, EvalEveryNode evalEveryNode, MatchedEventMap beginState, Object stateNodeId);

    /**
     * Makes a every-distinct state node.
     * @param parentNode is the parent evaluator
     * @param evalEveryNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeEveryDistinctStateNode(Evaluator parentNode, EvalEveryDistinctNode evalEveryNode, MatchedEventMap beginState, Object stateNodeId);

    /**
     * Makes an or-state node.
     * @param parentNode is the parent evaluator
     * @param evalOrNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeOrState(Evaluator parentNode, EvalOrNode evalOrNode, MatchedEventMap beginState, Object stateNodeId);

    /**
     * Makes a guard state node.
     * @param parentNode is the parent evaluator
     * @param evalGuardNode is the factory node
     * @param beginState is the begin state
     * @param stateNodeId is the state node's object id
     * @return state node
     */
    public EvalStateNode makeGuardState(Evaluator parentNode, EvalGuardNode evalGuardNode, MatchedEventMap beginState, Object stateNodeId);
}
