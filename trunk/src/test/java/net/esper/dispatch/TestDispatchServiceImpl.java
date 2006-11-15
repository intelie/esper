package net.esper.dispatch;

import net.esper.support.dispatch.SupportDispatchable;
import net.esper.util.AssertionException;
import junit.framework.TestCase;
import java.util.List;

public class TestDispatchServiceImpl extends TestCase
{
    private DispatchServiceImpl service;

    public void setUp()
    {
        service = new DispatchServiceImpl();
    }

    public void testAddAndDispatch()
    {
        // Dispatch without work to do, should complete
        service.dispatch();

        SupportDispatchable disOne = new SupportDispatchable();
        SupportDispatchable disTwo = new SupportDispatchable();
        service.addExternal(disOne);
        service.addExternal(disTwo);

        assertEquals(0, disOne.getAndResetNumExecuted());
        assertEquals(0, disTwo.getAndResetNumExecuted());

        service.dispatch();

        service.addExternal(disTwo);
        assertEquals(1, disOne.getAndResetNumExecuted());
        assertEquals(1, disTwo.getAndResetNumExecuted());

        service.dispatch();
        assertEquals(0, disOne.getAndResetNumExecuted());
        assertEquals(1, disTwo.getAndResetNumExecuted());
    }

    public void testAddDispatchTwice()
    {
        SupportDispatchable disOne = new SupportDispatchable();
        service.addExternal(disOne);

        try
        {
            service.addExternal(disOne);
            fail();
        }
        catch (AssertionException ex)
        {
            // Expected
        }

        service.dispatch();
        assertEquals(1, disOne.getAndResetNumExecuted());

        service.dispatch();
        assertEquals(0, disOne.getAndResetNumExecuted());
    }

    public void testAddInternalExternal()
    {
        SupportDispatchable dispatchables[] = new SupportDispatchable[5];
        for (int i = 0; i < dispatchables.length; i++)
        {
            dispatchables[i] = new SupportDispatchable();
        }
        SupportDispatchable.getAndResetInstanceList();

        service.addExternal(dispatchables[0]);
        service.addInternal(dispatchables[1]);
        service.addInternal(dispatchables[2]);
        service.addExternal(dispatchables[3]);
        service.addInternal(dispatchables[4]);

        service.dispatch();

        List<SupportDispatchable> dispatchList = SupportDispatchable.getAndResetInstanceList();
        assertSame(dispatchables[1], dispatchList.get(0));
        assertSame(dispatchables[2], dispatchList.get(1));
        assertSame(dispatchables[4], dispatchList.get(2));
        assertSame(dispatchables[0], dispatchList.get(3));
        assertSame(dispatchables[3], dispatchList.get(4));
    }

    //private static Log log = LogFactory.getLog(TestDispatchServiceImpl.class);
}
