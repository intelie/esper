using System;

using net.esper.support.emit;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.emit
{
    [TestFixture]
    public class TestEventEmitServiceImpl
    {
        [Test]
        public virtual void testEmitFlow()
        {
            EmitServiceImpl service = new MyEmitServiceImpl();

            SupportEmittedListener listenerOne = new SupportEmittedListener();
            service.AddListener(listenerOne, "1");

            SupportEmittedListener listenerTwo = new SupportEmittedListener();
            service.AddListener(listenerTwo, "2");

            SupportEmittedListener listenerThree = new SupportEmittedListener();
            service.AddListener(listenerThree, null);
            service.AddListener(listenerThree, "3");

            SupportEmittedListener listenerFour = new SupportEmittedListener();
            service.AddListener(listenerFour, "1");
            service.AddListener(listenerFour, "2");

            Assert.IsTrue(service.NumEventsEmitted == 0);
            service.emitEvent("a", null);

            Assert.IsTrue(listenerOne.getEmittedObjects().Count == 0);
            Assert.IsTrue(listenerTwo.getEmittedObjects().Count == 0);
            Assert.IsTrue(listenerThree.getEmittedObjects().Count == 1);
            Assert.IsTrue(listenerThree.getEmittedObjects()[0] == (Object)"a");
            Assert.IsTrue(listenerFour.getEmittedObjects().Count == 0);

            Assert.IsTrue(service.NumEventsEmitted == 1);
            service.emitEvent("b", "1");

            Assert.IsTrue(listenerOne.getEmittedObjects().Count == 1);
            Assert.IsTrue(listenerOne.getEmittedObjects()[0] == (Object)"b");
            Assert.IsTrue(listenerTwo.getEmittedObjects().Count == 0);
            Assert.IsTrue(listenerThree.getEmittedObjects().Count == 2);
            Assert.IsTrue(listenerThree.getEmittedObjects()[1] == (Object)"b");
            Assert.IsTrue(listenerFour.getEmittedObjects().Count == 1);
            Assert.IsTrue(listenerFour.getEmittedObjects()[0] == (Object)"b");

            Assert.IsTrue(service.NumEventsEmitted == 2);
            service.emitEvent("c", "3");

            Assert.IsTrue(listenerOne.getEmittedObjects().Count == 1);
            Assert.IsTrue(listenerTwo.getEmittedObjects().Count == 0);
            Assert.IsTrue(listenerThree.getEmittedObjects().Count == 3);
            Assert.IsTrue(listenerThree.getEmittedObjects()[2] == (Object)"c");
            Assert.IsTrue(listenerFour.getEmittedObjects().Count == 1);

            service.clearListeners();
            Assert.IsTrue(service.NumEventsEmitted == 3);
            service.emitEvent("c", "3");

            Assert.IsTrue(listenerOne.getEmittedObjects().Count == 1);
            Assert.IsTrue(listenerTwo.getEmittedObjects().Count == 0);
            Assert.IsTrue(listenerThree.getEmittedObjects().Count == 3);
            Assert.IsTrue(listenerFour.getEmittedObjects().Count == 1);
        }

        private class MyEmitServiceImpl : EmitServiceImpl
        {
            public MyEmitServiceImpl() { }
        }
    }
}