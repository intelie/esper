package net.esper.eql.parse;

import junit.framework.TestCase;
import net.esper.eql.generated.EsperEPLParser;
import net.esper.eql.spec.ViewSpec;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.CommonTree;

public class TestASTViewSpecHelper extends TestCase
{
    public void testBuildViewSpec() throws Exception
    {
        Tree ast = makeSingleAst(EsperEPLParser.VIEW_EXPR, null);
        ast.addChild(makeSingleAst(EsperEPLParser.IDENT, "namespace"));
        ast.addChild(makeSingleAst(EsperEPLParser.IDENT, "name"));
        ast.addChild(makeSingleAst(EsperEPLParser.NUM_INT, "10"));

        ViewSpec spec = ASTViewSpecHelper.buildSpec(ast);

        assertEquals("namespace", spec.getObjectNamespace());
        assertEquals("name", spec.getObjectName());
        assertEquals(1, spec.getObjectParameters().size());
        assertEquals(10, spec.getObjectParameters().get(0));
    }

    private Tree makeSingleAst(int type, String value)
    {
        CommonTree ast = new CommonTree();
        ast.token.setType(type);
        ast.token.setText(value);
        return ast;
    }
}
