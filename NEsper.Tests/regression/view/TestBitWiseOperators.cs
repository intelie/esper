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
        internal const sbyte FIRST_EVENT = 1;
        internal const short SECOND_EVENT = 2;
        internal const int THIRD_EVENT = FIRST_EVENT | SECOND_EVENT;
        internal const long FOURTH_EVENT = 4;
        internal const bool FITH_EVENT = false;

        private EPServiceProvider _epService;
        private SupportUpdateListener _testListener;
        private EPStatement _selectTestView;

        [Test]
        public virtual void testGetEventType()
        {
            setUpBitWiseStmt();
            EventType type = _selectTestView.EventType;
            log.Debug(".testGetEventType properties=" + CollectionHelper.Render(type.PropertyNames));
            Assert.AreEqual(typeof(SByte), type.GetPropertyType("myFirstProperty"));
            Assert.AreEqual(typeof(Int16), type.GetPropertyType("mySecondProperty"));
            Assert.AreEqual(typeof(Int32), type.GetPropertyType("myThirdProperty"));
            Assert.AreEqual(typeof(Int64), type.GetPropertyType("myFourthProperty"));
            Assert.AreEqual(typeof(bool), type.GetPropertyType("myFithProperty"));
        }

        [Test]
        public virtual void testBitWiseOperators()
        {
            setUpBitWiseStmt();
            _testListener.reset();

            sbyte tempAux = (sbyte)FIRST_EVENT;
            Int16 tempAux2 = (short)SECOND_EVENT;
            Int32 tempAux3 = (Int32)THIRD_EVENT;
            Int64 tempAux4 = (long)FOURTH_EVENT;
            bool tempAux5 = FITH_EVENT;
            SendEvent(FIRST_EVENT, tempAux, SECOND_EVENT, tempAux2, FIRST_EVENT, tempAux3, 3L, tempAux4, FITH_EVENT, tempAux5);

            EventBean received = _testListener.getAndResetLastNewData()[0];
            Assert.AreEqual((sbyte)1, (received["myFirstProperty"]));
            Assert.IsTrue(((Int16)(received["mySecondProperty"]) & SECOND_EVENT) == SECOND_EVENT);
            Assert.IsTrue(((Int32)(received["myThirdProperty"]) & FIRST_EVENT) == FIRST_EVENT);
            Assert.AreEqual(7L, (received["myFourthProperty"]));
            Assert.AreEqual(false, (received["myFithProperty"]));
        }

        [SetUp]
        public virtual void setUp()
        {
            _testListener = new SupportUpdateListener();
            _epService = EPServiceProviderManager.GetDefaultProvider();
            _epService.Initialize();
        }

        private void setUpBitWiseStmt()
        {
            String viewExpr = 
                "select (bytePrimitive & byteBoxed) as myFirstProperty, " +
                "(shortPrimitive | shortBoxed) as mySecondProperty, " + 
                "(intPrimitive | intBoxed) as myThirdProperty, " + 
                "(longPrimitive ^ longBoxed) as myFourthProperty, " + 
                "(boolPrimitive & boolBoxed) as myFithProperty " + 
                " from " + typeof(SupportBean).FullName + ".win:length(3) ";
            _selectTestView = _epService.EPAdministrator.createEQL(viewExpr);
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
            bean.bytePrimitive = bytePrimitive_;
            bean.byteBoxed = byteBoxed_;
            bean.shortPrimitive = shortPrimitive_;
            bean.shortBoxed = shortBoxed;
            bean.intPrimitive = intPrimitive_;
            bean.intBoxed = intBoxed_;
            bean.longPrimitive = longPrimitive_;
            bean.longBoxed = longBoxed_;
            bean.boolPrimitive = boolPrimitive_;
            bean.boolBoxed = boolBoxed_;
            _epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
