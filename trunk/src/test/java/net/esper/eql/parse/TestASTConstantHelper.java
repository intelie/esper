package net.esper.eql.parse;

import antlr.CommonAST;
import antlr.collections.AST;
import net.esper.eql.generated.EqlEvalTokenTypes;
import net.esper.type.PrimitiveValueType;
import junit.framework.TestCase;

public class TestASTConstantHelper extends TestCase implements EqlEvalTokenTypes
{
    public void testGetConstantTypeName(int astTypeConstant)
    {
        assertEquals(PrimitiveValueType.STRING, ASTConstantHelper.getConstantTypeName(STRING_TYPE));
        assertEquals(PrimitiveValueType.INTEGER, ASTConstantHelper.getConstantTypeName(INT_TYPE));
        assertEquals(PrimitiveValueType.BOOL, ASTConstantHelper.getConstantTypeName(BOOL_TYPE));

        try
        {
            ASTConstantHelper.getConstantTypeName(-1);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    public void testCanConvert()
    {
        assertTrue(ASTConstantHelper.canConvert(STRING_TYPE, PrimitiveValueType.STRING));
        assertFalse(ASTConstantHelper.canConvert(STRING_TYPE, PrimitiveValueType.FLOAT));

        assertTrue(ASTConstantHelper.canConvert(BOOL_TYPE, PrimitiveValueType.BOOL));
        assertFalse(ASTConstantHelper.canConvert(BOOL_TYPE, PrimitiveValueType.STRING));

        assertTrue(ASTConstantHelper.canConvert(FLOAT_TYPE, PrimitiveValueType.FLOAT));
        assertTrue(ASTConstantHelper.canConvert(FLOAT_TYPE, PrimitiveValueType.DOUBLE));
        assertFalse(ASTConstantHelper.canConvert(FLOAT_TYPE, PrimitiveValueType.STRING));
        assertFalse(ASTConstantHelper.canConvert(FLOAT_TYPE, PrimitiveValueType.LONG));

        assertTrue(ASTConstantHelper.canConvert(DOUBLE_TYPE, PrimitiveValueType.DOUBLE));
        assertFalse(ASTConstantHelper.canConvert(DOUBLE_TYPE, PrimitiveValueType.INTEGER));

        assertTrue(ASTConstantHelper.canConvert(LONG_TYPE, PrimitiveValueType.LONG));
        assertTrue(ASTConstantHelper.canConvert(LONG_TYPE, PrimitiveValueType.DOUBLE));
        assertTrue(ASTConstantHelper.canConvert(LONG_TYPE, PrimitiveValueType.FLOAT));
        assertFalse(ASTConstantHelper.canConvert(LONG_TYPE, PrimitiveValueType.STRING));
        assertFalse(ASTConstantHelper.canConvert(LONG_TYPE, PrimitiveValueType.BOOL));

        assertTrue(ASTConstantHelper.canConvert(INT_TYPE, PrimitiveValueType.FLOAT));
        assertTrue(ASTConstantHelper.canConvert(NUM_INT, PrimitiveValueType.FLOAT));
        assertTrue(ASTConstantHelper.canConvert(INT_TYPE, PrimitiveValueType.DOUBLE));
        assertTrue(ASTConstantHelper.canConvert(INT_TYPE, PrimitiveValueType.LONG));
        assertTrue(ASTConstantHelper.canConvert(INT_TYPE, PrimitiveValueType.SHORT));
        assertFalse(ASTConstantHelper.canConvert(NUM_INT, PrimitiveValueType.BOOL));
        assertFalse(ASTConstantHelper.canConvert(INT_TYPE, PrimitiveValueType.STRING));

        try
        {
            ASTConstantHelper.canConvert(-1, PrimitiveValueType.STRING);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

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
