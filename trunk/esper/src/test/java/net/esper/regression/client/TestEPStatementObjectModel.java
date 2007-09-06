package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementState;
import net.esper.client.soda.*;
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

    // This is a simple EQL only.
    // Each OM/SODA Api is tested in it's respective unit test (i.e. TestInsertInto), including toEQL()
    //
    public void testCreateFromOMComplete()
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setInsertInto(InsertIntoClause.create("ReadyStreamAvg", "line", "avgAge"));
        model.setSelectClause(SelectClause.create()
            .add("line")
            .add(Expressions.avg("age"), "avgAge"));
        Filter filter = Filter.create(SupportBean.class.getName(), Expressions.in("line", 1, 8, 10));
        model.setFromClause(FromClause.create(FilterStream.create(filter, "RS").addView("win", "time", 10)));
        model.setWhereClause(Expressions.isNotNull("waverId"));
        model.setGroupByClause(GroupByClause.create("line"));
        model.setHavingClause(Expressions.lt(Expressions.avg("age"), Expressions.constant(0)));
        model.setOutputLimitClause(OutputLimitClause.create(10, OutputLimitUnit.SECONDS));
        model.setOrderByClause(OrderByClause.create("line"));                

        assertEquals("", model.toEQL());
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
