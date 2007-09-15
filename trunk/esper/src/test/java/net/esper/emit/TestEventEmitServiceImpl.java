package net.esper.emit;

import junit.framework.TestCase;
import net.esper.support.emit.SupportEmittedListener;

public class TestEventEmitServiceImpl extends TestCase
{
    public void testEmitFlow()
    {
        EmitServiceImpl service = new EmitServiceImpl();

        SupportEmittedListener listenerOne = new SupportEmittedListener();
        service.addListener(listenerOne, "1");

        SupportEmittedListener listenerTwo = new SupportEmittedListener();
        service.addListener(listenerTwo, "2");

        SupportEmittedListener listenerThree = new SupportEmittedListener();
        service.addListener(listenerThree, null);
        service.addListener(listenerThree, "3");

        SupportEmittedListener listenerFour = new SupportEmittedListener();
        service.addListener(listenerFour, "1");
        service.addListener(listenerFour, "2");

        assertTrue(service.getNumEventsEmitted() == 0);
        service.emitEvent("a", null);

        assertTrue(listenerOne.getEmittedObjects().size() == 0);
        assertTrue(listenerTwo.getEmittedObjects().size() == 0);
        assertTrue(listenerThree.getEmittedObjects().size() == 1);
        assertTrue(listenerThree.getEmittedObjects().get(0) == "a");
        assertTrue(listenerFour.getEmittedObjects().size() == 0);

        assertTrue(service.getNumEventsEmitted() == 1);
        service.emitEvent("b", "1");

        assertTrue(listenerOne.getEmittedObjects().size() == 1);
        assertTrue(listenerOne.getEmittedObjects().get(0) == "b");
        assertTrue(listenerTwo.getEmittedObjects().size() == 0);
        assertTrue(listenerThree.getEmittedObjects().size() == 2);
        assertTrue(listenerThree.getEmittedObjects().get(1) == "b");
        assertTrue(listenerFour.getEmittedObjects().size() == 1);
        assertTrue(listenerFour.getEmittedObjects().get(0) == "b");

        assertTrue(service.getNumEventsEmitted() == 2);
        service.emitEvent("c", "3");

        assertTrue(listenerOne.getEmittedObjects().size() == 1);
        assertTrue(listenerTwo.getEmittedObjects().size() == 0);
        assertTrue(listenerThree.getEmittedObjects().size() == 3);
        assertTrue(listenerThree.getEmittedObjects().get(2) == "c");
        assertTrue(listenerFour.getEmittedObjects().size() == 1);

        service.clearListeners();
        assertTrue(service.getNumEventsEmitted() == 3);
        service.emitEvent("c", "3");

        assertTrue(listenerOne.getEmittedObjects().size() == 1);
        assertTrue(listenerTwo.getEmittedObjects().size() == 0);
        assertTrue(listenerThree.getEmittedObjects().size() == 3);
        assertTrue(listenerFour.getEmittedObjects().size() == 1);
    }
}
