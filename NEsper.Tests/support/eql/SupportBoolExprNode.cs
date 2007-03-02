using System;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
	public class SupportBoolExprNode:ExprNode
	{
		override public Type ReturnType
		{
			get
			{
				return typeof(bool);
			}
			
		}
		private bool evaluateResult;
		
		public SupportBoolExprNode(bool evaluateResult)
		{
			this.evaluateResult = evaluateResult;
		}

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
		{
		}

        public override Object Evaluate(EventBean[] eventsPerStream)
		{
			return evaluateResult;
		}
		
		public override String ExpressionString
		{
            get { return null; }
		}

        public override bool EqualsNode(ExprNode node)
		{
			throw new NotSupportedException("not implemented");
		}
	}
}
