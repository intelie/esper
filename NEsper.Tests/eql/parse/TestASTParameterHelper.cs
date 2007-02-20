using System;

using antlr;
using antlr.collections;

using net.esper.eql.generated;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{
    [TestFixture]
    public class TestASTParameterHelper
    {
        [Test]
        public virtual void testSingleConstant()
        {
            AST ast = makeSingleAst(EqlEvalTokenTypes.LONG_TYPE, "1");
            Assert.AreEqual(1L, convert(ast));

            ast = makeSingleAst(EqlEvalTokenTypes.STRING_TYPE, "'1'");
            Assert.AreEqual("1", convert(ast));

            ast = makeSingleAst(EqlEvalTokenTypes.STRING_TYPE, "\"hello\"");
            Assert.AreEqual("hello", convert(ast));
        }

        [Test]
        public virtual void testArray()
        {
            // Uniform type array
            AST ast = makeArrayAst(
                new int[] { EqlEvalTokenTypes.LONG_TYPE, EqlEvalTokenTypes.LONG_TYPE },
                new String[] { "1", "2" });
            long[] longArr = (long[])convert(ast);
            Assert.AreEqual(1L, longArr[0]);
            Assert.AreEqual(2L, longArr[1]);

            ast = makeArrayAst(
                new int[] { EqlEvalTokenTypes.STRING_TYPE, EqlEvalTokenTypes.STRING_TYPE },
                new String[] { "'1'", "'2'" });
            String[] strArr = (String[])convert(ast);
            Assert.AreEqual("1", strArr[0]);
            Assert.AreEqual("2", strArr[1]);

            ast = makeArrayAst(
                new int[] { EqlEvalTokenTypes.BOOL_TYPE, EqlEvalTokenTypes.BOOL_TYPE },
                new String[] { "True", "False" });
            bool[] boolArr = (bool[])convert(ast);
            Assert.AreEqual(true, boolArr[0]);
            Assert.AreEqual(false, boolArr[1]);

            // Mixed type array
            ast = makeArrayAst(
                new int[] { EqlEvalTokenTypes.STRING_TYPE, EqlEvalTokenTypes.INT_TYPE, EqlEvalTokenTypes.BOOL_TYPE },
                new String[] { "'A'", "10", "true" });
            Object[] mixedArr = (Object[])convert(ast);
            Assert.AreEqual("A", mixedArr[0]);
            Assert.AreEqual(10, mixedArr[1]);
            Assert.AreEqual(true, mixedArr[2]);
        }

        [Test]
        public virtual void testFrequencyParameter()
        {
            FrequencyParameter result = (FrequencyParameter)convert(makeFrequencyAst());
            Assert.AreEqual(9, result.Frequency);
        }

        [Test]
        public virtual void testRangeParameter()
        {
            RangeParameter result = (RangeParameter)convert(makeRangeAst());
            Assert.AreEqual(9, result.Low);
            Assert.AreEqual(20, result.High);
        }

        [Test]
        public virtual void testWildcardParameter()
        {
            AST ast = makeSingleAst(EqlEvalTokenTypes.STAR, "");
            convert(ast);
        }

        [Test]
        public virtual void testListParameter()
        {
            AST ast = makeSingleAst(EqlEvalTokenTypes.NUMERIC_PARAM_LIST, "");
            ast.addChild(makeSingleAst(EqlEvalTokenTypes.INT_TYPE, "99"));
            ast.addChild(makeRangeAst());
            ast.addChild(makeSingleAst(EqlEvalTokenTypes.STAR, ""));
            ast.addChild(makeFrequencyAst());

            ListParameter result = (ListParameter)convert(ast);
            Assert.AreEqual(4, result.Parameters.Count);

            IntParameter intParam = (IntParameter)result.Parameters[0];
            Assert.AreEqual(99, intParam.IntValue);

            RangeParameter rangeParam = (RangeParameter)result.Parameters[1];
            Assert.AreEqual(9, rangeParam.Low);
            Assert.AreEqual(20, rangeParam.High);

            Assert.IsTrue(result.Parameters[2] is WildcardParameter);

            FrequencyParameter freqParam = (FrequencyParameter)result.Parameters[3];
            Assert.AreEqual(9, freqParam.Frequency);
        }

        [Test]
        public virtual void testTimePeriod()
        {
            AST ast = makeInternal(
                new int[] { EqlEvalTokenTypes.SECOND_PART },
                new String[] { "2" },
                new int[] { EqlEvalTokenTypes.NUM_INT });
            Assert.AreEqual(2d, tryTimePeriod(ast));

            ast = makeInternal(
                new int[] { EqlEvalTokenTypes.MILLISECOND_PART },
                new String[] { "2" },
                new int[] { EqlEvalTokenTypes.NUM_INT });
            Assert.AreEqual(2 / 1000d, tryTimePeriod(ast));

            ast = makeInternal(
                new int[] { EqlEvalTokenTypes.MINUTE_PART },
                new String[] { "2" },
                new int[] { EqlEvalTokenTypes.NUM_INT });
            Assert.AreEqual(2 * 60d, tryTimePeriod(ast));

            ast = makeInternal(
                new int[] { EqlEvalTokenTypes.HOUR_PART },
                new String[] { "2" },
                new int[] { EqlEvalTokenTypes.NUM_INT });
            Assert.AreEqual(2 * 60 * 60d, tryTimePeriod(ast));

            ast = makeInternal(
                new int[] { EqlEvalTokenTypes.DAY_PART },
                new String[] { "2" },
                new int[] { EqlEvalTokenTypes.NUM_INT });
            Assert.AreEqual(2 * 24 * 60 * 60d, tryTimePeriod(ast));

            ast = makeInternal(
                new int[] {
                    EqlEvalTokenTypes.DAY_PART,
                    EqlEvalTokenTypes.HOUR_PART,
                    EqlEvalTokenTypes.MINUTE_PART,
                    EqlEvalTokenTypes.SECOND_PART,
                    EqlEvalTokenTypes.MILLISECOND_PART 
                },
                new String[] {
                    "2",
                    "3",
                    "4",
                    "5",
                    "6"
                },
                new int[] {
                    EqlEvalTokenTypes.NUM_INT,
                    EqlEvalTokenTypes.LONG_TYPE,
                    EqlEvalTokenTypes.NUM_INT,
                    EqlEvalTokenTypes.NUM_INT,
                    EqlEvalTokenTypes.NUM_INT
                });

            Assert.AreEqual(2 * 24 * 60 * 60d + 3 * 60 * 60 + 4 * 60 + 5 + 6 / 1000d, tryTimePeriod(ast));
        }

        private double tryTimePeriod(AST ast)
        {
            TimePeriodParameter result = (TimePeriodParameter)convert(ast);
            return result.NumSeconds;
        }

        private AST makeInternal(int[] parts, String[] values, int[] types)
        {
            AST ast = makeSingleAst(EqlEvalTokenTypes.TIME_PERIOD, "interval");
            for (int i = 0; i < parts.Length; i++)
            {
                AST childPart = makeSingleAst(parts[i], "part");
                AST childPartValue = makeSingleAst(types[i], values[i]);
                ast.addChild(childPart);
                childPart.addChild(childPartValue);
            }
            return ast;
        }

        private Object convert(AST ast)
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

        private AST makeArrayAst(int[] types, String[] values)
        {
            CommonAST ast = new CommonAST();
            ast.setType(EqlEvalTokenTypes.ARRAY_PARAM_LIST);

            for (int i = 0; i < types.Length; i++)
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
            AST ast = makeSingleAst(EqlEvalTokenTypes.NUMERIC_PARAM_RANGE, "");
            AST child1 = makeSingleAst(EqlEvalTokenTypes.INT_TYPE, "9");
            AST child2 = makeSingleAst(EqlEvalTokenTypes.INT_TYPE, "20");
            ast.addChild(child1);
            ast.addChild(child2);

            return ast;
        }

        private AST makeFrequencyAst()
        {
            AST ast = makeSingleAst(EqlEvalTokenTypes.NUMERIC_PARAM_FREQUENCY, "");
            AST child = makeSingleAst(EqlEvalTokenTypes.INT_TYPE, "9");
            ast.addChild(child);
            return ast;
        }
    }
}
