using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.filter;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.filter
{
	[TestFixture]
    public class TestFilterServiceImpl 
    {
        private class AnonymousClassFilterCallback : FilterCallback
        {
            public AnonymousClassFilterCallback(net.esper.support.filter.SupportFilterCallback callbackTwo, TestFilterServiceImpl enclosingInstance)
            {
                this.callbackTwo = callbackTwo;
                this.enclosingInstance = enclosingInstance;
            }

            private readonly SupportFilterCallback callbackTwo;
            private TestFilterServiceImpl enclosingInstance;
            public TestFilterServiceImpl Enclosing_Instance
            {
                get
                {
                    return enclosingInstance;
                }

            }
            public virtual void matchFound(EventBean _event)
            {
                TestFilterServiceImpl.log.Debug(".matchFound Removing callbackTwo");
                Enclosing_Instance.filterService.Remove(callbackTwo);
            }
        }
        private EventType eventTypeOne;
        private EventType eventTypeTwo;
        private FilterServiceImpl filterService;
        private List<FilterValueSet> filterSpecs;
        private List<SupportFilterCallback> filterCallbacks;
        private List<EventBean> events;
        private List<int[]> matchesExpected;

        [SetUp]
        public virtual void setUp()
        {
            filterService = new MyFilterServiceImpl();

            eventTypeOne = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
            eventTypeTwo = SupportEventTypeFactory.createBeanType(typeof(SupportBeanSimple));

            Object[] eventTypeTwoArgs = new Object[]
            {
                "myInt",
                FilterOperator.RANGE_HALF_CLOSED,
                1,
                10,
                "myString",
                FilterOperator.EQUAL,
                "Hello"
            } ;


            filterSpecs = new List<FilterValueSet>();
            filterSpecs.Add(SupportFilterSpecBuilder.build(eventTypeOne, new Object[0]).GetValueSet(null));
            filterSpecs.Add(SupportFilterSpecBuilder.build(eventTypeOne, new Object[] { "intPrimitive", FilterOperator.RANGE_CLOSED, 10, 20, "str", FilterOperator.EQUAL, "HELLO", "boolPrimitive", FilterOperator.EQUAL, false, "doubleBoxed", FilterOperator.GREATER, 100d }).GetValueSet(null));
            filterSpecs.Add(SupportFilterSpecBuilder.build(eventTypeTwo, new Object[0]).GetValueSet(null));
            filterSpecs.Add(SupportFilterSpecBuilder.build(eventTypeTwo, eventTypeTwoArgs).GetValueSet(null));

            // Create callbacks and add
            filterCallbacks = new List<SupportFilterCallback>();
            for (int i = 0; i < filterSpecs.Count; i++)
            {
                filterCallbacks.Add(new SupportFilterCallback());
                filterService.Add(filterSpecs[i], filterCallbacks[i]);
            }

            // Create events
            matchesExpected = new List<int[]>();
            events = new List<EventBean>();

            events.Add(makeTypeOneEvent(15, "HELLO", false, 101));
            matchesExpected.Add(new int[] { 1, 1, 0, 0 });

            events.Add(makeTypeTwoEvent("Hello", 100));
            matchesExpected.Add(new int[] { 0, 0, 1, 0 });

            events.Add(makeTypeTwoEvent("Hello", 1)); // eventNumber = 2
            matchesExpected.Add(new int[] { 0, 0, 1, 0 });

            events.Add(makeTypeTwoEvent("Hello", 2));
            matchesExpected.Add(new int[] { 0, 0, 1, 1 });

            events.Add(makeTypeOneEvent(15, "HELLO", true, 100));
            matchesExpected.Add(new int[] { 1, 0, 0, 0 });

            events.Add(makeTypeOneEvent(15, "HELLO", false, 99));
            matchesExpected.Add(new int[] { 1, 0, 0, 0 });

            events.Add(makeTypeOneEvent(9, "HELLO", false, 100));
            matchesExpected.Add(new int[] { 1, 0, 0, 0 });

            events.Add(makeTypeOneEvent(10, "no", false, 100));
            matchesExpected.Add(new int[] { 1, 0, 0, 0 });

            events.Add(makeTypeOneEvent(15, "HELLO", false, 999999)); // number 8
            matchesExpected.Add(new int[] { 1, 1, 0, 0 });

            events.Add(makeTypeTwoEvent("Hello", 10));
            matchesExpected.Add(new int[] { 0, 0, 1, 1 });

            events.Add(makeTypeTwoEvent("Hello", 11));
            matchesExpected.Add(new int[] { 0, 0, 1, 0 });
        }

        [Test]
        public virtual void testEvalEvents()
        {
            for (int i = 0; i < events.Count; i++)
            {
                filterService.Evaluate(events[i]);
                int[] matches = matchesExpected[i];

                for (int j = 0; j < matches.Length; j++)
                {
                    SupportFilterCallback callback = filterCallbacks[j];

                    if (matches[j] != callback.AndResetCountInvoked)
                    {
                        log.Debug(".testEvalEvents Match failed, event=" + events[i].Underlying);
                        log.Debug(".testEvalEvents Match failed, eventNumber=" + i + " index=" + j);
                        Assert.IsTrue(false);
                    }
                }
            }
        }

        [Test]
        public virtual void testInvalidType()
        {
            try
            {
                FilterValueSet spec = SupportFilterSpecBuilder.build(eventTypeTwo, new Object[] { "myString", FilterOperator.GREATER, 2 }).GetValueSet(null);
                filterService.Add(spec, new SupportFilterCallback());
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public virtual void testReusedCallback()
        {
            try
            {
                filterService.Add(filterSpecs[0], filterCallbacks[0]);
                Assert.IsTrue(false);
            }
            catch (System.SystemException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public virtual void testCallbackNoFound()
        {
            try
            {
                filterService.Remove(filterCallbacks[0]);
                filterService.Remove(filterCallbacks[0]);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }
        }

        /// <summary> Test for removing a callback that is waiting to occur,
        /// ie. a callback is removed which was a result of an evaluation and it
        /// thus needs to be removed from the tree AND the current dispatch list.
        /// </summary>
        [Test]
        public virtual void testActiveCallbackRemove()
        {
            FilterValueSet spec = SupportFilterSpecBuilder.build(eventTypeOne, new Object[0]).GetValueSet(null);
            SupportFilterCallback callbackTwo = new SupportFilterCallback();

            // callback that removes another matching filter spec callback
            FilterCallback callbackOne = new AnonymousClassFilterCallback(callbackTwo, this);

            filterService.Add(spec, callbackOne);
            filterService.Add(spec, callbackTwo);

            // send event
            filterService.Evaluate(makeTypeOneEvent(1, "HELLO", false, 1));

            // Callback two MUST be invoked, was removed by callback one, but since the
            // callback invocation order should not matter, the second one MUST also execute
            Assert.AreEqual(1, callbackTwo.AndResetCountInvoked);
        }

        private EventBean makeTypeOneEvent(int intPrimitive, String stringValue, bool boolPrimitive, double doubleBoxed)
        {
            SupportBean bean = new SupportBean();
            bean.intPrimitive = intPrimitive;
            bean.str = stringValue;
            bean.boolPrimitive = boolPrimitive;
            bean.doubleBoxed = doubleBoxed;
            return SupportEventBeanFactory.createObject(bean);
        }

        private EventBean makeTypeTwoEvent(String myString, int myInt)
        {
            SupportBeanSimple bean = new SupportBeanSimple(myString, myInt);
            return SupportEventBeanFactory.createObject(bean);
        }

        class MyFilterServiceImpl : FilterServiceImpl
        {
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TestFilterServiceImpl));
    }
}
