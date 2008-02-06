package com.espertech.esper.regression.support;

import com.espertech.esper.support.bean.SupportMarketDataBean;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ResultAssertInput
{
    private static TreeMap<Long, TimeAction> actions;

    static
    {
        init();
    }

    public static TreeMap<Long, TimeAction> getActions() {
        return actions;
    }

    private static void init()
    {
        actions = new TreeMap<Long, TimeAction>();

        // Instructions for a test set:
        // hardcoded for a 5.5-second time window and 1-second output rate !
        // First set the time, second send the event(s)
        add(200, makeEvent("S1", 100, 25), "Event E1 arrives");
        add(800, makeEvent("S2", 5000, 9), "Event E2 arrives");
        add(1000);
        add(1200);
        add(1500, makeEvent("S1", 150, 24), "Event E3 arrives");
        add(1500, makeEvent("S3", 10000, 1), "Event E4 arrives");
        add(2000);
        add(2100, makeEvent("S1", 155, 26), "Event E5 arrives");
        add(2200);
        add(2500);
        add(3000);
        add(3200);
        add(3500, makeEvent("S3", 11000, 2), "Event E6 arrives");
        add(4000);
        add(4200);
        add(4300, makeEvent("S1", 150, 22), "Event E7 arrives");
        add(4900, makeEvent("S3", 11500, 3), "Event E8 arrives");
        add(5000);
        add(5200);
        add(5700, "Event E1 leaves the time window");
        add(5900, makeEvent("S3", 10500, 1), "Event E9 arrives");
        add(6000);
        add(6200);
        add(6300, "Event E2 leaves the time window");
        add(7000, "Event E3 and E4 leave the time window");
        add(7200);
    }

    private static void add(long time, SupportMarketDataBean event, String eventDesc)
    {
        TimeAction timeAction = actions.get(time);
        if (timeAction == null)
        {
            timeAction = new TimeAction();
            actions.put(time, timeAction);
        }
        timeAction.add(event, eventDesc);
    }

    private static void add(long time)
    {
        add(time, null);
    }

    private static void add(long time, String desc)
    {
        TimeAction timeAction = actions.get(time);
        if (timeAction == null)
        {
            timeAction = new TimeAction();
            timeAction.setActionDesc(desc);
            actions.put(time, timeAction);
        }
    }

    private static SupportMarketDataBean makeEvent(String symbol, long volume, double price)
    {
        return new SupportMarketDataBean(symbol, price, volume, "");
    }

}
