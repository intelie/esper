package net.esper.pattern;

import net.esper.eql.spec.PluggableObjectCollection;
import net.esper.eql.spec.PluggableObjectType;
import net.esper.pattern.guard.GuardEnum;
import net.esper.pattern.observer.ObserverEnum;

/**
 * Helper producing a repository of built-in pattern objects.
 */
public class PatternObjectHelper
{
    private final static PluggableObjectCollection builtinPatternObjects;

    static
    {
        builtinPatternObjects = new PluggableObjectCollection();
        for (GuardEnum guardEnum : GuardEnum.values())
        {
            builtinPatternObjects.addObject(guardEnum.getNamespace(), guardEnum.getName(), guardEnum.getClazz(), PluggableObjectType.PATTERN_GUARD);
        }
        for (ObserverEnum observerEnum : ObserverEnum.values())
        {
            builtinPatternObjects.addObject(observerEnum.getNamespace(), observerEnum.getName(), observerEnum.getClazz(), PluggableObjectType.PATTERN_OBSERVER);
        }
    }

    /**
     * Returns the built-in pattern objects.
     * @return collection of built-in pattern objects.
     */
    public static PluggableObjectCollection getBuiltinPatternObjects()
    {
        return builtinPatternObjects;
    }
}
