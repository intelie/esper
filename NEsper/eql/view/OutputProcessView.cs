using System;
using System.Collections.Generic;

using net.esper.eql;
using net.esper.eql.core;
using net.esper.eql.join;
using net.esper.eql.spec;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.view;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.view
{
    /// <summary> A view that prepares output events, batching incoming 
    /// events and invoking the result set processor as necessary.
    /// </summary>

    public class OutputProcessView : ViewSupport, JoinSetIndicator
    {
        private readonly ResultSetProcessor resultSetProcessor;
        private readonly Boolean outputLastOnly;
        private readonly OutputCondition outputCondition;
        private List<EventBean> newEventsList = new List<EventBean>();
        private List<EventBean> oldEventsList = new List<EventBean>();
        private ISet<MultiKey<EventBean>> newEventsSet = new LinkedHashSet<MultiKey<EventBean>>();
        private ISet<MultiKey<EventBean>> oldEventsSet = new LinkedHashSet<MultiKey<EventBean>>();

        private static readonly Log log = LogFactory.GetLog(typeof(OutputProcessView));

        /// <summary>Ctor.</summary>
        /// <param name="resultSetProcessor">is processing the result set for publishing it out</param>
        /// <param name="streamCount">is the number of streams, indicates whether or not this view participates in a join</param>
        /// <param name="outputLimitSpec">is the specification for limiting output (the output condition and the result set processor)</param>
        /// <param name="viewContext">is the services the output condition may depend on</param>

        public OutputProcessView(
            ResultSetProcessor resultSetProcessor,
            int streamCount,
            OutputLimitSpec outputLimitSpec,
            ViewServiceContext viewContext)
        {
            log.Debug("creating view");

            if (streamCount < 1)
            {
                throw new ArgumentException("Output process view is part of at least 1 stream");
            }

            OutputCallback outputCallback = getCallbackToLocal(streamCount);
            this.outputCondition = OutputConditionFactory.CreateCondition(outputLimitSpec, viewContext, outputCallback);
            this.resultSetProcessor = resultSetProcessor;
            this.outputLastOnly = (outputLimitSpec != null) && (outputLimitSpec.IsDisplayLastOnly);
        }

        /// <summary>The update method is called if the view does not participate in a join.</summary>
        /// <param name="newData">new events</param>
        /// <param name="oldData">old events</param>

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".update Received update, " +
                        "  newData.Length==" + ((newData == null) ? 0 : newData.Length) +
                        "  oldData.Length==" + ((oldData == null) ? 0 : oldData.Length));
            }

            // add the incoming events to the event batches
            int newDataLength = 0;
            int oldDataLength = 0;
            if (newData != null)
            {
                newDataLength = newData.Length;
                foreach (EventBean ev in newData)
                {
                    newEventsList.Add(ev);
                }
            }
            if (oldData != null)
            {
                oldDataLength = oldData.Length;
                foreach (EventBean ev in oldData)
                {
                    oldEventsList.Add(ev);
                }
            }

            outputCondition.UpdateOutputCondition(newDataLength, oldDataLength);
        }

        /// <summary>This process (update) method is for participation in a join.</summary>
        /// <param name="newEvents">new events</param>
        /// <param name="oldEvents">old events</param>

        public void Process(ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(
                    ".process Received update, " +
                    "  newData.Length==" + ((newEvents == null) ? 0 : newEvents.Count) +
                    "  oldData.Length==" + ((oldEvents == null) ? 0 : oldEvents.Count));
            }

            // add the incoming events to the event batches
            foreach (MultiKey<EventBean> ev in newEvents)
            {
                newEventsSet.Add(ev);
            }
            foreach (MultiKey<EventBean> ev in oldEvents)
            {
                oldEventsSet.Add(ev);
            }

            outputCondition.UpdateOutputCondition(newEvents.Count, oldEvents.Count);
        }

        /// <summary>Called once the output condition has been met.Invokes the result set processor.Used for non-join event data.</summary>
        /// <param name="doOutput">true if the batched events should actually be output as well as processed, false if they should just be processed</param>
        /// <param name="forceUpdate">true if output should be made even when no updating events have arrived</param>

        protected void ContinueOutputProcessingView(Boolean doOutput, Boolean forceUpdate)
        {
            log.Debug(".ContinueOutputProcessingView");

            // Get the arrays of new and old events, or null if none
            EventBean[] newEvents = newEventsList.Count > 0 ? newEventsList.ToArray() : null;
            EventBean[] oldEvents = oldEventsList.Count > 0 ? oldEventsList.ToArray() : null;


            if (resultSetProcessor != null)
            {
                // Process the events and get the result
                Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.ProcessViewResult(newEvents, oldEvents);
                newEvents = newOldEvents != null ? newOldEvents.First : null;
                oldEvents = newOldEvents != null ? newOldEvents.Second : null;
            }
            else if (outputLastOnly)
            {
                // Keep only the last event, if there is one
                newEvents = newEvents != null ? new EventBean[] { newEvents[newEvents.Length - 1] } : newEvents;
                oldEvents = oldEvents != null ? new EventBean[] { oldEvents[oldEvents.Length - 1] } : oldEvents;
            }

            if (doOutput)
            {
                output(forceUpdate, newEvents, oldEvents);
            }
            resetEventBatches();
        }

        private void output(Boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (newEvents != null || oldEvents != null)
            {
                UpdateChildren(newEvents, oldEvents);
            }
            else if (forceUpdate)
            {
                UpdateChildren(null, null);
            }
        }

        private void resetEventBatches()
        {
            newEventsList.Clear();
            oldEventsList.Clear();
            newEventsSet.Clear();
            oldEventsSet.Clear();
        }

        /// <summary>Called once the output condition has been met.Invokes the result set processor.Used for join event data.</summary>
        /// <param name="doOutput">true if the batched events should actually be output as well as processed, false if they should just be processed</param>
        /// <param name="forceUpdate">true if output should be made even when no updating events have arrived</param>

        protected void ContinueOutputProcessingJoin(Boolean doOutput, Boolean forceUpdate)
        {
            log.Debug(".ContinueOutputProcessingJoin");

            EventBean[] newEvents = null;
            EventBean[] oldEvents = null;

            Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.ProcessJoinResult(newEventsSet, oldEventsSet);
            if (newOldEvents != null)
            {
                newEvents = newOldEvents.First;
                oldEvents = newOldEvents.Second;
            }

            if (doOutput)
            {
                output(forceUpdate, newEvents, oldEvents);
            }
            resetEventBatches();
        }

        /// <summary>
        /// Provides metadata information about the type of object the event collection contains.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// metadata for the objects in the collection
        /// </returns>
        public override EventType EventType
        {
            get
            {
                if (resultSetProcessor != null)
                {
					return resultSetProcessor.ResultEventType;
                }
                else
                {
                    return parent.EventType;
                }
            }
            set
            {
            }
        }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
        {
            if (resultSetProcessor != null)
            {
                throw new NotSupportedException();
            }
            else
            {
                return parent.GetEnumerator();
            }
        }


        /// <summary>
        /// Return null if the view will accept being attached to a particular object.
        /// </summary>
        /// <param name="parentViewable">is the potential parent for this view</param>
        /// <returns>
        /// null if this view can successfully attach to the parent, an error message if it cannot.
        /// </returns>
        public override String AttachesTo(Viewable parentViewable)
        {
            return null;
        }

        private OutputCallback getCallbackToLocal(int streamCount)
        {
            // single stream means no join
            // multiple streams means a join
            if (streamCount == 1)
            {
                return new OutputCallback(
                    delegate(Boolean doOutput, Boolean forceUpdate)
                    {
                        ContinueOutputProcessingView(doOutput, forceUpdate);
                    });
            }
            else
            {
                return new OutputCallback(
                    delegate(Boolean doOutput, Boolean forceUpdate)
                    {
                        ContinueOutputProcessingJoin(doOutput, forceUpdate);
                    });
            }
        }
    }
}
