package net.esper.eql.parse;

import junit.framework.TestCase;
import net.esper.eql.generated.EqlEvalTokenTypes;
import net.esper.view.ViewSpec;
import antlr.CommonAST;
import antlr.collections.AST;

public class TestASTViewSpecHelper extends TestCase implements EqlEvalTokenTypes
{
    public void testBuildViewSpec() throws Exception
    {
        AST ast = makeSingleAst(VIEW_EXPR, null);
        ast.addChild(makeSingleAst(IDENT, "namespace"));
        ast.addChild(makeSingleAst(IDENT, "name"));
        ast.addChild(makeSingleAst(NUM_INT, "10"));

        ViewSpec spec = ASTViewSpecHelper.buildSpec(ast);

        assertEquals("namespace", spec.getObjectNamespace());
        assertEquals("name", spec.getObjectName());
        assertEquals(1, spec.getObjectParameters().size());
        assertEquals(10, spec.getObjectParameters().get(0));
    }

    private AST makeSingleAst(int type, String value)
    {
        CommonAST ast = new CommonAST();
        ast.setType(type);
        ast.setText(value);
        return ast;
    }
}
