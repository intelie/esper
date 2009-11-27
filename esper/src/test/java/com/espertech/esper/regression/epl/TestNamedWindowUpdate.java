package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.core.EPStatementSPI;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanReadOnly;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestNamedWindowUpdate extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerSelect;
    private SupportUpdateListener listenerUpdate;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerSelect = new SupportUpdateListener();
        listenerUpdate = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("S0", SupportBean_S0.class);
    }

    public void testInvalid() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanReadOnly", SupportBeanReadOnly.class);

        epService.getEPAdministrator().createEPL("create window MyWindowOne.win:keepall() as select * from SupportBean");
        epService.getEPAdministrator().createEPL("create window MyWindowTwo.win:keepall() as select * from SupportBeanReadOnly");

        tryInvalid("on SupportBean update MyWindowOne set intPrimitive = 'A'",
                   "Error starting statement: Invalid assignment of column '\"A\"' of type 'java.lang.String' to event property 'intPrimitive' typed as 'int', column and parameter types mismatch [on SupportBean update MyWindowOne set intPrimitive = 'A']");
        tryInvalid("on SupportBean as sb1 update MyWindowOne as sb2 set intPrimitive = sb1.longPrimitive",
                   "Error starting statement: Invalid assignment of column 'sb1.longPrimitive' of type 'long' to event property 'intPrimitive' typed as 'int', column and parameter types mismatch [on SupportBean as sb1 update MyWindowOne as sb2 set intPrimitive = sb1.longPrimitive]");
        tryInvalid("on SupportBean update MyWindowOne set abc = 1",
                   "Error starting statement: Property 'abc' is not available for write access [on SupportBean update MyWindowOne set abc = 1]");
        tryInvalid("on SupportBean update MyWindowOne set intPrimitive = null",
                   "Error starting statement: Invalid assignment of column 'null' of null type to event property 'intPrimitive' typed as 'int', nullable type mismatch [on SupportBean update MyWindowOne set intPrimitive = null]");
        tryInvalid("on SupportBean update ABC set dummy = 1",
                   "Named window 'ABC' has not been declared [on SupportBean update ABC set dummy = 1]");
        tryInvalid("on SupportBean update MyWindowTwo set side = 'X'",
                   "Error starting statement: Property 'side' is not available for write access [on SupportBean update MyWindowTwo set side = 'X']");
    }

    public void testWhereClauseFlowAndSODA()
    {
        String[] fields = "string,intPrimitive,intBoxed".split(",");

        // create window one
        String stmtTextCreate = "create window MyWindowOne.win:keepall() as select * from SupportBean";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsert = "insert into MyWindowOne select * from SupportBean";
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        // create consumer
        String stmtTextSelect = "select irstream * from MyWindowOne";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        sendSupportBean("E1", 1, 11);
        sendSupportBean("E2", 2, 22);
        sendSupportBean("E3", 3, 33);
        listenerSelect.reset();

        // create delete stmt
        String stmtTextUpdate = "on S0 update MyWindowOne set intPrimitive = id, intBoxed = 0 where (string = p00)";
        EPStatement stmtUpdate = epService.getEPAdministrator().createEPL(stmtTextUpdate);
        stmtUpdate.addListener(listenerUpdate);
        assertEquals(StatementType.ON_UPDATE, ((EPStatementSPI) stmtUpdate).getStatementMetadata().getStatementType());

        sendS0(10, "E2");
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E2", 2, 22}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E2", 10, 0}});
        ArrayAssertionUtil.assertPropsPerRow(listenerUpdate.getLastOldData(), fields, new Object[][] {{"E2", 2, 22}});
        ArrayAssertionUtil.assertPropsPerRow(listenerUpdate.getAndResetLastNewData(), fields, new Object[][] {{"E2", 10, 0}});
        ArrayAssertionUtil.assertPropsPerRow(listenerWindow.getLastOldData(), fields, new Object[][] {{"E2", 2, 22}});
        ArrayAssertionUtil.assertPropsPerRow(listenerWindow.getAndResetLastNewData(), fields, new Object[][] {{"E2", 10, 0}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1, 11}, {"E3", 3, 33}, {"E2", 10, 0}});

        // SODA
        stmtUpdate.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtTextUpdate);
        assertEquals(stmtTextUpdate, model.toEPL());
        stmtUpdate = epService.getEPAdministrator().create(model);
        stmtUpdate.addListener(listenerUpdate);
        
        sendS0(200, "E3");
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E3", 3, 33}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E3", 200, 0}});
        ArrayAssertionUtil.assertPropsPerRow(listenerUpdate.getLastOldData(), fields, new Object[][] {{"E3", 3, 33}});
        ArrayAssertionUtil.assertPropsPerRow(listenerUpdate.getAndResetLastNewData(), fields, new Object[][] {{"E3", 200, 0}});
        ArrayAssertionUtil.assertPropsPerRow(listenerWindow.getLastOldData(), fields, new Object[][] {{"E3", 3, 33}});
        ArrayAssertionUtil.assertPropsPerRow(listenerWindow.getAndResetLastNewData(), fields, new Object[][] {{"E3", 200, 0}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1, 11}, {"E2", 10, 0}, {"E3", 200, 0}});

        // new event
        sendSupportBean("E4", 4, 44);
        sendS0(3000, "E4");
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E4",4, 44}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E4", 3000, 0}});
        ArrayAssertionUtil.assertPropsPerRow(listenerUpdate.getLastOldData(), fields, new Object[][] {{"E4",4, 44}});
        ArrayAssertionUtil.assertPropsPerRow(listenerUpdate.getAndResetLastNewData(), fields, new Object[][] {{"E4", 3000, 0}});
        ArrayAssertionUtil.assertPropsPerRow(listenerWindow.getLastOldData(), fields, new Object[][] {{"E4",4, 44}});
        ArrayAssertionUtil.assertPropsPerRow(listenerWindow.getAndResetLastNewData(), fields, new Object[][] {{"E4", 3000, 0}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1, 11}, {"E2", 10, 0}, {"E3", 200, 0}, {"E4", 3000, 0}});
    }

    public void testNullAssignAndWideningNoWhere()
    {
        Map<String, Object> typeDef = new HashMap<String, Object>();
        typeDef.put("string", String.class);
        typeDef.put("longVal", long.class);
        typeDef.put("intVal", Integer.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMap", typeDef);

        String[] fields = "string,longVal,intVal".split(",");
        epService.getEPAdministrator().createEPL("create window MyWindowOne.win:keepall() as select * from MyMap");
        epService.getEPAdministrator().createEPL("insert into MyWindowOne select * from MyMap");

        // create consumer
        String stmtTextSelect = "select irstream * from MyWindowOne";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        sendMap("MyMap", "E1", 10, null);
        listenerSelect.reset();

        // create delete stmt
        String stmtTextUpdate = "on SupportBean sb update MyWindowOne as mw set string = sb.string, longVal = mw.intVal";
        EPStatement stmtUpdate = epService.getEPAdministrator().createEPL(stmtTextUpdate);
        stmtUpdate.addListener(listenerUpdate);

        // test assign null, remains at original value
        epService.getEPRuntime().sendEvent(new SupportBean("A1", 0));
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E1", 10L, null}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"A1", 10L, null}});

        // add second row
        sendMap("MyMap", "E2", 20, -1);

        // test widening (Integer to long)
        epService.getEPRuntime().sendEvent(new SupportBean("A2", 0));
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"A1", 10L, null}, {"E2", 20L, -1}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"A2", 10L, null}, {"A2", -1L, -1}});
        
        // test multiple assignments/property reuse + cast
        stmtUpdate.destroy();
        stmtTextUpdate = "on SupportBean sb update MyWindowOne as nw set longVal = nw.intVal, intVal = cast(longVal, int)+1 where sb.string = nw.string";
        stmtUpdate = epService.getEPAdministrator().createEPL(stmtTextUpdate);
        stmtUpdate.addListener(listenerUpdate);

        sendMap("MyMap", "E3", 0, 1);
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 0));
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E3", 0L, 1}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E3", 1L, 2}});
    }

    public void testSubquery()
    {
        Map<String, Object> typeDef = new HashMap<String, Object>();
        typeDef.put("string", String.class);
        typeDef.put("longVal", long.class);
        typeDef.put("intVal", Integer.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMap", typeDef);
        epService.getEPAdministrator().getConfiguration().addEventType("S1", SupportBean_S1.class);
        String[] fields = "string,longVal,intVal".split(",");

        epService.getEPAdministrator().createEPL("create window MyWindowOne.win:keepall() as (string string, longVal long, intVal int)");
        epService.getEPAdministrator().createEPL("insert into MyWindowOne select string, longVal, intVal from MyMap");

        // create consumer
        String stmtTextSelect = "select irstream * from MyWindowOne";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        sendMap("MyMap", "E1", 1L, 20);
        listenerSelect.reset();

        String stmtTextUpdate = "on SupportBean sb update MyWindowOne as nw set intVal = (select id from S0.std:lastevent()) where nw.string = (select p10 from S1.std:lastevent())";
        EPStatement stmtUpdate = epService.getEPAdministrator().createEPL(stmtTextUpdate);
        stmtUpdate.addListener(listenerUpdate);

        // no event, where clause not satisfied
        epService.getEPRuntime().sendEvent(new SupportBean());
        assertFalse(listenerSelect.isInvoked());

        // find row but no value
        epService.getEPRuntime().sendEvent(new SupportBean_S1(0, "E1"));
        epService.getEPRuntime().sendEvent(new SupportBean());
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E1", 1L, 20}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E1", 1L, null}});

        // find row and update value
        epService.getEPRuntime().sendEvent(new SupportBean_S0(99, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean());
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E1", 1L, null}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E1", 1L, 99}});
        
        // find row and update value
        epService.getEPRuntime().sendEvent(new SupportBean_S0(98, "Y"));
        epService.getEPRuntime().sendEvent(new SupportBean());
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E1", 1L, 99}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E1", 1L, 98}});

        // test correlated to arriving stream
        stmtUpdate.destroy();
        stmtTextUpdate = "on SupportBean sb update MyWindowOne as nw set intVal = (select id+sb.intPrimitive from S0.std:lastevent()) where nw.string = (select p10 from S1.std:lastevent())";
        stmtUpdate = epService.getEPAdministrator().createEPL(stmtTextUpdate);
        stmtUpdate.addListener(listenerUpdate);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(97, "Z"));
        epService.getEPRuntime().sendEvent(new SupportBean("S", 97));
        assertFalse(listenerSelect.isInvoked());
        
        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1, "E1"));    // 97 + 3 = 100
        epService.getEPRuntime().sendEvent(new SupportBean("S", 3));
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastOldData(), fields, new Object[][] {{"E1", 1L, 98}});
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getAndResetLastNewData(), fields, new Object[][] {{"E1", 1L, 100}});
    }

    private SupportBean sendSupportBean(String string, int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMap(String typeName, String string, long longVal, Integer intVal)
    {
        Map<String, Object> typeDef = new HashMap<String, Object>();
        typeDef.put("string", string);
        typeDef.put("longVal", longVal);
        typeDef.put("intVal", intVal);
        epService.getEPRuntime().sendEvent(typeDef, typeName);
    }

    private void sendS0(int id, String p00)
    {
        epService.getEPRuntime().sendEvent(new SupportBean_S0(id, p00));
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }    
}