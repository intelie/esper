package net.esper.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.collection.Pair;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.*;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.window.TimeWindowView;

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
        List<ViewSpec> specifications = SupportViewSpecFactory.makeSpecListOne();
        ViewServiceContext context = SupportViewContextFactory.makeContext();

        // Check correct views created
        List<View> views = ViewServiceHelper.instantiateChain(existingParentViews, topView, specifications, context);

        assertEquals(3, views.size());
        assertEquals(ViewEnum.LENGTH_WINDOW.getClazz(), views.get(0).getClass());
        assertEquals(ViewEnum.UNIVARIATE_STATISTICS.getClazz(), views.get(1).getClass());
        assertEquals(ViewEnum.LAST_EVENT.getClazz(), views.get(2).getClass());

        // Check that the context is set
        specifications = SupportViewSpecFactory.makeSpecListFive();
        views = ViewServiceHelper.instantiateChain(existingParentViews, topView, specifications, context);
        TimeWindowView timeWindow = (TimeWindowView) views.get(0);
        assertEquals(context, timeWindow.getViewServiceContext());
    }

    public void testMatch() throws Exception
    {
        SupportStreamImpl stream = new SupportStreamImpl(TEST_CLASS, 10);
        List<ViewSpec> specifications = SupportViewSpecFactory.makeSpecListOne();
        Map<View, ViewSpec> repository = new HashMap<View, ViewSpec>();

        // No views under stream, no matches
        Pair<Viewable, List<View>> result = ViewServiceHelper.matchExistingViews(stream, repository, specifications);
        assertEquals(stream, result.getFirst());
        assertEquals(3, specifications.size());
        assertEquals(0, result.getSecond().size());

        // One top view under the stream that doesn't match
        SupportBeanClassView testView = new SupportBeanClassView(TEST_CLASS);
        repository.put(testView, SupportViewSpecFactory.makeSpec("std", "size", null, null));
        stream.addView(testView);
        result = ViewServiceHelper.matchExistingViews(stream, repository, specifications);

        assertEquals(stream, result.getFirst());
        assertEquals(3, specifications.size());
        assertEquals(0, result.getSecond().size());

        // Another top view under the stream that doesn't matches again
        testView = new SupportBeanClassView(TEST_CLASS);
        repository.put(testView, specifications.get(1));
        stream.addView(testView);
        result = ViewServiceHelper.matchExistingViews(stream, repository, specifications);

        assertEquals(stream, result.getFirst());
        assertEquals(3, specifications.size());
        assertEquals(0, result.getSecond().size());

        // One top view under the stream that does actually match
        SupportBeanClassView matchViewOne = new SupportBeanClassView(TEST_CLASS);
        repository.put(matchViewOne, specifications.get(0));
        stream.addView(matchViewOne);
        result = ViewServiceHelper.matchExistingViews(stream, repository, specifications);

        assertEquals(matchViewOne, result.getFirst());
        assertEquals(2, specifications.size());
        assertEquals(1, result.getSecond().size());
        assertEquals(matchViewOne, result.getSecond().get(0));

        // One child view under the top veiew that does not match
        testView = new SupportBeanClassView(TEST_CLASS);
        specifications = SupportViewSpecFactory.makeSpecListOne();
        repository.put(testView, specifications.get(2));
        matchViewOne.addView(testView);
        result = ViewServiceHelper.matchExistingViews(stream, repository, specifications);
        assertEquals(1, result.getSecond().size());
        assertEquals(matchViewOne, result.getSecond().get(0));

        assertEquals(matchViewOne, result.getFirst());
        assertEquals(2, specifications.size());

        // Add child view under the top veiw that does match
        SupportBeanClassView matchViewTwo = new SupportBeanClassView(TEST_CLASS);
        specifications = SupportViewSpecFactory.makeSpecListOne();
        repository.put(matchViewTwo, specifications.get(1));
        matchViewOne.addView(matchViewTwo);
        result = ViewServiceHelper.matchExistingViews(stream, repository, specifications);

        assertEquals(matchViewTwo, result.getFirst());
        assertEquals(1, specifications.size());

        // Add ultimate child view under the child view that does match
        SupportBeanClassView matchViewThree = new SupportBeanClassView(TEST_CLASS);
        specifications = SupportViewSpecFactory.makeSpecListOne();
        repository.put(matchViewThree, specifications.get(2));
        matchViewTwo.addView(matchViewThree);
        result = ViewServiceHelper.matchExistingViews(stream, repository, specifications);

        assertEquals(matchViewThree, result.getFirst());
        assertEquals(0, specifications.size());
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