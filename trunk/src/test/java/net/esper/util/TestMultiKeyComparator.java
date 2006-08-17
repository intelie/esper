package net.esper.util;

import java.util.Comparator;

import junit.framework.TestCase;
import net.esper.collection.MultiKey;
import net.esper.util.MultiKeyComparator;

public class TestMultiKeyComparator extends TestCase 
{
	Comparator<MultiKey> comparator;
	MultiKey firstValues;
	MultiKey secondValues;
	
	public void testCompareSingleProperty()
	{
		comparator = new MultiKeyComparator(new Boolean[] {false});

		firstValues = new MultiKey<Object>(new Double[] {3d});
		secondValues = new MultiKey<Object>(new Double[] {4d});
		assertTrue(comparator.compare(firstValues, secondValues) < 0);
		
		comparator = new MultiKeyComparator(new Boolean[] {true});

		assertTrue(comparator.compare(firstValues, secondValues) > 0);
		assertTrue(comparator.compare(firstValues, firstValues) == 0);
	}
	
	public void testCompareTwoProperties()
	{
		comparator = new MultiKeyComparator(new Boolean[] {false, false});

		firstValues = new MultiKey<Object>(new Object[] {3d, 3L});
		secondValues = new MultiKey<Object>(new Object[] {3d, 4L});
		assertTrue(comparator.compare(firstValues, secondValues) < 0);
		
		comparator = new MultiKeyComparator(new Boolean[] {false, true});
		
		assertTrue(comparator.compare(firstValues, secondValues) > 0);
		assertTrue(comparator.compare(firstValues, firstValues) == 0);
	}
	
	public void testInvalid()
	{
		comparator = new MultiKeyComparator(new Boolean[] {false, false});
	
		firstValues = new MultiKey<Object>(new Object[] {3d});
		secondValues = new MultiKey<Object>(new Object[] {3d, 4L});
		try
		{
			comparator.compare(firstValues, secondValues);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
		
		firstValues = new MultiKey<Object>(new Object[] {3d});
		secondValues = new MultiKey<Object>(new Object[] {3d});
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
