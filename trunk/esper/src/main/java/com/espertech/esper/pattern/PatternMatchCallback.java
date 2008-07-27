/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.event.EventBean;
import java.util.Map;

/**
 * Callback interface for anything that requires to be informed of matching events which would be stored
 * in the MatchedEventMap structure passed to the implementation.
 */
public interface PatternMatchCallback
{
    /**
     * Indicate matching events.
     * @param matchEvent contains a map of event tags and event objects
     */
    public void matchFound(Map<String, Object> matchEvent);
}
