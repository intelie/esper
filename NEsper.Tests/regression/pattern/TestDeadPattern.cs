using System;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.pattern
{
        [TestFixture]
    public class TestDeadPattern
    {
        private EPServiceProvider epService;

        [SetUp]
        public virtual void setUp()
        {
            Configuration config = new Configuration();
            config.AddEventTypeAlias("A", typeof(SupportBean_A).FullName);
            config.AddEventTypeAlias("B", typeof(SupportBean_B).FullName);
            config.AddEventTypeAlias("C", typeof(SupportBean_C).FullName);

            epService = EPServiceProviderManager.GetProvider("TestDeadPattern", config);
            epService.Initialize();
        }

        [Test]
        public virtual void testDeadPattern()
        {
            String pattern = "(A() -> B()) and not C()";
            // Adjust to 20000 to better test the limit
            for (int i = 0; i < 1000; i++)
            {
                epService.EPAdministrator.CreatePattern(pattern);
            }

            epService.EPRuntime.SendEvent(new SupportBean_C("C1"));

            long startTime = DateTimeHelper.CurrentTimeMillis;
            epService.EPRuntime.SendEvent(new SupportBean_A("A1"));
            long delta = DateTimeHelper.CurrentTimeMillis - startTime;

            log.Info(".testDeadPattern delta=" + delta);
            Assert.IsTrue(delta < 20, "performance: delta=" + delta);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
