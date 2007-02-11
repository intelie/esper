using System;

using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprEqualsNode
    {
        private ExprEqualsNode[] EqualsNodes;

        [SetUp]
        public virtual void setUp()
        {
            EqualsNodes = new ExprEqualsNode[4];
            EqualsNodes[0] = new ExprEqualsNode(false);

            EqualsNodes[1] = new ExprEqualsNode(false);
            EqualsNodes[1].AddChildNode(new SupportExprNode(1L));
            EqualsNodes[1].AddChildNode(new SupportExprNode((Object)1));

            EqualsNodes[2] = new ExprEqualsNode(true);
            EqualsNodes[2].AddChildNode(new SupportExprNode(1.5D));
            EqualsNodes[2].AddChildNode(new SupportExprNode((Object)1));

            EqualsNodes[3] = new ExprEqualsNode(false);
            EqualsNodes[3].AddChildNode(new SupportExprNode(1D));
            EqualsNodes[3].AddChildNode(new SupportExprNode((Object)1));
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(bool?), EqualsNodes[0].ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            // Test success
            EqualsNodes[0].AddChildNode(new SupportExprNode(typeof(String)));
            EqualsNodes[0].AddChildNode(new SupportExprNode(typeof(String)));
            EqualsNodes[0].validate(null, null);

            EqualsNodes[1].validate(null, null);
            EqualsNodes[2].validate(null, null);
            EqualsNodes[3].validate(null, null);

            EqualsNodes[0].ChildNodes.Clear();
            EqualsNodes[0].AddChildNode(new SupportExprNode(typeof(String)));

            // Test too few nodes under this node
            try
            {
                EqualsNodes[0].validate(null, null);
                Assert.Fail();
            }
            catch (System.SystemException ex)
            {
                // Expected
            }

            // Test mismatch type
            EqualsNodes[0].AddChildNode(new SupportExprNode(typeof(bool)));
            try
            {
                EqualsNodes[0].validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testEvaluateEquals()
        {
            EqualsNodes[0] = makeNode(true, false, false);
            Assert.IsFalse((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode(false, false, false);
            Assert.IsTrue((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode(true, true, false);
            Assert.IsTrue((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode(true, typeof(bool), null, typeof(bool), false);
            Assert.IsFalse((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode((Object)null, typeof(String), "ss", typeof(String), false);
            Assert.IsFalse((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode((Object)null, typeof(String), (Object)null, typeof(String), false);
            Assert.IsTrue((bool)EqualsNodes[0].Evaluate(null));

            // try a long and int
            EqualsNodes[1].validate(null, null);
            Assert.IsTrue((bool)EqualsNodes[1].Evaluate(null));

            // try a double and int
            EqualsNodes[2].validate(null, null);
            Assert.IsTrue((bool)EqualsNodes[2].Evaluate(null));

            EqualsNodes[3].validate(null, null);
            Assert.IsTrue((bool)EqualsNodes[3].Evaluate(null));
        }

        [Test]
        public virtual void testEvaluateNotEquals()
        {
            EqualsNodes[0] = makeNode(true, false, true);
            Assert.IsTrue((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode(false, false, true);
            Assert.IsFalse((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode(true, true, true);
            Assert.IsFalse((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode(true, typeof(bool), null, typeof(bool), true);
            Assert.IsTrue((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode((Object)null, typeof(String), "ss", typeof(String), true);
            Assert.IsTrue((bool)EqualsNodes[0].Evaluate(null));

            EqualsNodes[0] = makeNode((Object)null, typeof(String), (Object)null, typeof(String), true);
            Assert.IsFalse((bool)EqualsNodes[0].Evaluate(null));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            EqualsNodes[0].AddChildNode(new SupportExprNode(true));
            EqualsNodes[0].AddChildNode(new SupportExprNode(false));
            Assert.AreEqual("True = False", EqualsNodes[0].ExpressionString);
        }

        private ExprEqualsNode makeNode(Object valueLeft, Object valueRight, bool isNot)
        {
            ExprEqualsNode EqualsNode = new ExprEqualsNode(isNot);
            EqualsNode.AddChildNode(new SupportExprNode(valueLeft));
            EqualsNode.AddChildNode(new SupportExprNode(valueRight));
            return EqualsNode;
        }

        private ExprEqualsNode makeNode(Object valueLeft, Type typeLeft, Object valueRight, Type typeRight, bool isNot)
        {
            ExprEqualsNode EqualsNode = new ExprEqualsNode(isNot);
            EqualsNode.AddChildNode(new SupportExprNode(valueLeft, typeLeft));
            EqualsNode.AddChildNode(new SupportExprNode(valueRight, typeRight));
            return EqualsNode;
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(EqualsNodes[0].EqualsNode(EqualsNodes[1]));
            Assert.IsFalse(EqualsNodes[0].EqualsNode(EqualsNodes[2]));
        }
    }
}