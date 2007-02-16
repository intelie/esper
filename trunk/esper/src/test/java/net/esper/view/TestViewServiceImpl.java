package net.esper.view;

import junit.framework.TestCase;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewSpecFactory;
import net.esper.support.view.SupportViewContextFactory;

public class TestViewServiceImpl extends TestCase
{
    private ViewServiceImpl viewService;

    private Viewable viewOne;
    private Viewable viewTwo;
    private Viewable viewThree;
    private Viewable viewFour;
    private Viewable viewFive;

    private EventStream streamOne;
    private EventStream streamTwo;

    public void setUp() throws Exception
    {
        streamOne = new SupportStreamImpl(SupportMarketDataBean.class, 1);
        streamTwo = new SupportStreamImpl(SupportBean_A.class, 1);

        viewService = new ViewServiceImpl();

        ViewServiceContext context = SupportViewContextFactory.makeContext();

        viewOne = viewService.createViews(streamOne, SupportViewSpecFactory.makeFactoryListOne(streamOne.getEventType()), context);
        viewTwo = viewService.createViews(streamOne, SupportViewSpecFactory.makeFactoryListTwo(streamOne.getEventType()), context);
        viewThree = viewService.createViews(streamOne, SupportViewSpecFactory.makeFactoryListThree(streamOne.getEventType()), context);
        viewFour = viewService.createViews(streamOne, SupportViewSpecFactory.makeFactoryListFour(streamOne.getEventType()), context);
        viewFive = viewService.createViews(streamTwo, SupportViewSpecFactory.makeFactoryListFive(streamTwo.getEventType()), context);
    }

    public void testCheckChainReuse()
    {
        // Child views of first and second level must be the same
        assertEquals(2, streamOne.getViews().size());
        View child1_1 = streamOne.getViews().get(0);
        View child2_1 = streamOne.getViews().get(0);
        assertTrue(child1_1 == child2_1);

        assertEquals(2, child1_1.getViews().size());
        View child1_1_1 = child1_1.getViews().get(0);
        View child2_1_1 = child2_1.getViews().get(0);
        assertTrue(child1_1_1 == child2_1_1);

        assertEquals(2, child1_1_1.getViews().size());
        assertEquals(2, child2_1_1.getViews().size());
        assertTrue(child2_1_1.getViews().get(0) != child2_1_1.getViews().get(1));

        // Create one more view chain
        View child3_1 = streamOne.getViews().get(0);
        assertTrue(child3_1 == child1_1);
        assertEquals(2, child3_1.getViews().size());
        View child3_1_1 = child3_1.getViews().get(1);
        assertTrue(child3_1_1 != child2_1_1);
    }

    public void testRemove()
    {
        assertEquals(2, streamOne.getViews().size());
        assertEquals(1, streamTwo.getViews().size());

        viewService.remove(streamOne, viewOne);
        viewService.remove(streamOne, viewTwo);
        viewService.remove(streamOne, viewThree);
        viewService.remove(streamOne, viewFour);

        viewService.remove(streamTwo, viewFive);

        assertEquals(0, streamOne.getViews().size());
        assertEquals(0, streamTwo.getViews().size());
    }

    public void testRemoveInvalid()
    {
        try
        {
            viewService.remove(streamOne, viewOne);
            viewService.remove(streamOne, viewOne);
            TestCase.fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }
}