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

package com.espertech.esper.multithread;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import junit.framework.TestCase;

public class TestMTStmtPatternFollowedBy extends TestCase {

    public void testPatternFollowedBy() throws InterruptedException {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("S0", SupportBean_S0.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] epls = {
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=0)->sb=S0(id=1)) or (sc=S0(id=1)->sd=S0(id=0))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=1)->sb=S0(id=2)) or (sc=S0(id=2)->sd=S0(id=1))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=2)->sb=S0(id=3)) or (sc=S0(id=3)->sd=S0(id=2))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=3)->sb=S0(id=4)) or (sc=S0(id=4)->sd=S0(id=3))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=4)->sb=S0(id=5)) or (sc=S0(id=5)->sd=S0(id=4))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=5)->sb=S0(id=6)) or (sc=S0(id=6)->sd=S0(id=5))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=6)->sb=S0(id=7)) or (sc=S0(id=7)->sd=S0(id=6))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=7)->sb=S0(id=8)) or (sc=S0(id=8)->sd=S0(id=7))]",
                "select sa.id,sb.id,sc.id,sd.id from pattern [(sa=S0(id=8)->sb=S0(id=9)) or (sc=S0(id=9)->sd=S0(id=8))]"
        };

        for (int i = 0; i < 100; i++) {
            SupportMTUpdateListener listener = new SupportMTUpdateListener();
            EPStatement[] stmts = new EPStatement[epls.length];
            for (int j = 0; j < epls.length; j++) {
                stmts[j] = epService.getEPAdministrator().createEPL(epls[j]);
                stmts[j].addListener(listener);
            }

            int[] threadOneValues = new int[] {0, 2, 4, 6, 8};
            int[] threadTwoValues = new int[] {1, 3, 5, 7, 9};

            Thread threadOne = new Thread(new SenderRunnable(epService.getEPRuntime(), threadOneValues));
            Thread threadTwo = new Thread(new SenderRunnable(epService.getEPRuntime(), threadTwoValues));

            threadOne.start();
            threadTwo.start();
            threadOne.join();
            threadTwo.join();

            EventBean[] events = listener.getNewDataListFlattened();
            /* Comment in to print events delivered.
            for (int j = 0; j < events.length; j++) {
                EventBean out = events[j];
                /*
                System.out.println(" sa=" + getNull(out.get("sa.id")) +
                                   " sb=" + getNull(out.get("sb.id")) +
                                   " sc=" + getNull(out.get("sc.id")) +
                                   " sd=" + getNull(out.get("sd.id")));
            }
             */
            assertEquals(9, events.length);

            for (int j = 0; j < epls.length; j++) {
                stmts[j].destroy();
            }
        }
    }

    public static class SenderRunnable implements Runnable {

        private final EPRuntime runtime;
        private final int[] values;

        public SenderRunnable(EPRuntime runtime, int[] values) {
            this.runtime = runtime;
            this.values = values;
        }

        public void run() {
            for (int i = 0; i < values.length; i++) {
                runtime.sendEvent(new SupportBean_S0(values[i]));
            }
        }
    }

    private String getNull(Object value) {
        if (value == null) {
            return "-";
        }
        return value.toString();
    }
}
