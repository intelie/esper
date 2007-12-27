package net.esper.regression.support;

import junit.framework.TestCase;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.soda.EPStatementObjectModel;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.TimerEvent;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.support.bean.SupportBeanConstants;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.SupportUpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Test harness for testing expressions and comparing received MatchedEventMap instances against against expected results.
 */
public class PatternTestHarness implements SupportBeanConstants
{
    private final EventCollection sendEventCollection;
    private final CaseList caseList;

    // Array of expressions and match listeners for listening to events for each test descriptor
    private EPStatement expressions[];
    private SupportUpdateListener listeners[];

    public PatternTestHarness( EventCollection sendEventCollection,
                               CaseList caseList)
    {
        this.sendEventCollection = sendEventCollection;
        this.caseList = caseList;

        // Create a listener for each test descriptor
        this.listeners = new SupportUpdateListener[caseList.getNumTests()];
        for (int i = 0; i < listeners.length; i++)
        {
            listeners[i] = new SupportUpdateListener();
        }
        expressions = new EPStatement[listeners.length];
    }

    public void runTest() throws Exception
    {
        runTest(PatternTestStyle.USE_PATTERN_LANGUAGE);
        runTest(PatternTestStyle.USE_EQL);
        runTest(PatternTestStyle.COMPILE_TO_MODEL);
        runTest(PatternTestStyle.COMPILE_TO_EQL);
    }

