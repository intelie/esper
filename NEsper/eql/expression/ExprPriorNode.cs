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
using net.esper.view;
using net.esper.view.window;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Represents the 'prior' prior event function in an expression node tree.
	/// </summary>
	public class ExprPriorNode : ExprNode, ViewResourceCallback
	{
	    private Type resultType;
	    private int streamNumber;
	    private int constantIndexNumber;
	    private RelativeAccessByEventNIndex relativeAccess;
	    private RandomAccessByIndex randomAccess;

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">for resolving class names in library method invocations</param>
        /// <param name="viewResourceDelegate"></param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
	    public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	        if (this.ChildNodes.Count != 2)
	        {
	            throw new ExprValidationException("Prior node must have 2 child nodes");
	        }
	        if (!this.ChildNodes[0].IsConstantResult)
	        {
	            throw new ExprValidationException("Prior function requires an integer index parameter");
	        }
	        ExprNode constantNode = this.ChildNodes[0];
            if (constantNode.GetType() != typeof(int))
	        {
	            throw new ExprValidationException("Prior function requires an integer index parameter");
	        }

	        Object value = constantNode.Evaluate(null, false);
	        constantIndexNumber = Convert.ToInt32(value);

	        // Determine stream number
	        ExprIdentNode identNode = (ExprIdentNode) this.ChildNodes[1];
	        streamNumber = identNode.StreamId;
            resultType = this.ChildNodes[1].GetType();

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

        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
	    public override Type ReturnType
	    {
	    	get { return resultType; }
	    }

        /// <summary>
        /// Returns true if the expression node's evaluation value doesn't depend on any events data,
        /// as must be determined at validation time, which is bottom-up and therefore
        /// reliably allows each node to determine constant value.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// true for constant evaluation value, false for non-constant evaluation value
        /// </returns>
	    public override bool IsConstantResult
	    {
	    	get { return false; }
	    }

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <param name="isNewData"></param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
	    public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
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
	        Object evalResult = this.ChildNodes[1].Evaluate(eventsPerStream, isNewData);
	        eventsPerStream[streamNumber] = originalEvent;

	        return evalResult;
	    }

        /// <summary>
        /// Returns the expression node rendered as a string.
        /// </summary>
        /// <value></value>
        /// <returns> string rendering of expression
        /// </returns>
	    public override string ExpressionString
	    {
	    	get
	    	{
		        StringBuilder buffer = new StringBuilder();
		        buffer.Append("prior(");
		        buffer.Append(this.ChildNodes[0].ExpressionString);
		        buffer.Append(',');
		        buffer.Append(this.ChildNodes[1].ExpressionString);
		        buffer.Append(')');
		        return buffer.ToString();
	    	}
	    }

        /// <summary>
        /// Return true if a expression node semantically equals the current node, or false if not.
        /// Concrete implementations should compare the type and any additional information
        /// that impact the evaluation of a node.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
	    public override bool EqualsNode(ExprNode node)
	    {
	        if (!(node is ExprPriorNode))
	        {
	            return false;
	        }

	        return true;
	    }

        /// <summary>
        /// Supplies view resource.
        /// </summary>
        /// <value></value>
	    public Object ViewResource
	    {
            set
            {
                if (value is RelativeAccessByEventNIndex)
                {
                    relativeAccess = (RelativeAccessByEventNIndex)value;
                }
                else if (value is RandomAccessByIndex)
                {
                    randomAccess = (RandomAccessByIndex)value;
                }
                else
                {
                    throw new ArgumentException("View resource " + value.GetType() + " not recognized by expression node");
                }
            }
	    }
	}
} // End of namespace
