package com.espertech.esper.regression.support;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.event.EventBean;

import java.util.TreeMap;
import java.util.Map;
import java.util.Arrays;
import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.Assert;

public class ResultAssertExecution
{
    private static final Log log = LogFactory.getLog(ResultAssertExecution.class);
    private static final Log preformatlog = LogFactory.getLog("PREFORMATTED");

    private final EPServiceProvider engine;
    private EPStatement stmt;
    private final SupportUpdateListener listener;
    private final ResultAssertTestResult expected;
    private static final TreeMap<Long, TimeAction> input = ResultAssertInput.getActions();

    public ResultAssertExecution(EPServiceProvider engine,
                                 EPStatement stmt,
                                 SupportUpdateListener listener,
                                 ResultAssertTestResult expected) {
        this.engine = engine;
        this.stmt = stmt;
        this.listener = listener;
        this.expected = expected;
    }

    public void execute()
    {
        boolean isAssert = System.getProperty("ASSERTION_DISABLED") == null;

        // First execution is for ISTREAM only, which is the default
        execute(isAssert, true);

        // Second execution is for IRSTREAM, asserting both the insert and remove stream
        stmt.stop();
        String eql = stmt.getText();
        String irStreamEQL = eql.replace("select ", "select irstream ");
        stmt = engine.getEPAdministrator().createEQL(irStreamEQL);
        stmt.addListener(listener);
        execute(isAssert, false);
    }

    private void execute(boolean isAssert, boolean isExpectNullRemoveStream)
    {
        // For use in join tests, send join-to events
        engine.getEPRuntime().sendEvent(new SupportBean("S1", 0));
        engine.getEPRuntime().sendEvent(new SupportBean("S2", 0));
        engine.getEPRuntime().sendEvent(new SupportBean("S3", 0));

        if (preformatlog.isDebugEnabled())
        {
            preformatlog.debug(String.format("Category: %s   Output rate limiting: %s", expected.getCategory(), expected.getTitle()));
            preformatlog.debug("");
            preformatlog.debug("Statement:");
            preformatlog.debug(indentLines(stmt.getText()));
            preformatlog.debug("");
            preformatlog.debug(String.format("%28s  %38s", "Input", "Output"));
            preformatlog.debug(String.format("%45s  %15s  %15s", "", "Insert Stream", "Remove Stream"));
            preformatlog.debug(String.format("%28s  %30s", "-----------------------------------------------", "----------------------------------"));
            preformatlog.debug(String.format("%5s %5s%8s%8s", "Time", "Symbol", "Volume", "Price"));
        }

        for (Map.Entry<Long, TimeAction> timeEntry : input.entrySet())
        {
            long time = timeEntry.getKey();
            String timeInSec = String.format("%3.1f", time / 1000.0);

            log.info(".execute At " + timeInSec + " sending timer event");
            sendTimer(time);

            if (preformatlog.isDebugEnabled())
            {
                String comment = timeEntry.getValue().getActionDesc();
                comment = (comment == null) ? "" : comment;
                preformatlog.debug(String.format("%5s %24s %s", timeInSec, "", comment));
            }

            processAction(time, timeInSec, timeEntry.getValue(), isAssert, isExpectNullRemoveStream);
        }
    }

    private void processAction(long currentTime, String timeInSec, TimeAction value, boolean isAssert, boolean isExpectNullRemoveStream) {

        Map<Integer, StepDesc> assertions = expected.getAssertions().get(currentTime);

        // Assert step 0 which is the timer event result then send events and assert remaining
        assertStep(timeInSec, 0, assertions, expected.getProperties(), isAssert, isExpectNullRemoveStream);

        for (int step = 1; step < 10; step++)
        {
            if (value.getEvents().size() >= step)
            {
                EventSendDesc sendEvent = value.getEvents().get(step - 1);
                log.info(".execute At " + timeInSec + " sending event: " + sendEvent.getEvent() + " " + sendEvent.getEventDesc());
                engine.getEPRuntime().sendEvent(sendEvent.getEvent());

                if (preformatlog.isDebugEnabled())
                {
                    preformatlog.debug(String.format("%5s  %5s%8s %7.1f   %s", "",
                            sendEvent.getEvent().getSymbol(), sendEvent.getEvent().getVolume(), sendEvent.getEvent().getPrice(), sendEvent.getEventDesc()));
                }
            }

            assertStep(timeInSec, step, assertions, expected.getProperties(), isAssert, isExpectNullRemoveStream);
        }
    }

