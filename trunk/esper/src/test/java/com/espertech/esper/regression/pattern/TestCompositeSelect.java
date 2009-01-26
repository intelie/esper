package com.espertech.esper.regression.pattern;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.util.Random;
import java.util.Arrays;

public class TestCompositeSelect extends TestCase
{
    public void testFollowedByFilter()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("A", SupportBean_A.class.getName());
        config.addEventTypeAlias("B", SupportBean_B.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmtTxtOne = "insert into StreamOne select * from pattern [a=A -> b=B]";
        epService.getEPAdministrator().createEPL(stmtTxtOne);

        SupportUpdateListener listener = new SupportUpdateListener();
        String stmtTxtTwo = "select *, 1 as code from StreamOne";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmtTxtTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        EventBean event = listener.assertOneGetNewAndReset();

        System.out.println(Arrays.toString(stmtTwo.getEventType().getPropertyNames()));
        Object[] values = new Object[stmtTwo.getEventType().getPropertyNames().length];
        int count = 0;
        for (String name : stmtTwo.getEventType().getPropertyNames())
        {
            values[count++] = event.get(name);
        }
        System.out.println(Arrays.toString(values));
    }

    private class MyUpdateListener implements UpdateListener
    {
        private int badMatchCount;
        private int goodMatchCount;

        public void update(EventBean[] newEvents, EventBean[]
                oldEvents)
        {
            if (newEvents != null)
            {
                for (EventBean eventBean : newEvents)
                {
                    handleEvent(eventBean);
                }
            }
        }

        private void handleEvent(EventBean eventBean)
        {
            SupportTradeEvent tradeevent1 = (SupportTradeEvent)
                    eventBean.get("tradeevent1");
            SupportTradeEvent tradeevent2 = (SupportTradeEvent)
                    eventBean.get("tradeevent2");
            SupportTradeEvent tradeevent3 = (SupportTradeEvent)
                    eventBean.get("tradeevent3");

            if ((
                    tradeevent1.getUserId().equals(tradeevent2.getUserId()) ||
                            tradeevent1.getUserId().equals(tradeevent3.getUserId()) ||
                            tradeevent2.getUserId().equals(tradeevent3.getUserId())))
            {
                /*
                System.out.println("Bad Match : ");
                System.out.println(tradeevent1);
                System.out.println(tradeevent2);
                System.out.println(tradeevent3 + "\n");
                */
                badMatchCount++;
            }
            else
            {
                goodMatchCount++;
            }
        }
    }
}
