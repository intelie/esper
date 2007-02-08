using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.view;

namespace net.esper.eql.spec
{
	/// <summary> Abstract base specification for a stream, consists simply of an optional stream name and a list of views
	/// on to of the stream.
	/// <p>
	/// Implementation classes for views and patterns add additional information defining the
	/// stream of events.
	/// </summary>

    public abstract class StreamSpec
    {
        private String optionalStreamName;
        private List<ViewSpec> viewSpecs;

        /// <summary> Returns the name assigned.</summary>
        /// <returns> stream name or null if not assigned
        /// </returns>
        
        virtual public String OptionalStreamName
        {
            get { return optionalStreamName; }
        }

        /// <summary> Ctor.</summary>
        /// <param name="optionalStreamName">- stream name, or null if none supplied
        /// </param>
        /// <param name="viewSpecs">- specifies what view to use to derive data
        /// </param>

        public StreamSpec(String optionalStreamName, IList<ViewSpec> viewSpecs)
        {
            this.optionalStreamName = optionalStreamName;
            this.viewSpecs = new List<ViewSpec>();
            this.viewSpecs.AddRange(viewSpecs);
        }

        /// <summary> Returns view definitions to use to construct views to derive data on stream.</summary>
        /// <returns> view defs
        /// </returns>

        public List<ViewSpec> ViewSpecs
        {
            get { return viewSpecs; }
        }
    }
}