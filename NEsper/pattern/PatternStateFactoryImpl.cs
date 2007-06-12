///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.compat;

namespace net.esper.pattern
{
	/// <summary>Default pattern state factory.</summary>
	public class PatternStateFactoryImpl : PatternStateFactory
	{
	    private PatternContext context;

        /// <summary>
        /// Sets the context.
        /// </summary>
        /// <param name="context">The context.</param>
	    public void SetContext(PatternContext context)
	    {
	        this.context = context;
	    }

        /// <summary>
        /// Makes a guard state node.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator</param>
        /// <param name="evalGuardNode">is the factory node</param>
        /// <param name="beginState">is the begin state</param>
        /// <param name="context">is the pattern context</param>
        /// <param name="stateNodeId">is the state node's object id</param>
        /// <returns>state node</returns>
	    public EvalStateNode MakeGuardState(Evaluator parentNode, EvalGuardNode evalGuardNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId)
	    {
	        return new EvalGuardStateNode(parentNode, evalGuardNode, beginState, context, stateNodeId);
	    }

        /// <summary>
        /// Makes an or-state node.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator</param>
        /// <param name="evalOrNode">is the factory node</param>
        /// <param name="beginState">is the begin state</param>
        /// <param name="context">is the pattern context</param>
        /// <param name="stateNodeId">is the state node's object id</param>
        /// <returns>state node</returns>
	    public EvalStateNode MakeOrState(Evaluator parentNode, EvalOrNode evalOrNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId)
	    {
	        return new EvalOrStateNode(parentNode, evalOrNode, beginState, context);
	    }

        /// <summary>
        /// Makes a every-state node.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator</param>
        /// <param name="evalEveryNode">is the factory node</param>
        /// <param name="beginState">is the begin state</param>
        /// <param name="context">is the pattern context</param>
        /// <param name="stateNodeId">is the state node's object id</param>
        /// <returns>state node</returns>
	    public EvalStateNode MakeEveryStateNode(Evaluator parentNode, EvalEveryNode evalEveryNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId)
	    {
	        return new EvalEveryStateNode(parentNode, evalEveryNode, beginState, context);
	    }

        /// <summary>
        /// Makes a followed-by state node.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator</param>
        /// <param name="evalNotNode">is the factory node</param>
        /// <param name="beginState">is the begin state</param>
        /// <param name="stateNodeId">is the state node's object id</param>
        /// <returns>state node</returns>
	    public EvalStateNode MakeNotNode(Evaluator parentNode, EvalNotNode evalNotNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalNotStateNode(parentNode, evalNotNode, beginState, context);
	    }

        /// <summary>
        /// Makes a followed-by state node.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator</param>
        /// <param name="evalAndNode">is the factory node</param>
        /// <param name="beginState">is the begin state</param>
        /// <param name="stateNodeId">is the state node's object id</param>
        /// <returns>state node</returns>
	    public EvalStateNode MakeAndStateNode(Evaluator parentNode, EvalAndNode evalAndNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalAndStateNode(parentNode, evalAndNode, beginState, context);
	    }

        /// <summary>
        /// Makes a root state node.
        /// </summary>
        /// <param name="evalChildNode">is the first child node of the root node</param>
        /// <param name="beginState">is the state node's begin state</param>
        /// <returns>root state node</returns>
	    public EvalStateNode MakeRootNode(EvalNode evalChildNode, MatchedEventMap beginState)
	    {
	        return new EvalRootStateNode(evalChildNode, beginState, context);
	    }

        /// <summary>
        /// Makes a followed-by state node.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator</param>
        /// <param name="evalObserverNode">is the factory node</param>
        /// <param name="beginState">is the begin state</param>
        /// <param name="stateNodeId">is the state node's object id</param>
        /// <returns>state node</returns>
	    public EvalStateNode MakeObserverNode(Evaluator parentNode, EvalObserverNode evalObserverNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalObserverStateNode(parentNode, evalObserverNode, beginState, context);
	    }

        /// <summary>
        /// Makes the state of the followed by.
        /// </summary>
        /// <param name="parentNode">The parent node.</param>
        /// <param name="evalFollowedByNode">The eval followed by node.</param>
        /// <param name="beginState">State of the begin.</param>
        /// <param name="stateNodeId">The state node id.</param>
        /// <returns></returns>
	    public EvalStateNode MakeFollowedByState(Evaluator parentNode, EvalFollowedByNode evalFollowedByNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalFollowedByStateNode(parentNode, evalFollowedByNode, beginState, context);
	    }

        /// <summary>
        /// Makes a followed-by state node.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator</param>
        /// <param name="evalFilterNode">is the factory node</param>
        /// <param name="beginState">is the begin state</param>
        /// <param name="stateNodeId">is the state node's object id</param>
        /// <returns>state node</returns>
	    public EvalStateNode MakeFilterStateNode(Evaluator parentNode, EvalFilterNode evalFilterNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalFilterStateNode(parentNode, evalFilterNode, beginState, context);
	    }

        /// <summary>
        /// Makes the state node.
        /// </summary>
        /// <param name="evalNodeNumber">The eval node number.</param>
        /// <param name="matchEvents">The match events.</param>
        /// <param name="stateObjectId">The state object id.</param>
        /// <returns></returns>
	    public EvalStateNode MakeStateNode(EvalNodeNumber evalNodeNumber, MatchedEventMap matchEvents, Object stateObjectId)
	    {
	        throw new UnsupportedOperationException("State node factory not supported");
	    }

        /// <summary>
        /// Makes a parent state node for the child state node.
        /// </summary>
        /// <param name="evalNode">is the factory for the parent node</param>
        /// <param name="matchEvents">is the current match state</param>
        /// <param name="stateObjectId">is the parent state object id</param>
        /// <returns>parent state object</returns>
	    public EvalStateNode MakeParentStateNode(EvalNode evalNode, MatchedEventMap matchEvents, Object stateObjectId)
	    {
	        throw new UnsupportedOperationException("State node factory not supported");
	    }
	}
} // End of namespace
