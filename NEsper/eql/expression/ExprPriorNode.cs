///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.core;
using net.esper.events;
using net.esper.view;
using net.esper.view.window;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Represents the 'prior' prior event function in an expression node tree.
	/// </summary>
	public class ExprPriorNode : ExprNode, ViewResourceCallback
	{
	    private Class resultType;
	    private int streamNumber;
	    private int constantIndexNumber;
	    private RelativeAccessByEventNIndex relativeAccess;
	    private RandomAccessByIndex randomAccess;

	    public void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	        if (this.ChildNodes.Size() != 2)
	        {
	            throw new ExprValidationException("Prior node must have 2 child nodes");
	        }
	        if (!(this.ChildNodes.Get(0).IsConstantResult()))
	        {
	            throw new ExprValidationException("Prior function requires an integer index parameter");
	        }
	        ExprNode constantNode = this.ChildNodes.Get(0);
	        if (constantNode.Type != typeof(int))
	        {
	            throw new ExprValidationException("Prior function requires an integer index parameter");
	        }

	        Object value = constantNode.Evaluate(null, false);
	        constantIndexNumber = ((Number) value).IntValue();

	        // Determine stream number
	        ExprIdentNode identNode = (ExprIdentNode) this.ChildNodes.Get(1);
	        streamNumber = identNode.StreamId;
	        resultType = this.ChildNodes.Get(1).Type;

	        if (viewResourceDelegate == null)
	        {
	            throw new ExprValidationException("Prior function cannot be used in this context");
	        }
	        // Request a callback that provides the required access
	        if (!viewResourceDelegate.RequestCapability(streamNumber, new ViewCapPriorEventAccess(constantIndexNumber), this))
	        {
	            throw new ExprValidationException("Prior function requires the prior event view resource");
	        }
	    }

	    public Class GetType()
	    {
	        return resultType;
	    }

	    public bool IsConstantResult()
	    {
	        return false;
	    }

	    public Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
	    {
	        EventBean originalEvent = eventsPerStream[streamNumber];
	        EventBean substituteEvent = null;

	        if (randomAccess != null)
	        {
	            if (isNewData)
	            {
	                substituteEvent = randomAccess.GetNewData(constantIndexNumber);
	            }
	            else
	            {
	                substituteEvent = randomAccess.GetOldData(constantIndexNumber);
	            }
	        }
	        else
	        {
	            substituteEvent = relativeAccess.GetRelativeToEvent(originalEvent, constantIndexNumber);
	        }

	        // Substitute original event with prior event, evaluate inner expression
	        eventsPerStream[streamNumber] = substituteEvent;
	        Object evalResult = this.ChildNodes.Get(1).Evaluate(eventsPerStream, isNewData);
	        eventsPerStream[streamNumber] = originalEvent;

	        return evalResult;
	    }

	    public String ToExpressionString()
	    {
	        StringBuilder buffer = new StringBuilder();
	        buffer.Append("prior(");
	        buffer.Append(this.ChildNodes.Get(0).ToExpressionString());
	        buffer.Append(',');
	        buffer.Append(this.ChildNodes.Get(1).ToExpressionString());
	        buffer.Append(')');
	        return buffer.ToString();
	    }

	    public bool EqualsNode(ExprNode node)
	    {
	        if (!(node is ExprPriorNode))
	        {
	            return false;
	        }

	        return true;
	    }

	    public void SetViewResource(Object resource)
	    {
	        if (resource is RelativeAccessByEventNIndex)
	        {
	            relativeAccess = (RelativeAccessByEventNIndex) resource;
	        }
	        else if (resource is RandomAccessByIndex)
	        {
	            randomAccess = (RandomAccessByIndex) resource;
	        }
	        else
	        {
	            throw new IllegalArgumentException("View resource " + resource.Class + " not recognized by expression node");
	        }
	    }
	}
} // End of namespace
