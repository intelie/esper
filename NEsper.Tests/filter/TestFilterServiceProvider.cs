using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestFilterServiceProvider 
	{
		[Test]
		public virtual void  testGetService()
		{
			FilterService serviceOne = FilterServiceProvider.NewService();
			FilterService serviceTwo = FilterServiceProvider.NewService();
			
			Assert.IsTrue(serviceOne != null);
			Assert.IsTrue(serviceOne != serviceTwo);
		}
	}
}
