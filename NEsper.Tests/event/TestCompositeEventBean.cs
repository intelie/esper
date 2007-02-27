using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
    [TestFixture]
    public class TestCompositeEventBean : TestCompositeEventBase
    {
    	[SetUp]
		public override void setUp()
		{
			base.setUp();
		}
    	
        [Test]
        public virtual void testGet()
        {
            Assert.AreEqual(_event, eventBeanComplete["a"]);
            Assert.AreEqual(1, eventBeanComplete["a.intPrimitive"]);
            Assert.AreEqual("nestedValue", eventBeanComplete["b.nested.nestedValue"]);

            Assert.AreEqual(_event, eventBeanInComplete["a"]);
            Assert.AreEqual(1, eventBeanInComplete["a.intPrimitive"]);
            Assert.AreEqual(null, eventBeanInComplete["b.nested.nestedValue"]);
            Assert.AreEqual(null, eventBeanInComplete["b.nested"]);
            Assert.AreEqual(null, eventBeanInComplete["b"]);
        }
    }
}
