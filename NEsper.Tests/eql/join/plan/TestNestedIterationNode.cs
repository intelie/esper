using System;

using net.esper.eql.join.exec;
using net.esper.eql.join.table;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	
	[TestFixture]
	public class TestNestedIterationNode 
	{
		[Test]
		public virtual void  testMakeExec()
		{
			try
			{
				new NestedIterationNode(new int[]{});
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}
	}
}
