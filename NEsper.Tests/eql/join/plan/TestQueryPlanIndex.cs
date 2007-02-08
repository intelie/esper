using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	[TestFixture]
	public class TestQueryPlanIndex 
	{
		private QueryPlanIndex indexSpec;
		
		[SetUp]
		public virtual void  setUp()
		{
			String[][] indexes = new String[][]{new String[]{"p01", "p02"}, new String[]{"p21"}, new String[0]};
			
			indexSpec = new QueryPlanIndex(indexes);
		}
		
		[Test]
		public virtual void  testInvalidUse()
		{
			try
			{
				new QueryPlanIndex(null);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// Expected
			}
		}
		
		[Test]
		public virtual void  testGetIndexNum()
		{
			Assert.AreEqual(0, indexSpec.GetIndexNum(new String[]{"p01", "p02"}));
			Assert.AreEqual(1, indexSpec.GetIndexNum(new String[]{"p21"}));
			Assert.AreEqual(2, indexSpec.GetIndexNum(new String[0]));
			
			Assert.AreEqual(- 1, indexSpec.GetIndexNum(new String[]{"YY", "XX"}));
		}
		
		[Test]
		public virtual void  testAddIndex()
		{
			int indexNum = indexSpec.AddIndex(new String[]{"a", "b"});
			Assert.AreEqual(3, indexNum);
			Assert.AreEqual(3, indexSpec.GetIndexNum(new String[]{"a", "b"}));
		}
	}
}
