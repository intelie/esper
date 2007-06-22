using System;

using net.esper.client;
using net.esper.events;
using net.esper.regression.pattern;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.pattern
{

    [TestFixture]
    public class TestPatternStartLoop
    {
        private EPServiceProvider epService;
        private EPStatement patternStmt;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        /// <summary> Starting this statement fires an event and the listener Starts a new statement (same expression) again,
        /// causing a loop. This listener limits to 10 - this is a smoke test.
        /// </summary>
        [Test]
        public virtual void testStartFireLoop()
        {
            PatternUpdateListener listener = new PatternUpdateListener(this);

            String patternExpr = "not " + typeof(SupportBean).FullName;
            patternStmt = epService.EPAdministrator.CreatePattern(patternExpr);
            patternStmt.AddListener(listener);
            patternStmt.Stop();
            patternStmt.Start();
        }

        internal class PatternUpdateListener : UpdateListener
        {
            public PatternUpdateListener(TestPatternStartLoop enclosingInstance)
            {
                this.enclosingInstance = enclosingInstance;
            }
            private TestPatternStartLoop enclosingInstance;

            virtual public int Count
            {
                get { return count; }
            }

            public TestPatternStartLoop Enclosing_Instance
            {
                get { return enclosingInstance; }
            }

            private int count = 0;

            public virtual void Update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                TestPatternStartLoop.log.Warn(".Update");

                if (count < 10)
                {
                    count++;
                    String patternExpr = "not " + typeof(SupportBean).FullName;
                    Enclosing_Instance.patternStmt = Enclosing_Instance.epService.EPAdministrator.CreatePattern(patternExpr);
                    Enclosing_Instance.patternStmt.AddListener(this);
                    Enclosing_Instance.patternStmt.Stop();
                    Enclosing_Instance.patternStmt.Start();
                }
            }
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
