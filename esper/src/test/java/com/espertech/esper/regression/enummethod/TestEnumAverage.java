package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_Container;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestEnumAverage extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testAverage() {

        String[] fields = "val0,val1,val2,val3".split(",");
        String eplFragment = "select " +
                "beans.average(x => intBoxed) as val0," +
                "beans.average(x => doubleBoxed) as val1," +
                "beans.average(x => longBoxed) as val2," +
                "beans.average(x => bigDecimal) as val3 " +
                "from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Double.class, Double.class, Double.class, BigDecimal.class});

        epService.getEPRuntime().sendEvent(new SupportBean_Container(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_Container(Collections.<SupportBean>emptyList()));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null});

        List<SupportBean> list = new ArrayList<SupportBean>();
        list.add(make(2,3d,4l,5));
        epService.getEPRuntime().sendEvent(new SupportBean_Container(list));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2d, 3d, 4d, new BigDecimal(5.0d)});

        list.add(make(4,6d,8l,10));
        epService.getEPRuntime().sendEvent(new SupportBean_Container(list));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {(2+4)/2d, (3d+6d)/2d, (4L+8L)/2d, new BigDecimal((5+10)/2d)});
    }

    private SupportBean make(Integer intBoxed, Double doubleBoxed, Long longBoxed, int bigDecimal) {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        bean.setLongBoxed(longBoxed);
        bean.setBigDecimal(new BigDecimal(bigDecimal));
        return bean;
    }
}