    private void runTest(PatternTestStyle testStyle) throws Exception
    {
        EPServiceProvider serviceProvider = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        serviceProvider.initialize();

        EPRuntime runtime = serviceProvider.getEPRuntime();
        runtime.sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        // Send the start time to the runtime
        if (sendEventCollection.getTime(EventCollection.ON_START_EVENT_ID) != null)
        {
            TimerEvent startTime = new CurrentTimeEvent(sendEventCollection.getTime(EventCollection.ON_START_EVENT_ID));
            runtime.sendEvent(startTime);
            log.debug(".runTest Start time is " + startTime);
        }

        // Set up expression filters and match listeners

        int index = 0;
        for (EventExpressionCase descriptor : caseList.getResults())
        {
            String expressionText = descriptor.getExpressionText();
            EPStatementObjectModel model = descriptor.getObjectModel();

            EPStatement statement = null;

            try
            {
                if (model != null)
                {
                    statement = serviceProvider.getEPAdministrator().create(model);
                }
                else
                {
                    if (testStyle == PatternTestStyle.USE_PATTERN_LANGUAGE)
                    {
                        statement = serviceProvider.getEPAdministrator().createPattern(expressionText);
                    }
                    else if (testStyle == PatternTestStyle.USE_EQL)
                    {
                        String text = "select * from pattern [" + expressionText + "]";
                        statement = serviceProvider.getEPAdministrator().createEQL(text);
                        expressionText = text;
                    }
                    else if (testStyle == PatternTestStyle.COMPILE_TO_MODEL)
                    {
                        String text = "select * from pattern [" + expressionText + "]";
                        EPStatementObjectModel mymodel = serviceProvider.getEPAdministrator().compileEQL(text);
                        statement = serviceProvider.getEPAdministrator().create(mymodel);
                        expressionText = text;
                    }
                    else if (testStyle == PatternTestStyle.COMPILE_TO_EQL)
                    {
                        String text = "select * from pattern [" + expressionText + "]";
                        EPStatementObjectModel mymodel = serviceProvider.getEPAdministrator().compileEQL(text);
                        String reverse = mymodel.toEQL();
                        statement = serviceProvider.getEPAdministrator().createEQL(reverse);
                        expressionText = reverse;
                    }
                    else
                    {
                        throw new IllegalArgumentException("Unknown test style");
                    }
                }
            }
            catch (Exception ex)
            {
                String text = expressionText;
                if (model != null)
                {
                    text = "Model: " + model.toEQL();
                }
                log.fatal(".runTest Failed to create statement for style " + testStyle + " pattern expression=" + text, ex);
                TestCase.fail();
            }

            // We stop the statement again and start after the first listener was added.
            // Thus we can handle patterns that fireStatementStopped on startup.
            statement.stop();

            expressions[index] = statement;
            expressions[index].addListener(listeners[index]);

            // Start the statement again: listeners now got called for on-start events such as for a "not"
            statement.start();

            index++;
        }

        // Some expressions may fireStatementStopped as soon as they are started, such as a "not b()" expression, for example.
        // Check results for any such listeners/expressions.
        // NOTE: For EQL statements we do not support calling listeners when a pattern that fires upon start.
        // Reason is that this should not be a relevant functionality of a pattern, the start pattern
        // event itself cannot carry any information and is thus ignore. Note subsequent events
        // generated by the same pattern are fine.
        int totalEventsReceived = 0;
        if (testStyle != PatternTestStyle.USE_PATTERN_LANGUAGE)
        {
            clearListenerEvents();
            totalEventsReceived += countExpectedEvents(EventCollection.ON_START_EVENT_ID);
        }
        else    // Patterns do need to handle event publishing upon pattern expression start (patterns that turn true right away)
        {
            checkResults(testStyle, EventCollection.ON_START_EVENT_ID);
            totalEventsReceived += countListenerEvents();
            clearListenerEvents();
        }

        // Send actual test events
        for (Map.Entry<String, Object> entry : sendEventCollection.entrySet())
        {
            String eventId = entry.getKey();

            // Manipulate the time when this event was send
            if (sendEventCollection.getTime(eventId) != null)
            {
                TimerEvent currentTimeEvent = new CurrentTimeEvent(sendEventCollection.getTime(eventId));
                runtime.sendEvent(currentTimeEvent);
                log.debug(".runTest Sending event " + entry.getKey()
                           + " = " + entry.getValue() +
                          "  timed " + currentTimeEvent);
            }

            // Send event itself
            runtime.sendEvent(entry.getValue());

            // Check expected results for this event
            checkResults(testStyle, eventId);

            // Count and clear the list of events that each listener has received
            totalEventsReceived += countListenerEvents();
            clearListenerEvents();
        }

        // Count number of expected matches
        int totalExpected = 0;
        for (EventExpressionCase descriptor : caseList.getResults())
        {
            for (LinkedList<EventDescriptor> events : descriptor.getExpectedResults().values())
            {
                totalExpected += events.size();
            }
        }

        if (totalExpected != totalEventsReceived)
        {
            log.debug(".test Count expected does not match count received, expected=" + totalExpected +
                    " received=" + totalEventsReceived);
            TestCase.assertTrue(false);
        }

        // Kill all expressions
        for (EPStatement expression : expressions)
        {
            expression.removeAllListeners();
        }

        // Send test events again to also test that all were indeed killed
        for (Map.Entry<String, Object> entry : sendEventCollection.entrySet())
        {
            runtime.sendEvent(entry.getValue());
        }

        // Make sure all listeners are still at zero
        for (SupportUpdateListener listener : listeners)
        {
            if (listener.getNewDataList().size() > 0)
            {
                log.debug(".test A match was received after stopping all expressions");
                TestCase.assertTrue(false);
            }
        }
    }

    private void checkResults(PatternTestStyle testStyle, String eventId)
    {
        // For each test descriptor, make sure the listener has received exactly the events expected
        int index = 0;
        log.debug(".checkResults Checking results for event " + eventId);

        for (EventExpressionCase descriptor : caseList.getResults())
        {
            String expressionText = expressions[index].getText();

            LinkedHashMap<String, LinkedList<EventDescriptor>> allExpectedResults = descriptor.getExpectedResults();
            EventBean[] receivedResults = listeners[index].getLastNewData();
            index++;

            // If nothing at all was expected for this event, make sure nothing was received
            if (!(allExpectedResults.containsKey(eventId)))
            {
                if ((receivedResults != null) && (receivedResults.length > 0))
                {
                    log.debug(".checkResults Incorrect result for style " + testStyle + " expression : " + expressionText);
                    log.debug(".checkResults Expected no results for event " + eventId + ", but received " + receivedResults.length + " events");
                    log.debug(".checkResults Received, have " + receivedResults.length + " entries");
                    printList(receivedResults);
                    TestCase.assertFalse(true);
                }
                continue;
            }

            LinkedList<EventDescriptor> expectedResults = allExpectedResults.get(eventId);

            // Compare the result lists, not caring about the order of the elements
            if (!(compareLists(receivedResults, expectedResults)))
            {
                log.debug(".checkResults Incorrect result for style " + testStyle + " expression : " + expressionText);
                log.debug(".checkResults Expected size=" + expectedResults.size() + " received size=" + (receivedResults == null ? 0 : receivedResults.length));

                log.debug(".checkResults Expected, have " + expectedResults.size() + " entries");
                printList(expectedResults);
                log.debug(".checkResults Received, have " + (receivedResults == null ? 0 : receivedResults.length) + " entries");
                printList(receivedResults);

                TestCase.assertFalse(true);
            }
        }
    }
    
