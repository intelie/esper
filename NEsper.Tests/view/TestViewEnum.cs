using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view
{
	
	[TestFixture]
	public class TestViewEnum 
	{
		[Test]
		public virtual void  testForName()
		{
			ViewEnum enumValue = ViewEnum.ForName(ViewEnum.CORRELATION.Namespace, ViewEnum.CORRELATION.Name);
			Assert.AreEqual(enumValue, ViewEnum.CORRELATION);
			
			enumValue = ViewEnum.ForName(ViewEnum.CORRELATION.Namespace, "dummy");
			Assert.IsNull(enumValue);
			
			enumValue = ViewEnum.ForName("dummy", ViewEnum.CORRELATION.Name);
			Assert.IsNull(enumValue);
		}
	}
}
