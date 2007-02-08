using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.collection
{
    [TestFixture]
    public class TestTimeWindow
    {
        private TimeWindow window = new TimeWindow();

        [Test]
        public virtual void testAdd()
        {
            EventBean[] beans = new EventBean[6];
            for (int i = 0; i < beans.Length; i++)
            {
                beans[i] = createBean();
            }

            Assert.IsTrue(window.OldestTimestamp == null);
            Assert.IsTrue(window.IsEmpty);

            window.Add(19, beans[0]);
            Assert.IsTrue(window.OldestTimestamp == 19L);
            Assert.IsFalse(window.IsEmpty);
            window.Add(19, beans[1]);
            Assert.IsTrue(window.OldestTimestamp == 19L);
            window.Add(20, beans[2]);
            Assert.IsTrue(window.OldestTimestamp == 19L);
            window.Add(20, beans[3]);
            window.Add(21, beans[4]);
            window.Add(22, beans[5]);
            Assert.IsTrue(window.OldestTimestamp == 19L);

            List<EventBean> beanList = window.ExpireEvents(19);
            Assert.IsTrue(beanList == null);

            beanList = window.ExpireEvents(20);
            Assert.IsTrue(beanList.Count == 2);
            Assert.IsTrue(beanList[0] == beans[0]);
            Assert.IsTrue(beanList[1] == beans[1]);

            beanList = window.ExpireEvents(21);
            Assert.IsTrue(beanList.Count == 2);
            Assert.IsTrue(beanList[0] == beans[2]);
            Assert.IsTrue(beanList[1] == beans[3]);
            Assert.IsFalse(window.IsEmpty);
            Assert.IsTrue(window.OldestTimestamp == 21);

            beanList = window.ExpireEvents(22);
            Assert.IsTrue(beanList.Count == 1);
            Assert.IsTrue(beanList[0] == beans[4]);
            Assert.IsFalse(window.Count == 0);
            Assert.IsTrue(window.OldestTimestamp == 22);

            beanList = window.ExpireEvents(23);
            Assert.IsTrue(beanList.Count == 1);
            Assert.IsTrue(beanList[0] == beans[5]);
            Assert.IsTrue(window.Count == 0);
            Assert.IsTrue(window.OldestTimestamp == null);

            beanList = window.ExpireEvents(23);
            Assert.IsTrue(beanList == null);
            Assert.IsTrue(window.Count == 0);
            Assert.IsTrue(window.OldestTimestamp == null);
        }

        [Test]
        public virtual void testTimeWindowPerformance()
        {
            log.Info(".testTimeWindowPerformance Starting");

            TimeWindow window = new TimeWindow();

            // 1E7 yields for implementations...on 2.8GHz JDK 1.5
            // about 7.5 seconds for a LinkedList-backed
            // about 20 seconds for a LinkedHashMap-backed
            // about 30 seconds for a TreeMap-backed-backed
            for (int i = 0; i < 10; i++)
            {
                window.Add(i, SupportEventBeanFactory.createObject("a"));
                window.ExpireEvents(i - 100);
            }

            log.Info(".testTimeWindowPerformance Done");
        }

        private EventBean createBean()
        {
            return SupportEventBeanFactory.createObject(new SupportBean());
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}