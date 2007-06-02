using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.view;

namespace net.esper.eql.spec
{
	/**
	 * Specification for a stream, consists simply of an optional stream name and a list of views
	 * on to of the stream.
	 * <p>
	 * Implementation classes for views and patterns add additional information defining the
	 * stream of events.
	 */

    public interface StreamSpec
    {
        /// <summary>Returns the stream name, or null if undefined.</summary>
        
        String OptionalStreamName { get ; }

        /// <summary> Returns views definitions onto the stream.</summary>

        List<ViewSpec> ViewSpecs { get ; }
    }
}