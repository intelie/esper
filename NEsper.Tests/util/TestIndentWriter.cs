using System;
using System.IO;
using System.Text;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
	
	[TestFixture]
	public class TestIndentWriter 
	{
		private static readonly String NEWLINE = System.Environment.NewLine;
		private StringWriter buf;
		private IndentWriter writer;
		
		[SetUp]
		public virtual void  setUp()
		{
			buf = new StringWriter();
			writer = new IndentWriter(buf, 0, 2);
		}
		
		[Test]
		public virtual void  testWrite()
		{
			writer.WriteLine("a");
			assertWritten("a");

            writer.IncrIndent();
			writer.WriteLine("a");
			assertWritten("  a");

            writer.IncrIndent();
			writer.WriteLine("a");
			assertWritten("    a");
			
			writer.DecrIndent();
			writer.WriteLine("a");
			assertWritten("  a");

            writer.DecrIndent();
			writer.WriteLine("a");
			assertWritten("a");

            writer.DecrIndent();
			writer.WriteLine("a");
			assertWritten("a");
		}
		
		[Test]
		public virtual void  testCtor()
		{
			try
			{
				new IndentWriter(buf, 0, - 1);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
			
			try
			{
				new IndentWriter(buf, - 1, 11);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}
		
		private void  assertWritten(String text)
		{
			Assert.AreEqual(text + NEWLINE, buf.ToString());
			StringBuilder buffer = buf.GetStringBuilder();
			buf.GetStringBuilder().Remove(0, buffer.Length - 0);
		}
	}
}
