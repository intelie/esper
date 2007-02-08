using System;

using net.esper.eql.core;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
	
	[TestFixture]
	public class TestExprNode 
	{
		[Test]
		public virtual void  testGetValidatedSubtree()
		{
			SupportExprNode.ValidateCount = 0;
			
			// Confirms all child nodes validated
			// Confirms depth-first validation
			SupportExprNode topNode = new SupportExprNode(typeof(bool));
			
			SupportExprNode parent_1 = new SupportExprNode(typeof(bool));
			SupportExprNode parent_2 = new SupportExprNode(typeof(bool));
			
			topNode.AddChildNode(parent_1);
			topNode.AddChildNode(parent_2);
			
			SupportExprNode supportNode1_1 = new SupportExprNode(typeof(bool));
			SupportExprNode supportNode1_2 = new SupportExprNode(typeof(bool));
			SupportExprNode supportNode2_1 = new SupportExprNode(typeof(bool));
			SupportExprNode supportNode2_2 = new SupportExprNode(typeof(bool));
			
			parent_1.AddChildNode(supportNode1_1);
			parent_1.AddChildNode(supportNode1_2);
			parent_2.AddChildNode(supportNode2_1);
			parent_2.AddChildNode(supportNode2_2);
			
			topNode.GetValidatedSubtree(null, null);
			
			Assert.AreEqual(1, supportNode1_1.ValidateCountSnapshot);
			Assert.AreEqual(2, supportNode1_2.ValidateCountSnapshot);
			Assert.AreEqual(3, parent_1.ValidateCountSnapshot);
			Assert.AreEqual(4, supportNode2_1.ValidateCountSnapshot);
			Assert.AreEqual(5, supportNode2_2.ValidateCountSnapshot);
			Assert.AreEqual(6, parent_2.ValidateCountSnapshot);
			Assert.AreEqual(7, topNode.ValidateCountSnapshot);
		}
		
		[Test]
		public virtual void  testIdentToStaticMethod()
		{
			StreamTypeService typeService = new SupportStreamTypeSvc1Stream();
			AutoImportService autoImportService = new AutoImportServiceImpl(new String[]{"java.lang.*"});
			
			ExprNode identNode = new ExprIdentNode("Integer.valueOf(\"3\")");
			ExprNode result = identNode.GetValidatedSubtree(typeService, autoImportService);
			Assert.IsTrue(result is ExprStaticMethodNode);
			Assert.AreEqual(Int32.Parse("3"), result.Evaluate(null));
			
			identNode = new ExprIdentNode("Integer.valueOf(\'3\')");
			result = identNode.GetValidatedSubtree(typeService, autoImportService);
			Assert.IsTrue(result is ExprStaticMethodNode);
			Assert.AreEqual(Int32.Parse("3"), result.Evaluate(null));
			
			identNode = new ExprIdentNode("UknownClass.nonexistentMethod(\"3\")");
			try
			{
				result = identNode.GetValidatedSubtree(typeService, autoImportService);
				Assert.Fail();
			}
			catch (ExprValidationException e)
			{
				// Expected
			}
			
			identNode = new ExprIdentNode("unknownMap(\"key\")");
			try
			{
				result = identNode.GetValidatedSubtree(typeService, autoImportService);
				Assert.Fail();
			}
			catch (ExprValidationException e)
			{
				// Expected
			}
		}
		
		[Test]
		public virtual void  testDeepEquals()
		{
			Assert.IsFalse(ExprNode.DeepEquals(SupportExprNodeFactory.make2SubNodeAnd(), SupportExprNodeFactory.make3SubNodeAnd()));
			Assert.IsFalse(ExprNode.DeepEquals(SupportExprNodeFactory.makeEqualsNode(), SupportExprNodeFactory.makeMathNode()));
			Assert.IsTrue(ExprNode.DeepEquals(SupportExprNodeFactory.makeMathNode(), SupportExprNodeFactory.makeMathNode()));
			Assert.IsFalse(ExprNode.DeepEquals(SupportExprNodeFactory.makeMathNode(), SupportExprNodeFactory.make2SubNodeAnd()));
			Assert.IsTrue(ExprNode.DeepEquals(SupportExprNodeFactory.make3SubNodeAnd(), SupportExprNodeFactory.make3SubNodeAnd()));
		}
	}
}
