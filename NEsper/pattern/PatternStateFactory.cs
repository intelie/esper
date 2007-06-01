///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.pattern
{
	/// <summary>Factory for pattern state object implementations.</summary>
	public interface PatternStateFactory
	{
	    /// <summary>Sets the services for pattern state objects.</summary>
	    /// <param name="patternContext">is a pattern-level services</param>
	    void SetContext(PatternContext patternContext);

	    /// <summary>Makes a parent state node for the child state node.</summary>
	    /// <param name="evalNode">is the factory for the parent node</param>
	    /// <param name="matchEvents">is the current match state</param>
	    /// <param name="stateObjectId">is the parent state object id</param>
	    /// <returns>parent state object</returns>
	    EvalStateNode MakeParentStateNode(EvalNode evalNode, MatchedEventMap matchEvents, Object stateObjectId);

	    /// <summary>Makes a root state node.</summary>
	    /// <param name="evalChildNode">is the first child node of the root node</param>
	    /// <param name="beginState">is the state node's begin state</param>
	    /// <returns>root state node</returns>
	    EvalStateNode MakeRootNode(EvalNode evalChildNode, MatchedEventMap beginState);

	    /// <summary>Makes a followed-by state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalFollowedByNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateObjectId">is the state node's object id</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeFollowedByState(Evaluator parentNode, EvalFollowedByNode evalFollowedByNode, MatchedEventMap beginState, Object stateObjectId);

	    /// <summary>Makes a followed-by state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalFilterNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateNodeId">is the state node's object id</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeFilterStateNode(Evaluator parentNode, EvalFilterNode evalFilterNode, MatchedEventMap beginState, Object stateNodeId);

	    /// <summary>Makes any new state node.</summary>
	    /// <param name="evalNodeNumber">is the factory node number</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateObjectId">is the state node's object id</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeStateNode(EvalNodeNumber evalNodeNumber, MatchedEventMap beginState, Object stateObjectId);

	    /// <summary>Makes a followed-by state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalObserverNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateNodeId">is the state node's object id</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeObserverNode(Evaluator parentNode, EvalObserverNode evalObserverNode, MatchedEventMap beginState, Object stateNodeId);

	    /// <summary>Makes a followed-by state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalAndNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateNodeId">is the state node's object id</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeAndStateNode(Evaluator parentNode, EvalAndNode evalAndNode, MatchedEventMap beginState, Object stateNodeId);

	    /// <summary>Makes a followed-by state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalNotNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateNodeId">is the state node's object id</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeNotNode(Evaluator parentNode, EvalNotNode evalNotNode, MatchedEventMap beginState, Object stateNodeId);

	    /// <summary>Makes a every-state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalEveryNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateNodeId">is the state node's object id</param>
	    /// <param name="context">is the pattern context</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeEveryStateNode(Evaluator parentNode, EvalEveryNode evalEveryNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId);

	    /// <summary>Makes an or-state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalOrNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateNodeId">is the state node's object id</param>
	    /// <param name="context">is the pattern context</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeOrState(Evaluator parentNode, EvalOrNode evalOrNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId);

	    /// <summary>Makes a guard state node.</summary>
	    /// <param name="parentNode">is the parent evaluator</param>
	    /// <param name="evalGuardNode">is the factory node</param>
	    /// <param name="beginState">is the begin state</param>
	    /// <param name="stateNodeId">is the state node's object id</param>
	    /// <param name="context">is the pattern context</param>
	    /// <returns>state node</returns>
	    EvalStateNode MakeGuardState(Evaluator parentNode, EvalGuardNode evalGuardNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId);
	}
} // End of namespace
