using System;

using net.esper.client;
using net.esper.eql.parse;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.pattern
{
    [TestFixture]
    public class TestInvalidPattern
    {
        public TestInvalidPattern()
        {
            EVENT_NUM = typeof(SupportBean_N).FullName;
            EVENT_COMPLEX = typeof(SupportBeanComplexProps).FullName;
            EVENT_ALLTYPES = typeof(SupportBean).FullName;
        }

        private EPServiceProvider epService;
        private readonly String EVENT_NUM;
        private readonly String EVENT_COMPLEX;
        private readonly String EVENT_ALLTYPES;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
        }

        [Test]
        public virtual void testSyntaxException()
        {
            String exceptionText = getSyntaxExceptionPattern(EVENT_NUM + "(doublePrimitive='ss'");
            Assert.AreEqual("expecting \"RPAREN\", found '' near line 1, column 58 [net.esper.support.bean.SupportBean_N(doublePrimitive='ss']", exceptionText);
        }

        [Test]
        public virtual void testStatementException()
        {
            String exceptionText = null;

            // class not found
            exceptionText = getStatementExceptionPattern("dummypkg.dummy()");
            Assert.AreEqual("Failed to resolve event type: Failed to load class dummypkg.dummy [dummypkg.dummy()]", exceptionText);

            // simple property not found
            exceptionText = getStatementExceptionPattern(EVENT_NUM + "(dummy=1)");
            Assert.AreEqual("Property named 'dummy' not found in class net.esper.support.bean.SupportBean_N [net.esper.support.bean.SupportBean_N(dummy=1)]", exceptionText);

            // nested property not found
            exceptionText = getStatementExceptionPattern(EVENT_NUM + "(dummy.nested=1)");
            Assert.AreEqual("Property named 'dummy.nested' not found in class net.esper.support.bean.SupportBean_N [net.esper.support.bean.SupportBean_N(dummy.nested=1)]", exceptionText);

            // property wrong type
            exceptionText = getStatementExceptionPattern(EVENT_NUM + "(intPrimitive='s')");
            Assert.AreEqual("Implicit conversion from datatype 'String' to 'Int' for property 'intPrimitive' is not allowed [net.esper.support.bean.SupportBean_N(intPrimitive='s')]", exceptionText);

            // property not a primitive type
            exceptionText = getStatementExceptionPattern(EVENT_COMPLEX + "(nested=1)");
            Assert.AreEqual("Property named 'nested' of type 'net.esper.support.bean.SupportBeanComplexProps+SupportBeanSpecialGetterNested' is not supported type [net.esper.support.bean.SupportBeanComplexProps(nested=1)]", exceptionText);

            // no tag matches prior use
            exceptionText = getStatementExceptionPattern(EVENT_NUM + "(doublePrimitive=x.abc)");
            Assert.AreEqual("Event named ''x' not found in event pattern result set [net.esper.support.bean.SupportBean_N(doublePrimitive=x.abc)]", exceptionText);

            // duplicate property in filter
            exceptionText = getStatementExceptionPattern(EVENT_NUM + "(doublePrimitive=1, doublePrimitive=1)");
            Assert.AreEqual("Property named 'doublePrimitive' has been listed more than once as a filter parameter [net.esper.support.bean.SupportBean_N(doublePrimitive=1, doublePrimitive=1)]", exceptionText);

            // range not valid on string
            exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + "(string in [1:2])");
            Assert.AreEqual("Property named 'string' of type 'System.String' not numeric as required for ranges [net.esper.support.bean.SupportBean(string in [1:2])]", exceptionText);

            // range does not allow string params
            exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + "(doubleBoxed in ['a':2])");
            Assert.AreEqual("Implicit conversion from datatype 'String' to 'Double' for property 'doubleBoxed' is not allowed [net.esper.support.bean.SupportBean(doubleBoxed in ['a':2])]", exceptionText);

            // invalid observer arg
            exceptionText = getStatementExceptionPattern("timer:at(9l)");
            Assert.AreEqual("Error invoking constructor for observer 'timer:at', invalid parameter list for the object [timer:at(9l)]", exceptionText);

            // invalid guard arg
            exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + " where timer:within('s')");
            Assert.AreEqual("Error invoking constructor for guard 'within', invalid parameter list for the object [net.esper.support.bean.SupportBean where timer:within('s')]", exceptionText);

            // use-result property is wrong type
            exceptionText = getStatementExceptionPattern("x=" + EVENT_ALLTYPES + " -> " + EVENT_ALLTYPES + "(doublePrimitive=x.boolBoxed)");
            Assert.AreEqual("Type mismatch for property named 'doublePrimitive', supplied type of 'Nullable`1' does not match property type '" + typeof(Nullable<Double>).FullName + "' [x=net.esper.support.bean.SupportBean -> net.esper.support.bean.SupportBean(doublePrimitive=x.boolBoxed)]", exceptionText);
        }

        [Test]
        public virtual void testUseResult()
        {
            String EVENT = typeof(SupportBean_N).FullName;

            tryValid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive)");
            tryInvalid("xx=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive)");
            tryInvalid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = xx.doublePrimitive)");
            tryInvalid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.xx)");
            tryInvalid("xx=" + EVENT + " -> nb=" + EVENT + "(xx = na.doublePrimitive)");
            tryInvalid("na=" + EVENT + " -> nb=" + EVENT + "(xx = na.xx)");
            tryValid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive, intBoxed=na.intBoxed)");
            tryInvalid("xx=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = nb.doublePrimitive)");
            tryValid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in (na.doublePrimitive:na.doubleBoxed))");
            tryValid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.doublePrimitive:na.doubleBoxed])");
            tryValid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.intBoxed:na.intPrimitive])");
            tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.intBoxed:na.xx])");
            tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.intBoxed:na.boolBoxed])");
            tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.xx:na.intPrimitive])");
            tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.boolBoxed:na.intPrimitive])");
        }

        private void tryInvalid(String eqlInvalidPattern)
        {
            try
            {
                epService.EPAdministrator.CreatePattern(eqlInvalidPattern);
                Assert.Fail();
            }
            catch (EPException ex)
            {
                // Expected exception
            }
        }

        private String getSyntaxExceptionPattern(String expression)
        {
            String exceptionText = null;
            try
            {
                epService.EPAdministrator.CreatePattern(expression);
                Assert.Fail();
            }
            catch (EPStatementSyntaxException ex)
            {
                exceptionText = ex.Message;
                log.Debug(".getSyntaxExceptionPattern pattern=" + expression, ex);
                // Expected exception
            }

            return exceptionText;
        }

        private String getStatementExceptionPattern(String expression)
        {
            return getStatementExceptionPattern(expression, false);
        }

        private String getStatementExceptionPattern(String expression, bool isLogException)
        {
            String exceptionText = null;
            try
            {
                epService.EPAdministrator.CreatePattern(expression);
                Assert.Fail();
            }
            catch (EPStatementSyntaxException es)
            {
                throw es;
            }
            catch (EPStatementException ex)
            {
                // Expected exception
                exceptionText = ex.Message;
                if (isLogException)
                {
                    log.Debug(".getSyntaxExceptionPattern pattern=" + expression, ex);
                }
            }

            return exceptionText;
        }

        private void tryValid(String eqlInvalidPattern)
        {
            epService.EPAdministrator.CreatePattern(eqlInvalidPattern);
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
