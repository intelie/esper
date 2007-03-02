using System;

using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprBitWiseNode
    {

        private ExprBitWiseNode _bitWiseNode;

        [SetUp]
        public virtual void setUp()
        {
            _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
        }

        [Test]
        public virtual void testValidate()
        {
            // Must have exactly 2 subnodes
            try
            {
                _bitWiseNode.Validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException)
            {
                // Expected
                log.Debug("No nodes in the expression");
            }

            // Must have only number or boolean-type subnodes
            _bitWiseNode.AddChildNode(new SupportExprNode(typeof(String)));
            _bitWiseNode.AddChildNode(new SupportExprNode(typeof(Int32)));
            try
            {
                _bitWiseNode.Validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testGetType()
        {
            log.Debug(".testGetType");
            _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
            _bitWiseNode.AddChildNode(new SupportExprNode(typeof(Double)));
            _bitWiseNode.AddChildNode(new SupportExprNode(typeof(Int32)));
            try
            {
                _bitWiseNode.Validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException)
            {
                // Expected
            }
            _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
            _bitWiseNode.AddChildNode(new SupportExprNode(typeof(Int64)));
            _bitWiseNode.AddChildNode(new SupportExprNode(typeof(Int64)));
            _bitWiseNode.GetValidatedSubtree(null, null);
            Assert.AreEqual(typeof(long?), _bitWiseNode.ReturnType);
        }

        [Test]
        public virtual void testEvaluate()
        {
            log.Debug(".testEvaluate");
            _bitWiseNode.AddChildNode(new SupportExprNode((Object)10));
            _bitWiseNode.AddChildNode(new SupportExprNode((Object)12));
            _bitWiseNode.GetValidatedSubtree(null, null);
            Assert.AreEqual(8, _bitWiseNode.Evaluate(null));
        }

        [Test]
        public virtual void testEqualsNode()
        {
            log.Debug(".testEqualsNode");
            _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
            Assert.IsTrue(_bitWiseNode.EqualsNode(_bitWiseNode));
            Assert.IsFalse(_bitWiseNode.EqualsNode(new ExprBitWiseNode(BitWiseOpEnum.BXOR)));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            log.Debug(".testToExpressionString");
            _bitWiseNode = new ExprBitWiseNode(BitWiseOpEnum.BAND);
            _bitWiseNode.AddChildNode(new SupportExprNode(4));
            _bitWiseNode.AddChildNode(new SupportExprNode(2));
            Assert.AreEqual("(4&2)", _bitWiseNode.ExpressionString);
        }

        internal static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}