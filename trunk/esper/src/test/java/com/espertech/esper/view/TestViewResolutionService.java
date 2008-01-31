package com.espertech.esper.view;

import junit.framework.TestCase;
import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.ConfigurationPlugInView;
import com.espertech.esper.support.view.SupportViewFactoryOne;
import com.espertech.esper.support.view.SupportViewFactoryTwo;
import com.espertech.esper.view.stat.UnivariateStatisticsViewFactory;
import com.espertech.esper.eql.spec.PluggableObjectCollection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.List;

public class TestViewResolutionService extends TestCase
{
    private ViewResolutionService service;

    public void setUp()
    {
        service = new ViewResolutionServiceImpl(ViewEnumHelper.getBuiltinViews());
    }

    public void testInitializeFromConfig() throws Exception
    {
        service = createService(new String[] {"a", "b"}, new String[] {"v1", "v2"},
                new String[] {SupportViewFactoryOne.class.getName(), SupportViewFactoryTwo.class.getName()});

        ViewFactory factory = service.create("a", "v1");
        assertTrue(factory instanceof SupportViewFactoryOne);

        factory = service.create("b", "v2");
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
            service.create(namespace, name);
            fail();
        }
        catch (ViewProcessingException ex)
        {
            // expected
        }
    }
    
    public void testCreate() throws Exception
    {
        ViewFactory viewFactory = service.create(ViewEnum.UNIVARIATE_STATISTICS.getNamespace(), ViewEnum.UNIVARIATE_STATISTICS.getName());
        assertTrue(viewFactory instanceof UnivariateStatisticsViewFactory);
    }

    public void testInvalidViewName()
    {
        try
        {
            service.create("dummy", "bumblebee");
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

        PluggableObjectCollection desc = new PluggableObjectCollection();
        desc.addViews(configs);
        return new ViewResolutionServiceImpl(desc);
    }

    private static final Log log = LogFactory.getLog(TestViewResolutionService.class);
}
