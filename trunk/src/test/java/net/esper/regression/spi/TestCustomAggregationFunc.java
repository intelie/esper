package net.esper.regression.spi;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.eql.core.Aggregator;
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

    // TODO: check custom non-aggregation function with distinct, and static method with distinct
    // and either with all-keyword
    public void testCustomAggregation()
    {
        Configuration config = new Configuration();
        //config.addUserDefAggregation("myaggr", MyAggregationFunc.class.getName());

        String joinStatement = "select myaggr(intPrimitive) from " +
                SupportBean.class.getName() + "().win:length(3)";

        EPStatement joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);
    }

    public class MyAggregationFunc implements Aggregator
    {
        public void validate()
        {

        }

        public void enter(Object value)
        {

        }

        public void leave(Object value)
        {

        }

        public Object getValue()
        {
            return null;
        }

        public Class getValueType()
        {
            return null;
        }

        public Aggregator newAggregator()
        {
            return null;
        }
    }

}
