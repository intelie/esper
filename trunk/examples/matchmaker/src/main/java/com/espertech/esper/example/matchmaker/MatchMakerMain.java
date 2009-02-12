package com.espertech.esper.example.matchmaker;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.example.matchmaker.monitor.MatchAlertListener;
import com.espertech.esper.example.matchmaker.monitor.MatchMakingMonitor;
import com.espertech.esper.example.matchmaker.eventbean.MobileUserBean;
import com.espertech.esper.example.matchmaker.eventbean.Gender;
import com.espertech.esper.example.matchmaker.eventbean.HairColor;
import com.espertech.esper.example.matchmaker.eventbean.AgeRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

public class MatchMakerMain
{
    private static final Log log = LogFactory.getLog(MatchMakerMain.class);

    public static void main(String[] args)
    {
        log.info("Setting up EPL");
        // This code runs as part of the automated regression test suite; Therefore disable internal timer theading to safe resources
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        MatchAlertListener listener = new MatchAlertListener();
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        new MatchMakingMonitor(listener);

        log.info("Sending user information");
        MobileUserBean user_1 = new MobileUserBean(1, 10, 10,
                Gender.MALE, HairColor.BLONDE, AgeRange.AGE_4,
                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_1);
        epService.getEPRuntime().sendEvent(user_1);

        MobileUserBean user_2 = new MobileUserBean(2, 10, 10,
                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_1,
                Gender.MALE, HairColor.BLONDE, AgeRange.AGE_4);
        epService.getEPRuntime().sendEvent(user_2);

        log.info("Sending some near locations");
        user_1.setLocation(8.99999, 10);
        epService.getEPRuntime().sendEvent(user_1);

        user_1.setLocation(9, 10);
        epService.getEPRuntime().sendEvent(user_1);

        user_1.setLocation(11, 10);
        epService.getEPRuntime().sendEvent(user_1);

        user_1.setLocation(11.0000001, 10);
        epService.getEPRuntime().sendEvent(user_1);

        user_2.setLocation(10.0000001, 9);
        epService.getEPRuntime().sendEvent(user_2);

        user_1 = new MobileUserBean(1, 10, 10,
                Gender.MALE, HairColor.RED, AgeRange.AGE_6,
                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_5);
        epService.getEPRuntime().sendEvent(user_1);

        // Test all combinations
        for (Gender gender : Gender.values())
        {
            for (HairColor color : HairColor.values())
            {
                for (AgeRange age : AgeRange.values())
                {
                    // Try user preferences
                    MobileUserBean userA = new MobileUserBean(2, 10, 10,
                            Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_5,
                            gender, color, age);
                    epService.getEPRuntime().sendEvent(userA);

                }
            }
        }
        
        log.info("Sending 100k of random locations");
        Random random = new Random();
        for (int i = 1; i < 100000; i++)
        {
            int x = 10 + random.nextInt(i) / 100000;
            int y = 10 + random.nextInt(i) / 100000;

            user_2.setLocation(x, y);
            epService.getEPRuntime().sendEvent(user_2);
        }        

        log.info("Done.");
    }
}
