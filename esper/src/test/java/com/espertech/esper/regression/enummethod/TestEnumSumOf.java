package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_Container;
import com.espertech.esper.support.bean.SupportCollection;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestEnumSumOf extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_Container.class);
        config.addEventType("SupportCollection", SupportCollection.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testSumEvents() {

        String[] fields = "val0,val1,val2,val3".split(",");
        String eplFragment = "select " +
                "beans.sumOf(x => intBoxed) as val0," +
                "beans.sumOf(x => doubleBoxed) as val1," +
                "beans.sumOf(x => longBoxed) as val2," +
                "beans.sumOf(x => bigDecimal) as val3 " +
                "from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Integer.class, Double.class, Long.class, BigDecimal.class});

        epService.getEPRuntime().sendEvent(new SupportBean_Container(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_Container(Collections.<SupportBean>emptyList()));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null});

        List<SupportBean> list = new ArrayList<SupportBean>();
        list.add(make(2,3d,4l,5));
        epService.getEPRuntime().sendEvent(new SupportBean_Container(list));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, 3d, 4L, new BigDecimal(5)});

        list.add(make(4,6d,8l,10));
        epService.getEPRuntime().sendEvent(new SupportBean_Container(list));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2+4, 3d+6d, 4L+8L, new BigDecimal(5+10)});
    }

    public void testSumOfScalar() {

        String[] fields = "val0,val1".split(",");
        String eplFragment = "select " +
                "intvals.sumOf() as val0, " +
                "bdvals.sumOf() as val1 " +
                "from SupportCollection";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Integer.class, BigDecimal.class});

        epService.getEPRuntime().sendEvent(SupportCollection.makeNumeric("1,4,5"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1+4+5, new BigDecimal(1+4+5)});

        epService.getEPRuntime().sendEvent(SupportCollection.makeNumeric("3,4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3+4, new BigDecimal(3+4)});

        epService.getEPRuntime().sendEvent(SupportCollection.makeNumeric("3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, new BigDecimal(3)});

        epService.getEPRuntime().sendEvent(SupportCollection.makeNumeric(""));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        epService.getEPRuntime().sendEvent(SupportCollection.makeNumeric(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{null, null});
    }

    private SupportBean make(Integer intBoxed, Double doubleBoxed, Long longBoxed, int bigDecimal) {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        bean.setLongBoxed(longBoxed);
        bean.setBigDecimal(new BigDecimal(bigDecimal));
        return bean;
    }

    public void testInvalid() {
        String epl;

        epl = "select intvals.sumof(x=> x) from SupportCollection";
        tryInvalid(epl, "Error starting statement: Invalid input for built-in enumeration method 'sumof' and 1-parameter footprint, expecting collection of events as input, received collection of Integer [select intvals.sumof(x=> x) from SupportCollection]");

        epl = "select beans.sumof() from Bean";
        tryInvalid(epl, "Error starting statement: Invalid input for built-in enumeration method 'sumof' and 0-parameter footprint, expecting collection of values (typically scalar values) as input, received collecton of events of type 'com.espertech.esper.support.bean.SupportBean' [select beans.sumof() from Bean]");
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
