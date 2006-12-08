package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.bean.*;
import net.esper.support.util.SupportUpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestPerf3StreamAndPropertyJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testPerfAllProps()
    {
        // Statement where all streams are reachable from each other via properties
        String stmt = "select * from " +
                SupportBean_A.class.getName() + "().win:length(1000000) s1," +
                SupportBean_B.class.getName() + "().win:length(1000000) s2," +
                SupportBean_C.class.getName() + "().win:length(1000000) s3" +
            " where s1.id=s2.id and s2.id=s3.id and s1.id=s3.id";
        tryJoinPerf3Streams(stmt);
    }

    public void testPerfPartialProps()
    {
        // Statement where the s1 stream is not reachable by joining s2 to s3 and s3 to s1
        String stmt = "select * from " +
                SupportBean_A.class.getName() + ".win:length(1000000) s1," +
                SupportBean_B.class.getName() + ".win:length(1000000) s2," +
                SupportBean_C.class.getName() + ".win:length(1000000) s3" +
            " where s1.id=s2.id and s2.id=s3.id";   // ==> therefore s1.id = s3.id
        tryJoinPerf3Streams(stmt);
    }

    public void testPerfPartialStreams()
    {
        String methodName = ".testPerfPartialStreams";

        // Statement where the s1 stream is not reachable by joining s2 to s3 and s3 to s1
        String stmt = "select * from " +
                SupportBean_A.class.getName() + "().win:length(1000000) s1," +
                SupportBean_B.class.getName() + "().win:length(1000000) s2," +
                SupportBean_C.class.getName() + "().win:length(1000000) s3" +
            " where s1.id=s2.id";   // ==> stream s3 no properties supplied, full s3 scan
        
        joinView = epService.getEPAdministrator().createEQL(stmt);
        joinView.addListener(updateListener);

        // preload s3 with just 1 event
        sendEvent(new SupportBean_C("GE_0"));

        // Send events for each stream
        log.info(methodName + " Preloading events");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            sendEvent(new SupportBean_A("CSCO_" + i));
            sendEvent(new SupportBean_B("IBM_" + i));
        }
        log.info(methodName + " Done preloading");

        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        // Stay below 500, no index would be 4 sec plus
        assertTrue((endTime - startTime) < 500);
    }

    private void tryJoinPerf3Streams(String joinStatement)
    {
        String methodName = ".tryJoinPerf3Streams";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        // Send events for each stream
        log.info(methodName + " Preloading events");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
        {
            sendEvent(new SupportBean_A("CSCO_" + i));
            sendEvent(new SupportBean_B("IBM_" + i));
            sendEvent(new SupportBean_C("GE_" + i));
        }
        log.info(methodName + " Done preloading");

        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        // Stay below 500, no index would be 4 sec plus
        assertTrue((endTime - startTime) < 500);
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestPerf3StreamAndPropertyJoin.class);
}