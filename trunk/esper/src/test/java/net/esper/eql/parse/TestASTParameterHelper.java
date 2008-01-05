package net.esper.eql.parse;

import net.esper.eql.generated.EsperEPL2GrammarParser;
import net.esper.type.*;
import junit.framework.TestCase;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.CommonToken;

public class TestASTParameterHelper extends TestCase
{
    public void testSingleConstant() throws Exception
    {
        Tree ast = makeSingleAst(EsperEPL2GrammarParser.LONG_TYPE, "1");
        assertEquals(1L, convert(ast));

        ast = makeSingleAst(EsperEPL2GrammarParser.STRING_TYPE, "'1'");
        assertEquals("1", convert(ast));

        ast = makeSingleAst(EsperEPL2GrammarParser.STRING_TYPE, "\"hello\"");
        assertEquals("hello", convert(ast));
    }

    public void testArray() throws Exception
    {
        // Uniform type array
        Tree ast = makeArrayAst(new int[] { EsperEPL2GrammarParser.LONG_TYPE, EsperEPL2GrammarParser.LONG_TYPE },
                               new String[] { "1", "2" });
        long[] longArr = (long[]) convert(ast);
        assertEquals(1l, longArr[0]);
        assertEquals(2l, longArr[1]);

        ast = makeArrayAst(new int[] { EsperEPL2GrammarParser.STRING_TYPE, EsperEPL2GrammarParser.STRING_TYPE },
                               new String[] { "'1'", "'2'" });
        String[] strArr = (String[]) convert(ast);
        assertEquals("1", strArr[0]);
        assertEquals("2", strArr[1]);

        ast = makeArrayAst(new int[] { EsperEPL2GrammarParser.BOOL_TYPE, EsperEPL2GrammarParser.BOOL_TYPE },
                               new String[] { "true", "false" });
        boolean[] boolArr = (boolean[]) convert(ast);
        assertEquals(true, boolArr[0]);
        assertEquals(false, boolArr[1]);

        // Mixed type array
        ast = makeArrayAst(new int[] { EsperEPL2GrammarParser.STRING_TYPE, EsperEPL2GrammarParser.INT_TYPE, EsperEPL2GrammarParser.BOOL_TYPE },
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
        Tree ast = makeSingleAst(EsperEPL2GrammarParser.STAR,"");
        convert(ast);
    }

    public void testListParameter() throws Exception
    {
        Tree ast = makeSingleAst(EsperEPL2GrammarParser.NUMERIC_PARAM_LIST,"");
        ast.addChild(makeSingleAst(EsperEPL2GrammarParser.INT_TYPE,"99"));
        ast.addChild(makeRangeAst());
        ast.addChild(makeSingleAst(EsperEPL2GrammarParser.STAR,""));
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
        Tree ast = makeInternal(new int[] {EsperEPL2GrammarParser.SECOND_PART}, new String[] {"2"}, new int[] {EsperEPL2GrammarParser.NUM_INT});
        assertEquals(2d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {EsperEPL2GrammarParser.MILLISECOND_PART}, new String[] {"2"}, new int[] {EsperEPL2GrammarParser.NUM_INT});
        assertEquals(2/1000d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {EsperEPL2GrammarParser.MINUTE_PART}, new String[] {"2"}, new int[] {EsperEPL2GrammarParser.NUM_INT});
        assertEquals(2 * 60d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {EsperEPL2GrammarParser.HOUR_PART}, new String[] {"2"}, new int[] {EsperEPL2GrammarParser.NUM_INT});
        assertEquals(2 * 60 * 60d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {EsperEPL2GrammarParser.DAY_PART}, new String[] {"2"}, new int[] {EsperEPL2GrammarParser.NUM_INT});
        assertEquals(2 * 24 * 60 * 60d, tryTimePeriod(ast));

        ast = makeInternal(new int[] {EsperEPL2GrammarParser.DAY_PART, EsperEPL2GrammarParser.HOUR_PART, EsperEPL2GrammarParser.MINUTE_PART, EsperEPL2GrammarParser.SECOND_PART, EsperEPL2GrammarParser.MILLISECOND_PART},
                        new String[] {"2",      "3",       "4",         "5",         "6"},
                        new int[] {EsperEPL2GrammarParser.NUM_INT, EsperEPL2GrammarParser.LONG_TYPE, EsperEPL2GrammarParser.NUM_INT, EsperEPL2GrammarParser.NUM_INT, EsperEPL2GrammarParser.NUM_INT});
        assertEquals(2*24*60*60d + 3*60*60 + 4*60 + 5 + 6/1000d, tryTimePeriod(ast));
    }

    private double tryTimePeriod(Tree ast) throws Exception
    {
        TimePeriodParameter result = (TimePeriodParameter) convert(ast);
        return result.getNumSeconds();
    }

    private Tree makeInternal(int[] parts, String[] values, int[] types)
    {
        Tree ast = makeSingleAst(EsperEPL2GrammarParser.TIME_PERIOD, "interval");
        for (int i = 0; i < parts.length; i++)
        {
            Tree childPart = makeSingleAst(parts[i], "part");
            Tree childPartValue = makeSingleAst(types[i], values[i]);
            ast.addChild(childPart);
            childPart.addChild(childPartValue);
        }
        return ast;
    }

    private Object convert(Tree ast) throws Exception
    {
        return ASTParameterHelper.makeParameter(ast, System.currentTimeMillis());
    }

    private Tree makeSingleAst(int type, String value)
    {
        CommonTree ast = new CommonTree();
        ast.token = new CommonToken(type, value);
        return ast;
    }

    private Tree makeArrayAst(int[] types, String[] values) throws Exception
    {
        CommonTree ast = new CommonTree();
        ast.token = new CommonToken(EsperEPL2GrammarParser.ARRAY_PARAM_LIST, "array_list");
        ast.token.setType(EsperEPL2GrammarParser.ARRAY_PARAM_LIST);

        for (int i = 0; i < types.length; i++)
        {
            CommonTree child = new CommonTree();
            child.token = new CommonToken(types[i], values[i]);
            ast.addChild(child);
        }

        return ast;
    }

    private Tree makeRangeAst()
    {
        Tree ast = makeSingleAst(EsperEPL2GrammarParser.NUMERIC_PARAM_RANGE,"");
        Tree child1 = makeSingleAst(EsperEPL2GrammarParser.INT_TYPE,"9");
        Tree child2 = makeSingleAst(EsperEPL2GrammarParser.INT_TYPE,"20");
        ast.addChild(child1);
        ast.addChild(child2);

        return ast;
    }

    private Tree makeFrequencyAst()
    {
        Tree ast = makeSingleAst(EsperEPL2GrammarParser.NUMERIC_PARAM_FREQUENCY,"");
        Tree child = makeSingleAst(EsperEPL2GrammarParser.INT_TYPE,"9");
        ast.addChild(child);
        return ast;
    }
}
