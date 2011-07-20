/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.epl.spec.FilterSpecRaw;
import com.espertech.esper.filter.FilterSpecCompiled;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents a filter of events in the evaluation tree representing any event expressions.
 */
public class EvalFilterNode extends EvalNodeBase
{
    private static final long serialVersionUID = 0L;
    private final FilterSpecRaw rawFilterSpec;
    private final String eventAsName;
    private transient FilterSpecCompiled filterSpec;
    private final Integer consumptionLevel;

    /**
     * Constructor.
     * @param filterSpecification specifies the filter properties
     * @param eventAsName is the name to use for adding matching events to the MatchedEventMap
     * table used when indicating truth value of true.
     */
    protected EvalFilterNode(FilterSpecRaw filterSpecification,
                             String eventAsName,
                             Integer consumptionLevel)
    {
        this.rawFilterSpec = filterSpecification;
        this.eventAsName = eventAsName;
        this.consumptionLevel = consumptionLevel;
    }

    public EvalStateNode newState(Evaluator parentNode,
                                  MatchedEventMap beginState,
                                  EvalStateNodeNumber stateNodeId)
    {
        if (getContext().getConsumptionHandler() != null) {
            return new EvalFilterStateNodeConsumeImpl(parentNode, this, beginState);
        }
        return new EvalFilterStateNode(parentNode, this, beginState);
    }

    /**
     * Returns the raw (unoptimized/validated) filter definition.
     * @return filter def
     */
    public FilterSpecRaw getRawFilterSpec()
    {
        return rawFilterSpec;
    }

    /**
     * Returns filter specification.
     * @return filter definition
     */
    public final FilterSpecCompiled getFilterSpec()
    {
        return filterSpec;
    }

    /**
     * Sets a validated and optimized filter specification
     * @param filterSpec is the optimized filter
     */
    public void setFilterSpec(FilterSpecCompiled filterSpec)
    {
        this.filterSpec = filterSpec;
    }

    /**
     * Returns the tag for any matching events to this filter, or null since tags are optional.
     * @return tag string for event
     */
    public final String getEventAsName()
    {
        return eventAsName;
    }

    public Integer getConsumptionLevel() {
        return consumptionLevel;
    }

    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend"})
    public final String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("EvalFilterNode rawFilterSpec=" + this.rawFilterSpec);
        buffer.append(" filterSpec=" + this.filterSpec);
        buffer.append(" eventAsName=" + this.eventAsName);
        return buffer.toString();
    }

    private static final Log log = LogFactory.getLog(EvalFilterNode.class);
}
