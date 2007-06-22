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

using net.esper.collection;

namespace net.esper.util
{
	[TestFixture]
	public class TestMultiKeyComparator
	{
		private IComparer<MultiKeyUntyped> comparator;
		private MultiKeyUntyped firstValues;
		private MultiKeyUntyped secondValues;

		[Test]
		public void TestCompareSingleProperty()
		{
			comparator = new MultiKeyComparator(new Boolean[] {false});

			firstValues = new MultiKeyUntyped(new Object[] {3d});
			secondValues = new MultiKeyUntyped(new Object[] {4d});
			Assert.IsTrue(comparator.Compare(firstValues, secondValues) < 0);

			comparator = new MultiKeyComparator(new bool[] {true});

			Assert.IsTrue(comparator.Compare(firstValues, secondValues) > 0);
			Assert.IsTrue(comparator.Compare(firstValues, firstValues) == 0);
		}

		[Test]
		public void TestCompareTwoProperties()
		{
			comparator = new MultiKeyComparator(new bool[] {false, false});

			firstValues = new MultiKeyUntyped(new Object[] {3d, 3L});
			secondValues = new MultiKeyUntyped(new Object[] {3d, 4L});
			Assert.IsTrue(comparator.Compare(firstValues, secondValues) < 0);

			comparator = new MultiKeyComparator(new bool[] {false, true});

			Assert.IsTrue(comparator.Compare(firstValues, secondValues) > 0);
			Assert.IsTrue(comparator.Compare(firstValues, firstValues) == 0);
		}

		[Test]
		public void TestInvalid()
		{
			comparator = new MultiKeyComparator(new bool[] {false, false});

			firstValues = new MultiKeyUntyped(new Object[] {3d});
			secondValues = new MultiKeyUntyped(new Object[] {3d, 4L});
			try
			{
				comparator.Compare(firstValues, secondValues);
				Assert.Fail();
			}
			catch(ArgumentException e)
			{
				// Expected
			}

			firstValues = new MultiKeyUntyped(new Object[] {3d});
			secondValues = new MultiKeyUntyped(new Object[] {3d});
			try
			{
				comparator.Compare(firstValues, secondValues);
				Assert.Fail();
			}
			catch(ArgumentException e)
			{
				// Expected
			}

		}
	}
} // End of namespace
