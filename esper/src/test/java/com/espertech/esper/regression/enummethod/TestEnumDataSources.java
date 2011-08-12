/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.bean.lrreport.LocationReportFactory;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;

public class TestEnumDataSources extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportBean_A", SupportBean_A.class);
        config.addEventType("SupportBean_ST0", SupportBean_ST0.class);
        config.addEventType("SupportBean_ST0_Container", SupportBean_ST0_Container.class);
        config.addEventType("SupportCollection", SupportCollection.class);
        config.addImport(LocationReportFactory.class);
        config.getEngineDefaults().getExpression().setUdfCache(false);
        config.addPlugInSingleRowFunction("makeSampleList", SupportBean_ST0_Container.class.getName(), "makeSampleList");
        config.addPlugInSingleRowFunction("makeSampleArray", SupportBean_ST0_Container.class.getName(), "makeSampleArray");
        config.addPlugInSingleRowFunction("makeSampleListString", SupportCollection.class.getName(), "makeSampleListString");
        config.addPlugInSingleRowFunction("makeSampleArrayString", SupportCollection.class.getName(), "makeSampleArrayString");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testPrevWindowSorted() {
        EPStatement stmt = epService.getEPAdministrator().createEPL("select prevwindow(st0) as val0, prevwindow(st0).esperInternalNoop() as val1 " +
                "from SupportBean_ST0.ext:sort(3, p00 asc) as st0");
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmt.getEventType(), "val0,val1".split(","), new Class[]{SupportBean_ST0[].class, Collection.class});

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 5));
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E2", 6));
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E1,E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E3", 4));
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E3,E1,E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E5", 3));
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E5,E3,E1");
        listener.reset();
        stmt.destroy();

        // Scalar version
        String[] fields = new String[] {"val0"};
        EPStatement stmtScalar = epService.getEPAdministrator().createEPL("select prevwindow(id).where(x => x not like '%ignore%') as val0 " +
                "from SupportBean_ST0.win:keepall() as st0");
        stmtScalar.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtScalar.getEventType(), fields, new Class[]{Collection.class});

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 5));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E2ignore", 6));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E3", 4));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E3", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ignoreE5", 3));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E3", "E1");
        listener.reset();
    }

    public void testNamedWindow() {

        // test named window
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportBean_ST0");
        epService.getEPAdministrator().createEPL("on SupportBean_A delete from MyWindow");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean_ST0");
        String eplNamedWindow = "select MyWindow.allOf(x => x.p00 < 5) as allOfX from SupportBean.win:keepall()";
        EPStatement stmtNamedWindow = epService.getEPAdministrator().createEPL(eplNamedWindow);
        stmtNamedWindow.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtNamedWindow.getEventType(), "allOfX".split(","), new Class[]{Boolean.class});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(null, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0", "1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        assertEquals(false, listener.assertOneGetNewAndReset().get("allOfX"));

        stmtNamedWindow.destroy();
        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));

        // test named window correlated
        String eplNamedWindowCorrelated = "select MyWindow(key0 = sb.string).allOf(x => x.p00 < 5) as allOfX from SupportBean.win:keepall() sb";
        EPStatement stmtNamedWindowCorrelated = epService.getEPAdministrator().createEPL(eplNamedWindowCorrelated);
        stmtNamedWindowCorrelated.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(null, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E2", "KEY1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));
        assertEquals(null, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(new SupportBean("KEY1", 0));
        assertEquals(true, listener.assertOneGetNewAndReset().get("allOfX"));
        stmtNamedWindowCorrelated.destroy();
    }

    public void testSubselect() {

        // test subselect
        String eplSubselect = "select (select * from SupportBean_ST0.win:keepall()).allOf(x => x.p00 < 5) as allOfX from SupportBean.win:keepall()";
        EPStatement stmtSubselect = epService.getEPAdministrator().createEPL(eplSubselect);
        stmtSubselect.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0", "1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(true, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0", "1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("allOfX"));
        stmtSubselect.destroy();

        // test subselect scalar return
        String eplSubselectScalar = "select (select id from SupportBean_ST0.win:keepall()).allOf(x => x  like '%B%') as allOfX from SupportBean.win:keepall()";
        EPStatement stmtSubselectScalar = epService.getEPAdministrator().createEPL(eplSubselectScalar);
        stmtSubselectScalar.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("B1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(true, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("A1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("allOfX"));
        stmtSubselectScalar.destroy();

        // test subselect-correlated scalar return
        String eplSubselectScalarCorrelated = "select (select key0 from SupportBean_ST0.win:keepall() st0 where st0.id = sb.string).allOf(x => x  like '%hello%') as allOfX from SupportBean.win:keepall() sb";
        EPStatement stmtSubselectScalarCorrlated = epService.getEPAdministrator().createEPL(eplSubselectScalarCorrelated);
        stmtSubselectScalarCorrlated.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("A1", "hello", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(null, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("A2", "hello", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("A2", 1));
        assertEquals(true, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("A3", "test", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("A3", 1));
        assertEquals(false, listener.assertOneGetNewAndReset().get("allOfX"));
        stmtSubselectScalarCorrlated.destroy();
    }

    public void testAccessAggregation() {
        String[] fields = new String[] {"val0", "val1", "val2", "val3", "val4"};

        // test window(*) and first(*)
        String eplWindowAgg = "select " +
                "window(*).allOf(x => x.intPrimitive < 5) as val0," +
                "first(*).allOf(x => x.intPrimitive < 5) as val1," +
                "first(*, 1).allOf(x => x.intPrimitive < 5) as val2," +
                "last(*).allOf(x => x.intPrimitive < 5) as val3," +
                "last(*, 1).allOf(x => x.intPrimitive < 5) as val4" +
                " from SupportBean.win:length(2)";
        EPStatement stmtWindowAgg = epService.getEPAdministrator().createEPL(eplWindowAgg);
        stmtWindowAgg.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true, true, null, true, null});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false, true, false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true, false});

        stmtWindowAgg.destroy();

        // test scalar: window(*) and first(*)
        String eplWindowAggScalar = "select " +
                "window(intPrimitive).allOf(x => x < 5) as val0," +
                "first(intPrimitive).allOf(x => x < 5) as val1," +
                "first(intPrimitive, 1).allOf(x => x < 5) as val2," +
                "last(intPrimitive).allOf(x => x < 5) as val3," +
                "last(intPrimitive, 1).allOf(x => x < 5) as val4" +
                " from SupportBean.win:length(2)";
        EPStatement stmtWindowAggScalar = epService.getEPAdministrator().createEPL(eplWindowAggScalar);
        stmtWindowAggScalar.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true, true, null, true, null});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false, true, false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true, false});

        stmtWindowAggScalar.destroy();
    }

    public void testProperty() {

        // test fragment type - collection inside
        String eplFragment = "select contained.allOf(x => x.p00 < 5) as allOfX from SupportBean_ST0_Container.win:keepall()";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make3Value("ID1,KEY1,1"));
        assertEquals(true, listener.assertOneGetNewAndReset().get("allOfX"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make3Value("ID1,KEY1,10"));
        assertEquals(false, listener.assertOneGetNewAndReset().get("allOfX"));
        stmtFragment.destroy();

        // test array and iterable
        String[] fields = "val0,val1".split(",");
        eplFragment = "select intarray.sumof() as val0, " +
                "intiterable.sumOf() as val1 " +
                " from SupportCollection.win:keepall()";
        stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportCollection.makeNumeric("5,6,7"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {5+6+7, 5+6+7});
    }

    public void testPrevFuncs() {
        // test prevwindow(*) etc
        String[] fields = new String[] {"val0", "val1", "val2"};
        String epl = "select " +
                "prevwindow(sb).allOf(x => x.intPrimitive < 5) as val0," +
                "prev(sb,1).allOf(x => x.intPrimitive < 5) as val1," +
                "prevtail(sb,1).allOf(x => x.intPrimitive < 5) as val2" +
                " from SupportBean.win:length(2) as sb";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true, true, true});
        stmt.destroy();

        // test scalar prevwindow(property) etc
        String eplScalar = "select " +
                "prevwindow(intPrimitive).allOf(x => x < 5) as val0," +
                "prev(intPrimitive,1).allOf(x => x < 5) as val1," +
                "prevtail(intPrimitive,1).allOf(x => x < 5) as val2" +
                " from SupportBean.win:length(2) as sb";
        EPStatement stmtScalar = epService.getEPAdministrator().createEPL(eplScalar);
        stmtScalar.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true, true, true});
    }

    public void testUDFStaticMethod() {

        String[] fields = "val1,val2,val3,val4".split(",");
        epService.getEPAdministrator().getConfiguration().addImport(SupportBean_ST0_Container.class);
        String epl = "select " +
                "SupportBean_ST0_Container.makeSampleList().where(x => x.p00 < 5) as val1, " +
                "SupportBean_ST0_Container.makeSampleArray().where(x => x.p00 < 5) as val2, " +
                "makeSampleList().where(x => x.p00 < 5) as val3, " +
                "makeSampleArray().where(x => x.p00 < 5) as val4 " +
                "from SupportBean.win:length(2) as sb";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        
        SupportBean_ST0_Container.setSamples(new String[] {"E1,1", "E2,20", "E3,3"});
        epService.getEPRuntime().sendEvent(new SupportBean());
        for (String field : fields) {
            SupportBean_ST0[] result = toArray((Collection) listener.assertOneGetNew().get(field));
            assertEquals("Failed for field " + field, 2, result.length);
        }
        listener.reset();

        SupportBean_ST0_Container.setSamples(null);
        epService.getEPRuntime().sendEvent(new SupportBean());
        for (String field : fields) {
            assertNull(listener.assertOneGetNew().get(field));
        }
        listener.reset();

        SupportBean_ST0_Container.setSamples(new String[0]);
        epService.getEPRuntime().sendEvent(new SupportBean());
        for (String field : fields) {
            SupportBean_ST0[] result = toArray((Collection) listener.assertOneGetNew().get(field));
            assertEquals(0, result.length);
        }
        listener.reset();
        stmt.destroy();

        // test UDF returning scalar values collection
        fields = "val0,val1,val2,val3".split(",");
        epService.getEPAdministrator().getConfiguration().addImport(SupportCollection.class);
        String eplScalar = "select " +
                "SupportCollection.makeSampleListString().where(x => x != 'E1') as val0, " +
                "SupportCollection.makeSampleArrayString().where(x => x != 'E1') as val1, " +
                "makeSampleListString().where(x => x != 'E1') as val2, " +
                "makeSampleArrayString().where(x => x != 'E1') as val3 " +
                "from SupportBean.win:length(2) as sb";
        EPStatement stmtScalar = epService.getEPAdministrator().createEPL(eplScalar);
        stmtScalar.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtScalar.getEventType(), fields, new Class[] {Collection.class, Collection.class, Collection.class, Collection.class});

        SupportCollection.setSampleCSV("E1,E2,E3");
        epService.getEPRuntime().sendEvent(new SupportBean());
        for (String field : fields) {
            LambdaAssertionUtil.assertValuesArrayScalar(listener, field, "E2", "E3");
        }
        listener.reset();

        SupportCollection.setSampleCSV(null);
        epService.getEPRuntime().sendEvent(new SupportBean());
        for (String field : fields) {
            LambdaAssertionUtil.assertValuesArrayScalar(listener, field, null);
        }
        listener.reset();

        SupportCollection.setSampleCSV("");
        epService.getEPRuntime().sendEvent(new SupportBean());
        for (String field : fields) {
            LambdaAssertionUtil.assertValuesArrayScalar(listener, field);
        }
        listener.reset();
    }

    private SupportBean_ST0[] toArray(Collection<SupportBean_ST0> it) {
        if (!it.isEmpty() && it.iterator().next() instanceof EventBean) {
            fail("Iterator provides EventBean instances");
        }
        return it.toArray(new SupportBean_ST0[it.size()]);
    }
}
