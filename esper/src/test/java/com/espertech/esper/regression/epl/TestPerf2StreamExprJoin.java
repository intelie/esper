/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanRange;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPerf2StreamExprJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_ST0", SupportBean_ST0.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);
    }

    public void test2Stream1Hash2HashConstant()
    {
        String epl;

        epl = "select intPrimitive as val from SupportBean.win:keepall() sb, SupportBean_ST0.std:lastevent() s0 where sb.string = 'E6750'";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6750);

        epl = "select intPrimitive as val from SupportBean_ST0.std:lastevent() s0, SupportBean.win:keepall() sb where sb.string = 'E6749'";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6749);

        epService.getEPAdministrator().createEPL("create variable string myconst = 'E6751'");
        epl = "select intPrimitive as val from SupportBean_ST0.std:lastevent() s0, SupportBean.win:keepall() sb where sb.string = myconst";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6751);

        epl = "select intPrimitive as val from SupportBean_ST0.std:lastevent() s0, SupportBean.win:keepall() sb where sb.string = (id || '6752')";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6752);

        epl = "select intPrimitive as val from SupportBean.win:keepall() sb, SupportBean_ST0.std:lastevent() s0 where sb.string = (id || '6753')";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6753);

        epl = "select intPrimitive as val from SupportBean.win:keepall() sb, SupportBean_ST0.std:lastevent() s0 where sb.string = 'E6754' and sb.intPrimitive=6754'";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6754);

        epl = "select intPrimitive as val from SupportBean_ST0.std:lastevent() s0, SupportBean.win:keepall() sb where sb.string = (id || '6755') and sb.intPrimitive=6755";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6755);

        epl = "select intPrimitive as val from SupportBean_ST0.std:lastevent() s0, SupportBean.win:keepall() sb where sb.intPrimitive between 6756 and 6756";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6756);

        epl = "select intPrimitive as val from SupportBean_ST0.std:lastevent() s0, SupportBean.win:keepall() sb where sb.intPrimitive >= 6757 and intPrimitive <= 6757";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6757);

        epl = "select intPrimitive as val from SupportBean_ST0.std:lastevent() s0, SupportBean.win:keepall() sb where sb.string = 'E6758' and sb.intPrimitive >= 6758 and intPrimitive <= 6758";
        runAssertion(epl, new SupportBean_ST0("E", -1), 6758);

        epl = "select sum(intPrimitive) as val from SupportBeanRange.std:lastevent() s0, SupportBean.win:keepall() sb where sb.intPrimitive >= (rangeStart + 1) and intPrimitive <= (rangeEnd - 1)";
        runAssertion(epl, new SupportBeanRange("R1", 6000, 6005), 6001+6002+6003+6004);

        epl = "select sum(intPrimitive) as val from SupportBeanRange.std:lastevent() s0, SupportBean.win:keepall() sb where sb.intPrimitive >= 6001 and intPrimitive <= (rangeEnd - 1)";
        runAssertion(epl, new SupportBeanRange("R1", 6000, 6005), 6001+6002+6003+6004);

        epl = "select sum(intPrimitive) as val from SupportBeanRange.std:lastevent() s0, SupportBean.win:keepall() sb where sb.intPrimitive between (rangeStart + 1) and (rangeEnd - 1)";
        runAssertion(epl, new SupportBeanRange("R1", 6000, 6005), 6001+6002+6003+6004);

        epl = "select sum(intPrimitive) as val from SupportBeanRange.std:lastevent() s0, SupportBean.win:keepall() sb where sb.intPrimitive between (rangeStart + 1) and 6004";
        runAssertion(epl, new SupportBeanRange("R1", 6000, 6005), 6001+6002+6003+6004);

        epl = "select sum(intPrimitive) as val from SupportBeanRange.std:lastevent() s0, SupportBean.win:keepall() sb where sb.intPrimitive in (6001 : (rangeEnd - 1)]";
        runAssertion(epl, new SupportBeanRange("R1", 6000, 6005), 6002+6003+6004);
    }

    private void runAssertion(String epl, Object event, Object expected) {

        String[] fields = "val".split(",");
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("E" + i, i));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(event);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{expected});
        }
        long delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        log.info("delta=" + delta);

        epService.getEPAdministrator().destroyAllStatements();
    }

    private static final Log log = LogFactory.getLog(TestPerf2StreamExprJoin.class);
}
