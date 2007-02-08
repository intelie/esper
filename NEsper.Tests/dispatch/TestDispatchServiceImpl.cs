using System;
using System.Collections.Generic;

using net.esper.support.dispatch;
using net.esper.util;

using NUnit.Core;
using NUnit.Framework;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.dispatch
{
    [TestFixture]
    public class TestDispatchServiceImpl
    {
        private DispatchServiceImpl service;

        [SetUp]
        public virtual void setUp()
        {
            service = new DispatchServiceImpl();
        }

        [Test]
        public virtual void testAddAndDispatch()
        {
            // Dispatch without work to do, should complete
            service.Dispatch();

            SupportDispatchable disOne = new SupportDispatchable();
            SupportDispatchable disTwo = new SupportDispatchable();
            service.AddExternal(disOne);
            service.AddExternal(disTwo);

            Assert.AreEqual(0, disOne.getAndResetNumExecuted());
            Assert.AreEqual(0, disTwo.getAndResetNumExecuted());

            service.Dispatch();

            service.AddExternal(disTwo);
            Assert.AreEqual(1, disOne.getAndResetNumExecuted());
            Assert.AreEqual(1, disTwo.getAndResetNumExecuted());

            service.Dispatch();
            Assert.AreEqual(0, disOne.getAndResetNumExecuted());
            Assert.AreEqual(1, disTwo.getAndResetNumExecuted());
        }

        [Test]
        public virtual void testAddDispatchTwice()
        {
            SupportDispatchable disOne = new SupportDispatchable();
            service.AddExternal(disOne);

            try
            {
                service.AddExternal(disOne);
                Assert.Fail();
            }
            catch (net.esper.util.AssertionException ex)
            {
                // Expected
            }

            service.Dispatch();
            Assert.AreEqual(1, disOne.getAndResetNumExecuted());

            service.Dispatch();
            Assert.AreEqual(0, disOne.getAndResetNumExecuted());
        }

        [Test]
        public virtual void testAddInternalExternal()
        {
            SupportDispatchable[] dispatchables = new SupportDispatchable[5];
            for (int i = 0; i < dispatchables.Length; i++)
            {
                dispatchables[i] = new SupportDispatchable();
            }
            SupportDispatchable.getAndResetInstanceList();

            service.AddExternal(dispatchables[0]);
            service.AddInternal(dispatchables[1]);
            service.AddInternal(dispatchables[2]);
            service.AddExternal(dispatchables[3]);
            service.AddInternal(dispatchables[4]);

            service.Dispatch();

            IList<SupportDispatchable> dispatchList = SupportDispatchable.getAndResetInstanceList();
            Assert.AreSame(dispatchables[1], dispatchList[0]);
            Assert.AreSame(dispatchables[2], dispatchList[1]);
            Assert.AreSame(dispatchables[4], dispatchList[2]);
            Assert.AreSame(dispatchables[0], dispatchList[3]);
            Assert.AreSame(dispatchables[3], dispatchList[4]);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}