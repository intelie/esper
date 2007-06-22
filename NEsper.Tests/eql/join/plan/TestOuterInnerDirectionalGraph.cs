using System;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{

	[TestFixture]
	public class TestOuterInnerDirectionalGraph
	{
		private OuterInnerDirectionalGraph graph;

		[SetUp]
		public virtual void  setUp()
		{
			graph = new OuterInnerDirectionalGraph(4);
		}

		[Test]
		public virtual void  testAdd()
		{
			graph.Add(0, 1);

			// testing duplicate add
			tryInvalidAdd(0, 1);

			// test adding out-of-bounds stream
			tryInvalidAdd(0, 4);
			tryInvalidAdd(4, 0);
			tryInvalidAdd(4, 4);
			tryInvalidAdd(2, - 1);
			tryInvalidAdd(- 1, 2);
		}

		[Test]
		public virtual void  testIsInner()
		{
			graph.Add(0, 1);
			Assert.IsTrue(graph.IsInner(0, 1));
			Assert.IsFalse(graph.IsInner(1, 0));
			Assert.IsFalse(graph.IsInner(2, 0));
			Assert.IsFalse(graph.IsInner(0, 2));

			graph.Add(1, 0);
			Assert.IsTrue(graph.IsInner(0, 1));
			Assert.IsTrue(graph.IsInner(1, 0));

			graph.Add(2, 0);
			Assert.IsTrue(graph.IsInner(2, 0));
			Assert.IsFalse(graph.IsInner(0, 2));

			tryInvalidIsInner(4, 0);
			tryInvalidIsInner(0, 4);
			tryInvalidIsInner(1, 1);
			tryInvalidIsInner(1, - 1);
			tryInvalidIsInner(- 1, 1);
		}

		[Test]
		public virtual void  testIsOuter()
		{
			graph.Add(0, 1);
			Assert.IsTrue(graph.IsOuter(0, 1));
			Assert.IsFalse(graph.IsOuter(1, 0));
			Assert.IsFalse(graph.IsOuter(0, 2));
			Assert.IsFalse(graph.IsOuter(2, 0));

			graph.Add(1, 0);
			Assert.IsTrue(graph.IsOuter(1, 0));
			Assert.IsTrue(graph.IsOuter(0, 1));

			graph.Add(2, 0);
			Assert.IsTrue(graph.IsOuter(2, 0));
			Assert.IsFalse(graph.IsOuter(0, 2));

			tryInvalidIsInner(4, 0);
			tryInvalidIsInner(0, 4);
			tryInvalidIsInner(1, 1);
			tryInvalidIsInner(1, - 1);
			tryInvalidIsInner(- 1, 1);
		}

		[Test]
		public virtual void  testGetInner()
		{
			tryInvalidGetInner(4);
			tryInvalidGetInner(- 1);

			Assert.IsNull(graph.GetInner(0));

			graph.Add(0, 1);
			Assert.IsNull(graph.GetInner(1));
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{1}, graph.GetInner(0));
			graph.Add(0, 3);
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{1, 3}, graph.GetInner(0));
			graph.Add(1, 0);
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{0}, graph.GetInner(1));
			graph.Add(1, 2);
			graph.Add(1, 3);
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{0, 2, 3}, graph.GetInner(1));
		}

		[Test]
		public virtual void  testGetOuter()
		{
			tryInvalidGetOuter(4);
			tryInvalidGetOuter(- 1);

			Assert.IsNull(graph.GetOuter(0));

			graph.Add(0, 1);
			Assert.IsNull(graph.GetOuter(0));
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{0}, graph.GetOuter(1));
			graph.Add(0, 3);
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{0}, graph.GetOuter(3));
			graph.Add(1, 0);
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{0}, graph.GetOuter(1));
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{1}, graph.GetOuter(0));
			graph.Add(1, 3);
			graph.Add(2, 3);
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{0, 1, 2}, graph.GetOuter(3));
		}

		private void  tryInvalidGetOuter(int stream)
		{
			try
			{
				graph.GetOuter(stream);
				Assert.Fail();
			}
			catch (System.Exception ex)
			{
				// expected
			}
		}

		private void  tryInvalidGetInner(int stream)
		{
			try
			{
				graph.GetInner(stream);
				Assert.Fail();
			}
			catch (System.Exception ex)
			{
				// expected
			}
		}

		private void  tryInvalidIsInner(int inner, int outer)
		{
			try
			{
				graph.IsInner(inner, outer);
				Assert.Fail();
			}
			catch (System.Exception ex)
			{
				// expected
			}
		}

		private void  tryInvalidIsOuter(int inner, int outer)
		{
			try
			{
				graph.IsOuter(outer, inner);
				Assert.Fail();
			}
			catch (System.Exception ex)
			{
				// expected
			}
		}

		private void  tryInvalidAdd(int inner, int outer)
		{
			try
			{
				graph.Add(inner, outer);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}
	}
}
