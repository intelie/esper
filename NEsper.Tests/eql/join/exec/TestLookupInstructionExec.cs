using System;
using System.Collections.Generic;

using net.esper.support.eql.join;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.exec
{

    [TestFixture]
    public class TestLookupInstructionExec
    {
        private LookupInstructionExec exec;
        private SupportRepositoryImpl rep;
        private TableLookupStrategy[] lookupStrategies;

        [SetUp]
        public virtual void setUp()
        {
            lookupStrategies = new TableLookupStrategy[4];
            for (int i = 0; i < lookupStrategies.Length; i++)
            {
                lookupStrategies[i] = new SupportTableLookupStrategy(1);
            }

            exec = new LookupInstructionExec(0, "test", new int[] { 1, 2, 3, 4 }, lookupStrategies, new bool[] { false, true, true, false, false });

            rep = new SupportRepositoryImpl();
        }

        [Test]
        public void testProcessAllResults()
        {
            bool result = exec.Process(rep);

            Assert.IsTrue(result);
            Assert.AreEqual(4, rep.getLookupResultsList().Count);
            ArrayAssertionUtil.AreEqualExactOrder(
                (ICollection<int>) new int[] { 1, 2, 3, 4 },
                (ICollection<int>) rep.getResultStreamList());
        }

        [Test]
        public void testProcessNoRequiredResults()
        {
            lookupStrategies[1] = new SupportTableLookupStrategy(0);

            bool result = exec.Process(rep);

            Assert.IsFalse(result);
            Assert.AreEqual(0, rep.getLookupResultsList().Count);
        }

        [Test]
        public void testProcessPartialOptionalResults()
        {
            lookupStrategies[3] = new SupportTableLookupStrategy(0);

            bool result = exec.Process(rep);

            Assert.IsTrue(result);
            Assert.AreEqual(3, rep.getLookupResultsList().Count);
        }
    }
}
