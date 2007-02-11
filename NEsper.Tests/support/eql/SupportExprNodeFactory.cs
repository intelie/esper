using System;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.type;

namespace net.esper.support.eql
{
	
	public class SupportExprNodeFactory
	{
		public static ExprEqualsNode makeEqualsNode()
		{
			ExprEqualsNode topNode = new ExprEqualsNode(false);
			ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
			ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
			topNode.AddChildNode(i1_1);
			topNode.AddChildNode(i1_2);
			
			validate(topNode);
			
			return topNode;
		}
		
		public static ExprAndNode make2SubNodeAnd()
		{
			ExprAndNode topNode = new ExprAndNode();
			
			ExprEqualsNode e1 = new ExprEqualsNode(false);
			ExprEqualsNode e2 = new ExprEqualsNode(false);
			
			topNode.AddChildNode(e1);
			topNode.AddChildNode(e2);
			
			ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
			ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
			e1.AddChildNode(i1_1);
			e1.AddChildNode(i1_2);
			
			ExprIdentNode i2_1 = new ExprIdentNode("StringValue", "s1");
			ExprIdentNode i2_2 = new ExprIdentNode("StringValue", "s0");
			e2.AddChildNode(i2_1);
			e2.AddChildNode(i2_2);
			
			validate(topNode);
			
			return topNode;
		}
		
		public static ExprNode make3SubNodeAnd()
		{
			ExprNode topNode = new ExprAndNode();
			
			ExprEqualsNode[] equalNodes = new ExprEqualsNode[3];
			for (int i = 0; i < equalNodes.Length; i++)
			{
				equalNodes[i] = new ExprEqualsNode(false);
				topNode.AddChildNode(equalNodes[i]);
			}
			
			ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
			ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
			equalNodes[0].AddChildNode(i1_1);
			equalNodes[0].AddChildNode(i1_2);
			
			ExprIdentNode i2_1 = new ExprIdentNode("StringValue", "s1");
			ExprIdentNode i2_2 = new ExprIdentNode("StringValue", "s0");
			equalNodes[1].AddChildNode(i2_1);
			equalNodes[1].AddChildNode(i2_2);
			
			ExprIdentNode i3_1 = new ExprIdentNode("boolBoxed", "s0");
			ExprIdentNode i3_2 = new ExprIdentNode("boolPrimitive", "s1");
			equalNodes[2].AddChildNode(i3_1);
			equalNodes[2].AddChildNode(i3_2);
			
			validate(topNode);
			
			return topNode;
		}
		
		public static ExprNode makeIdentNode(String fieldName, String streamName)
		{
			ExprIdentNode node = new ExprIdentNode(fieldName, streamName);
			validate(node);
			return node;
		}
		
		public static ExprNode makeMathNode()
		{
			ExprIdentNode node1 = new ExprIdentNode("intBoxed", "s0");
			ExprIdentNode node2 = new ExprIdentNode("intPrimitive", "s0");
			ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
			mathNode.AddChildNode(node1);
			mathNode.AddChildNode(node2);
			
			validate(mathNode);
			
			return mathNode;
		}
		
		public static ExprNode makeMathNode(MathArithTypeEnum operator_, Object valueLeft_, Object valueRight_)
		{
			ExprMathNode mathNode = new ExprMathNode(operator_);
			mathNode.AddChildNode(new SupportExprNode(valueLeft_));
			mathNode.AddChildNode(new SupportExprNode(valueRight_));
			validate(mathNode);
			return mathNode;
		}
		
		public static ExprNode makeSumAndFactorNode()
		{
			// sum node
			ExprSumNode sum = new ExprSumNode(false);
			ExprIdentNode ident = new ExprIdentNode("intPrimitive", "s0");
			sum.AddChildNode(ident);
			
			ExprIdentNode node = new ExprIdentNode("intBoxed", "s0");
			ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
			mathNode.AddChildNode(node);
			mathNode.AddChildNode(sum);
			
			validate(mathNode);
			
			return mathNode;
		}
		
		public static ExprAggregateNode makeSumAggregateNode()
		{
			ExprSumNode top = new ExprSumNode(false);
			ExprIdentNode ident = new ExprIdentNode("intPrimitive", "s0");
			top.AddChildNode(ident);
			
			validate(top);
			
			return top;
		}
		
		public static ExprNode makeCountNode(Object value, Type type)
		{
			ExprCountNode countNode = new ExprCountNode(false);
			countNode.AddChildNode(new SupportExprNode(value, type));
			SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[]{10, 20});
			countNode.setAggregationResultFuture(future, 1);
			validate(countNode);
			return countNode;
		}
		
