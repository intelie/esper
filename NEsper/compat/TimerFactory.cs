using System;
using System.Configuration;
using System.Threading;

namespace net.esper.compat
{
    public class TimerFactory
    {
        /// <summary>
        /// Gets the default timer factory
        /// </summary>

        public static ITimerFactory DefaultTimerFactory
        {
            get
            {
                using (ReaderLock readerLock = new ReaderLock(readerWriterLock))
                {
                    if (defaultTimerFactory == null)
                    {
                        // use the system timer factory unless explicitly instructed
                        // to do otherwise.

                        defaultTimerFactory = new HighResolutionTimerFactory();
                    }

                    return defaultTimerFactory;
                }
            }
            set
            {
                using (WriterLock writerLock = new WriterLock(readerWriterLock))
                {
                    defaultTimerFactory = value;
                }
            }
        }

        private static ITimerFactory defaultTimerFactory;
        private static ReaderWriterLock readerWriterLock = new ReaderWriterLock();
    }
}
