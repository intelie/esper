import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import junit.framework.TestCase;

public class TestDeleteMe extends TestCase {

    public void testit() throws Exception {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        String epl = "create schema TickEvent(symbol string);" +
                "create schema NewsEvent(symbol string);" +
                "select * from TickEvent.std:lastevent(), NewsEvent.std:lastevent();" +
                "" +
                "select * from TickEvent.std:unique(symbol) as t, NewsEvent.std:unique(symbol) as n\n" +
                "where t.symbol = n.symbol;" +
                "" +
                "select * from TickEvent.std:unique(symbol) as t\n" +
                "full outer join NewsEvent.std:unique(symbol) as n on t.symbol = n.symbol;" +
                "" +
                "select * from TickEvent as t unidirectional, NewsEvent.std:unique(symbol) as n \n" +
                "where t.symbol = n.symbol;" +
                "" +
                "select * from pattern[every timer:interval(5 sec)] unidirectional, \n" +
                "  TickEvent.std:unique(symbol) t, NewsEvent.std:unique(symbol) as n \n" +
                "where t.symbol = n.symbol";
        epService.getEPAdministrator().getDeploymentAdmin().parseDeploy(epl, null, null, null);
    }

}
