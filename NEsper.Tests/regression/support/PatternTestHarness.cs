using System;
using System.Collections.Generic;
using System.Text;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.support
{
    /// <summary>
    /// Test harness for testing expressions and comparing received MatchedEventMap
    /// instances against against expected results.
    /// </summary>

    public class PatternTestHarness : SupportBeanConstants
    {
        private readonly EventCollection sendEventCollection;
        private readonly CaseList caseList;

        // Array of expressions and match listeners for listening to events for each test descriptor
        private EPStatement[] expressions;
        private SupportUpdateListener[] listeners;

        public PatternTestHarness(EventCollection sendEventCollection, CaseList caseList)
        {
            this.sendEventCollection = sendEventCollection;
            this.caseList = caseList;

            // Create a listener for each test descriptor
            this.listeners = new SupportUpdateListener[caseList.NumTests];
            for (int i = 0; i < listeners.Length; i++)
            {
                listeners[i] = new SupportUpdateListener();
            }
            expressions = new EPStatement[listeners.Length];
        }

        public void runTest()
        {
            runTest(false);
            runTest(true);
        }

        private void runTest(bool useEQL)
        {
            EPServiceProvider serviceProvider = EPServiceProviderManager.GetDefaultProvider();
            serviceProvider.Initialize();

            EPRuntime runtime = serviceProvider.EPRuntime;
            runtime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

            // Send the start time to the runtime
            if (sendEventCollection.getTime(EventCollection.ON_START_EVENT_ID) != null)
            {
                TimerEvent startTime = new CurrentTimeEvent(sendEventCollection.getTime(EventCollection.ON_START_EVENT_ID).GetValueOrDefault());
                runtime.SendEvent(startTime);
                log.Debug(".runTest Start time is " + startTime);
            }

            // Set up expression filters and match listeners

            int index = 0;
            foreach (EventExpressionCase descriptor in caseList.Results)
            {
                String expressionText = descriptor.getExpressionText();

                EPStatement statement = null;

                try
                {
                    if (useEQL)
                    {
                        expressionText = "select * from pattern [" + expressionText + "]";
                        statement = serviceProvider.EPAdministrator.createEQL(expressionText);
                    }
                    else
                    {
                        statement = serviceProvider.EPAdministrator.createPattern(expressionText);
                    }
                }
                catch (Exception ex)
                {
                    log.Fatal(".runTest Failed to create statement for pattern expression=" + expressionText, ex);
                    Assert.Fail();
                }

                // We stop the statement again and start after the first listener was added.
                // Thus we can handle patterns that fire on Startup.
                statement.Stop();

                expressions[index] = statement;
                expressions[index].AddListener(listeners[index]);

                // Start the statement again: listeners now got called for on-start events such as for a "not"
                statement.Start();

                index++;
            }

            // Some expressions may fire as soon as they are Started, such as a "not b()" expression, for example.
            // Check results for any such listeners/expressions.
            // NOTE: For EQL statements we do not support calling listeners when a pattern that fires upon Start.
            // Reason is that this should not be a relevant functionality of a pattern, the Start pattern
            // event itself cannot carry any information and is thus ignore. Note subsequent events
            // generated by the same pattern are fine.
            int totalEventsReceived = 0;
            if (useEQL)
            {
                clearListenerEvents();
                totalEventsReceived += countExpectedEvents(EventCollection.ON_START_EVENT_ID);
            }
            else    // Patterns do need to handle event publishing upon pattern expression Start (patterns that turn true right away)
            {
                checkResults(EventCollection.ON_START_EVENT_ID);
                totalEventsReceived += countListenerEvents();
                clearListenerEvents();
            }

            // Send actual test events
            foreach (KeyValuePair<String, Object> entry in sendEventCollection)
            {
                String eventId = entry.Key;

                // Manipulate the time when this event was send
                if (sendEventCollection.getTime(eventId) != null)
                {
                    TimerEvent currentTimeEvent = new CurrentTimeEvent(sendEventCollection.getTime(eventId).GetValueOrDefault());
                    runtime.SendEvent(currentTimeEvent);
                    log.Debug(".runTest Sending event " + entry.Key
                               + " = " + entry.Value +
                              "  timed " + currentTimeEvent);
                }

                // Send event itself
                runtime.SendEvent(entry.Value);

                // Check expected results for this event
                checkResults(eventId);

                // Count and clear the list of events that each listener has received
                totalEventsReceived += countListenerEvents();
                clearListenerEvents();
            }

            // Count number of expected matches
            int totalExpected = 0;
            foreach (EventExpressionCase descriptor in caseList.Results)
            {
                foreach (List<EventDescriptor> events in descriptor.getExpectedResults().Values)
                {
                    totalExpected += events.Count;
                }
            }

            if (totalExpected != totalEventsReceived)
            {
                log.Debug(".test Count expected does not match count received, expected=" + totalExpected +
                        " received=" + totalEventsReceived);
                Assert.IsTrue(false);
            }

            // Kill all expressions
            foreach (EPStatement expression in expressions)
            {
                expression.RemoveAllListeners();
            }

            // Send test events again to also test that all were indeed killed
            foreach (KeyValuePair<String, Object> entry in sendEventCollection)
            {
                runtime.SendEvent(entry.Value);
            }

            // Make sure all listeners are still at zero
            foreach (SupportUpdateListener listener in listeners)
            {
                if (listener.NewDataList.Count > 0)
                {
                    log.Debug(".test A match was received after Stopping all expressions");
                    Assert.IsTrue(false);
                }
            }
        }

        private void checkResults(String eventId)
        {
            // For each test descriptor, make sure the listener has received exactly the events expected
            int index = 0;
            log.Debug(".checkResults Checking results for event " + eventId);

            foreach (EventExpressionCase descriptor in caseList.Results)
            {
                String expressionText = expressions[index].Text;

                LinkedDictionary<String, List<EventDescriptor>> allExpectedResults = descriptor.getExpectedResults();
                EventBean[] receivedResults = listeners[index].LastNewData;
                index++;

                // If nothing at all was expected for this event, make sure nothing was received
                if (!(allExpectedResults.ContainsKey(eventId)))
                {
                    if ((receivedResults != null) && (receivedResults.Length > 0))
                    {
                        log.Debug(".checkResults Incorrect result for expression : " + expressionText);
                        log.Debug(".checkResults Expected no results for event " + eventId + ", but received " + receivedResults.Length + " events");
                        log.Debug(".checkResults Received, have " + receivedResults.Length + " entries");
                        printList(receivedResults);
                        Assert.IsFalse(true);
                    }
                    continue;
                }

                List<EventDescriptor> expectedResults = allExpectedResults.Fetch(eventId);

                // Compare the result lists, not caring about the order of the elements
                if (!(compareLists(receivedResults, expectedResults)))
                {
                    log.Debug(".checkResults Incorrect result for expression : " + expressionText);
                    log.Debug(".checkResults Expected size=" + expectedResults.Count + " received size=" + (receivedResults == null ? 0 : receivedResults.Length));

                    log.Debug(".checkResults Expected, have " + expectedResults.Count + " entries");
                    printList(expectedResults);
                    log.Debug(".checkResults Received, have " + (receivedResults == null ? 0 : receivedResults.Length) + " entries");
                    printList(receivedResults);

                    Assert.IsFalse(true);
                }
            }
        }

        private bool compareLists(EventBean[] receivedResults,
                                  List<EventDescriptor> expectedResults)
        {
            int receivedSize = (receivedResults == null) ? 0 : receivedResults.Length;
            if (expectedResults.Count != receivedSize)
            {
                return false;
            }

            // To make sure all received events have been expected
            List<EventDescriptor> expectedResultsClone = new List<EventDescriptor>(expectedResults);

            // Go through the list of expected results and remove from received result list if found
            foreach (EventDescriptor desc in expectedResults)
            {
                EventDescriptor foundMatch = null;

                foreach (EventBean received in receivedResults)
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

                expectedResultsClone.Remove(foundMatch);
            }

            // Any left over received results also invalidate the test
            if (expectedResultsClone.Count > 0)
            {
                return false;
            }
            return true;
        }

        private static bool compareEvents(EventDescriptor eventDesc, EventBean eventBean)
        {
            foreach (KeyValuePair<String, Object> entry in eventDesc.EventProperties)
            {
                if (eventBean[entry.Key] != entry.Value)
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
            foreach (SupportUpdateListener listener in listeners)
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
            foreach (SupportUpdateListener listener in listeners)
            {
                foreach (EventBean[] events in listener.NewDataList)
                {
                    count += events.Length;
                }
            }
            return count;
        }

        private void printList(List<EventDescriptor> events)
        {
            int index = 0;
            foreach (EventDescriptor desc in events)
            {
                StringBuilder buffer = new StringBuilder();
                int count = 0;

                foreach (KeyValuePair<String, Object> entry in desc.EventProperties)
                {
                    buffer.Append(" (" + (count++) + ") ");
                    buffer.Append("tag=" + entry.Key);

                    String id = findValue(entry.Value);
                    buffer.Append("  eventId=" + id);
                }

                log.Debug(".printList (" + index + ") : " + buffer.ToString());
                index++;
            }
        }

        private void printList(EventBean[] events)
        {
            if (events == null)
            {
                log.Debug(".printList : null-value events array");
                return;
            }

            log.Debug(".printList : " + events.Length + " elements...");
            for (int i = 0; i < events.Length; i++)
            {
                log.Debug("  " + EventBeanUtility.PrintEvent(events[i]));
            }
        }

        /**
         * Find the value object in the map of object names and values
         */
        private String findValue(Object value)
        {
            foreach (KeyValuePair<String, Object> entry in sendEventCollection)
            {
                if (value == entry.Value)
                {
                    return entry.Key;
                }
            }
            return null;
        }

        private int countExpectedEvents(String eventId)
        {
            int result = 0;
            foreach (EventExpressionCase descriptor in caseList.Results)
            {
                LinkedDictionary<String, List<EventDescriptor>> allExpectedResults = descriptor.getExpectedResults();

                // If nothing at all was expected for this event, make sure nothing was received
                if (allExpectedResults.ContainsKey(eventId))
                {
                    result++;
                }
            }
            return result;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
