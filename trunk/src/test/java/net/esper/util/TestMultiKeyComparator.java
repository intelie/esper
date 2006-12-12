package net.esper.util;

import java.util.Comparator;

import junit.framework.TestCase;
import net.esper.collection.MultiKeyUntyped;

public class TestMultiKeyComparator extends TestCase 
{
	Comparator<MultiKeyUntyped> comparator;
	MultiKeyUntyped firstValues;
	MultiKeyUntyped secondValues;
	
	public void testCompareSingleProperty()
	{
		comparator = new MultiKeyComparator(new Boolean[] {false});

		firstValues = new MultiKeyUntyped(new Object[] {3d});
		secondValues = new MultiKeyUntyped(new Object[] {4d});
		assertTrue(comparator.compare(firstValues, secondValues) < 0);
		
		comparator = new MultiKeyComparator(new Boolean[] {true});

		assertTrue(comparator.compare(firstValues, secondValues) > 0);
		assertTrue(comparator.compare(firstValues, firstValues) == 0);
	}
	
	public void testCompareTwoProperties()
	{
		comparator = new MultiKeyComparator(new Boolean[] {false, false});

		firstValues = new MultiKeyUntyped(new Object[] {3d, 3L});
		secondValues = new MultiKeyUntyped(new Object[] {3d, 4L});
		assertTrue(comparator.compare(firstValues, secondValues) < 0);
		
		comparator = new MultiKeyComparator(new Boolean[] {false, true});
		
		assertTrue(comparator.compare(firstValues, secondValues) > 0);
		assertTrue(comparator.compare(firstValues, firstValues) == 0);
	}
	
	public void testInvalid()
	{
		comparator = new MultiKeyComparator(new Boolean[] {false, false});
	
		firstValues = new MultiKeyUntyped(new Object[] {3d});
		secondValues = new MultiKeyUntyped(new Object[] {3d, 4L});
		try
		{
			comparator.compare(firstValues, secondValues);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
		
		firstValues = new MultiKeyUntyped(new Object[] {3d});
		secondValues = new MultiKeyUntyped(new Object[] {3d});
		try
		{
			comparator.compare(firstValues, secondValues);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}

	}
}
