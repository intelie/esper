using System;

using net.esper.regression.support;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{
    [TestFixture]
    public class TestSuperAndInterfaces
    {
        private static String INTERFACE_A;
        private static String INTERFACE_B;
        private static String INTERFACE_C;
        private static String INTERFACE_D;
        private static String INTERFACE_BASE_D;
        private static String INTERFACE_BASE_D_BASE;
        private static String INTERFACE_BASE_AB;
        private static String SUPER_G;
        private static String SUPER_G_IMPL;
        private static String OVERRIDE_BASE;
        private static String OVERRIDE_ONE;
        private static String OVERRIDE_ONEA;
        private static String OVERRIDE_ONEB;

        [Test]
        public virtual void testInterfacedEvents()
        {
            EventCollection events = EventCollectionFactory.GetSetFiveInterfaces();
            CaseList testCaseList = new CaseList();
            EventExpressionCase testCase = null;

            testCase = new EventExpressionCase("c=" + INTERFACE_C);
            testCase.Add("e1", "c", events.GetEvent("e1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("baseab=" + INTERFACE_BASE_AB);
            testCase.Add("e2", "baseab", events.GetEvent("e2"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_A);
            testCase.Add("e2", "a", events.GetEvent("e2"));
            testCase.Add("e3", "a", events.GetEvent("e3"));
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCase.Add("e13", "a", events.GetEvent("e13"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_B);
            testCase.Add("e2", "a", events.GetEvent("e2"));
            testCase.Add("e4", "a", events.GetEvent("e4"));
            testCase.Add("e6", "a", events.GetEvent("e6"));
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_B + "(b='B1')");
            testCase.Add("e2", "a", events.GetEvent("e2"));
            testCase.Add("e4", "a", events.GetEvent("e4"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_A + "(a='A3')");
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_C + "(c='C2')");
            testCase.Add("e6", "a", events.GetEvent("e6"));
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_C + "(c='C1')");
            testCase.Add("e1", "a", events.GetEvent("e1"));
            testCase.Add("e2", "a", events.GetEvent("e2"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_D + "(d='D1')");
            testCase.Add("e5", "a", events.GetEvent("e5"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_BASE_D + "(baseD='BaseD')");
            testCase.Add("e5", "a", events.GetEvent("e5"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_BASE_D_BASE + "(baseDBase='BaseDBase')");
            testCase.Add("e5", "a", events.GetEvent("e5"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_D + "(d='D1', baseD='BaseD', baseDBase='BaseDBase')");
            testCase.Add("e5", "a", events.GetEvent("e5"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + INTERFACE_BASE_D + "(baseD='BaseD', baseDBase='BaseDBase')");
            testCase.Add("e5", "a", events.GetEvent("e5"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + SUPER_G);
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCase.Add("e13", "a", events.GetEvent("e13"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + SUPER_G + "(g='G1')");
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + SUPER_G + "(baseAB='BaseAB5')");
            testCase.Add("e13", "a", events.GetEvent("e13"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + SUPER_G + "(baseAB='BaseAB4', g='G1', a='A3')");
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + SUPER_G_IMPL + "(baseAB='BaseAB4', g='G1', a='A3', b='B4', c='C2')");
            testCase.Add("e12", "a", events.GetEvent("e12"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE);
            testCase.Add("e8", "a", events.GetEvent("e8"));
            testCase.Add("e9", "a", events.GetEvent("e9"));
            testCase.Add("e10", "a", events.GetEvent("e10"));
            testCase.Add("e11", "a", events.GetEvent("e11"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_ONE);
            testCase.Add("e8", "a", events.GetEvent("e8"));
            testCase.Add("e9", "a", events.GetEvent("e9"));
            testCase.Add("e10", "a", events.GetEvent("e10"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_ONEA);
            testCase.Add("e8", "a", events.GetEvent("e8"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_ONEB);
            testCase.Add("e9", "a", events.GetEvent("e9"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='OB1')");
            testCase.Add("e9", "a", events.GetEvent("e9"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='O3')");
            testCase.Add("e10", "a", events.GetEvent("e10"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='OBase')");
            testCase.Add("e11", "a", events.GetEvent("e11"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='O2')");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_ONE + "(val='OA1')");
            testCase.Add("e8", "a", events.GetEvent("e8"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every a=" + OVERRIDE_ONE + "(val='O3')");
            testCase.Add("e10", "a", events.GetEvent("e10"));
            testCaseList.AddTest(testCase);

            PatternTestHarness util = new PatternTestHarness(events, testCaseList);
            util.RunTest();
        }

        static TestSuperAndInterfaces()
        {
            INTERFACE_A = typeof(ISupportA).FullName;
            INTERFACE_B = typeof(ISupportB).FullName;
            INTERFACE_C = typeof(ISupportC).FullName;
            INTERFACE_D = typeof(ISupportD).FullName;
            INTERFACE_BASE_D = typeof(ISupportBaseD).FullName;
            INTERFACE_BASE_D_BASE = typeof(ISupportBaseDBase).FullName;
            INTERFACE_BASE_AB = typeof(ISupportBaseAB).FullName;
            SUPER_G = typeof(ISupportAImplSuperG).FullName;
            SUPER_G_IMPL = typeof(ISupportAImplSuperGImplPlus).FullName;
            OVERRIDE_BASE = typeof(SupportOverrideBase).FullName;
            OVERRIDE_ONE = typeof(SupportOverrideOne).FullName;
            OVERRIDE_ONEA = typeof(SupportOverrideOneA).FullName;
            OVERRIDE_ONEB = typeof(SupportOverrideOneB).FullName;
        }
    }
}