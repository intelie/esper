using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.events;
using net.esper.view;

namespace net.esper.eql.view
{
    /// <summary>
    /// Simple filter view filtering events using a filter expression tree.
    /// </summary>

    public class FilterExprView : ViewSupport

    {
        private ExprEvaluator exprEvaluator;

        /// <summary> Ctor.</summary>
        /// <param name="exprEvaluator">- Filter expression evaluation impl
        /// </param>

        public FilterExprView(ExprEvaluator exprEvaluator)
        {
            this.exprEvaluator = exprEvaluator;
        }

        public override EventType EventType
        {
            get { return parent.EventType; }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return parent.GetEnumerator();
        }

        public override String AttachesTo(Viewable parentViewable)
        {
            return null;
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            EventBean[] filteredNewData = filterEvents(exprEvaluator, newData);
            EventBean[] filteredOldData = filterEvents(exprEvaluator, oldData);

            if ((filteredNewData != null) || (filteredOldData != null))
            {
                this.updateChildren(filteredNewData, filteredOldData);
            }
        }

        /// <summary> Filters events using the supplied evaluator.</summary>
        /// <param name="exprEvaluator">- evaluator to use
        /// </param>
        /// <param name="events">- events to filter
        /// </param>
        /// <returns> filtered events, or null if no events got through the filter 
        /// </returns>

        internal static EventBean[] filterEvents(ExprEvaluator exprEvaluator, EventBean[] events)
        {
            if (events == null)
            {
                return null;
            }

            EventBean[] evalEventArr = new EventBean[1];
            bool[] passResult = new bool[events.Length];
            int passCount = 0;

            for (int i = 0; i < events.Length; i++)
            {
                evalEventArr[0] = events[i];
                bool pass = (Boolean)exprEvaluator.Evaluate(evalEventArr);
                if (pass)
                {
                    passResult[i] = true;
                    passCount++;
                }
            }

            if (passCount == 0)
            {
                return null;
            }
            if (passCount == events.Length)
            {
                return events;
            }

            EventBean[] resultArray = new EventBean[passCount];
            int count = 0;
            for (int i = 0; i < passResult.Length; i++)
            {
                if (passResult[i])
                {
                    resultArray[count] = events[i];
                    count++;
                }
            }
            return resultArray;
        }
    }
}