using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{

	[TestFixture]
    public class TestOutputProcessView 
    {
        private OutputProcessView outputProcessViewUpdate;
        private OutputProcessView outputProcessViewProcess;
        private SupportSchemaNeutralView childViewNoJoin;
        private SupportSchemaNeutralView childViewJoin;
        private SupportResultSetProcessor resultSetProcessor;

        [SetUp]
        public virtual void setUp()
        {
            resultSetProcessor = new SupportResultSetProcessor();
            outputProcessViewUpdate = new OutputProcessView(resultSetProcessor, 1, null, null);
            outputProcessViewProcess = new OutputProcessView(resultSetProcessor, 2, null, null);


            childViewNoJoin = new SupportSchemaNeutralView();
            outputProcessViewUpdate.AddView(childViewNoJoin);
            childViewJoin = new SupportSchemaNeutralView();
            outputProcessViewProcess.AddView(childViewJoin);
        }

        [Test]
        public virtual void testAttachesTo()
        {
            Assert.IsNull(outputProcessViewUpdate.AttachesTo(null));
        }

        [Test]
        public virtual void testGetEventType()
        {
            Assert.AreSame(resultSetProcessor.ResultEventType, outputProcessViewUpdate.EventType);
        }

        [Test]
        public virtual void testUpdate()
        {
            EventBean[] oldData = new EventBean[1];
            EventBean[] newData = new EventBean[1];
            oldData[0] = SupportEventBeanFactory.createObject(new SupportBean());
            newData[0] = SupportEventBeanFactory.createObject(new SupportBean());

            outputProcessViewUpdate.Update(newData, oldData);

            Assert.AreSame(newData[0], childViewNoJoin.LastNewData[0]);
            Assert.AreSame(oldData[0], childViewNoJoin.LastOldData[0]);
        }

        [Test]
        public virtual void testIterator()
        {
            try
            {
                outputProcessViewUpdate.GetEnumerator();
                Assert.Fail();
            }
            catch (System.NotSupportedException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testProcess()
        {
            EventBean[] oldData = new EventBean[1];
            EventBean[] newData = new EventBean[1];
            oldData[0] = SupportEventBeanFactory.createObject(new SupportBean());
            newData[0] = SupportEventBeanFactory.createObject(new SupportBean());

            outputProcessViewProcess.Process(makeEventSet(newData[0]), makeEventSet(oldData[0]));

            Assert.AreSame(newData[0], childViewJoin.LastNewData[0]);
            Assert.AreSame(oldData[0], childViewJoin.LastOldData[0]);
        }

        private ISet<MultiKey<EventBean>> makeEventSet(EventBean _event)
        {
            ISet<MultiKey<EventBean>> result = new EHashSet<MultiKey<EventBean>>();
            result.Add(new MultiKey<EventBean>(new EventBean[] { _event }));
            return result;
        }
    }
}
