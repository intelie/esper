///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Reflection;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents an array in a filter expressiun tree.
    /// </summary>
	
    public class ExprArrayNode : ExprNode
	{
	    private Type coercionType;
	    private bool mustCoerce;
	    private int length;
	    private Object constantResult;

	    /// <summary>Ctor.</summary>
	    public ExprArrayNode()
	    {
	    }

	    public void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	        length = this.ChildNodes.Size();

	        // Can be an empty array with no content
	        if (this.ChildNodes.Size() == 0)
	        {
	            coercionType = typeof(Object);
	            constantResult = new Object[0];
	            return;
	        }

	        List<Class> comparedTypes = new LinkedList<Class>();
	        for (int i = 0; i < length; i++)
	        {
	            comparedTypes.Add(this.ChildNodes.Get(i).Type);
	        }

	        // Determine common denominator type
	        try {
	            coercionType = TypeHelper.GetCommonCoercionType(comparedTypes.ToArray(new Class[0]));

	            // Determine if we need to coerce numbers when one type doesn't match any other type
	            if (TypeHelper.IsNumeric(coercionType))
	            {
	                mustCoerce = false;
	                foreach (Type comparedType in comparedTypes)
	                {
	                    if (comparedType != coercionType)
	                    {
	                        mustCoerce = true;
	                    }
	                }
	            }
	        }
	        catch (CoercionException ex)
	        {
	            // expected, such as mixing String and int values, or Java classes (not boxed) and primitives
	            // use Object[] in such cases
	        }
	        if (coercionType == null)
	        {
	            coercionType = typeof(Object);
	        }

	        // Determine if we are dealing with constants only
	        Object[] results = new Object[length];
	        int index = 0;
	        foreach (ExprNode child in this.ChildNodes)
	        {
	            if (!child.IsConstantResult())
	            {
	                results = null;  // not using a constant result
	                break;
	            }
	            results[index++] = child.Evaluate(null, false);
	        }

	        // Copy constants into array and coerce, if required
	        if (results != null)
	        {
	            constantResult = Array.NewInstance(coercionType, length);
	            for (int i = 0; i < length; i++)
	            {
	                if (mustCoerce)
	                {
	                    Object boxed = results[i];
	                    if (boxed != null)
	                    {
	                        Object coercedResult = TypeHelper.CoerceBoxed(boxed, coercionType);
	                        Array.Set(constantResult, i, coercedResult);
	                    }
	                }
	                else
	                {
	                    Array.Set(constantResult, i, results[i]);
	                }
	            }
	        }
	    }

	    public bool IsConstantResult()
	    {
	        return constantResult != null;
	    }

	    public Type GetType()
	    {
	        return Array.NewInstance(coercionType, 0).Class;
	    }

	    public Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
	    {
	        if (constantResult != null)
	        {
	            return constantResult;
	        }

	        Object array = Array.NewInstance(coercionType, length);

	        if (length == 0)
	        {
	            return array;
	        }

	        int index = 0;
	        foreach (ExprNode child in this.ChildNodes)
	        {
	            Object result = child.Evaluate(eventsPerStream, isNewData);
	            if (result != null)
	            {
	                if (mustCoerce)
	                {
	                    Number boxed = (Number) result;
	                    Object coercedResult = TypeHelper.CoerceBoxed(boxed, coercionType);
	                    Array.Set(array, index, coercedResult);
	                }
	                else
	                {
	                    Array.Set(array, index, result);
	                }
	            }
	            index++;
	        }

	        return array;
	    }

	    public String ToExpressionString()
	    {
	        StringBuilder buffer = new StringBuilder();
	        String delimiter = "";

	        buffer.Append("{");
	        foreach (ExprNode expr in this.ChildNodes)
	        {
	            buffer.Append(delimiter);
	            buffer.Append(expr.ToExpressionString());
	            delimiter = ",";
	        }

	        buffer.Append('}');
	        return buffer.ToString();
	    }

	    public bool EqualsNode(ExprNode node)
	    {
	        if (!(node is ExprArrayNode))
	        {
	            return false;
	        }
	        return true;
	    }
	}
} // End of namespace
