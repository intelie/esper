using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
	
	[TestFixture]
	public class TestIndent 
	{
		[Test]
		public virtual void  testIndent()
		{
			Assert.AreEqual("", Indent.CreateIndent(0));
            Assert.AreEqual(" ", Indent.CreateIndent(1));
            Assert.AreEqual("  ", Indent.CreateIndent(2));
			
			try
			{
                Indent.CreateIndent(-1);
				Assert.Fail();
			}
			catch (ArgumentException)
			{
				// expected
			}
		}
	}
}