    private boolean compareLists(EventBean[] receivedResults,
                                 LinkedList<EventDescriptor> expectedResults)
    {
        int receivedSize = (receivedResults == null) ? 0 : receivedResults.length;
        if (expectedResults.size() != receivedSize)
        {
            return false;
        }

        // To make sure all received events have been expected
        LinkedList<EventDescriptor> expectedResultsClone = new LinkedList<EventDescriptor>(expectedResults);

        // Go through the list of expected results and remove from received result list if found
        for (EventDescriptor desc : expectedResults)
        {
            EventDescriptor foundMatch = null;

            for (EventBean received : receivedResults)
            {
                if (compareEvents(desc, received))
                {
                    foundMatch = desc;
                    break;
                }
            }

            // No match between expected and received
            if (foundMatch == null)
            {
                return false;
            }

            expectedResultsClone.remove(foundMatch);
        }

        // Any left over received results also invalidate the test
        if (expectedResultsClone.size() > 0)
        {
            return false;
        }
        return true;
    }

    private static boolean compareEvents(EventDescriptor eventDesc, EventBean eventBean)
    {
        for (Map.Entry<String, Object> entry : eventDesc.getEventProperties().entrySet())
        {
            if (!(eventBean.get(entry.getKey()) == (entry.getValue())))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Clear the event list of all listeners
     */
    private void clearListenerEvents()
    {
        for (SupportUpdateListener listener : listeners)
        {
            listener.reset();
        }
    }

    /**
     * Clear the event list of all listeners
     */
    private int countListenerEvents()
    {
        int count = 0;
        for (SupportUpdateListener listener : listeners)
        {
            for (EventBean[] events : listener.getNewDataList())
            {
                count += events.length;
            }
        }
        return count;
    }

    private void printList(LinkedList<EventDescriptor> events)
    {
        int index = 0;
        for (EventDescriptor desc : events)
        {
            StringBuffer buffer = new StringBuffer();
            int count = 0;

            for (Map.Entry<String, Object> entry : desc.getEventProperties().entrySet())
            {
                buffer.append(" (" + (count++) + ") ");
                buffer.append("tag=" + entry.getKey());

                String id = findValue(entry.getValue());
                buffer.append("  eventId=" + id);
            }

            log.debug(".printList (" + index + ") : " + buffer.toString());
            index++;
        }
    }

    private void printList(EventBean[] events)
    {
        if (events == null)
        {
            log.debug(".printList : null-value events array");
            return;
        }
        
        log.debug(".printList : " + events.length + " elements...");
        for (int i = 0; i < events.length; i++)
        {
            log.debug("  " + EventBeanUtility.printEvent(events[i]));
        }
    }

    /**
     * Find the value object in the map of object names and values
     */
    private String findValue(Object value)
    {
        for (Map.Entry<String, Object> entry : sendEventCollection.entrySet())
        {
            if (value == entry.getValue())
            {
                return entry.getKey();
            }
        }
        return null;
    }

    private int countExpectedEvents(String eventId)
    {
        int result = 0;
        for (EventExpressionCase descriptor : caseList.getResults())
        {
            LinkedHashMap<String, LinkedList<EventDescriptor>> allExpectedResults = descriptor.getExpectedResults();

            // If nothing at all was expected for this event, make sure nothing was received
            if (allExpectedResults.containsKey(eventId))
            {
                result++;
            }
        }
        return result;
    }

    private enum PatternTestStyle
    {
        USE_PATTERN_LANGUAGE,
        USE_EQL,
        COMPILE_TO_MODEL,
        COMPILE_TO_EQL;
    }

    private static final Log log = LogFactory.getLog(PatternTestHarness.class);
}
