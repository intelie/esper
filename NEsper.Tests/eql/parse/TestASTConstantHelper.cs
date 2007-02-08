using System;

using antlr;
using antlr.collections;

using net.esper.eql.generated;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{
	[TestFixture]
    public class TestASTConstantHelper 
    {
        public virtual void testGetConstantTypeName(int astTypeConstant)
        {
            Assert.AreEqual(PrimitiveValueType.STRING, ASTConstantHelper.getConstantTypeName(EqlEvalTokenTypes.STRING_TYPE));
            Assert.AreEqual(PrimitiveValueType.INTEGER, ASTConstantHelper.getConstantTypeName(EqlEvalTokenTypes.INT_TYPE));
            Assert.AreEqual(PrimitiveValueType.BOOL, ASTConstantHelper.getConstantTypeName(EqlEvalTokenTypes.BOOL_TYPE));

            try
            {
                ASTConstantHelper.getConstantTypeName(-1);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testCanConvert()
        {
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.STRING_TYPE, PrimitiveValueType.STRING));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.STRING_TYPE, PrimitiveValueType.FLOAT));

            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.BOOL_TYPE, PrimitiveValueType.BOOL));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.BOOL_TYPE, PrimitiveValueType.STRING));

            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.FLOAT_TYPE, PrimitiveValueType.FLOAT));
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.FLOAT_TYPE, PrimitiveValueType.DOUBLE));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.FLOAT_TYPE, PrimitiveValueType.STRING));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.FLOAT_TYPE, PrimitiveValueType.LONG));

            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.DOUBLE_TYPE, PrimitiveValueType.DOUBLE));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.DOUBLE_TYPE, PrimitiveValueType.INTEGER));

            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.LONG_TYPE, PrimitiveValueType.LONG));
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.LONG_TYPE, PrimitiveValueType.DOUBLE));
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.LONG_TYPE, PrimitiveValueType.FLOAT));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.LONG_TYPE, PrimitiveValueType.STRING));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.LONG_TYPE, PrimitiveValueType.BOOL));

            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.INT_TYPE, PrimitiveValueType.FLOAT));
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.NUM_INT, PrimitiveValueType.FLOAT));
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.INT_TYPE, PrimitiveValueType.DOUBLE));
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.INT_TYPE, PrimitiveValueType.LONG));
            Assert.IsTrue(ASTConstantHelper.canConvert(EqlEvalTokenTypes.INT_TYPE, PrimitiveValueType.SHORT));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.NUM_INT, PrimitiveValueType.BOOL));
            Assert.IsFalse(ASTConstantHelper.canConvert(EqlEvalTokenTypes.INT_TYPE, PrimitiveValueType.STRING));

            try
            {
                ASTConstantHelper.canConvert(-1, PrimitiveValueType.STRING);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testParse()
        {
            Assert.AreEqual(5, ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.NUM_INT, "5")));
            Assert.AreEqual(-1, ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.INT_TYPE, "-1")));
            Assert.AreEqual(35983868567L, ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.LONG_TYPE, "35983868567")));
            Assert.AreEqual(1.45656f, ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.FLOAT_TYPE, "1.45656")));
            Assert.AreEqual(-3.346456456d, ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.DOUBLE_TYPE, "-3.346456456")));
            Assert.AreEqual("a", ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.STRING_TYPE, "'a'")));
            Assert.AreEqual(true, ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.BOOL_TYPE, "true")));
            Assert.IsNull(ASTConstantHelper.parse(makeAST(EqlEvalTokenTypes.NULL_TYPE, null)));
        }

        private AST makeAST(int type, String text)
        {
            AST ast = new CommonAST();
            ast.setType(type);
            ast.setText(text);
            return ast;
        }
    }
}
