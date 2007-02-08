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
			Assert.AreEqual("", Indent.indent(0));
			Assert.AreEqual(" ", Indent.indent(1));
			Assert.AreEqual("  ", Indent.indent(2));
			
			try
			{
				Indent.indent(- 1);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}
	}
}
