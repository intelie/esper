package com.espertech.esper.regression.rowrecog;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestRowPatternRecognitionVariantStream extends TestCase {

    public void testInstanceOfDynamicVariantStream()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("S0", SupportBean_S0.class);
        config.addEventType("S1", SupportBean_S1.class);

        ConfigurationVariantStream variantStreamConfig = new ConfigurationVariantStream();
        variantStreamConfig.addEventTypeName("S0");
        variantStreamConfig.addEventTypeName("S1");
        config.addVariantStream("MyVariantType", variantStreamConfig);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a,b".split(",");
        String text = "select * from MyVariantType.win:keepall() " +
                "match_recognize (" +
                "  measures A.id? as a, B.id? as b" +
                "  pattern (A B) " +
                "  define " +
                "    A as instanceof(A, " + SupportBean_S0.class.getName() + ")," +
                "    B as instanceof(B, " + SupportBean_S1.class.getName() + ")" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPAdministrator().createEPL("insert into MyVariantType select * from S0");
        epService.getEPAdministrator().createEPL("insert into MyVariantType select * from S1");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "S0"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "S1"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{1, 2}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{1, 2}});
    }
}