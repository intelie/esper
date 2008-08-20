/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.vaevent.ValueAddEventService;
import com.espertech.esper.pattern.PatternObjectResolutionService;
import com.espertech.esper.schedule.TimeProvider;

import java.net.URI;

/**
 * An uncompiled, unoptimize for of stream specification created by a parser.
 */
public interface StreamSpecRaw extends StreamSpec
{
    /**
     * Compiles a raw stream specification consisting event type information and filter expressions
     * to an validated, optimized form for use with filter service
     * @param eventAdapterService supplies type information
     * @param methodResolutionService for resolving imports
     * @param patternObjectResolutionService for resolving pattern objects
     * @param timeProvider - provides engine current time
     * @param namedWindowService is the service managing named windows
     * @param variableService provides variable values
     * @param engineURI the engine URI
     * @param optionalPlugInTypeResolutionURIS is URIs for resolving the event name against plug-inn event representations, if any  
     * @param valueAddEventService service that handles update events
     * @return compiled stream
     * @throws ExprValidationException to indicate validation errors
     */
    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      MethodResolutionService methodResolutionService,
                                      PatternObjectResolutionService patternObjectResolutionService,
                                      TimeProvider timeProvider,
                                      NamedWindowService namedWindowService,
                                      ValueAddEventService valueAddEventService,
                                      VariableService variableService,
                                      String engineURI,
                                      URI[] optionalPlugInTypeResolutionURIS)
        throws ExprValidationException;

}
