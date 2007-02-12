using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprIdentNode
    {
        private ExprIdentNode[] identNodes;
        private StreamTypeService streamTypeService;

        [SetUp]
        public virtual void setUp()
        {
            identNodes = new ExprIdentNode[4];
            identNodes[0] = new ExprIdentNode("mapped('a')");
            identNodes[1] = new ExprIdentNode("nestedValue", "nested");
            identNodes[2] = new ExprIdentNode("indexed[1]", "s2");
            identNodes[3] = new ExprIdentNode("intPrimitive", "s0");

            streamTypeService = new SupportStreamTypeSvc3Stream();
        }

        static void sendToAbyss(Object value)
        {
        }

        [Test]
        public virtual void testValidateInvalid()
        {
            try
            {
                sendToAbyss(identNodes[0].StreamId);
                Assert.Fail();
            }
            catch (SystemException ex)
            {
                // expected
            }

            try
            {
                sendToAbyss(identNodes[0].ReturnType);
                Assert.Fail();
            }
            catch (SystemException ex)
            {
                // expected
            }

            try
            {
                sendToAbyss(identNodes[0].ResolvedStreamName);
                Assert.Fail();
            }
            catch (SystemException ex)
            {
                // expected
            }

            try
            {
                sendToAbyss(identNodes[0].ResolvedPropertyName);
                Assert.Fail();
            }
            catch (SystemException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testValidate()
        {
            identNodes[0].validate(streamTypeService, null);
            Assert.AreEqual(2, identNodes[0].StreamId);
            Assert.AreEqual(typeof(String), identNodes[0].ReturnType);
            Assert.AreEqual("mapped('a')", identNodes[0].ResolvedPropertyName);

            identNodes[1].validate(streamTypeService, null);
            Assert.AreEqual(2, identNodes[1].StreamId);
            Assert.AreEqual(typeof(String), identNodes[1].ReturnType);
            Assert.AreEqual("nested.nestedValue", identNodes[1].ResolvedPropertyName);

            identNodes[2].validate(streamTypeService, null);
            Assert.AreEqual(2, identNodes[2].StreamId);
            Assert.AreEqual(typeof(int), identNodes[2].ReturnType);
            Assert.AreEqual("indexed[1]", identNodes[2].ResolvedPropertyName);

            identNodes[3].validate(streamTypeService, null);
            Assert.AreEqual(0, identNodes[3].StreamId);
            Assert.AreEqual(typeof(int), identNodes[3].ReturnType);
            Assert.AreEqual("intPrimitive", identNodes[3].ResolvedPropertyName);

            tryInvalidValidate(new ExprIdentNode(""));
            tryInvalidValidate(new ExprIdentNode("dummy"));
            tryInvalidValidate(new ExprIdentNode("nested", "s0"));
            tryInvalidValidate(new ExprIdentNode("dummy", "s2"));
            tryInvalidValidate(new ExprIdentNode("intPrimitive", "s2"));
            tryInvalidValidate(new ExprIdentNode("intPrimitive", "s3"));
        }

        [Test]
        public virtual void testGetType()
        {
            // test success
            identNodes[0].validate(streamTypeService, null);
            Assert.AreEqual(typeof(String), identNodes[0].ReturnType);
        }

        [Test]
        public virtual void testEvaluate()
        {
            EventBean[] events = new EventBean[] { MakeEvent(10) };

            identNodes[3].validate(streamTypeService, null);
            Assert.AreEqual(10, identNodes[3].Evaluate(events));
            Assert.IsNull(identNodes[3].Evaluate(new EventBean[2]));
        }

        [Test]
        public virtual void testEvaluatePerformance()
        {
            // test performance of evaluate for indexed events
            // fails if the getter is not in place

            EventBean[] events = SupportStreamTypeSvc3Stream.SampleEvents;
            identNodes[2].validate(streamTypeService, null);

            long startTime = DateTime.Now.Ticks;
            for (int i = 0; i < 100000; i++)
            {
                identNodes[2].Evaluate(events);
            }
            long endTime = DateTime.Now.Ticks;
            long delta = endTime - startTime;
            log.Info(".testEvaluate delta=" + delta);
            Assert.IsTrue(delta < 2000000);
        }

        [Test]
        public virtual void testToExpressionString()
        {
            for (int i = 0; i < identNodes.Length; i++)
            {
                identNodes[i].validate(streamTypeService, null);
            }
            Assert.AreEqual("mapped('a')", identNodes[0].ExpressionString);
            Assert.AreEqual("nested.nestedValue", identNodes[1].ExpressionString);
            Assert.AreEqual("s2.indexed[1]", identNodes[2].ExpressionString);
            Assert.AreEqual("s0.intPrimitive", identNodes[3].ExpressionString);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            identNodes[0].validate(streamTypeService, null);
            identNodes[2].validate(streamTypeService, null);
            identNodes[3].validate(streamTypeService, null);
            Assert.IsTrue(identNodes[3].EqualsNode(identNodes[3]));
            Assert.IsFalse(identNodes[0].EqualsNode(identNodes[2]));
        }

        private EventBean MakeEvent(int intPrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.intPrimitive = intPrimitive;
            return SupportEventBeanFactory.createObject(_event);
        }

        private void tryInvalidValidate(ExprIdentNode identNode)
        {
            try
            {
                identNode.validate(streamTypeService, null);
                Assert.Fail();
            }
            catch (ExprValidationException)
            {
                // expected
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}