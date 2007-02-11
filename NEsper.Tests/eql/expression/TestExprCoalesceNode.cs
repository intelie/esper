using System;

using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprCoalesceNode
    {
        private ExprCoalesceNode[] coalesceNodes;

        [SetUp]
        public virtual void setUp()
        {
            coalesceNodes = new ExprCoalesceNode[5];

            coalesceNodes[0] = new ExprCoalesceNode();
            coalesceNodes[0].AddChildNode(new SupportExprNode((Object)null, typeof(long)));
            coalesceNodes[0].AddChildNode(new SupportExprNode((Object)null, typeof(int)));
            coalesceNodes[0].AddChildNode(new SupportExprNode(4, typeof(sbyte)));

            coalesceNodes[1] = new ExprCoalesceNode();
            coalesceNodes[1].AddChildNode(new SupportExprNode((Object)null, typeof(string)));
            coalesceNodes[1].AddChildNode(new SupportExprNode("a", typeof(string)));

            coalesceNodes[2] = new ExprCoalesceNode();
            coalesceNodes[2].AddChildNode(new SupportExprNode((Object)null, typeof(bool)));
            coalesceNodes[2].AddChildNode(new SupportExprNode(true, typeof(bool)));

            coalesceNodes[3] = new ExprCoalesceNode();
            coalesceNodes[3].AddChildNode(new SupportExprNode((Object)null, typeof(char)));
            coalesceNodes[3].AddChildNode(new SupportExprNode((Object)null, typeof(char)));
            coalesceNodes[3].AddChildNode(new SupportExprNode((Object)null, typeof(char)));
            coalesceNodes[3].AddChildNode(new SupportExprNode('b', typeof(Char)));

            coalesceNodes[4] = new ExprCoalesceNode();
            coalesceNodes[4].AddChildNode(new SupportExprNode(5, typeof(float)));
            coalesceNodes[4].AddChildNode(new SupportExprNode((Object)null, typeof(double)));
        }

        [Test]
        public virtual void testGetType()
        {
            for (int i = 0; i < coalesceNodes.Length; i++)
            {
                coalesceNodes[i].validate(null, null);
            }

            Assert.AreEqual(typeof(long?), coalesceNodes[0].ReturnType);
            Assert.AreEqual(typeof(string), coalesceNodes[1].ReturnType);
            Assert.AreEqual(typeof(bool?), coalesceNodes[2].ReturnType);
            Assert.AreEqual(typeof(char?), coalesceNodes[3].ReturnType);
            Assert.AreEqual(typeof(double?), coalesceNodes[4].ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            ExprCoalesceNode coalesceNode = new ExprCoalesceNode();
            coalesceNode.AddChildNode(new SupportExprNode(1));

            // Test too few nodes under this node
            try
            {
                coalesceNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // Test node result type not fitting
            coalesceNode.AddChildNode(new SupportExprNode("s"));
            try
            {
                coalesceNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testEvaluate()
        {
            for (int i = 0; i < coalesceNodes.Length; i++)
            {
                coalesceNodes[i].validate(null, null);
            }

            Assert.AreEqual(4L, coalesceNodes[0].Evaluate(null));
            Assert.AreEqual("a", coalesceNodes[1].Evaluate(null));
            Assert.AreEqual(true, coalesceNodes[2].Evaluate(null));
            Assert.AreEqual('b', coalesceNodes[3].Evaluate(null));
            Assert.AreEqual(5D, coalesceNodes[4].Evaluate(null));
        }

        [Test]
        public virtual void testEquals()
        {
            Assert.IsFalse(coalesceNodes[0].EqualsNode(new ExprEqualsNode(true)));
            Assert.IsTrue(coalesceNodes[0].EqualsNode(coalesceNodes[1]));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            //Assert.AreEqual("coalesce(null, null, ", coalesceNodes[0].ExpressionString);
        }
    }
}