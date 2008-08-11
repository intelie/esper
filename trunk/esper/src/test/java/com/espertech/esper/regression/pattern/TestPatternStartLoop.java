package com.espertech.esper.regression.pattern;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPatternStartLoop extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement patternStmt;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    /**
     * Starting this statement fires an event and the listener starts a new statement (same expression) again,
     * causing a loop. This listener limits to 10 - this is a smoke test.
     */
    public void testStartFireLoop()
    {
        String patternExpr = "not " + SupportBean.class.getName();
        patternStmt = epService.getEPAdministrator().createPattern(patternExpr);
        patternStmt.addListener(new PatternUpdateListener());
        patternStmt.stop();
        patternStmt.start();
    }

    class PatternUpdateListener implements UpdateListener {

        private int count = 0;

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            log.warn(".update");

            if (count < 10)
            {
                count++;
                String patternExpr = "not " + SupportBean.class.getName();
                patternStmt = epService.getEPAdministrator().createPattern(patternExpr);
                patternStmt.addListener(this);
                patternStmt.stop();
                patternStmt.start();
            }
        }

        public int getCount()
        {
            return count;
        }
    };

    private static Log log = LogFactory.getLog(TestPatternStartLoop.class);
}
