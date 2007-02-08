using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
    [TestFixture]
    public class TestCompositeEventBean : TestCompositeEventBase
    {
        [Test]
        public virtual void testGet()
        {
            Assert.AreEqual(_event, eventBeanComplete["a"]);
            Assert.AreEqual(1, eventBeanComplete["a.IntPrimitive"]);
            Assert.AreEqual("nestedValue", eventBeanComplete["b.nested.nestedValue"]);

            Assert.AreEqual(_event, eventBeanInComplete["a"]);
            Assert.AreEqual(1, eventBeanInComplete["a.IntPrimitive"]);
            Assert.AreEqual(null, eventBeanInComplete["b.nested.nestedValue"]);
            Assert.AreEqual(null, eventBeanInComplete["b.nested"]);
            Assert.AreEqual(null, eventBeanInComplete["b"]);
        }
    }
}
