package net.esper.view;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.type.StringValue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestViewFactory extends TestCase
{
    private SupportBeanClassView parentViewable = new SupportBeanClassView(SupportMarketDataBean.class);

    public void testCreate() throws Exception
    {
        List<Object> parameters = new LinkedList<Object>();
        parameters.add("price");
        ViewSpec spec = new ViewSpec(ViewEnum.UNIVARIATE_STATISTICS.getNamespace(), ViewEnum.UNIVARIATE_STATISTICS.getName(), parameters);

        Viewable view = ViewFactory.create(parentViewable, spec);
        if (view instanceof ContextAwareView)
        {
            ContextAwareView contextAwareView = (ContextAwareView) view;
            contextAwareView.setViewServiceContext(SupportViewContextFactory.makeContext());            
        }

        assertTrue(view != null);
        assertTrue(view.getViews().size() == 0);
        assertTrue(view.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.getName()) != null);
    }

    public void testInvalidViewName()
    {
        ViewSpec spec = new ViewSpec("dummy", "bumblebee", null);

        try
        {
            ViewFactory.create(parentViewable, spec);
            assertFalse(true);
        }
        catch (ViewProcessingException ex)
        {
            log.debug(".testInvalidViewName Expected exception caught, msg=" + ex.getMessage());
        }
    }

    public void testInvalidViewParameters()
    {
        // Forget to populate view parameters, should error
        List<Object> parameters = new LinkedList<Object>();
        ViewSpec spec = new ViewSpec("stat", "uni", parameters);

        try
        {
            ViewFactory.create(parentViewable, spec);
            assertFalse(true);
        }
        catch (ViewProcessingException ex)
        {
            log.debug(".testInvalidViewParameters Expected exception caught, msg=" + ex.getMessage());
        }
    }

    public void testInvalidAttach()
    {
        // View doesn't allow non-numeric fields, the factory should check this
        List<Object> parameters = new LinkedList<Object>();
        parameters.add(new StringValue("symbol"));
        ViewSpec spec = new ViewSpec("stat", "uni", parameters);

        try
        {
            ViewFactory.create(parentViewable, spec);
            assertFalse(true);
        }
        catch (ViewProcessingException ex)
        {
            log.debug(".testInvalidViewName Expected exception caught, msg=" + ex.getMessage());
        }
    }

    private static final Log log = LogFactory.getLog(TestViewFactory.class);
}
