package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;

public class TestEPStatement extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testStatementDestroy()
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName(), "s1");
        stmt.addListener(testListener);
        sendEvent();
        testListener.assertOneGetNewAndReset();

        stmt.destroy();
        sendEvent();
        assertFalse(testListener.isInvoked());        
    }

    private EPStatement[] createStmts(String[] statementNames)
    {
        EPStatement statements[] = new EPStatement[statementNames.length];
        for (int i = 0; i < statementNames.length; i++)
        {
            statements[i] = epService.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName(), statementNames[i]);
        }
        return statements;
    }

    private EPStatement createStmt(String statementName)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName(), statementName);
        return stmt;
    }

    private void sendEvent()
    {
        SupportBean bean = new SupportBean();
        epService.getEPRuntime().sendEvent(bean);
    }
}
