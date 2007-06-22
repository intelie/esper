using System;
using System.Collections.Generic;
using System.Threading;

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

        public int NumExecuted
        {
            get { return numExecuted; }
        }

        private int numExecuted;

        /// <summary>
        /// Initializes a new instance of the <see cref="ExecutorService"/> class.
        /// </summary>
        public ExecutorService()
        {
            futuresMonitor = new Object();
            futuresPending = new List<FutureImpl>();
            isActive = true;
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

            futuresPending.Remove(future);
        }

        /// <summary>
        /// Submits the specified runnable to the thread pool.
        /// </summary>
        /// <param name="runnable">The runnable.</param>
        /// <returns></returns>
        public Future Submit(Runnable runnable)
        {
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
            isActive = false;
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
        }
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
            return new ExecutorService();
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

        public override void Invoke()
        {
            runnable.Run();
        }
    }
}
