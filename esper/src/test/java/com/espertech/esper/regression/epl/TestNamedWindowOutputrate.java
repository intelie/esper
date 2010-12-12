package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerEvent;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.EPStatementSPI;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.epl.named.NamedWindowProcessor;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class TestNamedWindowOutputrate extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testOutputSnapshot() {
        epService.getEPAdministrator().createEPL("create schema SupportBean as " + SupportBean.class.getName());

        epService.getEPAdministrator().createEPL("create window MyWindowOne.win:keepall() as (string string, intv int)");
        epService.getEPAdministrator().createEPL("insert into MyWindowOne select string, intPrimitive as intv from SupportBean");

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        String[] fields = new String[] {"string","c"};
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select irstream string, count(*) as c from MyWindowOne group by string output snapshot every 1 second");
        stmtSelect.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("A", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("A", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("B", 4));

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));

        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"A", 2L}, {"B", 1L}});

        epService.getEPRuntime().sendEvent(new SupportBean("B", 5));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2000));

        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"A", 2L}, {"B", 2L}});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(3000));

        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"A", 2L}, {"B", 2L}});

        epService.getEPRuntime().sendEvent(new SupportBean("A", 5));
        epService.getEPRuntime().sendEvent(new SupportBean("C", 1));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(4000));

        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"A", 3L}, {"B", 2L}, {"C", 1L}});
    }
}
