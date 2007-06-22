///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.schedule
{
	[TestFixture]
	public class TestScheduleSpec
	{
	    [Test]
	    public void TestValidate()
	    {
	        // Test all units missing
	        EDictionary<ScheduleUnit, TreeSet<int>> unitValues = new HashDictionary<ScheduleUnit, TreeSet<int>>();
	        AssertInvalid(unitValues);

	        // Test one unit missing
	        unitValues = (new ScheduleSpec()).UnitValues;
	        unitValues.Remove(ScheduleUnit.HOURS);
	        AssertInvalid(unitValues);

	        // Test all units are wildcards
	        unitValues = (new ScheduleSpec()).UnitValues;
	        new ScheduleSpec(unitValues);

	        // Test invalid value in month
	        TreeSet<int> values = new TreeSet<int>();
	        values.Add(0);
	        unitValues.Put(ScheduleUnit.MONTHS, values);
	        AssertInvalid(unitValues);

	        // Test valid value in month
	        values = new TreeSet<int>();
	        values.Add(1);
	        values.Add(5);
	        unitValues.Put(ScheduleUnit.MONTHS, values);
	        new ScheduleSpec(unitValues);
	    }

	    [Test]
	    public void TestCompress()
	    {
	        EDictionary<ScheduleUnit, TreeSet<int>> unitValues = new HashDictionary<ScheduleUnit, TreeSet<int>>();
	        unitValues = (new ScheduleSpec()).UnitValues;

	        // Populate Month with all valid values
	        TreeSet<int> monthValues = new TreeSet<int>();
	        for (int i = ScheduleUnit.MONTHS.Min(); i <= ScheduleUnit.MONTHS.Max(); i++)
	        {
	            monthValues.Add(i);
	        }
	        unitValues.Put(ScheduleUnit.MONTHS, monthValues);

	        // Construct spec, test that month was replaced with wildcards
	        ScheduleSpec spec = new ScheduleSpec(unitValues);
	        Assert.IsTrue(spec.UnitValues.Fetch(ScheduleUnit.MONTHS) == null);
	    }

	    private void AssertInvalid(EDictionary<ScheduleUnit, TreeSet<int>> unitValues)
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

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
