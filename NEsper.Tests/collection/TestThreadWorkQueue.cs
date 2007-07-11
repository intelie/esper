using System;
using System.Collections.Generic;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
    [TestFixture]
    public class TestThreadWorkQueue
    {
        [SetUp]
        public virtual void setUp()
        {
        }

        [Test]
        public void testFlow()
        {
            ThreadWorkQueue.Add("a");
            ThreadWorkQueue.Add("b");
            compare(new String[] { "a", "b" });

            ThreadWorkQueue.AddFront("0");
            ThreadWorkQueue.Add("c");
            compare(new String[] { "0", "c" });

            ThreadWorkQueue.Add("d");
            ThreadWorkQueue.AddFront("1");
            compare(new String[] { "1", "d" });

            ThreadWorkQueue.AddFront("e");
            ThreadWorkQueue.AddFront("2");
            compare(new String[] { "2", "e" });

            ThreadWorkQueue.Add("a");
            ThreadWorkQueue.AddFront("0");
            ThreadWorkQueue.Add("b");
            ThreadWorkQueue.AddFront("1");
            ThreadWorkQueue.Add("c");
            ThreadWorkQueue.AddFront("2");
            compare(new String[] { "2", "1", "0", "a", "b", "c" });
        }

        private void compare(String[] results)
        {
            List<String> result = new List<String>();

            String entry;
            while ((entry = ((String)ThreadWorkQueue.Next())) != null)
            {
                result.Add(entry);
            }

            ArrayAssertionUtil.AreEqualExactOrder(
                (ICollection<string>) result,
                (ICollection<string>) results);
        }
    }
}

