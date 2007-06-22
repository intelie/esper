using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.join.plan
{

	[TestFixture]
	public class TestQueryGraph
	{
		private QueryGraph queryGraph;

		[SetUp]
		public virtual void  setUp()
		{
			queryGraph = new QueryGraph(3);
		}

		[Test]
		public virtual void  testFillEquivalency()
		{
			// test with just 3 streams
			queryGraph.Add(0, "p00", 1, "p10");
			queryGraph.Add(1, "p10", 2, "p20");

			Assert.IsFalse(queryGraph.IsNavigable(0, 2));
			Assert.IsNull(queryGraph.GetKeyProperties(0, 2));
			Assert.IsNull(queryGraph.GetIndexProperties(0, 2));

			QueryGraph.FillEquivalentNav(queryGraph);

			Assert.IsTrue(queryGraph.IsNavigable(0, 2));
			String[] expectedOne = new String[]{"p00"};
			String[] expectedTwo = new String[]{"p20"};
			Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<string>) expectedOne,
                (ICollection<string>) queryGraph.GetKeyProperties(0, 2)
                ));
			Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<string>) expectedTwo,
                (ICollection<string>) queryGraph.GetIndexProperties(0, 2)
                ));

			// test with 5 streams, connect all streams to all streams
			queryGraph = new QueryGraph(5);
			queryGraph.Add(0, "p0", 1, "p1");
			queryGraph.Add(3, "p3", 4, "p4");
			queryGraph.Add(2, "p2", 3, "p3");
			queryGraph.Add(1, "p1", 2, "p2");

			QueryGraph.FillEquivalentNav(queryGraph);

			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					if (i == j)
					{
						continue;
					}
					Assert.IsTrue(queryGraph.IsNavigable(i, j), "Not navigable: i=" + i + " j=" + j);
				}
			}
		}

		[Test]
		public virtual void  testAdd()
		{
			// Try invalid add
			try
			{
				queryGraph.Add(1, null, 2, null);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}

			// Try invalid add
			try
			{
				queryGraph.Add(1, "a", 1, "b");
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}

			// Try :        s1.p11 = s2.p21  and  s2.p22 = s3.p31
			Assert.IsTrue(queryGraph.Add(1, "p11", 2, "p21"));
			Assert.IsTrue(queryGraph.Add(2, "p22", 3, "p31"));
			Assert.IsFalse(queryGraph.Add(2, "p22", 3, "p31"));
			log.Debug(queryGraph.ToString());
		}

		[Test]
		public virtual void  testIsNavigable()
		{
			Assert.IsFalse(queryGraph.IsNavigable(0, 1));
			Assert.IsFalse(queryGraph.IsNavigable(0, 2));
			Assert.IsFalse(queryGraph.IsNavigable(1, 2));

			queryGraph.Add(0, "p1", 1, "p2");
			Assert.IsTrue(queryGraph.IsNavigable(0, 1));
			Assert.IsFalse(queryGraph.IsNavigable(0, 2));
			Assert.IsFalse(queryGraph.IsNavigable(1, 2));

			queryGraph.Add(2, "p1", 1, "p2");
			Assert.IsTrue(queryGraph.IsNavigable(0, 1));
			Assert.IsFalse(queryGraph.IsNavigable(0, 2));
			Assert.IsTrue(queryGraph.IsNavigable(1, 2));

			queryGraph.Add(2, "p1", 0, "p2");
			Assert.IsTrue(queryGraph.IsNavigable(0, 1));
			Assert.IsTrue(queryGraph.IsNavigable(0, 2));
			Assert.IsTrue(queryGraph.IsNavigable(1, 2));
		}

		[Test]
		public virtual void  testGetNavigableStreams()
		{
			queryGraph = new QueryGraph(5);
			queryGraph.Add(3, "p3", 4, "p4");
			queryGraph.Add(2, "p2", 3, "p3");
			queryGraph.Add(1, "p1", 2, "p2");

			Assert.AreEqual(0, queryGraph.GetNavigableStreams(0).Count);
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{2}, queryGraph.GetNavigableStreams(1));
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{1, 3}, queryGraph.GetNavigableStreams(2));
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{2, 4}, queryGraph.GetNavigableStreams(3));
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{3}, queryGraph.GetNavigableStreams(4));
		}

		[Test]
		public virtual void  testGetProperties()
		{
			// s1.p11 = s0.p01 and s0.p02 = s1.p12
			queryGraph.Add(1, "p11", 0, "p01");
			queryGraph.Add(0, "p02", 1, "p12");
			log.Debug(queryGraph.ToString());

			String[] expectedOne = new String[]{"p11", "p12"};
			String[] expectedTwo = new String[]{"p01", "p02"};
			Assert.IsTrue(CollectionHelper.AreEqual(
                expectedTwo, queryGraph.GetIndexProperties(1, 0)));
            Assert.IsTrue(CollectionHelper.AreEqual(
                expectedOne, queryGraph.GetIndexProperties(0, 1)));
            Assert.IsTrue(CollectionHelper.AreEqual(
                expectedOne, queryGraph.GetKeyProperties(1, 0)));
            Assert.IsTrue(CollectionHelper.AreEqual(
                expectedTwo, queryGraph.GetKeyProperties(0, 1)));
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
