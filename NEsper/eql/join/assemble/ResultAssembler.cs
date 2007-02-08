using System;

using net.esper.events;
using net.esper.eql.join.rep;

namespace net.esper.eql.join.assemble
{
	/// <summary> Interface for indicating a result in the form of a single row of multiple events, which could
	/// represent either a full result over all streams or a partial result over a subset of streams.
	/// </summary>
    public interface ResultAssembler
    {
        /// <summary> Publish a result row.</summary>
        /// <param name="row">is the result to publish
        /// </param>
        /// <param name="fromStreamNum">is the originitor that publishes the row
        /// </param>
        /// <param name="myEvent">is optional and is the event that led to the row result
        /// </param>
        /// <param name="myNode">is optional and is the result node of the event that led to the row result 
        /// </param>
        void Result(EventBean[] row, int fromStreamNum, EventBean myEvent, Node myNode);
    }
}