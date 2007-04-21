package net.esper.pattern;

import net.esper.client.ConfigurationPlugInPatternObject;
import net.esper.client.ConfigurationException;
import net.esper.eql.spec.PatternGuardSpec;
import net.esper.eql.spec.PatternObserverSpec;
import net.esper.support.pattern.SupportObserverFactory;
import net.esper.support.pattern.SupportGuardFactory;
import net.esper.pattern.guard.TimerWithinGuardFactory;
import net.esper.pattern.observer.TimerIntervalObserverFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class TestPatternObjectResolutionServiceImpl extends TestCase
{
    private PatternObjectResolutionServiceImpl service;

    public void setUp()
    {
        List<ConfigurationPlugInPatternObject> init = new ArrayList<ConfigurationPlugInPatternObject>();
        init.add(makeGuardSpec("g", "h", SupportGuardFactory.class.getName()));
        init.add(makeObserverSpec("a", "b", SupportObserverFactory.class.getName()));
        service = new PatternObjectResolutionServiceImpl(init);
    }

    public void testMake() throws Exception
    {
        assertTrue(service.create(new PatternGuardSpec("g", "h", Arrays.asList(new Object[] {100}))) instanceof SupportGuardFactory);
        assertTrue(service.create(new PatternObserverSpec("a", "b", Arrays.asList(new Object[] {100}))) instanceof SupportObserverFactory);
        assertTrue(service.create(new PatternGuardSpec("timer", "within", Arrays.asList(new Object[] {100}))) instanceof TimerWithinGuardFactory);
        assertTrue(service.create(new PatternObserverSpec("timer", "interval", Arrays.asList(new Object[] {100}))) instanceof TimerIntervalObserverFactory);
    }

    public void testInvalidConfig()
    {
        List<ConfigurationPlugInPatternObject> init = new ArrayList<ConfigurationPlugInPatternObject>();
        init.add(makeGuardSpec("x", "y", "a"));
        tryInvalid(init);

        init.clear();
        init.add(makeGuardSpec("a", "b", null));
        tryInvalid(init);
    }

    private void tryInvalid(List<ConfigurationPlugInPatternObject> config)
    {
        try
        {
            service = new PatternObjectResolutionServiceImpl(config);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }


    private ConfigurationPlugInPatternObject makeGuardSpec(String namespace, String name, String factory)
    {
        ConfigurationPlugInPatternObject guardSpec = new ConfigurationPlugInPatternObject();
        guardSpec.setNamespace(namespace);
        guardSpec.setName(name);
        guardSpec.setPatternObjectType(ConfigurationPlugInPatternObject.PatternObjectType.GUARD);
        guardSpec.setFactoryClassName(factory);
        return guardSpec; 
    }

    private ConfigurationPlugInPatternObject makeObserverSpec(String namespace, String name, String factory)
    {
        ConfigurationPlugInPatternObject obsSpec = new ConfigurationPlugInPatternObject();
        obsSpec.setNamespace(namespace);
        obsSpec.setName(name);
        obsSpec.setPatternObjectType(ConfigurationPlugInPatternObject.PatternObjectType.OBSERVER);
        obsSpec.setFactoryClassName(factory);
        return obsSpec;
    }
}
