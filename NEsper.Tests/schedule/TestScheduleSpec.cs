using System;

using net.esper.compat;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.schedule
{
    [TestFixture]
    public class TestScheduleSpec
    {
        [Test]
        public virtual void testValidate()
        {
            // Test all units missing
            EDictionary<ScheduleUnit, ETreeSet<int>> unitValues = new EHashDictionary<ScheduleUnit, ETreeSet<int>>();
            assertInvalid(unitValues);

            // Test one unit missing
            unitValues = (new ScheduleSpec()).UnitValues;
            unitValues.Remove(ScheduleUnit.HOURS);
            assertInvalid(unitValues);

            // Test all units are wildcards
            unitValues = (new ScheduleSpec()).UnitValues;
            new ScheduleSpec(unitValues);

            // Test invalid value in month
            ETreeSet<int> values = new ETreeSet<int>();
            values.Add(0);
            unitValues.Put(ScheduleUnit.MONTHS, values);
            assertInvalid(unitValues);

            // Test valid value in month
            values = new ETreeSet<int>();
            values.Add(1);
            values.Add(5);
            unitValues.Put(ScheduleUnit.MONTHS, values);
            new ScheduleSpec(unitValues);
        }

        [Test]
        public virtual void testCompress()
        {
            EDictionary<ScheduleUnit, ETreeSet<int>> unitValues = new EHashDictionary<ScheduleUnit, ETreeSet<int>>();
            unitValues = (new ScheduleSpec()).UnitValues;

            // Populate Month with all valid values
            ETreeSet<int> monthValues = new ETreeSet<int>();
            for (int i = ScheduleUnit.MONTHS.Min(); i <= ScheduleUnit.MONTHS.Max(); i++)
            {
                monthValues.Add(i);
            }
            unitValues.Put(ScheduleUnit.MONTHS, monthValues);

            // Construct spec, test that month was replaced with wildcards
            ScheduleSpec spec = new ScheduleSpec(unitValues);
            Assert.IsTrue(spec.UnitValues.Fetch(ScheduleUnit.MONTHS) == null);
        }

        private void assertInvalid(EDictionary<ScheduleUnit, ETreeSet<int>> unitValues)
        {
            try
            {
                new ScheduleSpec(unitValues);
                Assert.IsFalse(true);
            }
            catch (ArgumentException ex)
            {
                log.Debug(".assertInvalid Expected exception, msg=" + ex.Message);
                // Expected exception
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
