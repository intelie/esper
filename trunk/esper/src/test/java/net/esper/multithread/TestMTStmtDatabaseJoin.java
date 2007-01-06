package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportDatabaseService;

import java.util.concurrent.*;
import java.util.Properties;

/**
 * Test for multithread-safety for database joins.
 *
 */
public class TestMTStmtDatabaseJoin extends TestCase
{
    private EPServiceProvider engine;

    private final static String EVENT_NAME = SupportBean.class.getName();

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        configDB.setConnectionCatalog("test");
        configDB.setConnectionReadOnly(true);
        configDB.setConnectionTransactionIsolation(1);
        configDB.setConnectionAutoCommit(true);
        Configuration configuration = new Configuration();
        configuration.addDatabaseReference("MyDB", configDB);

        engine = EPServiceProviderManager.getProvider("TestMTStmtDatabaseJoin", configuration);        
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testJoin() throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEQL("select * \n" +
                "  from " + EVENT_NAME + ".win:length(1000) as s0,\n" +
                "      sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s1"
                );
        trySendAndReceive(4, stmt, 1000);
        trySendAndReceive(2, stmt, 2000);
    }

    private void trySendAndReceive(int numThreads, EPStatement statement, int numRepeats) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtDatabaseJoinCallable(engine, statement, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue("Failed in " + statement.getText(), (Boolean) future[i].get());
        }
    }
}
