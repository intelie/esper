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

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestASTConstantHelper : EqlEvalTokenTypes
	{
	    [Test]
	    public void testParse()
	    {
            Assert.AreEqual(5, ASTConstantHelper.Parse(MakeAST(NUM_INT, "5")));
            Assert.AreEqual(-1, ASTConstantHelper.Parse(MakeAST(INT_TYPE, "-1")));
            Assert.AreEqual(35983868567L, ASTConstantHelper.Parse(MakeAST(LONG_TYPE, "35983868567")));
            Assert.AreEqual(1.45656f, ASTConstantHelper.Parse(MakeAST(FLOAT_TYPE, "1.45656")));
            Assert.AreEqual(-3.346456456d, ASTConstantHelper.Parse(MakeAST(DOUBLE_TYPE, "-3.346456456")));
            Assert.AreEqual("a", ASTConstantHelper.Parse(MakeAST(STRING_TYPE, "'a'")));
            Assert.AreEqual(true, ASTConstantHelper.Parse(MakeAST(BOOL_TYPE, "true")));
            Assert.IsNull(ASTConstantHelper.Parse(MakeAST(NULL_TYPE, null)));
	    }

	    private AST MakeAST(int type, String text)
	    {
	        AST ast = new CommonAST();
	        ast.setType(type);
	        ast.setText(text);
	        return ast;
	    }
	}
} // End of namespace
