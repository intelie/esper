using System;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{

	[TestFixture]
    public class TestRangeValueEventProp 
    {
        private FilterSpecParamRangeValue[] _params = new FilterSpecParamRangeValue[5];

        [SetUp]
        public virtual void setUp()
        {
            _params[0] = new RangeValueEventProp("a", "b");
            _params[1] = new RangeValueEventProp("asName", "b");
            _params[2] = new RangeValueEventProp("asName", "boolBoxed");
            _params[3] = new RangeValueEventProp("asName", "intPrimitive");
            _params[4] = new RangeValueEventProp("asName", "intPrimitive");
        }

        [Test]
        public virtual void testCheckType()
        {
            EDictionary<String, EventType> taggedEventTypes = new EHashDictionary<String, EventType>();
            taggedEventTypes.Put("asName", SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)));

            tryInvalidCheckType(taggedEventTypes, _params[0]);
            tryInvalidCheckType(taggedEventTypes, _params[1]);
            tryInvalidCheckType(taggedEventTypes, _params[2]);
            _params[3].CheckType(taggedEventTypes);
        }

        [Test]
        public virtual void testGetFilterValue()
        {
            SupportBean eventBean = new SupportBean();
            eventBean.intPrimitive = 1000;
            EventBean _event = SupportEventBeanFactory.createObject(eventBean);
            MatchedEventMap matchedEvents = new MatchedEventMap();
            matchedEvents.Add("asName", _event);

            tryInvalidGetFilterValue(matchedEvents, _params[0]);
            tryInvalidGetFilterValue(matchedEvents, _params[1]);
            tryInvalidGetFilterValue(matchedEvents, _params[2]);
            Assert.AreEqual(1000.0, _params[3].GetFilterValue(matchedEvents));
        }

        [Test]
        public virtual void testEquals()
        {
            Assert.IsFalse(_params[0].Equals(_params[1]));
            Assert.IsFalse(_params[2].Equals(_params[3]));
            Assert.IsTrue(_params[3].Equals(_params[4]));
        }

        private void tryInvalidCheckType(EDictionary<String, EventType> taggedEventTypes, FilterSpecParamRangeValue value)
        {
            try
            {
                value.CheckType(taggedEventTypes);
                Assert.Fail();
            }
            catch (System.SystemException ex)
            {
                // expected
            }
        }

        private void tryInvalidGetFilterValue(MatchedEventMap matchedEvents, FilterSpecParamRangeValue value)
        {
            try
            {
                value.GetFilterValue(matchedEvents);
                Assert.Fail();
            }
            catch (SystemException)
            {
                // expected
            }
        }
    }
}
