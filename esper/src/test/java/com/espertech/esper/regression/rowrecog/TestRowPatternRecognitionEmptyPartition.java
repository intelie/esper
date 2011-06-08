package com.espertech.esper.regression.rowrecog;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRowPatternRecognitionEmptyPartition extends TestCase {

    private static final Log log = LogFactory.getLog(TestRowPatternRecognitionEmptyPartition.class);

    public void testEmptyPartition()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "value".split(",");
        String text = "select * from MyEvent.win:length(10) " +
                "match_recognize (" +
                "  partition by value" +
                "  measures E1.value as value" +
                "  pattern (E1 E2 | E2 E1 ) " +
                "  define " +
                "    E1 as E1.string = 'A', " +
                "    E2 as E2.string = 'B' " +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("A", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("B", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("B", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("A", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("B", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("A", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("A", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("B", 4));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("A", 6));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("B", 7));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("B", 8));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("A", 7));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {7});

        /**
         * Comment-in for testing partition removal.
         */
        for (int i = 0; i < 1000000; i++) {
            epService.getEPRuntime().sendEvent(new SupportRecogBean("A", i));
            //epService.getEPRuntime().sendEvent(new SupportRecogBean("B", i));
            //ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {i});
        }
    }
}
