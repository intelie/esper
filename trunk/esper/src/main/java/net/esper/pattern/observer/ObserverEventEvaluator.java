/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.observer;

import net.esper.pattern.*;

/**
 * For use by {@link EventObserver} instances to place an event for processing/evaluation.
 */
public interface ObserverEventEvaluator
{
    /**
     * Indicate an event for evaluation (sub-expression the observer represents has turned true).
     * @param matchEvent is the matched events so far
     */
    public void observerEvaluateTrue(MatchedEventMap matchEvent);

    /**
     * Indicate that the observer turned permanently false.
     */
    public void observerEvaluateFalse();
}
