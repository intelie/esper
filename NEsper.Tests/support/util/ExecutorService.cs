using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.support.util
{
    /// <summary>
    /// Class that provides access to threadPool like services.  This class exists to
    /// provide an easier bridge between the CLR thread pool and the JVM thread pool
    /// mechanisms.
    /// </summary>

    public class ExecutorService
    {
        private readonly Object futuresMonitor;
        private readonly List<FutureImpl> futuresPending;
        private bool isActive;
        private bool isShutdown;

        /// <summary>
        /// Gets the number of items executed.
        /// </summary>
        /// <value>The num executed.</value>
        public int NumExecuted
        {
            get { return numExecuted; }
        }

        private int numExecuted;

        /// <summary>
        /// Initializes a new instance of the <see cref="ExecutorService"/> class.
        /// </summary>
        public ExecutorService(int maxNumThreads)
        {
            int workerThreads;
            int completionThreads;

            ThreadPool.GetMaxThreads(out workerThreads, out completionThreads);
            ThreadPool.SetMaxThreads(maxNumThreads, completionThreads);

            if (log.IsDebugEnabled)
            {
                log.Debug(String.Format(".ctor - Creating Executor with maxNumThreads = {0}", maxNumThreads));
            }

            futuresMonitor = new Object();
            futuresPending = new List<FutureImpl>();
            isActive = true;
            isShutdown = false;
            numExecuted = 0;
        }

        /// <summary>
        /// Dispatches the future.
        /// </summary>
        private void DispatchFuture(Object userData)
        {
            FutureImpl future = userData as FutureImpl;

            if (isActive)
            {
                if (future != null)
                {
                    future.Invoke();
                    numExecuted++;
                }
            }

            lock (futuresMonitor)
            {
                futuresPending.Remove(future);
                if (futuresPending.Count == 0)
                {
                    Monitor.PulseAll(futuresMonitor);
                }
            }

        }

        /// <summary>
        /// Submits the specified runnable to the thread pool.
        /// </summary>
        /// <param name="runnable">The runnable.</param>
        /// <returns></returns>
        public Future Submit(Runnable runnable)
        {
            if ( isShutdown )
            {
                throw new IllegalStateException("ExecutorService is shutdown");
            }

            FutureRunnableImpl future = new FutureRunnableImpl();
            future.Runnable = runnable;
            futuresPending.Add(future);

            ThreadPool.QueueUserWorkItem(DispatchFuture, future);

            return future;
        }


        /// <summary>
        /// Submits the specified callable to the thread pool.
        /// </summary>
        /// <param name="callable">The callable.</param>
        /// <returns></returns>
        public Future Submit(Callable callable)
        {
            if (isShutdown)
            {
                throw new IllegalStateException("ExecutorService is shutdown");
            }

            FutureCallbackImpl future = new FutureCallbackImpl();
            future.Callable = callable;
            futuresPending.Add(future);

            ThreadPool.QueueUserWorkItem(DispatchFuture, future);

            return future;
        }

        /// <summary>
        /// Shutdowns this instance.
        /// </summary>
        public void Shutdown()
        {
            // Mark the executor as inactive so that we
            // don't take any new callables.
            isShutdown = true;
        }

        /// <summary>
        /// Awaits the termination.
        /// </summary>
        /// <param name="timeout">The timeout.</param>
        public void AwaitTermination(TimeSpan timeout)
        {
            if ( futuresPending.Count != 0 )
            {
                lock (futuresMonitor)
                {
                    Monitor.Wait(futuresMonitor, timeout);
                }
            }

            isActive = false;
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }

    /// <summary>
    /// Class that provides access to threadPool like services.  This class exists to
    /// provide an easier bridge between the CLR thread pool and the JVM thread pool
    /// mechanisms.
    /// </summary>
    /// 
    public class Executors
    {
        /// <summary>
        /// Supposably creates a new thread pool and returns the executor.  Ours does
        /// nothing as we use the CLR thread pool.
        /// </summary>
        /// <param name="maxNumThreads">The max num threads.</param>
        /// <returns></returns>
        public static ExecutorService NewFixedThreadPool(int maxNumThreads)
        {
            return new ExecutorService(maxNumThreads);
        }
    }

    public interface Future
    {
        /// <summary>
        /// Gets the result value from the execution.
        /// </summary>
        /// <returns></returns>

        Object Get();
    }

    abstract public class FutureImpl : Future
    {
        /// <summary>
        /// Gets or sets the value.
        /// </summary>
        /// <value>The value.</value>
        public object Value
        {
            get { return value; }
            set { this.value = value; }
        }

        private object value;

        /// <summary>
        /// Gets the result value from the execution.
        /// </summary>
        /// <returns></returns>
        public object Get()
        {
            return value;
        }

        public abstract void Invoke();
    }

    public class FutureCallbackImpl : FutureImpl
    {
        /// <summary>
        /// Gets or sets the callable.
        /// </summary>
        /// <value>The callable.</value>
        public Callable Callable
        {
            get { return callable; }
            set { callable = value; }
        }

        private Callable callable;

        /// <summary>
        /// Invokes this instance.
        /// </summary>
        public override void Invoke()
        {
            this.Value = callable.Call();
        }
    }

    public class FutureRunnableImpl : FutureImpl
    {
        /// <summary>
        /// Gets or sets the runnable.
        /// </summary>
        /// <value>The runnable.</value>
        public Runnable Runnable
        {
            get { return runnable; }
            set { runnable = value; }
        }

        private Runnable runnable;

        /// <summary>
        /// Invokes this instance.
        /// </summary>
        public override void Invoke()
        {
            runnable.Run();
        }
    }
}
