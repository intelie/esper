package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestNamedWindowIndexAddedValType extends TestCase
{
    public void testRevision()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean_S0", SupportBean_S0.class);
        config.addEventType("SupportBean_S1", SupportBean_S1.class);

        ConfigurationRevisionEventType revType = new ConfigurationRevisionEventType();
        revType.addNameBaseEventType("SupportBean_S0");
        revType.addNameDeltaEventType("SupportBean_S1");
        revType.setKeyPropertyNames(new String[] {"id"});
        revType.setPropertyRevision(ConfigurationRevisionEventType.PropertyRevision.MERGE_EXISTS);
        config.addRevisionEventType("RevType", revType);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        // widen to long
        String stmtTextCreate = "create window MyWindowOne.win:keepall() as select * from RevType";
        epService.getEPAdministrator().createEPL(stmtTextCreate);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne select * from SupportBean_S0");
        epService.getEPAdministrator().createEPL("insert into MyWindowOne select * from SupportBean_S1");

        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex1 on MyWindowOne(p10)");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex2 on MyWindowOne(p00)");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "p00"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "p10"));

        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where p10='1'");
    }
}