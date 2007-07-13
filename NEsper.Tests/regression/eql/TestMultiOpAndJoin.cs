using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{

    [TestFixture]
    public class TestMultiOpAndJoin
    {
        public TestMultiOpAndJoin()
        {
            eventData = new int[][]
			{
				new int[]{1, 100},
				new int[]{2, 100},
				new int[]{1, 200},
				new int[]{2, 200}
			};
            eventsA = new SupportBean[eventData.Length];
            eventsB = new SupportBean[eventData.Length];
        }

        private EPServiceProvider epService;
        private EPStatement joinView;
        private SupportUpdateListener updateListener;

        private readonly int[][] eventData;
        private SupportBean[] eventsA;
        private SupportBean[] eventsB;

        [SetUp]
        public virtual void setUp()
        {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
            updateListener = new SupportUpdateListener();

            String eventClass = typeof(SupportBean).FullName;

            String joinStatement =
                "select * from " +
                eventClass + "(string='A').win:length(3) as streamA," +
                eventClass + "(string='B').win:length(3) as streamB" +
                " where streamA.intPrimitive = streamB.intPrimitive " +
                "and streamA.intBoxed = streamB.intBoxed";

            joinView = epService.EPAdministrator.CreateEQL(joinStatement);
            joinView.AddListener(updateListener);

            for (int i = 0; i < eventData.Length; i++)
            {
                eventsA[i] = new SupportBean();
                eventsA[i].SetString("A");
                eventsA[i].SetIntPrimitive(eventData[i][0]);
                eventsA[i].SetIntBoxed(eventData[i][1]);

                eventsB[i] = new SupportBean();
                eventsB[i].SetString("B");
                eventsB[i].SetIntPrimitive(eventData[i][0]);
                eventsB[i].SetIntBoxed(eventData[i][1]);
            }
        }

        [Test]
        public virtual void testJoin()
        {
            SendEvent(eventsA[0]);
            SendEvent(eventsB[1]);
            SendEvent(eventsB[2]);
            SendEvent(eventsB[3]);
            Assert.IsNull(updateListener.LastNewData); // No events expected
        }

        [Test]
        public virtual void testEventType()
        {
            Assert.AreEqual(typeof(SupportBean), joinView.EventType.GetPropertyType("streamA"));
            Assert.AreEqual(typeof(SupportBean), joinView.EventType.GetPropertyType("streamB"));
            Assert.AreEqual(2, joinView.EventType.PropertyNames.Count);
        }

        private void SendEvent(Object _event)
        {
            epService.EPRuntime.SendEvent(_event);
        }
    }
}