using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.type
{
	[TestFixture]
	public class TestStringValue 
	{
		[Test]
		public virtual void  testParse()
		{
			Assert.AreEqual("a", StringValue.parseString("\"a\""));
			Assert.AreEqual("", StringValue.parseString("\"\""));
			Assert.AreEqual("", StringValue.parseString("''"));
			Assert.AreEqual("b", StringValue.parseString("'b'"));
		}
		
		[Test]
		public virtual void  testInvalid()
		{
			tryInvalid("\"");
			tryInvalid("'");
		}
		
		private void  tryInvalid(String invalidString)
		{
			try
			{
				StringValue.parseString(invalidString);
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
		}
	}
}
