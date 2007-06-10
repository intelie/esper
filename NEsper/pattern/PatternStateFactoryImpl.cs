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

	    public void SetContext(PatternContext context)
	    {
	        this.context = context;
	    }

	    public EvalStateNode MakeGuardState(Evaluator parentNode, EvalGuardNode evalGuardNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId)
	    {
	        return new EvalGuardStateNode(parentNode, evalGuardNode, beginState, context, stateNodeId);
	    }

	    public EvalStateNode MakeOrState(Evaluator parentNode, EvalOrNode evalOrNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId)
	    {
	        return new EvalOrStateNode(parentNode, evalOrNode, beginState, context);
	    }

	    public EvalStateNode MakeEveryStateNode(Evaluator parentNode, EvalEveryNode evalEveryNode, MatchedEventMap beginState, PatternContext context, Object stateNodeId)
	    {
	        return new EvalEveryStateNode(parentNode, evalEveryNode, beginState, context);
	    }

	    public EvalStateNode MakeNotNode(Evaluator parentNode, EvalNotNode evalNotNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalNotStateNode(parentNode, evalNotNode, beginState, context);
	    }

	    public EvalStateNode MakeAndStateNode(Evaluator parentNode, EvalAndNode evalAndNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalAndStateNode(parentNode, evalAndNode, beginState, context);
	    }

	    public EvalStateNode MakeRootNode(EvalNode evalChildNode, MatchedEventMap beginState)
	    {
	        return new EvalRootStateNode(evalChildNode, beginState, context);
	    }

	    public EvalStateNode MakeObserverNode(Evaluator parentNode, EvalObserverNode evalObserverNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalObserverStateNode(parentNode, evalObserverNode, beginState, context);
	    }

	    public EvalStateNode MakeFollowedByState(Evaluator parentNode, EvalFollowedByNode evalFollowedByNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalFollowedByStateNode(parentNode, evalFollowedByNode, beginState, context);
	    }

	    public EvalStateNode MakeFilterStateNode(Evaluator parentNode, EvalFilterNode evalFilterNode, MatchedEventMap beginState, Object stateNodeId)
	    {
	        return new EvalFilterStateNode(parentNode, evalFilterNode, beginState, context);
	    }

	    public EvalStateNode MakeStateNode(EvalNodeNumber evalNodeNumber, MatchedEventMap matchEvents, Object stateObjectId)
	    {
	        throw new UnsupportedOperationException("State node factory not supported");
	    }

	    public EvalStateNode MakeParentStateNode(EvalNode evalNode, MatchedEventMap matchEvents, Object stateObjectId)
	    {
	        throw new UnsupportedOperationException("State node factory not supported");
	    }
	}
} // End of namespace
