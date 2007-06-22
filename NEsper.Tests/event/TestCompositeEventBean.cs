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
            Assert.AreEqual(_event, _eventBeanComplete["a"]);
            Assert.AreEqual(1, _eventBeanComplete["a.intPrimitive"]);
            Assert.AreEqual("nestedValue", _eventBeanComplete["b.nested.nestedValue"]);

            Assert.AreEqual(_event, _eventBeanInComplete["a"]);
            Assert.AreEqual(1, _eventBeanInComplete["a.intPrimitive"]);
            Assert.AreEqual(null, _eventBeanInComplete["b.nested.nestedValue"]);
            Assert.AreEqual(null, _eventBeanInComplete["b.nested"]);
            Assert.AreEqual(null, _eventBeanInComplete["b"]);
        }
    }
}
