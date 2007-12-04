package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.soda.*;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;

public class TestVariables extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerSet;

    // test create variable syntax
    // test output rate limiting

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerSet = new SupportUpdateListener();
    }

    public void testVariableInFilterBoolean() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", String.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", String.class, null);

        String stmtTextSet = "on " + SupportBean_S0.class.getName() + " set var1 = p00, var2 = p01";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1", "var2"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null, null}});

        String stmtTextSelect = "select string, intPrimitive from " + SupportBean.class.getName() + "(string = var1 or string = var2)";
        String[] fieldsSelect = new String[] {"string", "intPrimitive"};
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listener);

        sendSupportBean(null, 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {null, 1});

        sendSupportBeanS0NewThread(100, "a", "b");
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"a", "b"});

        sendSupportBean("a", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {"a", 2});

        sendSupportBean(null, 1);
        assertFalse(listener.isInvoked());

        sendSupportBean("b", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {"b", 3});

        sendSupportBean("c", 4);
        assertFalse(listener.isInvoked());

        sendSupportBeanS0NewThread(100, "e", "c");
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"e", "c"});

        sendSupportBean("c", 5);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {"c", 5});

        sendSupportBean("e", 6);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {"e", 6});

        stmtSet.destroy();
    }

    public void testVariableInFilter() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", String.class, null);

        String stmtTextSet = "on " + SupportBean_S0.class.getName() + " set var1 = p00";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null}});

        String stmtTextSelect = "select string, intPrimitive from " + SupportBean.class.getName() + "(string = var1)";
        String[] fieldsSelect = new String[] {"string", "intPrimitive"};
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listener);

        sendSupportBean(null, 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {null, 1});

        sendSupportBeanS0NewThread(100, "a", "b");
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"a"});

        sendSupportBean("a", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {"a", 2});

        sendSupportBean(null, 1);
        assertFalse(listener.isInvoked());

        sendSupportBeanS0NewThread(100, "e", "c");
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"e"});

        sendSupportBean("c", 5);
        assertFalse(listener.isInvoked());

        sendSupportBean("e", 6);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {"e", 6});

        stmtSet.destroy();
    }

    public void testAssignmentOrderNoDup()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, "12");
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Integer.class, "2");
        epService.getEPAdministrator().getConfiguration().addVariable("var3", Integer.class, null);

        String stmtTextSet = "on " + SupportBean.class.getName() + " set var1 = intPrimitive, var2 = var1 + 1, var3 = var1 + var2";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1", "var2", "var3"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{12, 2, null}});

        sendSupportBean("S1", 3);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {3, 4, 7});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{3, 4, 7}});

        sendSupportBean("S1", -1);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {-1, 0, -1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{-1, 0, -1}});

        sendSupportBean("S1", 90);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {90, 91, 181});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{90, 91, 181}});

        stmtSet.destroy();
    }
    
    public void testAssignmentOrderDup() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, 0);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Integer.class, 1);
        epService.getEPAdministrator().getConfiguration().addVariable("var3", Integer.class, 2);

        String stmtTextSet = "on " + SupportBean.class.getName() + " set var1 = intPrimitive, var2 = var2, var1 = intBoxed, var3 = var3 + 1";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1", "var2", "var3"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{0, 1, 2}});

        sendSupportBean("S1", -1, 10);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {10, 1, 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{10, 1, 3}});

        sendSupportBean("S2", -2, 20);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {20, 1, 4});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{20, 1, 4}});

        sendSupportBeanNewThread("S3", -3, 30);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {30, 1, 5});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{30, 1, 5}});

        sendSupportBeanNewThread("S4", -4, 40);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {40, 1, 6});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{40, 1, 6}});

        stmtSet.destroy();
    }

    public void testObjectModel()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", double.class, 10d);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Long.class, 11L);

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("var1", "var2", "id"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean_A.class.getName())));

        EPStatement stmtSelect = epService.getEPAdministrator().create(model);
        String stmtText = "select var1, var2, id from " + SupportBean_A.class.getName();
        assertEquals(stmtText, model.toEQL());
        stmtSelect.addListener(listener);

        String[] fieldsSelect = new String[] {"var1", "var2", "id"};
        sendSupportBean_A("E1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {10d, 11L, "E1"});

        model = new EPStatementObjectModel();
        model.setOnExpr(OnClause.createOnSet("var1", Expressions.property("intPrimitive")).addAssignment("var2", Expressions.property("intBoxed")));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName())));
        String stmtTextSet = "on " + SupportBean.class.getName() + " set var1 = intPrimitive, var2 = intBoxed";
        EPStatement stmtSet = epService.getEPAdministrator().create(model);
        stmtSet.addListener(listenerSet);
        assertEquals(stmtTextSet, model.toEQL());

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Double.class, typeSet.getPropertyType("var1"));
        assertEquals(Long.class, typeSet.getPropertyType("var2"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        String[] fieldsVar = new String[] {"var1", "var2"};
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), fieldsVar));

        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{10d, 11L}});
        sendSupportBean("S1", 3, 4);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {3d, 4L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{3d, 4L}});

        sendSupportBean_A("E2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {3d, 4L, "E2"});

        stmtSet.destroy();
        stmtSelect.destroy();
    }

    public void testCompile()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", double.class, 10d);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Long.class, 11L);

        String stmtText = "select var1, var2, id from " + SupportBean_A.class.getName();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(stmtText);
        EPStatement stmtSelect = epService.getEPAdministrator().create(model);
        assertEquals(stmtText, model.toEQL());
        stmtSelect.addListener(listener);

        String[] fieldsSelect = new String[] {"var1", "var2", "id"};
        sendSupportBean_A("E1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {10d, 11L, "E1"});

        String stmtTextSet = "on " + SupportBean.class.getName() + " set var1 = intPrimitive, var2 = intBoxed";
        model = epService.getEPAdministrator().compileEQL(stmtTextSet);
        EPStatement stmtSet = epService.getEPAdministrator().create(model);
        stmtSet.addListener(listenerSet);
        assertEquals(stmtTextSet, model.toEQL());

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Double.class, typeSet.getPropertyType("var1"));
        assertEquals(Long.class, typeSet.getPropertyType("var2"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        String[] fieldsVar = new String[] {"var1", "var2"};
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), fieldsVar));

        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{10d, 11L}});
        sendSupportBean("S1", 3, 4);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {3d, 4L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{3d, 4L}});

        sendSupportBean_A("E2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {3d, 4L, "E2"});

        stmtSet.destroy();
        stmtSelect.destroy();
    }

    public void testRuntimeConfig()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, 10);

        String stmtText = "select var1, string from " + SupportBean.class.getName() + "(string like 'E%')";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtText);
        stmtSelect.addListener(listener);

        String[] fieldsSelect = new String[] {"var1", "string"};
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {10, "E1"});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {10, "E2"});

        String stmtTextSet = "on " + SupportBean.class.getName() + "(string like 'S%') set var1 = intPrimitive";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Integer.class, typeSet.getPropertyType("var1"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), new String[] {"var1"}));

        String[] fieldsVar = new String[] {"var1"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{10}});

        sendSupportBean("S1", 3);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{3}});

        sendSupportBean("E3", 4);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {3, "E3"});

        sendSupportBean("S2", -1);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {-1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{-1}});

        sendSupportBean("E4", 5);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {-1, "E4"});

        try
        {
            epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, 10);
        }
        catch (ConfigurationException ex)
        {
            assertEquals("Error creating variable: Variables by name 'var1' has already been created", ex.getMessage());
        }

        stmtSet.destroy();
        stmtSelect.destroy();
    }

    public void testRuntimeOrderMultiple()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Integer.class, 1);

        String stmtTextSet = "on " + SupportBean.class.getName() + "(string like 'S%' or string like 'B%') set var1 = intPrimitive, var2 = intBoxed";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1", "var2"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null, 1}});

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Integer.class, typeSet.getPropertyType("var1"));
        assertEquals(Integer.class, typeSet.getPropertyType("var2"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), new String[] {"var1", "var2"}));

        sendSupportBean("S1", 3, null);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {3, null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{3, null}});

        sendSupportBean("S1", -1, -2);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {-1, -2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{-1, -2}});

        String stmtText = "select var1, var2, string from " + SupportBean.class.getName() + "(string like 'E%' or string like 'B%')";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtText);
        stmtSelect.addListener(listener);
        String[] fieldsSelect = new String[] {"var1", "var2", "string"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, null);

        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {-1, -2, "E1"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{-1, -2}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{-1, -2, "E1"}});

        sendSupportBean("S1", 11, 12);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {11, 12});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{11, 12}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{11, 12, "E1"}});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {11, 12, "E2"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{11, 12, "E2"}});

        stmtSelect.destroy();
        stmtSet.destroy();
    }

    public void testEngineConfigAPI()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addVariable("p_1", String.class, "begin");
        config.addVariable("p_2", boolean.class, true);
        config.addVariable("p_3", String.class, "value");

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmtTextSet = "on " + SupportBean.class.getName() + "(string like 'S%') set p_1 = 'end', p_2 = false, p_3 = null";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"p_1", "p_2", "p_3"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{"begin", true, "value"}});

        EventType typeSet = stmtSet.getEventType();
        assertEquals(String.class, typeSet.getPropertyType("p_1"));
        assertEquals(Boolean.class, typeSet.getPropertyType("p_2"));
        assertEquals(String.class, typeSet.getPropertyType("p_3"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), fieldsVar));

        sendSupportBean("S1", 3);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"end", false, null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{"end", false, null}});

        sendSupportBean("S2", 4);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"end", false, null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{"end", false, null}});

        stmtSet.destroy();
    }

    public void testEngineConfigXML() throws Exception
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<esper-configuration xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"../esper-configuration-1-0.xsd\">" +
                "<variable name=\"p_1\" type=\"string\" />" +
                "<variable name=\"p_2\" type=\"bool\" initialization-value=\"true\"/>" +
                "<variable name=\"p_3\" type=\"long\" initialization-value=\"10\"/>" +
                "<variable name=\"p_4\" type=\"double\" initialization-value=\"11.1d\"/>" +
                "</esper-configuration>";

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document configDoc = builderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

        Configuration config = SupportConfigFactory.getConfiguration();
        config.configure(configDoc);
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmtTextSet = "on " + SupportBean.class.getName() + " set p_1 = string, p_2 = boolBoxed, p_3 = intBoxed, p_4 = intBoxed";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"p_1", "p_2", "p_3", "p_4"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null, true, 10L, 11.1d}});

        EventType typeSet = stmtSet.getEventType();
        assertEquals(String.class, typeSet.getPropertyType("p_1"));
        assertEquals(Boolean.class, typeSet.getPropertyType("p_2"));
        assertEquals(Long.class, typeSet.getPropertyType("p_3"));
        assertEquals(Double.class, typeSet.getPropertyType("p_4"));
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), fieldsVar));

        SupportBean bean = new SupportBean();
        bean.setString("text");
        bean.setBoolBoxed(false);
        bean.setIntBoxed(200);
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"text", false, 200L, 200d});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{"text", false, 200L, 200d}});

        bean = new SupportBean();   // leave all fields null
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {null, null, null, null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null, null, null, null}});

        stmtSet.destroy();
    }

    public void testCoercion()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Float.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Double.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var3", Long.class, null);

        String stmtTextSet = "on " + SupportBean.class.getName() + " set var1 = intPrimitive, var2 = intPrimitive, var3=intBoxed";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1", "var2", "var3"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null, null, null}});

        String stmtText = "select var1, var2, var3, id from " + SupportBean_A.class.getName() + ".win:length(2)";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtText);
        stmtSelect.addListener(listener);
        String[] fieldsSelect = new String[] {"var1", "var2", "var3", "id"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, null);

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Float.class, typeSet.getPropertyType("var1"));
        assertEquals(Double.class, typeSet.getPropertyType("var2"));
        assertEquals(Long.class, typeSet.getPropertyType("var3"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        ArrayAssertionUtil.assertEqualsAnyOrder(typeSet.getPropertyNames(), fieldsVar);

        sendSupportBean_A("A1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {null, null, null, "A1"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{null, null, null, "A1"}});

        sendSupportBean("S1", 1, 2);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {1f, 1d, 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{1f, 1d, 2L}});

        sendSupportBean_A("A2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {1f, 1d, 2L, "A2"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{1f, 1d, 2L, "A1"}, {1f, 1d, 2L, "A2"}});

        sendSupportBean("S1", 10, 20);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {10f, 10d, 20L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{10f, 10d, 20L}});

        sendSupportBean_A("A3");
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fieldsSelect, new Object[] {10f, 10d, 20L, "A3"});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fieldsSelect, new Object[] {10f, 10d, 20L, "A1"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{10f, 10d, 20L, "A2"}, {10f, 10d, 20L, "A3"}});

        stmtSelect.destroy();
        stmtSet.destroy();
    }

    public void testInvalidSet()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", String.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", boolean.class, false);
        epService.getEPAdministrator().getConfiguration().addVariable("var3", int.class, 1);

        tryInvalidSet("on " + SupportBean.class.getName() + " set dummy = 100",
                      "Error starting view: Variables by name 'dummy' has not been created or configured [on net.esper.support.bean.SupportBean set dummy = 100]");

        tryInvalidSet("on " + SupportBean.class.getName() + " set var1 = 1",
                      "Error starting view: Variables 'var1' of declared type 'java.lang.String' cannot be assigned a value of type 'java.lang.Integer' [on net.esper.support.bean.SupportBean set var1 = 1]");

        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = 'abc'",
                      "Error starting view: Variables 'var3' of declared type 'java.lang.Integer' cannot be assigned a value of type 'java.lang.String' [on net.esper.support.bean.SupportBean set var3 = 'abc']");

        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = doublePrimitive",
                      "Error starting view: Variables 'var3' of declared type 'java.lang.Integer' cannot be assigned a value of type 'double' [on net.esper.support.bean.SupportBean set var3 = doublePrimitive]");

        tryInvalidSet("on " + SupportBean.class.getName() + " set var2 = 'false'", null);
        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = 1.1", null);
        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = 22222222222222", null);
    }

    private void tryInvalidSet(String stmtText, String message)
    {
        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            if (message != null)
            {
                assertEquals(message, ex.getMessage());
            }
        }
    }

    public void testInvalidInitialization()
    {
        tryInvalid(Integer.class, "abcdef",
                "Error creating variable: Variables 'var1' of declared type 'java.lang.Integer' cannot be initialized by value 'abcdef': java.lang.NumberFormatException: For input string: \"abcdef\"");

        tryInvalid(Integer.class, new Double(11.1),
                "Error creating variable: Variables 'var1' of declared type 'java.lang.Integer' cannot be initialized by a value of type 'java.lang.Double'");

        tryInvalid(Math.class, "abcdef",
                "Error creating variable: Invalid variable type for variable 'var1' as type 'java.lang.Math', only Java primitive, boxed or String types are allowed");

        tryInvalid(int.class, new Double(11.1), null);
        tryInvalid(String.class, true, null);

        // Try invalid configuration
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addVariable("p_1", String.class, 5);

        try
        {
            epService = EPServiceProviderManager.getDefaultProvider(config);
            epService.initialize();
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    private void tryInvalid(Class type, Object value, String message)
    {
        try
        {
            epService.getEPAdministrator().getConfiguration().addVariable("var1", type, value);
            fail();
        }
        catch (ConfigurationException ex)
        {
            if (message != null)
            {
                assertEquals(message, ex.getMessage());
            }
        }
    }

    private SupportBean_A sendSupportBean_A(String id)
    {
        SupportBean_A bean = new SupportBean_A(id);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive, Integer intBoxed)
    {
        SupportBean bean = makeSupportBean(string, intPrimitive, intBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendSupportBeanNewThread(final String string, final int intPrimitive, final Integer intBoxed) throws InterruptedException
    {
        Thread t = new Thread() {
            public void run()
            {
                SupportBean bean = makeSupportBean(string, intPrimitive, intBoxed);
                epService.getEPRuntime().sendEvent(bean);
            }
        };
        t.start();
        t.join();
    }

    private void sendSupportBeanS0NewThread(final int id, final String p00, final String p01) throws InterruptedException
    {
        Thread t = new Thread() {
            public void run()
            {
                epService.getEPRuntime().sendEvent(new SupportBean_S0(id, p00, p01));
            }
        };
        t.start();
        t.join();
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
