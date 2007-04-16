using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.events;
using net.esper.filter;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.support.filter
{
    public class IndexTreeBuilderRunnable
    {
        protected internal static readonly Random random = new Random();

        private FilterCallbackSetNode topNode;
        private List<FilterSpec> testFilterSpecs;
        private List<EventBean> matchedEvents;
        private List<EventBean> unmatchedEvents;
        private bool isCompleted;
        private ManualResetEvent joinEvent;
        private Exception caughtException;

        /// <summary>
        /// Gets an exception that may have occurred during the course of this
        /// execution path.
        /// </summary>

        public Exception CaughtException
        {
            get { return caughtException; }
        }


        public IndexTreeBuilderRunnable(FilterCallbackSetNode topNode, List<FilterSpec> testFilterSpecs, List<EventBean> matchedEvents, List<EventBean> unmatchedEvents)
        {
            this.topNode = topNode;
            this.testFilterSpecs = testFilterSpecs;
            this.matchedEvents = matchedEvents;
            this.unmatchedEvents = unmatchedEvents;
            this.isCompleted = false;
            this.joinEvent = new ManualResetEvent(false);
        }

        public bool IsCompleted
        {
            get { return isCompleted; }
        }

        public void Join()
        {
            joinEvent.WaitOne();
        }

        public virtual void Run( Object userData )
        {
            try
            {
                long currentThreadId = Thread.CurrentThread.ManagedThreadId;

                // Choose one of filter specifications, randomly, then reserve to make sure no one else has the same
                FilterSpec filterSpec = null;
                EventBean unmatchedEvent = null;
                EventBean matchedEvent = null;

                int index = 0;
                do
                {
                    index = random.Next(testFilterSpecs.Count);
                    filterSpec = testFilterSpecs[index];
                    unmatchedEvent = unmatchedEvents[index];
                    matchedEvent = matchedEvents[index];
                }
                while (!ObjectReservationSingleton.Instance.reserve(filterSpec));

                // Add expression
                FilterValueSet filterValues = filterSpec.GetValueSet(null);
                FilterCallback filterCallback = new SupportFilterCallback();
                IndexTreeBuilder treeBuilder = new IndexTreeBuilder();
                IndexTreePath pathAddedTo = treeBuilder.Add(filterValues, filterCallback, topNode);

                // Fire a no-match
                IList<FilterCallback> matches = new List<FilterCallback>();
                topNode.MatchEvent(unmatchedEvent, matches);

                if (matches.Count != 0)
                {
                    log.Fatal(".run (" + currentThreadId + ") Got a match but expected no-match, matchCount=" + matches.Count + "  bean=" + unmatchedEvent + "  match=" + matches[0].GetHashCode());
                    Assert.IsFalse(true);
                }

                // Fire a match
                topNode.MatchEvent(matchedEvent, matches);

                if (matches.Count != 1)
                {
                    log.Fatal(".run (" + currentThreadId + ") Got zero or two or more match but expected a match, count=" + matches.Count + "  bean=" + matchedEvent);
                    Assert.IsFalse(true);
                }

                // Remove the same expression again
                treeBuilder.Remove(filterCallback, pathAddedTo, topNode);
                log.Debug(".run (" + Thread.CurrentThread.ManagedThreadId + ")" + " Completed");

                ObjectReservationSingleton.Instance.unreserve(filterSpec);
            }
            catch( Exception ex )
            {
                caughtException = ex;
                isCompleted = true;
            }
            finally
            {
                isCompleted = true;
                joinEvent.Set();
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
