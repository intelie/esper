package com.espertech.esper.regression.rev;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestNamedWinRevision extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerOne;
    private SupportUpdateListener listenerTwo;
    private SupportUpdateListener listenerThree;
    private SupportUpdateListener listenerFour;

    // TODO: test invalid configuration
    
    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        config.addEventTypeAlias("FullEvent", SupportRevisionFull.class);
        config.addEventTypeAlias("D1", SupportDeltaOne.class);
        config.addEventTypeAlias("D2", SupportDeltaTwo.class);
        config.addEventTypeAlias("D3", SupportDeltaThree.class);
        config.addEventTypeAlias("D4", SupportDeltaFour.class);
        config.addEventTypeAlias("D5", SupportDeltaFive.class);

        ConfigurationRevisionEvent configRev = new ConfigurationRevisionEvent();
        configRev.setKeyPropertyNames(new String[] {"k0"});
        configRev.setAliasFullEvent("FullEvent");
        configRev.addAliasRevisionEvent("D1");
        configRev.addAliasRevisionEvent("D2");
        configRev.addAliasRevisionEvent("D3");
        configRev.addAliasRevisionEvent("D4");
        configRev.addAliasRevisionEvent("D5");
        config.addRevisionEvent("RevisableQuote", configRev);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerOne = new SupportUpdateListener();
        listenerTwo = new SupportUpdateListener();
        listenerThree = new SupportUpdateListener();
        listenerFour = new SupportUpdateListener();

        epService.getEPAdministrator().createEPL("create window RevQuote.win:keepall() as select * from RevisableQuote");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from FullEvent");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D1");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D2");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D3");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D4");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D5");
    }

    public void testRevision()
    {
        EPStatement consumerOne = epService.getEPAdministrator().createEPL("select * from RevQuote");
        consumerOne.addListener(listenerOne);
        EPStatement consumerTwo = epService.getEPAdministrator().createEPL("select k0, count(*), sum(Long.parse(p0)) as sumP0 from RevQuote group by k0");
        consumerTwo.addListener(listenerTwo);
    }
}
