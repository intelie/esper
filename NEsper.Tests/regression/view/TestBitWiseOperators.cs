using System;

using net.esper.compat;
using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestBitWiseOperators
    {
        internal const sbyte FIRST__event = 1;
        internal const short SECOND__event = 2;
        internal const int THIRD__event = FIRST__event | SECOND__event;
        internal const long FOURTH__event = 4;
        internal const bool FITH__event = false;

        private EPServiceProvider _epService;
        private SupportUpdateListener _testListener;
        private EPStatement _selectTestView;

        [SetUp]
        public virtual void SetUp()
        {
            _testListener = new SupportUpdateListener();
            _epService = EPServiceProviderManager.GetDefaultProvider();
            _epService.Initialize();
        }

        [Test]
        public virtual void TestEventType()
        {
            SetUpBitWiseStmt();
            EventType type = _selectTestView.EventType;
            log.Debug(".testGetEventType properties=" + CollectionHelper.Render(type.PropertyNames));
            Assert.AreEqual(typeof(sbyte?), type.GetPropertyType("myFirstProperty"));
            Assert.AreEqual(typeof(short?), type.GetPropertyType("mySecondProperty"));
            Assert.AreEqual(typeof(int?), type.GetPropertyType("myThirdProperty"));
            Assert.AreEqual(typeof(long?), type.GetPropertyType("myFourthProperty"));
            Assert.AreEqual(typeof(bool?), type.GetPropertyType("myFithProperty"));
        }

        [Test]
        public void testBitWiseOperators()
        {
            SetUpBitWiseStmt();
            _testListener.Reset();

            sbyte tempAux = (sbyte)FIRST__event;
            Int16 tempAux2 = (short)SECOND__event;
            Int32 tempAux3 = (Int32)THIRD__event;
            Int64 tempAux4 = (long)FOURTH__event;
            bool tempAux5 = FITH__event;
            SendEvent(FIRST__event, tempAux, SECOND__event, tempAux2, FIRST__event, tempAux3, 3L, tempAux4, FITH__event, tempAux5);

            EventBean received = _testListener.GetAndResetLastNewData()[0];
            Assert.AreEqual((sbyte)1, (received["myFirstProperty"]));
            Assert.IsTrue(((Int16)(received["mySecondProperty"]) & SECOND__event) == SECOND__event);
            Assert.IsTrue(((Int32)(received["myThirdProperty"]) & FIRST__event) == FIRST__event);
            Assert.AreEqual(7L, (received["myFourthProperty"]));
            Assert.AreEqual(false, (received["myFithProperty"]));
        }

        private void SetUpBitWiseStmt()
        {
            String viewExpr =
                "select (bytePrimitive & byteBoxed) as myFirstProperty, " +
                "(shortPrimitive | shortBoxed) as mySecondProperty, " +
                "(intPrimitive | intBoxed) as myThirdProperty, " +
                "(longPrimitive ^ longBoxed) as myFourthProperty, " +
                "(boolPrimitive & boolBoxed) as myFithProperty " +
                " from " + typeof(SupportBean).FullName + ".win:length(3) ";
            _selectTestView = _epService.EPAdministrator.CreateEQL(viewExpr);
            _selectTestView.AddListener(_testListener);
        }

        protected internal virtual void SendEvent(
            sbyte bytePrimitive_,
            sbyte? byteBoxed_,
            short shortPrimitive_,
            short? shortBoxed,
            int intPrimitive_,
            int? intBoxed_,
            long longPrimitive_,
            long? longBoxed_,
            bool boolPrimitive_,
            bool? boolBoxed_)
        {
            SupportBean bean = new SupportBean();
            bean.SetBytePrimitive(bytePrimitive_);
            bean.SetByteBoxed(byteBoxed_);
            bean.SetShortPrimitive(shortPrimitive_);
            bean.SetShortBoxed(shortBoxed);
            bean.SetIntPrimitive(intPrimitive_);
            bean.SetIntBoxed(intBoxed_);
            bean.SetLongPrimitive(longPrimitive_);
            bean.SetLongBoxed(longBoxed_);
            bean.SetBoolPrimitive(boolPrimitive_);
            bean.SetBoolBoxed(boolBoxed_);
            _epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
