package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementState;
import net.esper.client.soda.EPStatementObjectModel;
import net.esper.client.soda.SelectClause;
import net.esper.client.soda.FromClause;
import net.esper.client.soda.FilterStream;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.SupportBean;

public class TestEPStatementObjectModel extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    // This is a simple EQL only.
    // Each OM/SODA Api is tested in it's respective unit test (i.e. TestInsertInto), including toEQL()
    // 
    public void testCreateFromOM()
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName())));

        EPStatement stmt = epService.getEPAdministrator().create(model, "s1");
        stmt.addListener(listener);

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testCompileToOM()
    {
        String stmtText = "select * from " + SupportBean.class.getName();
        EPStatementObjectModel model = epService.getEPAdministrator().compile(stmtText);
        assertNotNull(model);        
    }
    
    public void testEQLtoOMtoStmt()
    {
        String stmtText = "select * from " + SupportBean.class.getName();
        EPStatementObjectModel model = epService.getEPAdministrator().compile(stmtText);

        EPStatement stmt = epService.getEPAdministrator().create(model, "s1");
        stmt.addListener(listener);

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().getUnderlying());
        assertEquals(stmtText, stmt.getText());
        assertEquals("s1", stmt.getName());
    }
}
