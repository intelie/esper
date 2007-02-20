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
			Assert.AreEqual("a", StringValue.ParseString("\"a\""));
			Assert.AreEqual("", StringValue.ParseString("\"\""));
			Assert.AreEqual("", StringValue.ParseString("''"));
			Assert.AreEqual("b", StringValue.ParseString("'b'"));
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
				StringValue.ParseString(invalidString);
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
		}
	}
}
