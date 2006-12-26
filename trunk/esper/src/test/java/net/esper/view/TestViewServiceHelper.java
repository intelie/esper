package net.esper.view;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.*;
import net.esper.view.window.TimeWindowView;
import net.esper.view.window.LengthWindowView;
import net.esper.view.ViewFactory;
import net.esper.view.std.SizeView;
import net.esper.view.std.LastElementView;
import net.esper.view.stat.UnivariateStatisticsView;
import net.esper.collection.Pair;

public class TestViewServiceHelper extends TestCase
{
    private final static Class TEST_CLASS = SupportMarketDataBean.class;

    private SupportSchemaNeutralView top;
    private SupportSchemaNeutralView child_1;
    private SupportSchemaNeutralView child_2;
    private SupportSchemaNeutralView child_2_1;
    private SupportSchemaNeutralView child_2_2;
    private SupportSchemaNeutralView child_2_1_1;
    private SupportSchemaNeutralView child_2_2_1;
    private SupportSchemaNeutralView child_2_2_2;

    public void setUp()
    {
        top = new SupportSchemaNeutralView("top");

        child_1 = new SupportSchemaNeutralView("1");
        child_2 = new SupportSchemaNeutralView("2");
        top.addView(child_1);
        top.addView(child_2);

        child_2_1 = new SupportSchemaNeutralView("2_1");
        child_2_2 = new SupportSchemaNeutralView("2_2");
        child_2.addView(child_2_1);
        child_2.addView(child_2_2);

        child_2_1_1 = new SupportSchemaNeutralView("2_1_1");
        child_2_2_1 = new SupportSchemaNeutralView("2_2_1");
        child_2_2_2 = new SupportSchemaNeutralView("2_2_2");
        child_2_1.addView(child_2_1_1);
        child_2_2.addView(child_2_2_1);
        child_2_2.addView(child_2_2_2);
    }

    public void testInstantiateChain() throws Exception
    {
        LinkedList<View> existingParentViews = new LinkedList<View>();

        SupportBeanClassView topView = new SupportBeanClassView(TEST_CLASS);
        List<ViewFactory> viewFactories = SupportViewSpecFactory.makeFactoryListOne(topView.getEventType());
        ViewServiceContext context = SupportViewContextFactory.makeContext();

        // Check correct views created
        List<View> views = ViewServiceHelper.instantiateChain(existingParentViews, topView, viewFactories, context);

        assertEquals(3, views.size());
        assertEquals(ViewEnum.LENGTH_WINDOW.getClazz(), views.get(0).getClass());
        assertEquals(ViewEnum.UNIVARIATE_STATISTICS.getClazz(), views.get(1).getClass());
        assertEquals(ViewEnum.LAST_EVENT.getClazz(), views.get(2).getClass());

        // Check that the context is set
        viewFactories = SupportViewSpecFactory.makeFactoryListFive(topView.getEventType());
        views = ViewServiceHelper.instantiateChain(existingParentViews, topView, viewFactories, context);
        TimeWindowView timeWindow = (TimeWindowView) views.get(0);
        assertEquals(context, timeWindow.getViewServiceContext());
    }

