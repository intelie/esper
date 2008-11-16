package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.util.SerializableObjectCopier;

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

    // This is a simple EPL only.
    // Each OM/SODA Api is tested in it's respective unit test (i.e. TestInsertInto), including toEPL()
    // 
    public void testCreateFromOM() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName())));
        SerializableObjectCopier.copy(model);

        EPStatement stmt = epService.getEPAdministrator().create(model, "s1");
        stmt.addListener(listener);

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().getUnderlying());
    }

    // This is a simple EPL only.
    // Each OM/SODA Api is tested in it's respective unit test (i.e. TestInsertInto), including toEPL()
    //
    public void testCreateFromOMComplete() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setInsertInto(InsertIntoClause.create("ReadyStreamAvg", "line", "avgAge"));
        model.setSelectClause(SelectClause.create()
            .add("line")
            .add(Expressions.avg("age"), "avgAge"));
        Filter filter = Filter.create(SupportBean.class.getName(), Expressions.in("line", 1, 8, 10));
        model.setFromClause(FromClause.create(FilterStream.create(filter, "RS").addView("win", "time", Expressions.constant(10))));
        model.setWhereClause(Expressions.isNotNull("waverId"));
        model.setGroupByClause(GroupByClause.create("line"));
        model.setHavingClause(Expressions.lt(Expressions.avg("age"), Expressions.constant(0)));
        model.setOutputLimitClause(OutputLimitClause.create(10, OutputLimitUnit.SECONDS));
        model.setOrderByClause(OrderByClause.create("line"));                

        assertEquals("insert into ReadyStreamAvg(line, avgAge) select line, avg(age) as avgAge from com.espertech.esper.support.bean.SupportBean(line in (1, 8, 10)).win:time(10) as RS where (waverId != null) group by line having (avg(age) < 0) output every 10.0 seconds order by line", model.toEPL());
        SerializableObjectCopier.copy(model);
    }

    public void testCompileToOM() throws Exception
    {
        String stmtText = "select * from " + SupportBean.class.getName();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        SerializableObjectCopier.copy(model);
        assertNotNull(model);
    }
    
    public void testEPLtoOMtoStmt() throws Exception
    {
        String stmtText = "select * from " + SupportBean.class.getName();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        SerializableObjectCopier.copy(model);

        EPStatement stmt = epService.getEPAdministrator().create(model, "s1");
        stmt.addListener(listener);

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().getUnderlying());
        assertEquals(stmtText, stmt.getText());
        assertEquals("s1", stmt.getName());
    }
}
