using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestRangeValueDouble 
	{
		private FilterSpecParamRangeValue[] _params = new FilterSpecParamRangeValue[5];
		
		[SetUp]
		public virtual void  setUp()
		{
			_params[0] = new RangeValueDouble(5.5);
			_params[1] = new RangeValueDouble(0);
			_params[2] = new RangeValueDouble(5.5);
		}
		
		[Test]
		public virtual void  testCheckType()
		{
			_params[0].CheckType(null);
		}
		
		[Test]
		public virtual void  testGetFilterValue()
		{
			Assert.AreEqual(5.5, _params[0].GetFilterValue(null));
		}
		
		[Test]
		public virtual void  testEquals()
		{
			Assert.IsFalse(_params[0].Equals(_params[1]));
			Assert.IsFalse(_params[1].Equals(_params[2]));
			Assert.IsTrue(_params[0].Equals(_params[2]));
		}
	}
}
