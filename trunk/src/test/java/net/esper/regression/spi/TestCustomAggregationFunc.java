package net.esper.regression.spi;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestCustomAggregationFunc extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    // TODO: check custom non-aggre function with distinct, and static method with distinct
    // and either with all-keyword
    public void testCustomAggregation()
    {
        String joinStatement = "select myaggr(price) from " +
                SupportBean.class.getName() + "().win:length(3)";

        EPStatement joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);
    }

}
