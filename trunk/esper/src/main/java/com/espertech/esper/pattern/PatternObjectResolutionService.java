package com.espertech.esper.pattern;

import com.espertech.esper.eql.spec.PatternGuardSpec;
import com.espertech.esper.eql.spec.PatternObserverSpec;
import com.espertech.esper.pattern.guard.GuardFactory;
import com.espertech.esper.pattern.observer.ObserverFactory;

/**
 * Factory service for resolving pattern objects such as guards and observers.
 */
public interface PatternObjectResolutionService
{
    /**
     * Creates an observer factory considering configured plugged-in resources.
     * @param spec is the namespace, name and parameters for the observer
     * @return observer factory
     * @throws PatternObjectException if the observer cannot be resolved
     */
    public ObserverFactory create(PatternObserverSpec spec) throws PatternObjectException;

    /**
     * Creates a guard factory considering configured plugged-in resources.
     * @param spec is the namespace, name and parameters for the guard
     * @return guard factory
     * @throws PatternObjectException if the guard cannot be resolved
     */
    public GuardFactory create(PatternGuardSpec spec) throws PatternObjectException;
}
