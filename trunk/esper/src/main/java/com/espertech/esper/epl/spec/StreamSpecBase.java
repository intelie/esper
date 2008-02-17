/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.spec.ViewSpec;
import com.espertech.esper.util.MetaDefItem;

import java.util.List;
import java.util.LinkedList;

/**
 * Abstract base specification for a stream, consists simply of an optional stream name and a list of views
 * on to of the stream.
 * <p>
 * Implementation classes for views and patterns add additional information defining the
 * stream of events.
 */
public abstract class StreamSpecBase implements MetaDefItem
{
    private String optionalStreamName;
    private List<ViewSpec> viewSpecs = new LinkedList<ViewSpec>();
    private boolean isUnidirectional;

    /**
     * Ctor.
     * @param optionalStreamName - stream name, or null if none supplied
     * @param viewSpecs - specifies what view to use to derive data
     * @param isUnidirectional - true to indicate a unidirectional stream in a join, applicable for joins
     */
    public StreamSpecBase(String optionalStreamName, List<ViewSpec> viewSpecs, boolean isUnidirectional)
    {
        this.optionalStreamName = optionalStreamName;
        this.viewSpecs.addAll(viewSpecs);
        this.isUnidirectional = isUnidirectional;
    }

    /**
     * Default ctor.
     */
    public StreamSpecBase()
    {
    }

    /**
     * Returns the name assigned.
     * @return stream name or null if not assigned
     */
    public String getOptionalStreamName()
    {
        return optionalStreamName;
    }

    /**
     * Returns view definitions to use to construct views to derive data on stream.
     * @return view defs
     */
    public List<ViewSpec> getViewSpecs()
    {
        return viewSpecs;
    }

    /**
     * Returns true to indicate a unidirectional stream in a join, applicable for joins.
     * @return indicator whether the stream is unidirectional in a join
     */
    public boolean isUnidirectional()
    {
        return isUnidirectional;
    }
}
