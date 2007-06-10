///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;
using net.esper.view;
using net.esper.view.window;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Represents the 'prev' previous event function in an expression node tree.
	/// </summary>
	public class ExprPreviousNode : ExprNode, ViewResourceCallback
	{
	    private Type resultType;
	    private int streamNumber;
	    private int? constantIndexNumber;
	    private bool isConstantIndex;

	    private RandomAccessByIndexGetter randomAccessGetter;
	    private RelativeAccessByEventNIndexGetter relativeAccessGetter;

	    public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	        if (this.ChildNodes.Count != 2)
	        {
	            throw new ExprValidationException("Previous node must have 2 child nodes");
	        }

	        // Determine if the index is a constant value or an expression to evaluate
	        if (this.ChildNodes[0].IsConstantResult)
	        {
	            ExprNode constantNode = (ExprNode) this.ChildNodes[0];
	            Object value = constantNode.Evaluate(null, false);
	            if (!(value is Number))
	            {
	                throw new ExprValidationException("Previous function requires an integer index parameter or expression");
	            }

	            Number valueNumber = (Number) value;
	            if (TypeHelper.IsFloatingPointNumber(valueNumber))
	            {
	                throw new ExprValidationException("Previous function requires an integer index parameter or expression");
	            }

	            constantIndexNumber = valueNumber.IntValue();
	            isConstantIndex = true;
	        }

	        // Determine stream number
	        ExprIdentNode identNode = (ExprIdentNode) this.ChildNodes[1];
	        streamNumber = identNode.StreamId;
            resultType = this.ChildNodes[1].GetType();

	        if (viewResourceDelegate == null)
	        {
	            throw new ExprValidationException("Previous function cannot be used in this context");
	        }

	        // Request a callback that provides the required access
	        if (!viewResourceDelegate.RequestCapability(streamNumber, new ViewCapDataWindowAccess(constantIndexNumber), this))
	        {
	            throw new ExprValidationException("Previous function requires a single data window view onto the stream");
	        }
	    }

	    public override Type ReturnType
	    {
	    	get { return resultType; }
	    }

	    public override bool IsConstantResult
	    {
	    	get { return false; }
	    }

	    public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
	    {
	        int index;

	        // Use constant if supplied
	        if (isConstantIndex)
	        {
	            index = constantIndexNumber;
	        }
	        else
	        {
	            // evaluate first child, which returns the index
	            Object indexResult = this.ChildNodes[0].Evaluate(eventsPerStream, isNewData);
	            if (indexResult == null)
	            {
	                return null;
	            }
	            index = ((Number) indexResult).IntValue();
	        }

	        // access based on index returned
	        EventBean substituteEvent = null;
	        if (randomAccessGetter != null)
	        {
	            RandomAccessByIndex randomAccess = randomAccessGetter.Accessor;
	            if (isNewData)
	            {
	                substituteEvent = randomAccess.GetNewData(index);
	            }
	        }
	        else
	        {
	            if (isNewData)
	            {
	                EventBean evalEvent = eventsPerStream[streamNumber];
	                RelativeAccessByEventNIndex relativeAccess = relativeAccessGetter.GetAccessor(evalEvent);
	                substituteEvent = relativeAccess.GetRelativeToEvent(evalEvent, index);
	            }
	        }
	        if (substituteEvent == null)
	        {
	            return null;
	        }

	        // Substitute original event with prior event, evaluate inner expression
	        EventBean originalEvent = eventsPerStream[streamNumber];
	        eventsPerStream[streamNumber] = substituteEvent;
	        Object evalResult = this.ChildNodes[1].Evaluate(eventsPerStream, isNewData);
	        eventsPerStream[streamNumber] = originalEvent;

	        return evalResult;
	    }

	    public override string ExpressionString
	    {
	    	get
	    	{
		        StringBuilder buffer = new StringBuilder();
		        buffer.Append("prev(");
		        buffer.Append(this.ChildNodes[0].ExpressionString);
		        buffer.Append(',');
		        buffer.Append(this.ChildNodes[1].ExpressionString);
		        buffer.Append(')');
		        return buffer.ToString();
	    	}
	    }

	    public override bool EqualsNode(ExprNode node)
	    {
	        if (!(node is ExprPreviousNode))
	        {
	            return false;
	        }

	        return true;
	    }

	    public void SetViewResource(Object resource)
	    {
	        if (resource is RandomAccessByIndexGetter)
	        {
	            randomAccessGetter = (RandomAccessByIndexGetter) resource;
	        }
	        else if (resource is RelativeAccessByEventNIndexGetter)
	        {
	            relativeAccessGetter = (RelativeAccessByEventNIndexGetter) resource;
	        }
	        else
	        {
	            throw new ArgumentException("View resource " + resource.Class + " not recognized by expression node");
	        }
	    }
	}
} // End of namespace
