package net.esper.eql.parse;

import junit.framework.TestCase;
import net.esper.eql.generated.EsperEPL2GrammarParser;
import net.esper.eql.spec.ViewSpec;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.CommonToken;

public class TestASTViewSpecHelper extends TestCase
{
    public void testBuildViewSpec() throws Exception
    {
        Tree ast = makeSingleAst(EsperEPL2GrammarParser.VIEW_EXPR, null);
        ast.addChild(makeSingleAst(EsperEPL2GrammarParser.IDENT, "namespace"));
        ast.addChild(makeSingleAst(EsperEPL2GrammarParser.IDENT, "name"));
        ast.addChild(makeSingleAst(EsperEPL2GrammarParser.NUM_INT, "10"));

        ViewSpec spec = ASTViewSpecHelper.buildSpec(ast, System.currentTimeMillis());

        assertEquals("namespace", spec.getObjectNamespace());
        assertEquals("name", spec.getObjectName());
        assertEquals(1, spec.getObjectParameters().size());
        assertEquals(10, spec.getObjectParameters().get(0));
    }

    private Tree makeSingleAst(int type, String value)
    {
        CommonTree ast = new CommonTree();
        ast.token = new CommonToken(type, value);
        return ast;
    }
}
