package net.esper.eql.parse;

import antlr.CommonAST;
import antlr.collections.AST;
import junit.framework.TestCase;
import net.esper.eql.generated.EqlEvalTokenTypes;

public class TestASTConstantHelper extends TestCase implements EqlEvalTokenTypes
{
    public void testParse()
    {
        assertEquals(5, ASTConstantHelper.parse(makeAST(NUM_INT, "5")));
        assertEquals(-1, ASTConstantHelper.parse(makeAST(INT_TYPE, "-1")));
        assertEquals(35983868567L, ASTConstantHelper.parse(makeAST(LONG_TYPE, "35983868567")));
        assertEquals(1.45656f, ASTConstantHelper.parse(makeAST(FLOAT_TYPE, "1.45656")));
        assertEquals(-3.346456456d, ASTConstantHelper.parse(makeAST(DOUBLE_TYPE, "-3.346456456")));
        assertEquals("a", ASTConstantHelper.parse(makeAST(STRING_TYPE, "'a'")));
        assertEquals(true, ASTConstantHelper.parse(makeAST(BOOL_TYPE, "true")));
        assertNull(ASTConstantHelper.parse(makeAST(NULL_TYPE, null)));
    }

    private AST makeAST(int type, String text)
    {
        AST ast = new CommonAST();
        ast.setType(type);
        ast.setText(text);
        return ast;
    }
}
