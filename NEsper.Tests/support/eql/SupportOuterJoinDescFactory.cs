using System;

using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.type;

namespace net.esper.support.eql
{
	
	public class SupportOuterJoinDescFactory
	{
		public static OuterJoinDesc makeDesc(String propOne, String streamOne, String propTwo, String streamTwo, OuterJoinType type)
		{
			ExprIdentNode identNodeOne = new ExprIdentNode(propOne, streamOne);
			ExprIdentNode identNodeTwo = new ExprIdentNode(propTwo, streamTwo);
			
			identNodeOne.Validate(new SupportStreamTypeSvc3Stream(), null);
			identNodeTwo.Validate(new SupportStreamTypeSvc3Stream(), null);
			OuterJoinDesc desc = new OuterJoinDesc(type, identNodeOne, identNodeTwo);
			
			return desc;
		}
	}
}
