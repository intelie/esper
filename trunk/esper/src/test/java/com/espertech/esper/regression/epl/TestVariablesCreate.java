package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.soda.CreateVariableClause;
import com.espertech.esper.client.soda.Expressions;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.util.Arrays;
import java.util.Map;

public class TestVariablesCreate extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerCreateOne;
    private SupportUpdateListener listenerCreateTwo;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerCreateOne = new SupportUpdateListener();
        listenerCreateTwo = new SupportUpdateListener();
    }

    public void testOM()
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setCreateVariable(CreateVariableClause.create("long", "var1", null));
        epService.getEPAdministrator().create(model);
        assertEquals("create variable long var1", model.toEPL());

        model = new EPStatementObjectModel();
        model.setCreateVariable(CreateVariableClause.create("string", "var2", Expressions.constant("abc")));
        epService.getEPAdministrator().create(model);
        assertEquals("create variable string var2 = \"abc\"", model.toEPL());

        String stmtTextSelect = "select var1, var2 from " + SupportBean.class.getName();
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listener);

        String[] fieldsVar = new String[] {"var1", "var2"};
        sendSupportBean("E1", 10);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsVar, new Object[] {null, "abc"});
    }

    public void testCompile()
    {
        String text = "create variable long var1";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        epService.getEPAdministrator().create(model);
        assertEquals(text, model.toEPL());

        text = "create variable string var2 = \"abc\"";
        model = epService.getEPAdministrator().compileEPL(text);
        epService.getEPAdministrator().create(model);
        assertEquals(text, model.toEPL());

        String stmtTextSelect = "select var1, var2 from " + SupportBean.class.getName();
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listener);

        String[] fieldsVar = new String[] {"var1", "var2"};
        sendSupportBean("E1", 10);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsVar, new Object[] {null, "abc"});
    }

    public void testSubscribeAndIterate()
    {
        String stmtCreateTextOne = "create variable long var1 = null";
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL(stmtCreateTextOne);
        stmtCreateOne.addListener(listenerCreateOne);
        String[] fieldsVar1 = new String[] {"var1"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateOne.iterator(), fieldsVar1, new Object[][] {{null}});
        assertFalse(listenerCreateOne.isInvoked());

        EventType typeSet = stmtCreateOne.getEventType();
        assertEquals(Long.class, typeSet.getPropertyType("var1"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), new String[] {"var1"}));

        String stmtCreateTextTwo = "create variable long var2 = 20";
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEPL(stmtCreateTextTwo);
        stmtCreateTwo.addListener(listenerCreateTwo);
        String[] fieldsVar2 = new String[] {"var2"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fieldsVar2, new Object[][] {{20L}});
        assertFalse(listenerCreateTwo.isInvoked());

        String stmtTextSet = "on " + SupportBean.class.getName() + " set var1 = intPrimitive * 2, var2 = var1 + 1";
        epService.getEPAdministrator().createEPL(stmtTextSet);

        sendSupportBean("E1", 100);
        ArrayAssertionUtil.assertProps(listenerCreateOne.getLastNewData()[0], fieldsVar1, new Object[] {200L});
        ArrayAssertionUtil.assertProps(listenerCreateOne.getLastOldData()[0], fieldsVar1, new Object[] {null});
        listenerCreateOne.reset();
        ArrayAssertionUtil.assertProps(listenerCreateTwo.getLastNewData()[0], fieldsVar2, new Object[] {201L});
        ArrayAssertionUtil.assertProps(listenerCreateTwo.getLastOldData()[0], fieldsVar2, new Object[] {20L});
        listenerCreateOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateOne.iterator(), fieldsVar1, new Object[][] {{200L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fieldsVar2, new Object[][] {{201L}});

        sendSupportBean("E2", 200);
        ArrayAssertionUtil.assertProps(listenerCreateOne.getLastNewData()[0], fieldsVar1, new Object[] {400L});
        ArrayAssertionUtil.assertProps(listenerCreateOne.getLastOldData()[0], fieldsVar1, new Object[] {200L});
        listenerCreateOne.reset();
        ArrayAssertionUtil.assertProps(listenerCreateTwo.getLastNewData()[0], fieldsVar2, new Object[] {401L});
        ArrayAssertionUtil.assertProps(listenerCreateTwo.getLastOldData()[0], fieldsVar2, new Object[] {201L});
        listenerCreateOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateOne.iterator(), fieldsVar1, new Object[][] {{400L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fieldsVar2, new Object[][] {{401L}});

        stmtCreateTwo.stop();
        stmtCreateTwo.start();

        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateOne.iterator(), fieldsVar1, new Object[][] {{400L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fieldsVar2, new Object[][] {{401L}});
    }

    public void testDeclarationAndSelect() throws Exception
    {
        Object[][] variables = new Object[][] {
                {"var1", "int", "1", 1},
                {"var2", "int", "'2'", 2},
                {"var3", "INTEGER", " 3+2 ", 5},
                {"var4", "bool", " true|false ", true},
                {"var5", "boolean", " var1=1 ", true},
                {"var6", "double", " 1.11 ", 1.11d},
                {"var7", "double", " 1.20d ", 1.20d},
                {"var8", "Double", " ' 1.12 ' ", 1.12d},
                {"var9", "float", " 1.13f*2f ", 2.26f},
                {"var10", "FLOAT", " -1.14f ", -1.14f},
                {"var11", "string", " ' XXXX ' ", " XXXX "},
                {"var12", "string", " \"a\" ", "a"},
                {"var13", "character", "'a'", 'a'},
                {"var14", "char", "'x'", 'x'},
                {"var15", "short", " 20 ", (short) 20},
                {"var16", "SHORT", " ' 9 ' ", (short)9},
                {"var17", "long", " 20*2 ", (long) 40},
                {"var18", "LONG", " ' 9 ' ", (long)9},
                {"var19", "byte", " 20*2 ", (byte) 40},
                {"var20", "BYTE", "9+1", (byte)10},
                {"var21", "int", null, null},
                {"var22", "bool", null, null},
                {"var23", "double", null, null},
                {"var24", "float", null, null},
                {"var25", "string", null, null},
                {"var26", "char", null, null},
                {"var27", "short", null, null},
                {"var28", "long", null, null},
                {"var29", "BYTE", null, null},
        };

        for (int i = 0; i < variables.length; i++)
        {
            String text = "create variable " + variables[i][1] + " " + variables[i][0];
            if (variables[i][2] != null)
            {
                text += " = " + variables[i][2];
            }

            epService.getEPAdministrator().createEPL(text);
        }

        // select all variables
        StringBuilder buf = new StringBuilder();
        String delimiter = "";
        buf.append("select ");
        for (int i = 0; i < variables.length; i++)
        {
            buf.append(delimiter);
            buf.append(variables[i][0]);
            delimiter = ",";
        }
        buf.append(" from ");
        buf.append(SupportBean.class.getName());
        EPStatement stmt = epService.getEPAdministrator().createEPL(buf.toString());
        stmt.addListener(listener);

        // assert initialization values
        sendSupportBean("E1", 1);
        EventBean received = listener.assertOneGetNewAndReset();
        for (int i = 0; i < variables.length; i++)
        {
            assertEquals(variables[i][3], received.get((String)variables[i][0]));
        }
    }

    public void testInvalid()
    {
        String stmt = "create variable somedummy myvar = 10";
        tryInvalid(stmt, "Error starting view: Cannot create variable 'myvar', type 'somedummy' is not a recognized type [create variable somedummy myvar = 10]");

        stmt = "create variable string myvar = 5";
        tryInvalid(stmt, "Error starting view: Cannot create variable: Variable 'myvar' of declared type 'java.lang.String' cannot be initialized by a value of type 'java.lang.Integer' [create variable string myvar = 5]");

        stmt = "create variable string myvar = 'a'";
        epService.getEPAdministrator().createEPL("create variable string myvar = 'a'");
        tryInvalid(stmt, "Error starting view: Cannot create variable: Variable by name 'myvar' has already been created [create variable string myvar = 'a']");

        tryInvalid("select * from " + SupportBean.class.getName() + " output every somevar events",
            "Error starting view: Error in the output rate limiting clause: Variable named 'somevar' has not been declared [select * from com.espertech.esper.support.bean.SupportBean output every somevar events]");
    }

    private void tryInvalid(String stmtText, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean makeSupportBean(String string, int intPrimitive, Integer intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        return bean;
    }
}
