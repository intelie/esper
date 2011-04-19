package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportDateTime;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestDTProperty extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportDateTime", SupportDateTime.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testProp() {

        String startTime = "2002-05-30T9:01:02.003";
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(SupportDateTime.make(startTime).getMsecdate()));

        String[] fields = "valmoh,valmoy,valdom,valdow,valdoy,valera,valhod,valmos,valsom,valwye,valyea,val1,val2,val3".split(",");
        String eplFragment = "select " +
                "current_timestamp.getMinuteOfHour() as valmoh,"+
                "current_timestamp.getMonthOfYear() as valmoy,"+
                "current_timestamp.getDayOfMonth() as valdom,"+
                "current_timestamp.getDayOfWeek() as valdow,"+
                "current_timestamp.getDayOfYear() as valdoy,"+
                "current_timestamp.getEra() as valera,"+
                "current_timestamp.gethourOfDay() as valhod,"+
                "current_timestamp.getmillisOfSecond()  as valmos,"+
                "current_timestamp.getsecondOfMinute() as valsom,"+
                "current_timestamp.getweekyear() as valwye,"+
                "current_timestamp.getyear() as valyea,"+
                "utildate.gethourOfDay() as val1," +
                "msecdate.gethourOfDay() as val2," +
                "caldate.gethourOfDay() as val3" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        for (String field : fields) {
            assertEquals(Integer.class, stmtFragment.getEventType().getPropertyType(field));
        }

        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {
                1, 4, 30, 5, 150, 1, 9, 3, 2, 22, 2002, 9, 9, 9
        });
    }
}