    public void testMatch() throws Exception
    {
        SupportStreamImpl stream = new SupportStreamImpl(TEST_CLASS, 10);
        List<ViewFactory> viewFactories = SupportViewSpecFactory.makeFactoryListOne(stream.getEventType());

        // No views under stream, no matches
        Pair<Viewable, List<View>> result = ViewServiceHelper.matchExistingViews(stream, viewFactories);
        assertEquals(stream, result.getFirst());
        assertEquals(3, viewFactories.size());
        assertEquals(0, result.getSecond().size());

        // One top view under the stream that doesn't match
        SupportBeanClassView testView = new SupportBeanClassView(TEST_CLASS);
        stream.addView(new SizeView());
        result = ViewServiceHelper.matchExistingViews(stream, viewFactories);

        assertEquals(stream, result.getFirst());
        assertEquals(3, viewFactories.size());
        assertEquals(0, result.getSecond().size());

        // Another top view under the stream that doesn't matche again
        testView = new SupportBeanClassView(TEST_CLASS);
        stream.addView(new LengthWindowView(999, null));
        result = ViewServiceHelper.matchExistingViews(stream, viewFactories);

        assertEquals(stream, result.getFirst());
        assertEquals(3, viewFactories.size());
        assertEquals(0, result.getSecond().size());

        // One top view under the stream that does actually match
        LengthWindowView myLengthWindowView = new LengthWindowView(1000, null);
        stream.addView(myLengthWindowView);
        result = ViewServiceHelper.matchExistingViews(stream, viewFactories);

        assertEquals(myLengthWindowView, result.getFirst());
        assertEquals(2, viewFactories.size());
        assertEquals(1, result.getSecond().size());
        assertEquals(myLengthWindowView, result.getSecond().get(0));

        // One child view under the top view that does not match
        testView = new SupportBeanClassView(TEST_CLASS);
        viewFactories = SupportViewSpecFactory.makeFactoryListOne(stream.getEventType());
        myLengthWindowView.addView(new UnivariateStatisticsView("volume"));
        result = ViewServiceHelper.matchExistingViews(stream, viewFactories);
        assertEquals(1, result.getSecond().size());
        assertEquals(myLengthWindowView, result.getSecond().get(0));
        assertEquals(myLengthWindowView, result.getFirst());
        assertEquals(2, viewFactories.size());

        // Add child view under the top view that does match
        viewFactories = SupportViewSpecFactory.makeFactoryListOne(stream.getEventType());
        UnivariateStatisticsView myUnivarView = new UnivariateStatisticsView("price");
        myLengthWindowView.addView(myUnivarView);
        result = ViewServiceHelper.matchExistingViews(stream, viewFactories);

        assertEquals(myUnivarView, result.getFirst());
        assertEquals(1, viewFactories.size());

        // Add ultimate child view under the child view that does match
        viewFactories = SupportViewSpecFactory.makeFactoryListOne(stream.getEventType());
        LastElementView lastElementView = new LastElementView();
        myUnivarView.addView(lastElementView);
        result = ViewServiceHelper.matchExistingViews(stream, viewFactories);

        assertEquals(lastElementView, result.getFirst());
        assertEquals(0, viewFactories.size());
    }

    public void testAddMergeViews()
    {
        List<ViewSpec> specOne = SupportViewSpecFactory.makeSpecListOne();

        ViewServiceHelper.addMergeViews(specOne);
        assertEquals(3, specOne.size());

        List<ViewSpec> specFour = SupportViewSpecFactory.makeSpecListTwo();
        ViewServiceHelper.addMergeViews(specFour);
        assertEquals(2, specFour.size());
        assertEquals("merge", specFour.get(1).getObjectName());
        assertEquals(specFour.get(0).getObjectParameters().size(), specFour.get(1).getObjectParameters().size());
    }

    public void testRemoveChainLeafView()
    {
        // Remove a non-leaf, expect no removals
        List<View> removedViews = ViewServiceHelper.removeChainLeafView(top, child_2_2);
        assertEquals(0, removedViews.size());
        assertEquals(2, child_2.getViews().size());

        // Remove the whole tree child-by-child
        removedViews = ViewServiceHelper.removeChainLeafView(top, child_2_2_2);
        assertEquals(1, removedViews.size());
        assertEquals(child_2_2_2, removedViews.get(0));
        assertEquals(2, child_2.getViews().size());

        removedViews = ViewServiceHelper.removeChainLeafView(top, child_2_2_1);
        assertEquals(2, removedViews.size());
        assertEquals(child_2_2_1, removedViews.get(0));
        assertEquals(child_2_2, removedViews.get(1));
        assertEquals(1, child_2.getViews().size());

        removedViews = ViewServiceHelper.removeChainLeafView(top, child_1);
        assertEquals(1, removedViews.size());
        assertEquals(child_1, removedViews.get(0));

        removedViews = ViewServiceHelper.removeChainLeafView(top, child_2_1_1);
        assertEquals(3, removedViews.size());
        assertEquals(child_2_1_1, removedViews.get(0));
        assertEquals(child_2_1, removedViews.get(1));
        assertEquals(child_2, removedViews.get(2));

        assertEquals(0, child_2.getViews().size());
        assertEquals(0, top.getViews().size());
    }
}