package com.espertech.esper.regression.rev;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.event.EventBean;
import junit.framework.TestCase;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class TestNamedWinRevision extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement stmtCreateWin;
    private SupportUpdateListener listenerOne;
    private SupportUpdateListener listenerTwo;
    private SupportUpdateListener listenerThree;
    private SupportUpdateListener listenerFour;

    // TODO: test invalid configuration
    // TODO: test subclasses
    // TODO: test no full type send
    // TODO: test stop and start
    // TODO: test send different event types
    // TODO: multithreaded
    // TODO: runtime configuration options
    // TODO: remaining TODOs
    // TODO: test multiple group keys
    // TODO: test on-delete
    // TODO: test invalid insert-into
    // TODO: test data windows: time, batch, unique, sorted-props, grouped
    // TODO: test policies for resolving last version
    // TODO: test invalid event type inserted
    // TODO: test send delta first then full
    
    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        config.addEventTypeAlias("FullEvent", SupportRevisionFull.class);
        config.addEventTypeAlias("D1", SupportDeltaOne.class);
        config.addEventTypeAlias("D2", SupportDeltaTwo.class);
        config.addEventTypeAlias("D3", SupportDeltaThree.class);
        config.addEventTypeAlias("D4", SupportDeltaFour.class);
        config.addEventTypeAlias("D5", SupportDeltaFive.class);

        ConfigurationRevisionEvent configRev = new ConfigurationRevisionEvent();
        configRev.setKeyPropertyNames(new String[] {"k0"});
        configRev.setAliasFullEvent("FullEvent");
        configRev.addAliasRevisionEvent("D1");
        configRev.addAliasRevisionEvent("D2");
        configRev.addAliasRevisionEvent("D3");
        configRev.addAliasRevisionEvent("D4");
        configRev.addAliasRevisionEvent("D5");
        config.addRevisionEvent("RevisableQuote", configRev);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerOne = new SupportUpdateListener();
        listenerTwo = new SupportUpdateListener();
        listenerThree = new SupportUpdateListener();
        listenerFour = new SupportUpdateListener();

        stmtCreateWin = epService.getEPAdministrator().createEPL("create window RevQuote.win:keepall() as select * from RevisableQuote");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from FullEvent");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D1");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D2");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D3");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D4");
        epService.getEPAdministrator().createEPL("insert into RevQuote select * from D5");
    }

    public void testRevision()
    {
        EPStatement consumerOne = epService.getEPAdministrator().createEPL("select * from RevQuote");
        consumerOne.addListener(listenerOne);
        EPStatement consumerTwo = epService.getEPAdministrator().createEPL("select k0, count(*) as count, sum(Long.parseLong(p0)) as sum from RevQuote group by k0");
        consumerTwo.addListener(listenerTwo);

        String[] fields = "k0,p0,p1,p2,p3,p4,p5".split(",");
        String[] agg = "k0,count,sum".split(",");

        epService.getEPRuntime().sendEvent(new SupportRevisionFull("k00", "01", "p10", "20", "p30", "40", "50"));
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"k00", "01", "p10", "20", "p30", "40", "50"});
        ArrayAssertionUtil.assertProps(stmtCreateWin.iterator().next(), fields, new Object[] {"k00", "01", "p10", "20", "p30", "40", "50"});
        ArrayAssertionUtil.assertProps(listenerTwo.assertOneGetNewAndReset(), agg, new Object[] {"k00", 1L, 1L});

        epService.getEPRuntime().sendEvent(new SupportDeltaThree("k00", "03", "41"));
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"k00", "03", "p10", "20", "p30", "41", "50"});
        ArrayAssertionUtil.assertProps(stmtCreateWin.iterator().next(), fields, new Object[] {"k00", "03", "p10", "20", "p30", "41", "50"});
        ArrayAssertionUtil.assertProps(listenerTwo.assertOneGetNewAndReset(), agg, new Object[] {"k00", 1L, 3L});

        epService.getEPRuntime().sendEvent(new SupportDeltaOne("k00", "p11", "51"));
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"k00", "03", "p11", "20", "p30", "41", "51"});

        epService.getEPRuntime().sendEvent(new SupportDeltaTwo("k00", "04", "21", "p31"));
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"k00", "04", "p11", "21", "p31", "41", "51"});

        epService.getEPRuntime().sendEvent(new SupportDeltaFour("k00", "05", "22", "52"));
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"k00", "05", "p11", "22", "p31", "41", "52"});

        epService.getEPRuntime().sendEvent(new SupportDeltaFive("k00", "p12", "53"));
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"k00", "05", "p12", "22", "p31", "41", "53"});

        epService.getEPRuntime().sendEvent(new SupportRevisionFull("k00", "06", "p13", "23", "p32", "42", "54"));
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"k00", "06", "p13", "23", "p32", "42", "54"});
    }

    public void testRevisionGen()
    {
        Random random = new Random();
        Map<String, Map<String, String>> last = new HashMap<String, Map<String, String>>();
        int count = 0;
        String[] groups = new String[] {"K0", "K1", "K2", "K4"};

        EPStatement consumerOne = epService.getEPAdministrator().createEPL("select * from RevQuote");
        consumerOne.addListener(listenerOne);

        for (int i = 0; i < groups.length; i++)
        {
            String key = groups[i];
            Object event = new SupportRevisionFull(key, "0-" + next(count), "1-" + next(count), "2-" + next(count),
                    "3-" + next(count), "4-" + next(count), "5-" + next(count));
            add(last, key, "0-" + next(count), "1-" + next(count), "2-" + next(count),
                    "3-" + next(count), "4-" + next(count), "5-" + next(count));
            epService.getEPRuntime().sendEvent(event);
        }
        listenerOne.reset();
        
        for (int i = 0; i < 10000; i++)
        {
            if (i % 20000 == 0)
            {
                System.out.println(".testRevisionGen Loop " + i);
            }
            int typeNum = random.nextInt(6);
            String key = groups[random.nextInt(groups.length)];
            count++;

            Object event;
            if (typeNum == 0) {
                event = new SupportRevisionFull(key, "0-" + next(count), "1-" + next(count), "2-" + next(count),
                        "3-" + next(count), "4-" + next(count), "5-" + next(count));
                add(last, key, "0-" + next(count), "1-" + next(count), "2-" + next(count),
                        "3-" + next(count), "4-" + next(count), "5-" + next(count));
            }
            else if (typeNum == 1) {
                event = new SupportDeltaOne(key, "1-" + next(count), "5-" + next(count));
                add(last, key, null, "1-" + next(count), null, null, null, "5-" + next(count));
            }
            else if (typeNum == 2) {
                event = new SupportDeltaTwo(key, "0-" + next(count), "2-" + next(count), "3-" + next(count));
                add(last, key, "0-" + next(count), null, "2-" + next(count), "3-" + next(count), null, null);
            }
            else if (typeNum == 3) {
                event = new SupportDeltaThree(key, "0-" + next(count), "4-" + next(count));
                add(last, key, "0-" + next(count), null, null, null, "4-" + next(count), null);
            }
            else if (typeNum == 4) {
                event = new SupportDeltaFour(key, "0-" + next(count), "2-" + next(count), "5-" + next(count));
                add(last, key, "0-" + next(count), null, "2-" + next(count), null, null, "5-" + next(count));
            }
            else if (typeNum == 5) {
                event = new SupportDeltaFive(key, "1-" + next(count), "5-" + next(count));
                add(last, key, null, "1-" + next(count), null, null, null, "5-" + next(count));
            }
            else {
                throw new IllegalStateException();
            }

            epService.getEPRuntime().sendEvent(event);
            assertEvent(last, listenerOne.assertOneGetNewAndReset(), count);
        }
    }

    private void assertEvent(Map<String, Map<String, String>> last, EventBean eventBean, int count)
    {
        String error = "Error asseting count " + count;
        String key = (String) eventBean.get("k0");
        Map<String, String> vals = last.get(key);
        assertEquals(error, vals.get("p0"), eventBean.get("p0"));
        assertEquals(error, vals.get("p1"), eventBean.get("p1"));
        assertEquals(error, vals.get("p2"), eventBean.get("p2"));
        assertEquals(error, vals.get("p3"), eventBean.get("p3"));
        assertEquals(error, vals.get("p4"), eventBean.get("p4"));
        assertEquals(error, vals.get("p5"), eventBean.get("p5"));
    }

    private void add(Map<String, Map<String, String>> last, String key, String s0, String s1, String s2, String s3, String s4, String s5)
    {
        Map<String, String> entry = last.get(key);
        if (entry == null)
        {
            entry = new HashMap<String, String>();
            last.put(key, entry);
        }

        if (s0 != null) {entry.put("p0", s0);}
        if (s1 != null) {entry.put("p1", s1);}
        if (s2 != null) {entry.put("p2", s2);}
        if (s3 != null) {entry.put("p3", s3);}
        if (s4 != null) {entry.put("p4", s4);}
        if (s5 != null) {entry.put("p5", s5);}
    }

    private String next(int num)
    {
        return Integer.toString(num);
    }
}
