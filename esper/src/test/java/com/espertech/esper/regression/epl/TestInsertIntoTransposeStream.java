package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestInsertIntoTransposeStream extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testTransposeEventJoinMap()
    {
        Map<String, Object> metadata = makeMap(new Object[][] {{"id", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("AEvent", metadata);
        epService.getEPAdministrator().getConfiguration().addEventType("BEvent", metadata);

        String stmtTextOne = "insert into MyStream select a, b from AEvent.win:keepall() as a, BEvent.win:keepall() as b";
        epService.getEPAdministrator().createEPL(stmtTextOne);

        String stmtTextTwo = "select a.id, b.id from MyStream";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextTwo);
        stmt.addListener(listener);

        Map<String, Object> eventOne = makeMap(new Object[][] {{"id", "A1"}});
        Map<String, Object> eventTwo = makeMap(new Object[][] {{"id", "B1"}});
        epService.getEPRuntime().sendEvent(eventOne, "AEvent");
        epService.getEPRuntime().sendEvent(eventTwo, "BEvent");

        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.id,b.id".split(","), new Object[] {"A1", "B1"});
    }

    public void testTransposeEventJoinPOJO()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("AEvent", SupportBean_A.class);
        epService.getEPAdministrator().getConfiguration().addEventType("BEvent", SupportBean_B.class);

        String stmtTextOne = "insert into MyStream select a.* as a, b.* as b from AEvent.win:keepall() as a, BEvent.win:keepall() as b";
        epService.getEPAdministrator().createEPL(stmtTextOne);

        String stmtTextTwo = "select a.id, b.id from MyStream";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextTwo);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.id,b.id".split(","), new Object[] {"A1", "B1"});
    }

    public void testTransposePOJOPropertyStream()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("Complex", SupportBeanComplexProps.class);

        String stmtTextOne = "insert into MyStream select nested as inneritem from Complex";
        epService.getEPAdministrator().createEPL(stmtTextOne);

        String stmtTextTwo = "select inneritem.nestedValue as result from MyStream";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextTwo);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "result".split(","), new Object[] {"nestedValue"});
    }

    public void testInvalidTransposeMapPropertyStream()
    {
        Map<String, Object> metadata = makeMap(new Object[][] {
                {"nested", makeMap(new Object[][] {{"nestedValue", String.class}}) }
        });
        epService.getEPAdministrator().getConfiguration().addEventType("Complex", metadata);

        String stmtTextOne = "insert into MyStream select nested as inneritem from Complex";
        epService.getEPAdministrator().createEPL(stmtTextOne);

        try
        {
            String stmtTextTwo = "select inneritem.nestedValue as result from MyStream";
            epService.getEPAdministrator().createEPL(stmtTextTwo);
        }
        catch (Exception ex)
        {
            assertEquals("Error starting statement: Failed to resolve property 'inneritem.nestedValue' to a stream or nested property in a stream [select inneritem.nestedValue as result from MyStream]", ex.getMessage());
        }
    }

    private Map<String, Object> makeMap(Object[][] entries)
    {
        Map result = new HashMap<String, Object>();
        for (Object[] entry : entries)
        {
            result.put(entry[0], entry[1]);
        }
        return result;
    }
}
