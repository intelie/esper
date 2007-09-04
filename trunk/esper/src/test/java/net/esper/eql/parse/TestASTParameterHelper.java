package net.esper.eql.parse;

import antlr.collections.AST;
import antlr.CommonAST;
import net.esper.eql.generated.EqlEvalTokenTypes;
import net.esper.type.*;
import junit.framework.TestCase;

public class TestASTParameterHelper extends TestCase implements EqlEvalTokenTypes
{
    public void testSingleConstant() throws Exception
    {
        AST ast = makeSingleAst(LONG_TYPE, "1");
        assertEquals(1L, convert(ast));

        ast = makeSingleAst(STRING_TYPE, "'1'");
        assertEquals("1", convert(ast));

        ast = makeSingleAst(STRING_TYPE, "\"hello\"");
        assertEquals("hello", convert(ast));
    }

    public void testArray() throws Exception
    {
        // Uniform type array
        AST ast = makeArrayAst(new int[] { LONG_TYPE, LONG_TYPE },
                               new String[] { "1", "2" });
        long[] longArr = (long[]) convert(ast);
        assertEquals(1l, longArr[0]);
        assertEquals(2l, longArr[1]);

        ast = makeArrayAst(new int[] { STRING_TYPE, STRING_TYPE },
                               new String[] { "'1'", "'2'" });
        String[] strArr = (String[]) convert(ast);
        assertEquals("1", strArr[0]);
        assertEquals("2", strArr[1]);

        ast = makeArrayAst(new int[] { BOOL_TYPE, BOOL_TYPE },
                               new String[] { "true", "false" });
        boolean[] boolArr = (boolean[]) convert(ast);
        assertEquals(true, boolArr[0]);
        assertEquals(false, boolArr[1]);

        // Mixed type array
        ast = makeArrayAst(new int[] { STRING_TYPE, INT_TYPE, BOOL_TYPE },
                               new String[] { "'A'", "10", "true" });
        Object[] mixedArr = (Object[]) convert(ast);
        assertEquals("A", mixedArr[0]);
        assertEquals(10, mixedArr[1]);
        assertEquals(true, mixedArr[2]);
    }

    public void testFrequencyParameter() throws Exception
    {
        FrequencyParameter result = (FrequencyParameter) convert(makeFrequencyAst());
        assertEquals(9, result.getFrequency());
    }

    public void testRangeParameter() throws Exception
    {
        RangeParameter result = (RangeParameter) convert(makeRangeAst());
        assertEquals(9, result.getLow());
        assertEquals(20, result.getHigh());
    }

    public void testWildcardParameter() throws Exception
    {
        AST ast = makeSingleAst(STAR,"");
        convert(ast);
    }

    public void testListParameter() throws Exception
    {
        AST ast = makeSingleAst(NUMERIC_PARAM_LIST,"");
        ast.addChild(makeSingleAst(INT_TYPE,"99"));
        ast.addChild(makeRangeAst());
        ast.addChild(makeSingleAst(STAR,""));
        ast.addChild(makeFrequencyAst());

        ListParameter result = (ListParameter) convert(ast);
        assertEquals(4, result.getParameters().size());

        IntParameter intParam = (IntParameter) result.getParameters().get(0);
        assertEquals(99, intParam.getIntValue());

        RangeParameter rangeParam = (RangeParameter) result.getParameters().get(1);
        assertEquals(9, rangeParam.getLow());
        assertEquals(20, rangeParam.getHigh());

        assertTrue(result.getParameters().get(2) instanceof WildcardParameter);

        FrequencyParameter freqParam = (FrequencyParameter) result.getParameters().get(3);
        assertEquals(9, freqParam.getFrequency());
    }

    public void testTimePeriod() throws Exception
    {
        AST ast = makeInternal(new int[] {SECOND_PART}, new String[] {"2"}, new int[] {NUM_INT});
        assertEquals(2d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {MILLISECOND_PART}, new String[] {"2"}, new int[] {NUM_INT});
        assertEquals(2/1000d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {MINUTE_PART}, new String[] {"2"}, new int[] {NUM_INT});
        assertEquals(2 * 60d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {HOUR_PART}, new String[] {"2"}, new int[] {NUM_INT});
        assertEquals(2 * 60 * 60d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {DAY_PART}, new String[] {"2"}, new int[] {NUM_INT});
        assertEquals(2 * 24 * 60 * 60d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {DAY_PART, HOUR_PART, MINUTE_PART, SECOND_PART, MILLISECOND_PART},
                        new String[] {"2",      "3",       "4",         "5",         "6"},
                        new int[] {NUM_INT, LONG_TYPE, NUM_INT, NUM_INT, NUM_INT});
        assertEquals(2*24*60*60d + 3*60*60 + 4*60 + 5 + 6/1000d, tryTimePeriod(ast));
    }

    private double tryTimePeriod(AST ast) throws Exception
    {
        TimePeriodParameter result = (TimePeriodParameter) convert(ast);
        return result.getNumSeconds();
    }

    private AST makeInternal(int[] parts, String[] values, int[] types)
    {
        AST ast = makeSingleAst(TIME_PERIOD, "interval");
        for (int i = 0; i < parts.length; i++)
        {
            AST childPart = makeSingleAst(parts[i], "part");
            AST childPartValue = makeSingleAst(types[i], values[i]);
            ast.addChild(childPart);
            childPart.addChild(childPartValue);
        }
        return ast;
    }

    private Object convert(AST ast) throws Exception
    {
        return ASTParameterHelper.makeParameter(ast);
    }

    private AST makeSingleAst(int type, String value)
    {
        CommonAST ast = new CommonAST();
        ast.setType(type);
        ast.setText(value);
        return ast;
    }

    private AST makeArrayAst(int[] types, String[] values) throws Exception
    {
        CommonAST ast = new CommonAST();
        ast.setType(ARRAY_PARAM_LIST);

        for (int i = 0; i < types.length; i++)
        {
            CommonAST child = new CommonAST();
            child.setType(types[i]);
            child.setText(values[i]);
            ast.addChild(child);
        }

        return ast;
    }

    private AST makeRangeAst()
    {
        AST ast = makeSingleAst(NUMERIC_PARAM_RANGE,"");
        AST child1 = makeSingleAst(INT_TYPE,"9");
        AST child2 = makeSingleAst(INT_TYPE,"20");
        ast.addChild(child1);
        ast.addChild(child2);

        return ast;
    }

    private AST makeFrequencyAst()
    {
        AST ast = makeSingleAst(NUMERIC_PARAM_FREQUENCY,"");
        AST child = makeSingleAst(INT_TYPE,"9");
        ast.addChild(child);
        return ast;
    }
}
