///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.compat;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
	public class SupportExprNode : ExprNode
	{
		private static int validateCount;

		private Type type;
		private Object value;
		private int validateCountSnapshot;

		public static void SetValidateCount(int validateCount)
		{
			SupportExprNode.validateCount = validateCount;
		}

		public SupportExprNode(Type type)
		{
			this.type = type;
			this.value = null;
		}

		public SupportExprNode(Object value)
		{
			this.type = value.GetType();
			this.value = value;
		}

		public SupportExprNode(Object value, Type type)
		{
			this.value = value;
			this.type = type;
		}

		public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
		{
			// Keep a count for if and when this was validated
			validateCount++;
			validateCountSnapshot = validateCount;
		}

		public override bool IsConstantResult
		{
			get { return false; }
		}

		public override Type ReturnType
		{
			get { return type; }
		}

		public int ValidateCountSnapshot
		{
			get { return validateCountSnapshot; }
		}

		public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
		{
			return value;
		}

		public void SetValue(Object value)
		{
			this.value = value;
		}

		public override String ExpressionString
		{
			get
			{
				if (value is String)
				{
					return "\"" + value + "\"";
				}
				return value.ToString();
			}
		}

		public override bool EqualsNode(ExprNode node)
		{
			throw new UnsupportedOperationException("not implemented");
		}
	}
} // End of namespace
