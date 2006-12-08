package net.esper.view;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.view.stat.UnivariateStatisticsViewFactory;

public class TestViewFactoryFactory extends TestCase
{
    public void testCreate() throws Exception
    {
        List<Object> parameters = new LinkedList<Object>();
        parameters.add("price");
        ViewSpec spec = new ViewSpec(ViewEnum.UNIVARIATE_STATISTICS.getNamespace(), ViewEnum.UNIVARIATE_STATISTICS.getName(), parameters);

        ViewFactory viewFactory = ViewFactoryFactory.create(spec);
        assertTrue(viewFactory instanceof UnivariateStatisticsViewFactory);
    }

    public void testInvalidViewName()
    {
        ViewSpec spec = new ViewSpec("dummy", "bumblebee", null);

        try
        {
            ViewFactoryFactory.create(spec);
            assertFalse(true);
        }
        catch (ViewProcessingException ex)
        {
            log.debug(".testInvalidViewName Expected exception caught, msg=" + ex.getMessage());
        }
    }

    private static final Log log = LogFactory.getLog(TestViewFactoryFactory.class);
}
