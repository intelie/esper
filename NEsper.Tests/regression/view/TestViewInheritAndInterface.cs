using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestViewInheritAndInterface
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public void testOverridingSubclass()
        {
            String viewExpr = "select val as value from " + typeof(SupportOverrideOne).FullName + ".win:length(10)";

            EPStatement testView = epService.EPAdministrator.CreateEQL(viewExpr);
            testListener = new SupportUpdateListener();
            testView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportOverrideOneA("valA", "valOne", "valBase"));
            EventBean _event = testListener.GetAndResetLastNewData()[0];
            Assert.AreEqual("valA", _event["value"]);

            epService.EPRuntime.SendEvent(new SupportOverrideBase("x"));
            Assert.IsFalse(testListener.IsInvoked);

            epService.EPRuntime.SendEvent(new SupportOverrideOneB("valB", "valTwo", "valBase2"));
            _event = testListener.GetAndResetLastNewData()[0];
            Assert.AreEqual("valB", _event["value"]);

            epService.EPRuntime.SendEvent(new SupportOverrideOne("valThree", "valBase3"));
            _event = testListener.GetAndResetLastNewData()[0];
            Assert.AreEqual("valThree", _event["value"]);
        }

        [Test]
        public void testImplementationClass()
        {
            String[] viewExpr = new String[] {
				"select baseAB from " + typeof( ISupportBaseAB ).FullName + ".win:length(10)",
				"select baseAB, a from " + typeof( ISupportA ).FullName + ".win:length(10)",
				"select baseAB, b from " + typeof( ISupportB ).FullName + ".win:length(10)",
				"select c from " + typeof( ISupportC ).FullName + ".win:length(10)",
				"select baseAB, a, g from " + typeof( ISupportAImplSuperG ).FullName + ".win:length(10)",
				"select baseAB, a, b, g, c from " + typeof( ISupportAImplSuperGImplPlus ).FullName + ".win:length(10)"
			};

            String[][] expected = new String[][] {
				new String[] { "baseAB" },
				new String[] { "baseAB", "a" },
				new String[] { "baseAB", "b" },
				new String[] { "c" },
				new String[] { "baseAB", "a", "g" },
				new String[] { "baseAB", "a", "b", "g", "c" }
			};

            EPStatement[] testViews = new EPStatement[viewExpr.Length];
            SupportUpdateListener[] listeners = new SupportUpdateListener[viewExpr.Length];
            for (int i = 0; i < viewExpr.Length; i++)
            {
                testViews[i] = epService.EPAdministrator.CreateEQL(viewExpr[i]);
                listeners[i] = new SupportUpdateListener();
                testViews[i].AddListener(listeners[i]);
            }

            epService.EPRuntime.SendEvent(new ISupportAImplSuperGImplPlus("g", "a", "baseAB", "b", "c"));
            for (int i = 0; i < listeners.Length; i++)
            {
                Assert.IsTrue(listeners[i].IsInvoked);
                EventBean _event = listeners[i].GetAndResetLastNewData()[0];

                for (int j = 0; j < expected[i].Length; j++)
                {
                    Assert.IsTrue(
                        _event.EventType.IsProperty(expected[i][j]),
                        "failed property valid check for stmt=" + viewExpr[i]);
                    Assert.AreEqual(
                        expected[i][j],
                        _event[expected[i][j]],
                        "failed property check for stmt=" + viewExpr[i]);
                }
            }
        }
    }
}
