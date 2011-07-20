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

package com.espertech.esper.regression.rowrecog;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRowPatternRecognitionPerf extends TestCase {

    private static final Log log = LogFactory.getLog(TestRowPatternRecognitionPerf.class);

    public void testPerfDisregardedMultimatches()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String text = "select * from MyEvent " +
                "match_recognize (" +
                "  partition by value " +
                "  measures A.string as a_string, C.string as c_string " +
                "  all matches " +
                "  pattern (A B*? C) " +
                "  define A as A.cat = '1'," +
                "         B as B.cat = '2'," +
                "         C as C.cat = '3'" +
                ")";
        // When testing aggregation:
        //"  measures A.string as a_string, count(B.string) as cntb, C.string as c_string " +

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        long start = System.currentTimeMillis();

        for (int partition = 0; partition < 2; partition++) {
            epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", "1", partition));
            for (int i = 0; i < 25000; i++) {
                epService.getEPRuntime().sendEvent(new SupportRecogBean("E2_" + i, "2", partition));
            }
            assertFalse(listener.isInvoked());

            epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", "3", partition));
            assertTrue(listener.getAndClearIsInvoked());
        }

        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 2000);
    }
}