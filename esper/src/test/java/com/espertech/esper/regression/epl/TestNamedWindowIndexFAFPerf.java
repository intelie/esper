package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestNamedWindowIndexFAFPerf extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableQueryPlan(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testFAFKeyBTreePerformance()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        // create window one
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        EPStatement idx = epService.getEPAdministrator().createEPL("create index idx1 on MyWindow(intPrimitive btree)");

        // insert X rows
        int maxRows = 10000;   //for performance testing change to int maxRows = 100000;
        for (int i = 0; i < maxRows; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("A", i));
        }
        epService.getEPRuntime().sendEvent(new SupportBean("B", 100));

        // fire single-key queries
        String eplIdx1One = "select intPrimitive as sumi from MyWindow where intPrimitive = 5501";
        runFAFBTreeAssertion(eplIdx1One, 5501);

        String eplIdx1Two = "select sum(intPrimitive) as sumi from MyWindow where intPrimitive > 9997";
        runFAFBTreeAssertion(eplIdx1Two, 9998 + 9999);

        // drop index, create multikey btree
        idx.destroy();
        idx = epService.getEPAdministrator().createEPL("create index idx2 on MyWindow(intPrimitive btree, string btree)");

        String eplIdx2One = "select intPrimitive as sumi from MyWindow where intPrimitive = 5501 and string = 'A'";
        runFAFBTreeAssertion(eplIdx2One, 5501);

        String eplIdx2Two = "select sum(intPrimitive) as sumi from MyWindow where intPrimitive in [5000:5004) and string = 'A'";
        runFAFBTreeAssertion(eplIdx2Two, 5000+5001+5003+5002);

        // TODO string range?
        //String eplIdx2Three = "select sum(intPrimitive) as sumi from MyWindow where intPrimitive=5001 and string between 'A' and 'B'";
        //runFAFBTreeAssertion(eplIdx2Three, 5001);
    }

    private void runFAFBTreeAssertion(String epl, Integer expected) {
        long start = System.currentTimeMillis();
        int loops = 1000;

        EPOnDemandPreparedQuery query = epService.getEPRuntime().prepareQuery(epl);
        for (int i = 0; i < loops; i++) {
            runFAFQuery(query, expected);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 1000);
    }

    public void testFAFKeyAndRangePerformance()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        // create window one
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        epService.getEPAdministrator().createEPL("create index idx1 on MyWindow(string hash, intPrimitive btree)");

        // insert X rows
        int maxRows = 10000;   //for performance testing change to int maxRows = 100000;
        for (int i=0; i < maxRows; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("A", i));
        }

        // fire N queries
        long start = System.currentTimeMillis();
        int loops = 1000;

        EPOnDemandPreparedQuery queryBetween = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive between 200 and 202");
        EPOnDemandPreparedQuery queryBetweenReversed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive between 202 and 199");
        EPOnDemandPreparedQuery queryGELE = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive >= 200 and intPrimitive <= 202");
        EPOnDemandPreparedQuery queryGELEReversed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive >= 202 and intPrimitive <= 200");
        EPOnDemandPreparedQuery queryGT = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive > 9997");
        EPOnDemandPreparedQuery queryGE = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive >= 9997");
        EPOnDemandPreparedQuery queryLT = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive < 5");
        EPOnDemandPreparedQuery queryLE = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive <= 5");
        EPOnDemandPreparedQuery queryIn = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive in [200:202]");
        EPOnDemandPreparedQuery queryInHalfOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive in [200:202)");
        EPOnDemandPreparedQuery queryInHalfClosed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive in (200:202]");
        EPOnDemandPreparedQuery queryInOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive in (200:202)");
        EPOnDemandPreparedQuery queryNotIn = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive not in [3:9997]");
        EPOnDemandPreparedQuery queryNotInHalfOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive not in [3:9997)");
        EPOnDemandPreparedQuery queryNotInHalfClosed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive not in (3:9997]");
        EPOnDemandPreparedQuery queryNotInOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where string = 'A' and intPrimitive not in (3:9997)");

        for (int i = 0; i < loops; i++) {
            runFAFQuery(queryBetween, 603);
            runFAFQuery(queryBetweenReversed, 199+200+201+202);
            runFAFQuery(queryGELE, 603);
            runFAFQuery(queryGELEReversed, null);
            runFAFQuery(queryGT, 9998 + 9999);
            runFAFQuery(queryGE, 9997 + 9998 + 9999);
            runFAFQuery(queryLT, 4+3+2+1);
            runFAFQuery(queryLE, 5+4+3+2+1);
            runFAFQuery(queryIn, 603);
            runFAFQuery(queryInHalfOpen, 401);
            runFAFQuery(queryInHalfClosed, 403);
            runFAFQuery(queryInOpen, 201);
            runFAFQuery(queryNotIn, 1+2+9998+9999);
            runFAFQuery(queryNotInHalfOpen, 1+2+9997+9998+9999);
            runFAFQuery(queryNotInHalfClosed, 1+2+3+9998+9999);
            runFAFQuery(queryNotInOpen, 1+2+3+9997+9998+9999);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 1000);

        // test no value returned
        EPOnDemandPreparedQuery query = epService.getEPRuntime().prepareQuery("select * from MyWindow where string = 'A' and intPrimitive < 0");
        EPOnDemandQueryResult result = query.execute();
        assertEquals(0, result.getArray().length);
    }

    public void testFAFRangePerformance()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        // create window one
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        epService.getEPAdministrator().createEPL("create index idx1 on MyWindow(intPrimitive btree)");

        // insert X rows
        int maxRows = 10000;   //for performance testing change to int maxRows = 100000;
        for (int i=0; i < maxRows; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("K", i));
        }

        // fire N queries
        long start = System.currentTimeMillis();
        int loops = 1000;

        EPOnDemandPreparedQuery queryBetween = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive between 200 and 202");
        EPOnDemandPreparedQuery queryBetweenReversed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive between 202 and 199");
        EPOnDemandPreparedQuery queryGELE = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive >= 200 and intPrimitive <= 202");
        EPOnDemandPreparedQuery queryGELEReversed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive >= 202 and intPrimitive <= 200");
        EPOnDemandPreparedQuery queryGT = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive > 9997");
        EPOnDemandPreparedQuery queryGE = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive >= 9997");
        EPOnDemandPreparedQuery queryLT = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive < 5");
        EPOnDemandPreparedQuery queryLE = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive <= 5");
        EPOnDemandPreparedQuery queryIn = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive in [200:202]");
        EPOnDemandPreparedQuery queryInHalfOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive in [200:202)");
        EPOnDemandPreparedQuery queryInHalfClosed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive in (200:202]");
        EPOnDemandPreparedQuery queryInOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive in (200:202)");
        EPOnDemandPreparedQuery queryNotIn = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive not in [3:9997]");
        EPOnDemandPreparedQuery queryNotInHalfOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive not in [3:9997)");
        EPOnDemandPreparedQuery queryNotInHalfClosed = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive not in (3:9997]");
        EPOnDemandPreparedQuery queryNotInOpen = epService.getEPRuntime().prepareQuery("select sum(intPrimitive) as sumi from MyWindow where intPrimitive not in (3:9997)");

        for (int i = 0; i < loops; i++) {
            runFAFQuery(queryBetween, 603);
            runFAFQuery(queryBetweenReversed, 199+200+201+202);
            runFAFQuery(queryGELE, 603);
            runFAFQuery(queryGELEReversed, null);
            runFAFQuery(queryGT, 9998 + 9999);
            runFAFQuery(queryGE, 9997 + 9998 + 9999);
            runFAFQuery(queryLT, 4+3+2+1);
            runFAFQuery(queryLE, 5+4+3+2+1);
            runFAFQuery(queryIn, 603);
            runFAFQuery(queryInHalfOpen, 401);
            runFAFQuery(queryInHalfClosed, 403);
            runFAFQuery(queryInOpen, 201);
            runFAFQuery(queryNotIn, 1+2+9998+9999);
            runFAFQuery(queryNotInHalfOpen, 1+2+9997+9998+9999);
            runFAFQuery(queryNotInHalfClosed, 1+2+3+9998+9999);
            runFAFQuery(queryNotInOpen, 1+2+3+9997+9998+9999);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 1000);

        // test no value returned
        EPOnDemandPreparedQuery query = epService.getEPRuntime().prepareQuery("select * from MyWindow where intPrimitive < 0");
        EPOnDemandQueryResult result = query.execute();
        assertEquals(0, result.getArray().length);
    }

    public void testFAFKeyPerformance()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);

        // create window one
        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as (f1 string, f2 int)";
        epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2) select string, intPrimitive from SupportBean");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex on MyWindowOne(f1)");

        // insert X rows
        int maxRows = 100;   //for performance testing change to int maxRows = 100000;
        for (int i=0; i < maxRows; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("K" + i, i));
        }

        // fire N queries each returning 1 row
        long start = System.currentTimeMillis();
        String queryText = "select * from MyWindowOne where f1='K10'";
        EPOnDemandPreparedQuery query = epService.getEPRuntime().prepareQuery(queryText);
        int loops = 10000;  

        for (int i = 0; i < loops; i++) {
            EPOnDemandQueryResult result = query.execute();
            assertEquals(1, result.getArray().length);
            assertEquals("K10", result.getArray()[0].get("f1"));
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 200);
        
        // test no value returned
        queryText = "select * from MyWindowOne where f1='KX'";
        query = epService.getEPRuntime().prepareQuery(queryText);
        EPOnDemandQueryResult result = query.execute();
        assertEquals(0, result.getArray().length);

        // test query null
        queryText = "select * from MyWindowOne where f1=null";
        query = epService.getEPRuntime().prepareQuery(queryText);
        result = query.execute();
        assertEquals(0, result.getArray().length);
        
        // insert null and test null
        epService.getEPRuntime().sendEvent(new SupportBean(null, -2));
        result = query.execute();
        assertEquals(1, result.getArray().length);
        assertEquals(-2, result.getArray()[0].get("f2"));

        // test two values
        epService.getEPRuntime().sendEvent(new SupportBean(null, -1));
        query = epService.getEPRuntime().prepareQuery("select * from MyWindowOne where f1=null order by f2 asc");
        result = query.execute();
        assertEquals(2, result.getArray().length);
        assertEquals(-2, result.getArray()[0].get("f2"));
        assertEquals(-1, result.getArray()[1].get("f2"));
    }

    private void runFAFQuery(EPOnDemandPreparedQuery query, Integer expectedValue) {
        EPOnDemandQueryResult result = query.execute();
        assertEquals(1, result.getArray().length);
        assertEquals(expectedValue, result.getArray()[0].get("sumi"));
    }
}