		public static ExprNode makeRelationalOpNode(RelationalOpEnum operator_, Object valueLeft_, Type typeLeft_, Object valueRight_, Type typeRight_)
		{
			ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
			opNode.AddChildNode(new SupportExprNode(valueLeft_, typeLeft_));
			opNode.AddChildNode(new SupportExprNode(valueRight_, typeRight_));
			validate(opNode);
			return opNode;
		}
		
		public static ExprNode makeRelationalOpNode(RelationalOpEnum operator_, Type typeLeft_, Type typeRight_)
		{
			ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
			opNode.AddChildNode(new SupportExprNode(typeLeft_));
			opNode.AddChildNode(new SupportExprNode(typeRight_));
			validate(opNode);
			return opNode;
		}
		
		public static ExprNode makeRelationalOpNode(RelationalOpEnum operator_, ExprNode nodeLeft_, ExprNode nodeRight_)
		{
			ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
			opNode.AddChildNode(nodeLeft_);
			opNode.AddChildNode(nodeRight_);
			validate(opNode);
			return opNode;
		}
		
		public static ExprInNode makeInSetNode(bool isNotIn)
		{
			// Build :      s0.intPrimitive in (1, 2)
			ExprInNode inNode = new ExprInNode(isNotIn);
			inNode.AddChildNode(makeIdentNode("intPrimitive", "s0"));
			inNode.AddChildNode(new SupportExprNode(1));
			inNode.AddChildNode(new SupportExprNode(2));
			validate(inNode);
			return inNode;
		}
		
		public static ExprRegexpNode makeRegexpNode(bool isNot)
		{
			// Build :      s0.string regexp "[a-z][a-z]"  (with not)
			ExprRegexpNode node = new ExprRegexpNode(isNot);
			node.AddChildNode(makeIdentNode("StringValue", "s0"));
			node.AddChildNode(new SupportExprNode("[a-z][a-z]"));
			validate(node);
			return node;
		}
		
		public static ExprLikeNode makeLikeNode(bool isNot, String optionalEscape)
		{
			// Build :      s0.string like "%abc__"  (with or witout escape)
			ExprLikeNode node = new ExprLikeNode(isNot);
			node.AddChildNode(makeIdentNode("StringValue", "s0"));
			node.AddChildNode(new SupportExprNode("%abc__"));
			if (optionalEscape != null)
			{
				node.AddChildNode(new SupportExprNode(optionalEscape));
			}
			validate(node);
			return node;
		}
		
		public static ExprCaseNode makeCaseSyntax1Node()
		{
			// Build (case 1 expression):
			// case when s0.intPrimitive = 1 then "a"
			//      when s0.intPrimitive = 2 then "b"
			//      else "c"
			// end
			ExprCaseNode caseNode = new ExprCaseNode(false);
			
			ExprNode node = makeEqualsNode("intPrimitive", "s0", 1);
			caseNode.AddChildNode(node);
			caseNode.AddChildNode(new SupportExprNode("a"));
			
			node = makeEqualsNode("intPrimitive", "s0", 2);
			caseNode.AddChildNode(node);
			caseNode.AddChildNode(new SupportExprNode("b"));
			
			caseNode.AddChildNode(new SupportExprNode("c"));
			
			validate(caseNode);
			
			return caseNode;
		}
		
		public static ExprCaseNode makeCaseSyntax2Node()
		{
			// Build (case 2 expression):
			// case s0.intPrimitive
			//   when 1 then "a"
			//   when 2 then "b"
			//   else "c"
			// end
			ExprCaseNode caseNode = new ExprCaseNode(true);
			caseNode.AddChildNode(makeIdentNode("intPrimitive", "s0"));
			
			caseNode.AddChildNode(new SupportExprNode(1));
			caseNode.AddChildNode(new SupportExprNode("a"));
			caseNode.AddChildNode(new SupportExprNode(2));
			caseNode.AddChildNode(new SupportExprNode("b"));
			caseNode.AddChildNode(new SupportExprNode("c"));
			
			validate(caseNode);
			
			return (caseNode);
		}
		
		private static ExprEqualsNode makeEqualsNode(String ident1, String stream1, Object value)
		{
			ExprEqualsNode topNode = new ExprEqualsNode(false);
			ExprIdentNode i1_1 = new ExprIdentNode(ident1, stream1);
			SupportExprNode constantNode = new SupportExprNode(value);
			topNode.AddChildNode(i1_1);
			topNode.AddChildNode(constantNode);
			return topNode;
		}
		
		private static void  validate(ExprNode topNode)
		{
			StreamTypeService streamTypeService = new SupportStreamTypeSvc3Stream();
			topNode.GetValidatedSubtree(streamTypeService, null);
		}
	}
}
