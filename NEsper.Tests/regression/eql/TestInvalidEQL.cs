using System;

using net.esper.client;
using net.esper.eql.parse;
using net.esper.regression.pattern;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
    [TestFixture]
    public class TestInvalidEQL
    {
        private EPServiceProvider epService;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
        }

        [Test]
        public virtual void testSyntaxException()
        {
            String EVENT = typeof(SupportBean_N).FullName;

            String exceptionText = getSyntaxExceptionEQL("select * from *");
            Assert.AreEqual(@"unexpected token: [""*"",<140>,line=1,col=15] near line 1, column 15 [select * from *]", exceptionText);
            //Assert.AreEqual("unexpected token: * near line 1, column 15 [select * from *]", exceptionText);
        }

        [Test]
        public virtual void testDifferentJoins()
        {
            String streamDef = "select * from " + typeof(SupportBean).FullName + ".win:length(3) as sa," + typeof(SupportBean).FullName + ".win:length(3) as sb" + " where ";

            String streamDefTwo = "select * from " + typeof(SupportBean).FullName + ".win:length(3)," + typeof(SupportMarketDataBean).FullName + ".win:length(3)" + " where ";

            tryInvalid(streamDef + "sa.intPrimitive = sb.str");
            tryValid(streamDef + "sa.intPrimitive = sb.intBoxed");
            tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive");
            tryValid(streamDef + "sa.intPrimitive = sb.longBoxed");

            tryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.boolPrimitive");
            tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.boolBoxed = sa.boolPrimitive");

            tryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.intPrimitive and sa.str=sX.str");
            tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.intPrimitive and sa.str=sb.str");

            tryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive or sa.str=sX.str");
            tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive or sb.intBoxed = sa.intPrimitive");

            // try constants
            tryValid(streamDef + "sa.intPrimitive=5");
            tryValid(streamDef + "sa.str='4'");
            tryValid(streamDef + "sa.str=\"4\"");
            tryValid(streamDef + "sa.boolPrimitive=false");
            tryValid(streamDef + "sa.longPrimitive=-5L");
            tryValid(streamDef + "sa.doubleBoxed=5.6d");
            tryValid(streamDef + "sa.floatPrimitive=-5.6f");

            tryInvalid(streamDef + "sa.intPrimitive='5'");
            tryInvalid(streamDef + "sa.str=5");
            tryInvalid(streamDef + "sa.boolBoxed=f");
            tryInvalid(streamDef + "sa.intPrimitive=x");
            tryValid(streamDef + "sa.intPrimitive=5.5");

            // try addition and subtraction
            tryValid(streamDef + "sa.intPrimitive=sa.intBoxed + 5");
            tryValid(streamDef + "sa.intPrimitive=2*sa.intBoxed - sa.intPrimitive/10 + 1");
            tryValid(streamDef + "sa.intPrimitive=2*(sa.intBoxed - sa.intPrimitive)/(10 + 1)");
            tryInvalid(streamDef + "sa.intPrimitive=2*(sa.intBoxed");

            // try comparison
            tryValid(streamDef + "sa.intPrimitive > sa.intBoxed and sb.doublePrimitive < sb.doubleBoxed");
            tryValid(streamDef + "sa.intPrimitive >= sa.intBoxed and sa.doublePrimitive <= sa.doubleBoxed");
            tryValid(streamDef + "sa.intPrimitive > (sa.intBoxed + sb.doublePrimitive)");
            tryInvalid(streamDef + "sa.intPrimitive >= sa.str");
            tryInvalid(streamDef + "sa.boolBoxed >= sa.boolPrimitive");

            // Try some nested
            tryValid(streamDef + "(sa.intPrimitive=3) or (sa.intBoxed=3 and sa.intPrimitive=1)");
            tryValid(streamDef + "((sa.intPrimitive>3) or (sa.intBoxed<3)) and sa.boolBoxed=false");
            tryValid(streamDef + "(sa.intPrimitive<=3 and sa.intPrimitive>=1) or (sa.boolBoxed=false and sa.boolPrimitive=true)");
            tryInvalid(streamDef + "sa.intPrimitive=3 or (sa.intBoxed=2");
            tryInvalid(streamDef + "sa.intPrimitive=3 or sa.intBoxed=2)");
            tryInvalid(streamDef + "sa.intPrimitive=3 or ((sa.intBoxed=2)");

            // Try some without stream name
            tryInvalid(streamDef + "intPrimitive=3");
            tryValid(streamDefTwo + "intPrimitive=3");

            // Try invalid outer join criteria
            String outerJoinDef = "select * from " + typeof(SupportBean).FullName + ".win:length(3) as sa " + "left outer join " + typeof(SupportBean).FullName + ".win:length(3) as sb ";
            tryInvalid(outerJoinDef + "");
            tryValid(outerJoinDef + "on sa.intPrimitive = sb.intBoxed");
            tryInvalid(outerJoinDef + "on sa.intPrimitive = sb.XX");
            tryInvalid(outerJoinDef + "on sa.XX = sb.XX");
            tryInvalid(outerJoinDef + "on sa.XX = sb.intBoxed");
            tryInvalid(outerJoinDef + "on sa.boolBoxed = sb.intBoxed");
            tryValid(outerJoinDef + "on sa.boolPrimitive = sb.boolBoxed");
            tryInvalid(outerJoinDef + "on sa.boolPrimitive = sb.str");
            tryInvalid(outerJoinDef + "on sa.intPrimitive <= sb.intBoxed");
            tryInvalid(outerJoinDef + "on sa.intPrimitive = sa.intBoxed");
            tryInvalid(outerJoinDef + "on sb.intPrimitive = sb.intBoxed");
            tryValid(outerJoinDef + "on sb.intPrimitive = sa.intBoxed");
        }

        private void tryInvalid(String eqlInvalidEQL)
        {
            try
            {
                epService.EPAdministrator.CreateEQL(eqlInvalidEQL);
                Assert.Fail();
            }
            catch (EPException)
            {
                // Expected exception
            }
        }

        private void tryValid(String eqlInvalidEQL)
        {
            epService.EPAdministrator.CreateEQL(eqlInvalidEQL);
        }

        private String getSyntaxExceptionEQL(String expression)
        {
            String exceptionText = null;
            try
            {
                epService.EPAdministrator.CreateEQL(expression);
                Assert.Fail();
            }
            catch (EPStatementSyntaxException ex)
            {
                exceptionText = ex.Message;
                log.Debug(".getSyntaxExceptionEQL eql=" + expression, ex);
                // Expected exception
            }

            return exceptionText;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}