// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.eql.spec;
using net.esper.pattern.guard;
using net.esper.pattern.observer;
using net.esper.support.pattern;

namespace net.esper.pattern
{
	[TestFixture]
	public class TestPatternObjectResolutionServiceImpl
	{
	    private PatternObjectResolutionServiceImpl service;

	    [SetUp]
	    public void SetUp()
	    {
	        List<ConfigurationPlugInPatternObject> init = new List<ConfigurationPlugInPatternObject>();
	        init.Add(MakeGuardSpec("g", "h", typeof(SupportGuardFactory).FullName));
	        init.Add(MakeObserverSpec("a", "b", typeof(SupportObserverFactory).FullName));
	        service = new PatternObjectResolutionServiceImpl(init);
	    }

	    [Test]
	    public void TestMake()
	    {
	        Assert.IsTrue(service.Create(new PatternGuardSpec("g", "h", new Object[] {100})) is SupportGuardFactory);
	        Assert.IsTrue(service.Create(new PatternObserverSpec("a", "b", new Object[] {100})) is SupportObserverFactory);
	        Assert.IsTrue(service.Create(new PatternGuardSpec("timer", "within", new Object[] {100})) is TimerWithinGuardFactory);
	        Assert.IsTrue(service.Create(new PatternObserverSpec("timer", "interval", new Object[] {100})) is TimerIntervalObserverFactory);
	    }

	    [Test]
	    public void TestInvalidConfig()
	    {
	        List<ConfigurationPlugInPatternObject> init = new List<ConfigurationPlugInPatternObject>();
	        init.Add(MakeGuardSpec("x", "y", "a"));
	        TryInvalid(init);

	        init.Clear();
	        init.Add(MakeGuardSpec("a", "b", null));
	        TryInvalid(init);
	    }

	    private void TryInvalid(IList<ConfigurationPlugInPatternObject> config)
	    {
	        try
	        {
	            service = new PatternObjectResolutionServiceImpl(config);
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            // expected
	        }
	    }


	    private ConfigurationPlugInPatternObject MakeGuardSpec(String _namespace, String name, String factory)
	    {
	        ConfigurationPlugInPatternObject guardSpec = new ConfigurationPlugInPatternObject();
            guardSpec.Namespace = _namespace;
	        guardSpec.Name = name;
            guardSpec.PatternObjectType = ConfigurationPlugInPatternObject.PatternObjectTypeEnum.GUARD;
            guardSpec.FactoryClassName = factory;
	        return guardSpec;
	    }

	    private ConfigurationPlugInPatternObject MakeObserverSpec(String _namespace, String name, String factory)
	    {
	        ConfigurationPlugInPatternObject obsSpec = new ConfigurationPlugInPatternObject();
	        obsSpec.Namespace = _namespace;
	        obsSpec.Name = name;
            obsSpec.PatternObjectType = ConfigurationPlugInPatternObject.PatternObjectTypeEnum.OBSERVER;
	        obsSpec.FactoryClassName = factory;
	        return obsSpec;
	    }
	}
} // End of namespace
