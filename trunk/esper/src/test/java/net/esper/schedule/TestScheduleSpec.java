package net.esper.schedule;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.SortedSet;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;

public class TestScheduleSpec extends TestCase
{
    public void testValidate()
    {
        // Test all units missing
        Map<ScheduleUnit, SortedSet<Integer>> unitValues = new HashMap<ScheduleUnit, SortedSet<Integer>>();
        assertInvalid(unitValues);

        // Test one unit missing
        unitValues = (new ScheduleSpec()).getUnitValues();
        unitValues.remove(ScheduleUnit.HOURS);
        assertInvalid(unitValues);

        // Test all units are wildcards
        unitValues = (new ScheduleSpec()).getUnitValues();
        new ScheduleSpec(unitValues);

        // Test invalid value in month
        SortedSet<Integer> values = new TreeSet<Integer>();
        values.add(0);
        unitValues.put(ScheduleUnit.MONTHS, values);
        assertInvalid(unitValues);

        // Test valid value in month
        values = new TreeSet<Integer>();
        values.add(1);
        values.add(5);
        unitValues.put(ScheduleUnit.MONTHS, values);
        new ScheduleSpec(unitValues);
    }

    public void testCompress()
    {
        Map<ScheduleUnit, SortedSet<Integer>> unitValues = new HashMap<ScheduleUnit, SortedSet<Integer>>();
        unitValues = (new ScheduleSpec()).getUnitValues();

        // Populate Month with all valid values
        SortedSet<Integer> monthValues = new TreeSet<Integer>();
        for (int i = ScheduleUnit.MONTHS.min(); i <= ScheduleUnit.MONTHS.max(); i++)
        {
            monthValues.add(i);
        }
        unitValues.put(ScheduleUnit.MONTHS, monthValues);

        // Construct spec, test that month was replaced with wildcards
        ScheduleSpec spec = new ScheduleSpec(unitValues);
        assertTrue(spec.getUnitValues().get(ScheduleUnit.MONTHS) == null);
    }

    private void assertInvalid(Map<ScheduleUnit, SortedSet<Integer>> unitValues)
    {
        try
        {
            new ScheduleSpec(unitValues);
            assertFalse(true);
        }
        catch (IllegalArgumentException ex)
        {
            log.debug(".assertInvalid Expected exception, msg=" + ex.getMessage());
            // Expected exception
        }
    }

    private static final Log log = LogFactory.getLog(TestScheduleSpec.class);
}