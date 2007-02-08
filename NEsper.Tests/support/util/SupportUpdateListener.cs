using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.support.util
{
    public class SupportUpdateListener : UpdateListener
    {
        virtual public EventBean[] LastNewData
        {
            get
            {
                return lastNewData;
            }

            set
            {
                this.lastNewData = value;
            }

        }

		virtual public EventBean[] LastOldData
        {
            get
            {
                return lastOldData;
            }

            set
            {
                this.lastOldData = value;
            }

        }
        virtual public bool Invoked
        {
            get
            {
                return isInvoked;
            }

        }

		virtual public EventBean[] getAndResetLastNewData()
		{
			EventBean[] lastNew = lastNewData;
			reset();
			return lastNew;
		}
		
		virtual public bool getAndClearIsInvoked()
		{
			bool invoked = isInvoked;
			isInvoked = false;
			return invoked;
		}

        private readonly IList<EventBean[]> newDataList;
        private readonly IList<EventBean[]> oldDataList;
        private EventBean[] lastNewData;
        private EventBean[] lastOldData;
        private bool isInvoked;

        public SupportUpdateListener()
        {
            newDataList = new List<EventBean[]>();
            oldDataList = new List<EventBean[]>();
        }

        public virtual void Update(EventBean[] newData, EventBean[] oldData)
        {
            this.oldDataList.Add(oldData);
            this.newDataList.Add(newData);

            this.lastNewData = newData;
            this.lastOldData = oldData;

            isInvoked = true;
        }

        public virtual void reset()
        {
            this.oldDataList.Clear();
            this.newDataList.Clear();
            this.lastNewData = null;
            this.lastOldData = null;
            isInvoked = false;
        }

        public virtual EventBean assertOneGetNewAndReset()
        {
            Assert.IsTrue(isInvoked);

            Assert.AreEqual(1, newDataList.Count);
            Assert.AreEqual(1, oldDataList.Count);

            Assert.AreEqual(1, lastNewData.Length);
            Assert.IsNull(lastOldData);

            EventBean lastNew = lastNewData[0];
            reset();
            return lastNew;
        }

        public virtual EventBean assertOneGetOldAndReset()
        {
            Assert.IsTrue(isInvoked);

            Assert.AreEqual(1, newDataList.Count);
            Assert.AreEqual(1, oldDataList.Count);

            Assert.AreEqual(1, lastOldData.Length);
            Assert.IsNull(lastNewData);

            EventBean lastNew = lastOldData[0];
            reset();
            return lastNew;
        }

        public IList<EventBean[]> NewDataList
        {
            get { return newDataList; }
        }

        public IList<EventBean[]> OldDataList
        {
            get { return oldDataList; }
        }
    }
}
