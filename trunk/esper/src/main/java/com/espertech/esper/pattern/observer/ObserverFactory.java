/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.observer;

import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.guard.GuardParameterException;

import java.util.List;

/**
 * Interface for factories for making observer instances.
 */
public interface ObserverFactory
{
    /**
     * Sets the observer object parameters.
     * @param observerParameters is a list of parameters
     * @throws ObserverParameterException thrown to indicate a parameter problem
     */
    public void setObserverParameters(List<Object> observerParameters) throws ObserverParameterException;

    /**
     * Make an observer instance.
     * @param context - services that may be required by observer implementation
     * @param beginState - start state for observer
     * @param observerEventEvaluator - receiver for events observed
     * @param stateNodeId - optional id for the associated pattern state node
     * @param observerState - state node for observer
     * @return observer instance
     */
    public EventObserver makeObserver(PatternContext context,
                                      MatchedEventMap beginState,
                                      ObserverEventEvaluator observerEventEvaluator,
                                      Object stateNodeId,
                                      Object observerState);
}
