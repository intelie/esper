package com.espertech.esper.epl.metric;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MetricsExecutorThreaded implements MetricsExecutor
{
    private final ExecutorService threadPool;

    public MetricsExecutorThreaded(final String engineURI)
    {
        ThreadFactory threadFactory = new ThreadFactory()
        {
            int count = 0;
            public Thread newThread(Runnable r)
            {
                Thread t = new Thread(r);
                t.setName("com.espertech.esper.metricsreporting-" + engineURI + "-" + count);
                count++;
                t.setDaemon(true);
                return t;
            }
        };
        threadPool = Executors.newCachedThreadPool(threadFactory);
    }

    public void execute(final MetricExec execution, final MetricExecutionContext executionContext)
    {
        Runnable runnable = new Runnable() {
            public void run()
            {
                execution.execute(executionContext);
            }
        };
        threadPool.execute(runnable);
    }

    public void destroy()
    {
        threadPool.shutdownNow();
        
        try
        {
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
