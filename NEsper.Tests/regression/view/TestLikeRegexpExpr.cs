using System;

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
    public class TestLikeRegexpExpr
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public virtual void testLikeRegexStringAndNull()
        {
            String caseExpr =
                "select p00 like p01 as r1, " +
                " p00 like p01 escape \"!\" as r2," + 
                " p02 regexp p03 as r3 " +
                " from " + typeof(SupportBean_S0).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);

            sendS0Event("a", "b", "c", "d");
            assertReceived(new Object[][]{
                new Object[] { "r1", false },
                new Object[] { "r2", false },
                new Object[] { "r3", false }
            });

            sendS0Event(null, "b", null, "d");
            assertReceived(new Object[][] {
                new Object[] { "r1", null },
                new Object[] { "r2", null }, 
                new Object[] { "r3", null }
            });

            sendS0Event("a", null, "c", null);
            assertReceived(new Object[][] {
                new Object[] { "r1", null }, 
                new Object[] { "r2", null }, 
                new Object[] { "r3", null }
            });

            sendS0Event(null, null, null, null);
            assertReceived(new Object[][] { 
                new Object[] { "r1", null }, 
                new Object[] { "r2", null },
                new Object[] { "r3", null }
            });

            sendS0Event("abcdef", "%de_", "a", "[a-c]");
            assertReceived(new Object[][] { 
                new Object[] { "r1", true },
                new Object[] { "r2", true },
                new Object[] { "r3", true }
            });

            sendS0Event("abcdef", "b%de_", "d", "[a-c]");
            assertReceived(new Object[][] { 
                new Object[] { "r1", false }, 
                new Object[] { "r2", false }, 
                new Object[] { "r3", false }
            });

            sendS0Event("!adex", "!%de_", "", ".");
            assertReceived(new Object[][] {
                new Object[] { "r1", true }, 
                new Object[] { "r2", false },
                new Object[] { "r3", false }
            });

            sendS0Event("%dex", "!%de_", "a", ".");
            assertReceived(new Object[][] {
                new Object[] { "r1", false },
                new Object[] { "r2", true },
                new Object[] { "r3", true } 
            });
        }

        [Test]
        public virtual void testInvalidLikeRegEx()
        {
            tryInvalid("intPrimitive like 'a' escape null");
            tryInvalid("intPrimitive like boolPrimitive");
            tryInvalid("boolPrimitive like string");
            tryInvalid("string like string escape intPrimitive");

            tryInvalid("intPrimitive regexp doublePrimitve");
            tryInvalid("intPrimitive regexp boolPrimitive");
            tryInvalid("boolPrimitive regexp string");
            tryInvalid("string regexp intPrimitive");
        }

        [Test]
        public virtual void testLikeRegexNumericAndNull()
        {
            String caseExpr =
                "select " +
                " intBoxed like '%01%' as r1, " +
                " doubleBoxed regexp '[0-9][0-9].[0-9][0-9]' as r2 " +
                " from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);

            sendSupportBeanEvent(101, 1.1);
            assertReceived(
                new Object[][] {
                    new Object[] { "r1", true },
                    new Object[] { "r2", false }
                });

            sendSupportBeanEvent(102, 11d);
            assertReceived(
                new Object[][] { 
                    new Object[] { "r1", false }, 
                    new Object[] { "r2", true }
                });

            sendSupportBeanEvent(null, null);
            assertReceived(
                new Object[][] {
                    new Object[] { "r1", null },
                    new Object[] { "r2", null }
                });
        }

        private void tryInvalid(String expr)
        {
            try
            {
                String statement = "select " + expr + " from " + typeof(SupportBean).FullName;
                epService.EPAdministrator.CreateEQL(statement);
                Assert.Fail();
            }
            catch (EPException ex)
            {
                // expected
            }
        }

        private void assertReceived(Object[][] objects)
        {
            EventBean _event = testListener.assertOneGetNewAndReset();
            for (int i = 0; i < objects.Length; i++)
            {
                String key = (String)objects[i][0];
                Object result = objects[i][1];
                Assert.AreEqual(result, _event[key], "key=" + key + " result=" + result);
            }
        }

        private void sendS0Event(String p00, String p01, String p02, String p03)
        {
            SupportBean_S0 bean = new SupportBean_S0(-1, p00, p01, p02, p03);
            epService.EPRuntime.SendEvent(bean);
        }

        private void sendSupportBeanEvent(int? intBoxed, double? doubleBoxed)
        {
            SupportBean bean = new SupportBean();
            bean.intBoxed = intBoxed;
            bean.doubleBoxed = doubleBoxed;
            epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}