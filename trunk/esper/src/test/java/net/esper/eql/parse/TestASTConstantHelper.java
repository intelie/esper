package net.esper.eql.parse;

import junit.framework.TestCase;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.Token;
import org.antlr.runtime.CommonToken;
import net.esper.eql.generated.EsperEPLParser;

public class TestASTConstantHelper extends TestCase
{
    public void testParse()
    {
        assertEquals(5, ASTConstantHelper.parse(makeAST(EsperEPLParser.NUM_INT, "5")));
        assertEquals(-1, ASTConstantHelper.parse(makeAST(EsperEPLParser.INT_TYPE, "-1")));
        assertEquals(35983868567L, ASTConstantHelper.parse(makeAST(EsperEPLParser.LONG_TYPE, "35983868567")));
        assertEquals(1.45656f, ASTConstantHelper.parse(makeAST(EsperEPLParser.FLOAT_TYPE, "1.45656")));
        assertEquals(-3.346456456d, ASTConstantHelper.parse(makeAST(EsperEPLParser.DOUBLE_TYPE, "-3.346456456")));
        assertEquals("a", ASTConstantHelper.parse(makeAST(EsperEPLParser.STRING_TYPE, "'a'")));
        assertEquals(true, ASTConstantHelper.parse(makeAST(EsperEPLParser.BOOL_TYPE, "true")));
        assertNull(ASTConstantHelper.parse(makeAST(EsperEPLParser.NULL_TYPE, null)));
    }

    private Tree makeAST(int type, String text)
    {
        CommonTree ast = new CommonTree();
        ast.token = new CommonToken(type, text);
        return ast;
    }
}
