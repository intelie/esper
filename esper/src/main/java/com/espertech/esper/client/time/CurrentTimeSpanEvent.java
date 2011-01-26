/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.time;

import java.io.Serializable;

/**
 * Event for externally controlling the time within an {@link com.espertech.esper.client.EPRuntime} or {@link com.espertech.esper.client.EPRuntimeIsolated} instance,
 * advancing time over a span of time.
 * <p>
 * The engine advances time according to the resolution passed in, completing at the target time provided.
 * <p>
 * When used without a resolution or with a negative or zero value for resolution the engine advances time according
 * to any statement schedules that may be present. If no statement schedules are present, the engine simply advances time
 * to the target time provided.
 * <p>
 * External clocking must be enabled via {@link TimerControlEvent} before this class can be used
 * to externally feed time.
 */
public final class CurrentTimeSpanEvent extends TimerEvent implements Serializable
{
    private static final long serialVersionUID = -1288617544919785502L;
    private long targetTimeInMillis;
    private Long optionalResolution;

    /**
     * Constructor taking only a target time to advance to.
     * <p>
     * Use this constructor to have the engine decide the resolution at which time advances, according to
     * present statement schedules.
     * @param targetTimeInMillis target time
     */
    public CurrentTimeSpanEvent(long targetTimeInMillis) {
        this.targetTimeInMillis = targetTimeInMillis;
    }

    /**
     * Constructor taking a target time to advance to and a resoultion to use to advance time.
     * <p>
     * Use this constructor to dictate a resolution at which time advances.
     * @param targetTimeInMillis target time
     * @param optionalResolution should be a positive value
     */
    public CurrentTimeSpanEvent(long targetTimeInMillis, long optionalResolution) {
        this.targetTimeInMillis = targetTimeInMillis;
        this.optionalResolution = optionalResolution;
    }

    /**
     * Returns the target time to advance engine time to.
     * @return target time
     */
    public long getTargetTimeInMillis() {
        return targetTimeInMillis;
    }

    /**
     * Sets the target time to advance engine time to.
     * @param targetTimeInMillis target time
     */
    public void setTargetTimeInMillis(long targetTimeInMillis) {
        this.targetTimeInMillis = targetTimeInMillis;
    }

    /**
     * Returns the resolution for advancing time, or null if none provided.
     * @return resolution
     */
    public Long getOptionalResolution() {
        return optionalResolution;
    }

    /**
     * Sets the resolution for advancing time, or provide a null value if no resolution is desired.
     * @param optionalResolution resolution
     */
    public void setOptionalResolution(Long optionalResolution) {
        this.optionalResolution = optionalResolution;
    }
}
