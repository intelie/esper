/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.filter.FilterSpecCompiled;
import net.esper.eql.spec.ViewSpec;
import net.esper.eql.spec.StreamSpecBase;

import java.util.List;

/**
 * Specification for building an event stream out of a filter for events (supplying type and basic filter criteria)
 * and views onto these events which are staggered onto each other to supply a final stream of events.
 */
public class FilterStreamSpecCompiled extends StreamSpecBase implements StreamSpecCompiled
{
    private FilterSpecCompiled filterSpec;

    /**
     * Ctor.
     * @param filterSpec - specifies what events we are interested in.
     * @param viewSpecs - specifies what view to use to derive data
     * @param optionalStreamName - stream name, or null if none supplied
     */
    public FilterStreamSpecCompiled(FilterSpecCompiled filterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
        this.filterSpec = filterSpec;
    }

    /**
     * Returns filter specification for which events the stream will getSelectListEvents.
     * @return filter spec
     */
    public FilterSpecCompiled getFilterSpec()
    {
        return filterSpec;
    }

    /**
     * Sets a filter specification.
     * @param filterSpec to set
     */
    public void setFilterSpec(FilterSpecCompiled filterSpec)
    {
        this.filterSpec = filterSpec;
    }
}
