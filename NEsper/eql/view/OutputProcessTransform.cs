using net.esper.collection;
using net.esper.events;
using net.esper.eql.core;

namespace net.esper.eql.view
{
    /// <summary>
    /// Method to transform an event based on the select expression.
    /// </summary>
	public class OutputProcessTransform
	{
        private readonly ResultSetProcessor resultSetProcessor;
        private readonly EventBean[] newData;

        /// <summary>Ctor.</summary>
        /// <param name="resultSetProcessor">
        /// is applying the select expressions to the events for the transformation
        /// </param>
        public OutputProcessTransform(ResultSetProcessor resultSetProcessor)
        {
            this.resultSetProcessor = resultSetProcessor;
            newData = new EventBean[1];
        }

        public EventBean Transform(EventBean _event)
        {
            newData[0] = _event;
            Pair<EventBean[], EventBean[]> pair = resultSetProcessor.ProcessViewResult(newData, null);
            return pair.First[0];
        }
	}
}