    private void assertStep(String timeInSec, int step, Map<Integer, StepDesc> stepAssertions, String[] fields, boolean isAssert, boolean isExpectNullRemoveStream) {

        if (preformatlog.isDebugEnabled())
        {
            if (listener.isInvoked())
            {
                UniformPair<String[]> received = renderReceived(fields);
                for (String newRow : received.getFirst())
                {
                    preformatlog.debug(String.format("%48s %s", "", newRow));
                }
                for (String oldRow : received.getSecond())
                {
                    preformatlog.debug(String.format("%68s %s", "", oldRow));
                }
            }
        }

        if (!isAssert)
        {
            listener.reset();
            return;
        }

        StepDesc stepDesc = null;
        if (stepAssertions != null)
        {
            stepDesc = stepAssertions.get(step);
        }
        
        // If there is no assertion, there should be no event received
        if (stepDesc == null)
        {
            Assert.assertFalse("At time " + timeInSec + " expected no events but received one or more", listener.isInvoked());
        }
        else
        {
            // If we do expect remove stream events, asset both
            if (!isExpectNullRemoveStream)
            {
                Assert.assertTrue("At time " + timeInSec + " expected events but received none", listener.isInvoked());
                ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), expected.getProperties(),
                        stepDesc.getNewDataPerRow(), "newData");

                ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), expected.getProperties(),
                        stepDesc.getOldDataPerRow(), "oldData");
            }
            // If we don't expect remove stream events (istream only), then asset new data only if there
            else
            {
                // If we do expect new data, assert
                if (stepDesc.getNewDataPerRow() != null)
                {
                    Assert.assertTrue("At time " + timeInSec + " expected events but received none", listener.isInvoked());
                    ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), expected.getProperties(),
                            stepDesc.getNewDataPerRow(), "newData");
                }
                else
                {
                    // If we don't expect new data, make sure its null
                    Assert.assertNull("At time " + timeInSec + " expected no insert stream events but received some", listener.getLastNewData());
                }

                Assert.assertNull("At time " + timeInSec + " expected no remove stream events but received some", listener.getLastOldData());
            }
        }
        listener.reset();
    }

    private UniformPair<String[]> renderReceived(String[] fields) {

        String[] renderNew = renderReceived(listener.getNewDataListFlattened(), fields);
        String[] renderOld = renderReceived(listener.getOldDataListFlattened(), fields);
        return new UniformPair<String[]>(renderNew, renderOld);
    }

    private String[] renderReceived(EventBean[] newDataListFlattened, String[] fields) {
        if (newDataListFlattened == null)
        {
            return new String[0];
        }
        String[] result = new String[newDataListFlattened.length];
        for (int i = 0; i < newDataListFlattened.length; i++)
        {
            Object[] values = new Object[fields.length];
            EventBean event = newDataListFlattened[i];
            for (int j = 0; j < fields.length; j++)
            {
                values[j] = event.get(fields[j]);
            }
            result[i] = Arrays.toString(values);
        }
        return result;
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = engine.getEPRuntime();
        runtime.sendEvent(event);
    }

    private String indentLines(String text)
    {
        StringWriter writer = new StringWriter();
        PrintWriter printer = new PrintWriter(writer);

        StringReader reader = new StringReader(text);
        LineNumberReader lineReader = new LineNumberReader(reader);
        boolean delimiter = false;
        while (true)
        {
            String line;
            try {
                line = lineReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null)
            {
                return writer.getBuffer().toString();
            }
            if (delimiter)
            {
                printer.println();
            }
            printer.print("  ");
            printer.print(line);
            delimiter = true;
        }
    }
}
