using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.pattern;
using net.esper.events;
using net.esper.view;

namespace net.esper.eql.spec
{
	/// <summary> Specification for building an event stream out of a pattern statement and views staggered onto the
	/// pattern statement.
	/// <p>
	/// The pattern statement is represented by the top EvalNode evaluation node.
	/// A pattern statement contains tagged events (i.e. a=A -> b=B).
	/// Thus the resulting event type is has properties "a" and "b" of the type of A and B.
	/// </summary>

    public class PatternStreamSpec : StreamSpec
    {
        /// <summary> Returns the pattern expression evaluation node for the top pattern operator.</summary>
        /// <returns> parent pattern expression node
        /// </returns>

        virtual public EvalNode EvalNode
        {
            get { return evalNode; }
        }

        private readonly EvalNode evalNode;
        private readonly EDictionary<String, EventType> taggedEventTypes; // Stores types for filters with tags

        /// <summary> Ctor.</summary>
        /// <param name="evalNode">- pattern evaluation node representing pattern statement
        /// </param>
        /// <param name="viewSpecs">- specifies what view to use to derive data
        /// </param>
        /// <param name="taggedEventTypes">- event tags and their types as specified in the pattern, copied to allow original collection to change
        /// </param>
        /// <param name="optionalStreamName">- stream name, or null if none supplied
        /// </param>

        public PatternStreamSpec(
            EvalNode evalNode,
            IDictionary<String, EventType> taggedEventTypes,
            IList<ViewSpec> viewSpecs,
            String optionalStreamName)
            : base(optionalStreamName, viewSpecs)
        {
            this.evalNode = evalNode;
            this.taggedEventTypes = new EHashDictionary<String, EventType>();
            this.taggedEventTypes.PutAll(taggedEventTypes);
        }

        /// <summary> Returns event types tagged in the pattern expression.</summary>
        /// <returns> map of tag and event type tagged in pattern expression
        /// </returns>

        public EDictionary<String, EventType> TaggedEventTypes
        {
            get { return taggedEventTypes; }
        }
    }
}
