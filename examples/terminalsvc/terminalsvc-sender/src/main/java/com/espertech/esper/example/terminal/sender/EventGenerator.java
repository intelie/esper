/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.sender;

import com.espertech.esper.example.terminal.common.*;

import java.util.List;
import java.util.Random;
import java.util.LinkedList;

public class EventGenerator
{
    private final Random random;

    public EventGenerator()
    {
        this.random = new Random();
    }

    public List<BaseTerminalEvent> generateBatch()
    {
        List<BaseTerminalEvent> batch = new LinkedList<BaseTerminalEvent>();

        // Sometimes generate a low paper or out-of-order event
        generateTerminalEvent(batch);

        // Generate a couple of checkin, completed and cancelled events, and sometimes an out-of-order
        generateCheckin(batch);

        return batch;
    }

    private void generateCheckin(List<BaseTerminalEvent> eventBatch)
    {
        // Generate up to 100 unique terminal ids between 100 and 200
        String[] termIds = new String[100];
        for (int i = 0; i < termIds.length; i++)
        {
            termIds[i] = Long.toString(i + 1000);
        }

        // Swap terminals to get a random ordering
        randomize(termIds);

        // Add a check-in event for each
        for (int i = 0; i < termIds.length; i++)
        {
            Checkin checkin = new Checkin(new TerminalInfo(termIds[i]));
            eventBatch.add(checkin);
        }

        // Add a cancelled or completed or out-of-order for each
        int completedCount = 0;
        int cancelledCount = 0;
        int outOfOrderCount = 0;
        for (int i = 0; i < termIds.length; i++)
        {
            BaseTerminalEvent event = null;

            // With a 1 in 1000 chance send an OutOfOrder
            if (random.nextInt(1000) == 0)
            {
                outOfOrderCount++;
                event = new OutOfOrder(new TerminalInfo(termIds[i]));
                System.out.println("Generated an Checkin followed by " + event.getType() + " event for terminal " + event.getTerm().getId());
            }
            else if (random.nextBoolean())
            {
                completedCount++;
                event = new Completed(new TerminalInfo(termIds[i]));
            }
            else
            {
                cancelledCount++;
                event = new Cancelled(new TerminalInfo(termIds[i]));
            }

            eventBatch.add(event);
        }

        System.out.println("Generated " + termIds.length + " Checkin events followed by " +
                completedCount + " Completed and " +
                cancelledCount + " Cancelled and " +
                outOfOrderCount + " OutOfOrder events");
    }

    private void generateTerminalEvent(List<BaseTerminalEvent> eventBatch)
    {
        if (random.nextInt(10) > 0)
        {
            return;
        }

        BaseTerminalEvent event = null;
        if (random.nextBoolean())
        {
            event = new LowPaper(getRandomTermInfo());
        }
        else
        {
            event = new OutOfOrder(getRandomTermInfo());
        }

        eventBatch.add(event);
        System.out.println("Generated " + event.getType() + " event for terminal " + event.getTerm().getId());
    }

    // Swap 100 values in the array
    private void randomize(String[] values)
    {
        for (int i = 0; i < 100; i++)
        {
            int pos1 = random.nextInt(values.length);
            int pos2 = random.nextInt(values.length);
            String temp = values[pos2];
            values[pos2] = values[pos1];
            values[pos1] = temp;
        }
    }

    private TerminalInfo getRandomTermInfo()
    {
        return new TerminalInfo(getRandomTermId());
    }

    private String getRandomTermId()
    {
        int id = random.nextInt(1000);
        return Integer.toString(id);
    }
}
