///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using antlr;
using antlr.collections;

using NUnit.Framework;

using net.esper.eql.generated;
using net.esper.eql.spec;

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestASTViewSpecHelper : EqlEvalTokenTypes
	{
	    [Test]
	    public void testBuildViewSpec()
	    {
	        AST ast = MakeSingleAst(VIEW_EXPR, null);
            ast.addChild(MakeSingleAst(IDENT, "namespace"));
            ast.addChild(MakeSingleAst(IDENT, "name"));
            ast.addChild(MakeSingleAst(NUM_INT, "10"));

	        ViewSpec spec = ASTViewSpecHelper.BuildSpec(ast);

	        Assert.AreEqual("namespace", spec.ObjectNamespace);
	        Assert.AreEqual("name", spec.ObjectName);
	        Assert.AreEqual(1, spec.ObjectParameters.Count);
	        Assert.AreEqual(10, spec.ObjectParameters[0]);
	    }

	    private AST MakeSingleAst(int type, String value)
	    {
	        CommonAST ast = new CommonAST();
	        ast.setType(type);
	        ast.setText(value);
	        return ast;
	    }
	}
} // End of namespace
