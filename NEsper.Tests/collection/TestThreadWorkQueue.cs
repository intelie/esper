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
        private ThreadWorkQueue queue;

        [SetUp]
        public virtual void setUp()
        {
            queue = new ThreadWorkQueue();
        }

        [Test]
        public virtual void testFlow()
        {
            queue.Add("a");
            queue.Add("b");
            compare(new String[] { "a", "b" });

            queue.AddFront("0");
            queue.Add("c");
            compare(new String[] { "0", "c" });

            queue.Add("d");
            queue.AddFront("1");
            compare(new String[] { "1", "d" });

            queue.AddFront("e");
            queue.AddFront("2");
            compare(new String[] { "2", "e" });

            queue.Add("a");
            queue.AddFront("0");
            queue.Add("b");
            queue.AddFront("1");
            queue.Add("c");
            queue.AddFront("2");
            compare(new String[] { "2", "1", "0", "a", "b", "c" });
        }

        private void compare(String[] results)
        {
            List<String> result = new List<String>();

            String entry;
            while ((entry = ((String)queue.Next())) != null)
            {
                result.Add(entry);
            }

            ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) result,
                (ICollection<string>) results);
        }
    }
}

