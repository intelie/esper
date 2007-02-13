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

        /**
         * Ctor.
         * @param resultSetProcessor is processing the result set for publishing it out
         * @param streamCount is the number of streams, indicates whether or not this view participates in a join
         * @param outputLimitSpec is the specification for limiting output (the output condition and the result set processor)
         * @param viewContext is the services the output condition may depend on
         */
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
            this.outputCondition = OutputConditionFactory.createCondition(outputLimitSpec, viewContext, outputCallback);
            this.resultSetProcessor = resultSetProcessor;
            this.outputLastOnly = (outputLimitSpec != null) && (outputLimitSpec.IsDisplayLastOnly);
        }

        /**
         * The update method is called if the view does not participate in a join.
         * @param newData - new events
         * @param oldData - old events
         */
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

            outputCondition.updateOutputCondition(newDataLength, oldDataLength);
        }

        /**
         * This process (update) method is for participation in a join.
         * @param newEvents - new events
         * @param oldEvents - old events
         */
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

            outputCondition.updateOutputCondition(newEvents.Count, oldEvents.Count);
        }

        /**
         * Called once the output condition has been met.
         * Invokes the result set processor.
         * Used for non-join event data.
         * @param doOutput - true if the batched events should actually be output as well as processed, false if they should just be processed
         * @param forceUpdate - true if output should be made even when no updating events have arrived
         * */
        protected void continueOutputProcessingView(Boolean doOutput, Boolean forceUpdate)
        {
            log.Debug(".continueOutputProcessingView");

            // Get the arrays of new and old events, or null if none
            EventBean[] newEvents = newEventsList.Count > 0 ? newEventsList.ToArray() : null;
            EventBean[] oldEvents = oldEventsList.Count > 0 ? oldEventsList.ToArray() : null;


            if (resultSetProcessor != null)
            {
                // Process the events and get the result
                Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processViewResult(newEvents, oldEvents);
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
                updateChildren(newEvents, oldEvents);
            }
            else if (forceUpdate)
            {
                updateChildren(null, null);
            }
        }

        private void resetEventBatches()
        {
            newEventsList.Clear();
            oldEventsList.Clear();
            newEventsSet.Clear();
            oldEventsSet.Clear();
        }

        /**
         * Called once the output condition has been met.
         * Invokes the result set processor.
         * Used for join event data.
         * @param doOutput - true if the batched events should actually be output as well as processed, false if they should just be processed
         * @param forceUpdate - true if output should be made even when no updating events have arrived	
         */
        protected void continueOutputProcessingJoin(Boolean doOutput, Boolean forceUpdate)
        {
            log.Debug(".continueOutputProcessingJoin");

            EventBean[] newEvents = null;
            EventBean[] oldEvents = null;

            Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.processJoinResult(newEventsSet, oldEventsSet);
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
                        continueOutputProcessingView(doOutput, forceUpdate);
                    });
            }
            else
            {
                return new OutputCallback(
                    delegate(Boolean doOutput, Boolean forceUpdate)
                    {
                        continueOutputProcessingJoin(doOutput, forceUpdate);
                    });
            }
        }
    }
}