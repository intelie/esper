using System;

using antlr;
using antlr.collections;

using net.esper.eql.generated;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{
	
	[TestFixture]
	public class TestASTViewSpecHelper  
	{
		[Test]
		public virtual void  testBuildViewSpec()
		{
            AST ast = makeSingleAst(EqlEvalTokenTypes.VIEW_EXPR, null);
            ast.addChild(makeSingleAst(EqlEvalTokenTypes.IDENT, "namespace"));
            ast.addChild(makeSingleAst(EqlEvalTokenTypes.IDENT, "name"));
            ast.addChild(makeSingleAst(EqlEvalTokenTypes.NUM_INT, "10"));
			
			ViewSpec spec = ASTViewSpecHelper.buildSpec(ast);
			
			Assert.AreEqual("namespace", spec.ObjectNamespace);
			Assert.AreEqual("name", spec.ObjectName);
			Assert.AreEqual(1, spec.ObjectParameters.Count);
			Assert.AreEqual(10, spec.ObjectParameters[0]);
		}
		
		private AST makeSingleAst(int type, String value)
		{
			CommonAST ast = new CommonAST();
			ast.setType(type);
			ast.setText(value);
			return ast;
		}
	}
}
