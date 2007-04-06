package net.esper.view;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.view.stat.UnivariateStatisticsViewFactory;
import net.esper.client.ConfigurationPlugInView;
import net.esper.client.ConfigurationException;
import net.esper.support.view.SupportViewFactoryOne;
import net.esper.support.view.SupportViewFactoryTwo;
import net.esper.eql.spec.ViewSpec;

public class TestViewResolutionService extends TestCase
{
    private ViewResolutionService service;

    public void setUp()
    {
        service = new ViewResolutionServiceImpl(null);
    }

    public void testInitializeFromConfig() throws Exception
    {
        service = createService(new String[] {"a", "b"}, new String[] {"v1", "v2"},
                new String[] {SupportViewFactoryOne.class.getName(), SupportViewFactoryTwo.class.getName()});

        ViewFactory factory = service.create(new ViewSpec("a", "v1", new LinkedList<Object>()));
        assertTrue(factory instanceof SupportViewFactoryOne);

        factory = service.create(new ViewSpec("b", "v2", new LinkedList<Object>()));
        assertTrue(factory instanceof SupportViewFactoryTwo);

        tryInvalid("a", "v3");
        tryInvalid("c", "v1");

        try
        {
            service = createService(new String[] {"a"}, new String[] {"v1"}, new String[] {"abc"});
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    private void tryInvalid(String namespace, String name)
    {
        try
        {
            service.create(new ViewSpec(namespace, name, new LinkedList<Object>()));
            fail();
        }
        catch (ViewProcessingException ex)
        {
            // expected
        }
    }
    
    public void testCreate() throws Exception
    {
        List<Object> parameters = new LinkedList<Object>();
        parameters.add("price");
        ViewSpec spec = new ViewSpec(ViewEnum.UNIVARIATE_STATISTICS.getNamespace(), ViewEnum.UNIVARIATE_STATISTICS.getName(), parameters);

        ViewFactory viewFactory = service.create(spec);
        assertTrue(viewFactory instanceof UnivariateStatisticsViewFactory);
    }

    public void testInvalidViewName()
    {
        ViewSpec spec = new ViewSpec("dummy", "bumblebee", null);

        try
        {
            service.create(spec);
            assertFalse(true);
        }
        catch (ViewProcessingException ex)
        {
            log.debug(".testInvalidViewName Expected exception caught, msg=" + ex.getMessage());
        }
    }


    private ViewResolutionService createService(String[] namespaces, String[] names, String[] classNames)
    {
        List<ConfigurationPlugInView> configs = new LinkedList<ConfigurationPlugInView>();
        for (int i = 0; i < namespaces.length; i++)
        {
            ConfigurationPlugInView config = new ConfigurationPlugInView();
            config.setNamespace(namespaces[i]);
            config.setName(names[i]);
            config.setFactoryClassName(classNames[i]);
            configs.add(config);
        }
        
        return new ViewResolutionServiceImpl(configs);
    }

    private static final Log log = LogFactory.getLog(TestViewResolutionService.class);
}
