///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.eql.core;
using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprNode
	{
	    [Test]
	    public void testGetValidatedSubtree()
	    {
	        SupportExprNode.SetValidateCount(0);

	        // Confirms all child nodes validated
	        // Confirms depth-first validation
	        SupportExprNode topNode = new SupportExprNode(typeof(Boolean));

	        SupportExprNode parent_1 = new SupportExprNode(typeof(Boolean));
	        SupportExprNode parent_2 = new SupportExprNode(typeof(Boolean));

	        topNode.AddChildNode(parent_1);
	        topNode.AddChildNode(parent_2);

	        SupportExprNode supportNode1_1 = new SupportExprNode(typeof(Boolean));
	        SupportExprNode supportNode1_2 = new SupportExprNode(typeof(Boolean));
	        SupportExprNode supportNode2_1 = new SupportExprNode(typeof(Boolean));
	        SupportExprNode supportNode2_2 = new SupportExprNode(typeof(Boolean));

	        parent_1.AddChildNode(supportNode1_1);
	        parent_1.AddChildNode(supportNode1_2);
	        parent_2.AddChildNode(supportNode2_1);
	        parent_2.AddChildNode(supportNode2_2);

	        topNode.GetValidatedSubtree(null, null, null);

	        Assert.AreEqual(1, supportNode1_1.ValidateCountSnapshot);
	        Assert.AreEqual(2, supportNode1_2.ValidateCountSnapshot);
	        Assert.AreEqual(3, parent_1.ValidateCountSnapshot);
	        Assert.AreEqual(4, supportNode2_1.ValidateCountSnapshot);
	        Assert.AreEqual(5, supportNode2_2.ValidateCountSnapshot);
	        Assert.AreEqual(6, parent_2.ValidateCountSnapshot);
	        Assert.AreEqual(7, topNode.ValidateCountSnapshot);
	    }

	    [Test]
	    public void testIdentToStaticMethod()
	    {
	        StreamTypeService typeService = new SupportStreamTypeSvc1Stream();
	        EngineImportService engineImportService = new EngineImportServiceImpl();
	        engineImportService.AddImport("System");
	        MethodResolutionService methodResolutionService = new MethodResolutionServiceImpl(engineImportService);

	        ExprNode identNode = new ExprIdentNode("Int32.Parse(\"3\")");
	        ExprNode result = identNode.GetValidatedSubtree(typeService, methodResolutionService, null);
	        Assert.IsTrue(result is ExprStaticMethodNode);
	        Assert.AreEqual(Int32.Parse("3"), result.Evaluate(null, false));

	        identNode = new ExprIdentNode("Int32.Parse(\'3\')");
	        result = identNode.GetValidatedSubtree(typeService, methodResolutionService, null);
	        Assert.IsTrue(result is ExprStaticMethodNode);
	        Assert.AreEqual(Int32.Parse("3"), result.Evaluate(null, false));

	        identNode = new ExprIdentNode("UknownClass.NonexistentMethod(\"3\")");
	        try
	        {
	            result = identNode.GetValidatedSubtree(typeService, methodResolutionService, null);
	            Assert.Fail();
	        }
	        catch(ExprValidationException e)
	        {
	            // Expected
	        }

	        identNode = new ExprIdentNode("unknownMap(\"key\")");
	        try
	        {
	            result = identNode.GetValidatedSubtree(typeService, methodResolutionService, null);
	            Assert.Fail();
	        }
	        catch(ExprValidationException e)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testDeepEquals()
	    {
	        Assert.IsFalse(ExprNode.DeepEquals(SupportExprNodeFactory.Make2SubNodeAnd(), SupportExprNodeFactory.Make3SubNodeAnd()));
	        Assert.IsFalse(ExprNode.DeepEquals(SupportExprNodeFactory.MakeEqualsNode(), SupportExprNodeFactory.MakeMathNode()));
	        Assert.IsTrue(ExprNode.DeepEquals(SupportExprNodeFactory.MakeMathNode(), SupportExprNodeFactory.MakeMathNode()));
	        Assert.IsFalse(ExprNode.DeepEquals(SupportExprNodeFactory.MakeMathNode(), SupportExprNodeFactory.Make2SubNodeAnd()));
	        Assert.IsTrue(ExprNode.DeepEquals(SupportExprNodeFactory.Make3SubNodeAnd(), SupportExprNodeFactory.Make3SubNodeAnd()));
	    }

	    [Test]
	    public void testParseMappedProp()
	    {
	        ExprNode.MappedPropertyParseResult result = ExprNode.ParseMappedProperty("a.b('c')");
	        Assert.AreEqual("a", result.TypeName);
	        Assert.AreEqual("b", result.MethodName);
	        Assert.AreEqual("c", result.ArgString);

	        result = ExprNode.ParseMappedProperty("a.b.c.d.E(\"hfhf f f f \")");
            Assert.AreEqual("a.b.c.d", result.TypeName);
	        Assert.AreEqual("E", result.MethodName);
	        Assert.AreEqual("hfhf f f f ", result.ArgString);

	        result = ExprNode.ParseMappedProperty("c.d.Doit(\"kf\"kf'kf\")");
            Assert.AreEqual("c.d", result.TypeName);
	        Assert.AreEqual("Doit", result.MethodName);
	        Assert.AreEqual("kf\"kf'kf", result.ArgString);

	        result = ExprNode.ParseMappedProperty("c.d.Doit('kf\"kf'kf\"')");
            Assert.AreEqual("c.d", result.TypeName);
	        Assert.AreEqual("Doit", result.MethodName);
	        Assert.AreEqual("kf\"kf'kf\"", result.ArgString);

	        result = ExprNode.ParseMappedProperty("f('a')");
            Assert.AreEqual(null, result.TypeName);
	        Assert.AreEqual("f", result.MethodName);
	        Assert.AreEqual("a", result.ArgString);

	        Assert.IsNull(ExprNode.ParseMappedProperty("('a')"));
	        Assert.IsNull(ExprNode.ParseMappedProperty(""));
	    }
	}
} // End of namespace
