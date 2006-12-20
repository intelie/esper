package net.esper.pattern.observer;

import junit.framework.TestCase;

public class TestObserverEnum extends TestCase
{
    public void testForName()
    {
        ObserverEnum enumValue = ObserverEnum.forName(ObserverEnum.TIMER_INTERVAL.getNamespace(), ObserverEnum.TIMER_INTERVAL.getName());
        assertEquals(enumValue, ObserverEnum.TIMER_INTERVAL);

        enumValue = ObserverEnum.forName(ObserverEnum.TIMER_INTERVAL.getNamespace(), "dummy");
        assertNull(enumValue);

        enumValue = ObserverEnum.forName("dummy", ObserverEnum.TIMER_INTERVAL.getName());
        assertNull(enumValue);
    }
}
