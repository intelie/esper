package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.SerializableObjectCopier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestLikeRegexpExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testLikeRegexStringAndNull()
    {
        String caseExpr = "select p00 like p01 as r1, " +
                                " p00 like p01 escape \"!\" as r2," +
                                " p02 regexp p03 as r3 " +
                          " from " + SupportBean_S0.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(caseExpr);
        selectTestCase.addListener(testListener);

        runLikeRegexStringAndNull();
    }

    public void testLikeRegexStringAndNull_OM() throws Exception
    {
        String stmtText = "select (p00 like p01) as r1, " +
                                "(p00 like p01 escape \"!\") as r2, " +
                                "(p02 regexp p03) as r3 " +
                          "from " + SupportBean_S0.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create()
                .add(Expressions.like(Expressions.property("p00"), Expressions.property("p01")), "r1")
                .add(Expressions.like(Expressions.property("p00"), Expressions.property("p01"), Expressions.constant("!")), "r2")
                .add(Expressions.regexp(Expressions.property("p02"), Expressions.property("p03")), "r3")
                );
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean_S0.class.getName())));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);        
        assertEquals(stmtText, model.toEPL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(testListener);

        runLikeRegexStringAndNull();

        String epl = "select * from " + SupportBean.class.getName() + "((string not like \"foo%\"))";
        EPPreparedStatement eps = epService.getEPAdministrator().prepareEPL(epl);
        EPStatement statement = epService.getEPAdministrator().create(eps);
        assertEquals(epl, statement.getText());

        epl = "select * from " + SupportBean.class.getName() + "((string not regexp \"foo\"))";
        eps = epService.getEPAdministrator().prepareEPL(epl);
        statement = epService.getEPAdministrator().create(eps);
        assertEquals(epl, statement.getText());
    }

    public void testLikeRegexStringAndNull_Compile() throws Exception
    {
        String stmtText = "select (p00 like p01) as r1, " +
                                "(p00 like p01 escape \"!\") as r2, " +
                                "(p02 regexp p03) as r3 " +
                          "from " + SupportBean_S0.class.getName();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEPL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(testListener);

        runLikeRegexStringAndNull();
    }

    private void runLikeRegexStringAndNull()
    {
        sendS0Event("a", "b", "c", "d");
        assertReceived(new Object[][] {{"r1", false}, {"r2", false}, {"r3", false}});

        sendS0Event(null, "b", null, "d");
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}, {"r3", null}});

        sendS0Event("a", null, "c", null);
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}, {"r3", null}});

        sendS0Event(null, null, null, null);
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}, {"r3", null}});

        sendS0Event("abcdef", "%de_", "a", "[a-c]");
        assertReceived(new Object[][] {{"r1", true}, {"r2", true}, {"r3", true}});

        sendS0Event("abcdef", "b%de_", "d", "[a-c]");
        assertReceived(new Object[][] {{"r1", false}, {"r2", false}, {"r3", false}});

        sendS0Event("!adex", "!%de_", "", ".");
        assertReceived(new Object[][] {{"r1", true}, {"r2", false}, {"r3", false}});

        sendS0Event("%dex", "!%de_", "a", ".");
        assertReceived(new Object[][] {{"r1", false}, {"r2", true}, {"r3", true}});
    }

    public void testInvalidLikeRegEx()
    {
        tryInvalid("intPrimitive like 'a' escape null");
        tryInvalid("intPrimitive like boolPrimitive");
        tryInvalid("boolPrimitive like string");
        tryInvalid("string like string escape intPrimitive");

        tryInvalid("intPrimitive regexp doublePrimitve");
        tryInvalid("intPrimitive regexp boolPrimitive");
        tryInvalid("boolPrimitive regexp string");
        tryInvalid("string regexp intPrimitive");
    }

    public void testLikeRegexNumericAndNull()
    {
        String caseExpr = "select intBoxed like '%01%' as r1, " +
                                " doubleBoxed regexp '[0-9][0-9].[0-9]' as r2 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(caseExpr);
        selectTestCase.addListener(testListener);

        sendSupportBeanEvent(101, 1.1);
        assertReceived(new Object[][] {{"r1", true}, {"r2", false}});

        sendSupportBeanEvent(102, 11d);
        assertReceived(new Object[][] {{"r1", false}, {"r2", true}});

        sendSupportBeanEvent(null, null);
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}});
    }

    private void tryInvalid(String expr)
    {
        try
        {
            String statement = "select " + expr + " from " + SupportBean.class.getName();
            epService.getEPAdministrator().createEPL(statement);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }

    private void assertReceived(Object[][] objects)
    {
        EventBean event = testListener.assertOneGetNewAndReset();
        for (int i = 0; i < objects.length; i++)
        {
            String key = (String) objects[i][0];
            Object result = objects[i][1];
            assertEquals("key=" + key + " result=" + result, result, event.get(key));
        }
    }

    private void sendS0Event(String p00, String p01, String p02, String p03)
    {
        SupportBean_S0 bean = new SupportBean_S0(-1, p00, p01, p02, p03);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendSupportBeanEvent(Integer intBoxed, Double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestLikeRegexpExpr.class);
}
