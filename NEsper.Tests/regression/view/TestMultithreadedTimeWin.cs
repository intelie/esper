using System;
using System.Threading;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    /// <summary>
    /// Test for N threads feeding events that affect M statements which employ a small time window.
    /// Each of the M statements is associated with a symbol and each event send hits exactly one
    /// statement only.
    /// <p>
    /// Thus the timer is fairly busy when active, competing with N application threads.
    /// Created for ESPER-59 Internal Threading Bugs Found.
    /// <p>
    /// Exceptions can occur in
    /// (1) an application thread during SendEvent() outside of the listener, causes the test to fail
    /// (2) an application thread during SendEvent() inside of the listener, causes assertion to fail
    /// (3) the timer thread, causes an exception to be logged and assertion *may* fail
    /// </summary>

    [TestFixture]
    public class TestMultithreadedTimeWin
    {
        private Thread[] threads;
        private ResultUpdateListener[] listeners;

        [Test]
        public void testMultithreaded()
        {
            int numSymbols = 1;
            int numThreads = 4;
            int numEventsPerThread = 50000;
            double timeWindowSize = 0.2;

            // Set up threads, statements and listeners
            setUp(numSymbols, numThreads, numEventsPerThread, timeWindowSize);

            // Start threads
            long startTime = DateTimeHelper.CurrentTimeMillis;
            foreach (Thread thread in threads)
            {
                thread.Start();
            }

            // Wait for completion
            foreach (Thread thread in threads)
            {
                thread.Join();
            }
            long endTime = DateTimeHelper.CurrentTimeMillis;

            // Check listener results
            long totalReceived = 0;
            foreach (ResultUpdateListener listener in listeners)
            {
                totalReceived += listener.NumReceived;
                Assert.IsFalse(listener.CaughtRuntimeException);
            }
            double numTimeWindowAdvancements = (endTime - startTime) / 1000 / timeWindowSize;

            log.Info("Completed, expected=" + numEventsPerThread * numThreads + " numTimeWindowAdvancements=" + numTimeWindowAdvancements + " totalReceived=" + totalReceived);
            Assert.IsTrue(totalReceived < numEventsPerThread * numThreads + numTimeWindowAdvancements + 1);
            Assert.IsTrue(totalReceived >= numEventsPerThread * numThreads);
        }

        private void setUp(int numSymbols, int numThreads, int numEvents, double timeWindowSize)
        {
            EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            // Create a statement for N number of symbols, each it's own listener
            String[] symbols = new String[numSymbols];
            listeners = new ResultUpdateListener[symbols.Length];
            for (int i = 0; i < symbols.Length; i++)
            {
                symbols[i] = "S" + i;
                String viewExpr = "select symbol, sum(volume) as sumVol " + "from " + typeof(SupportMarketDataBean).FullName + "(symbol='" + symbols[i] + "').win:time(" + timeWindowSize + ")";
                EPStatement testStmt = epService.EPAdministrator.CreateEQL(viewExpr);
                listeners[i] = new ResultUpdateListener();
                testStmt.AddListener(listeners[i]);
            }

            // Create threads to send events
            threads = new Thread[numThreads];
            TimeWinRunnable[] runnables = new TimeWinRunnable[threads.Length];
            Object lockObj = new Object();
            for (int i = 0; i < threads.Length; i++)
            {
                runnables[i] = new TimeWinRunnable(i, epService.EPRuntime, lockObj, symbols, numEvents);
                threads[i] = new Thread(new ThreadStart(runnables[i].Run));
            }
        }

        public class TimeWinRunnable
        {
            private readonly int threadNum;
            private readonly EPRuntime epRuntime;
            private readonly Object sharedLock;
            private readonly String[] symbols;
            private readonly int numberOfEvents;

            public TimeWinRunnable(int threadNum, EPRuntime epRuntime, Object sharedLock, String[] symbols, int numberOfEvents)
            {
                this.threadNum = threadNum;
                this.epRuntime = epRuntime;
                this.sharedLock = sharedLock;
                this.symbols = symbols;
                this.numberOfEvents = numberOfEvents;
            }

            public virtual void Run()
            {
                for (int i = 0; i < numberOfEvents; i++)
                {
                    int symbolNum = (threadNum + numberOfEvents) % symbols.Length;
                    String symbol = symbols[symbolNum];
                    long volume = 1;

                    Object _event = new SupportMarketDataBean(symbol, -1, volume, null);

                    lock (sharedLock)
                    {
                        epRuntime.SendEvent(_event);
                    }
                }
            }
        }

        public class ResultUpdateListener : UpdateListener
        {
            virtual public int NumReceived
            {
                get { return numReceived; }
            }

            virtual public bool CaughtRuntimeException
            {
                get { return isCaughtRuntimeException; }
            }

            private bool isCaughtRuntimeException;
            private int numReceived = 0;
            private String lastSymbol = null;

            public virtual void Update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                if ((newEvents == null) || (newEvents.Length == 0))
                {
                    return;
                }

                try
                {
                    numReceived += newEvents.Length;

                    String symbol = (String)newEvents[0]["symbol"];
                    if (lastSymbol != null)
                    {
                        Assert.AreEqual(lastSymbol, symbol);
                    }
                    else
                    {
                        lastSymbol = symbol;
                    }
                }
                catch (IllegalStateException ex)
                {
                    log.Error("Unexpected exception querying results", ex);
                    isCaughtRuntimeException = true;
                    throw;
                }
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
