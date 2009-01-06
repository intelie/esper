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

public class MatchMakerMain
{
    public static void main(String[] args)
    {
        // This code runs as part of the automated regression test suite; Therefore disable internal timer theading to safe resources
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        MatchAlertListener listener = new MatchAlertListener();
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        new MatchMakingMonitor(listener);

        MobileUserBean user_1 = new MobileUserBean(1, 10, 10,
                Gender.MALE, HairColor.BLONDE, AgeRange.AGE_4,
                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_1);
        epService.getEPRuntime().sendEvent(user_1);

        MobileUserBean user_2 = new MobileUserBean(2, 10, 10,
                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_1,
                Gender.MALE, HairColor.BLONDE, AgeRange.AGE_4);
        epService.getEPRuntime().sendEvent(user_2);

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
    }    
}
