package net.esper.example.qos_sla.monitor;

import net.esper.client.*;
import net.esper.example.qos_sla.eventbean.OperationMeasurement;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorRateMonitor
{
    public ErrorRateMonitor()
    {
        EPAdministrator admin = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();

        EPStatement pattern = admin.createPattern("every timer:at(*, *, *, *, *, */10)");
        final EPStatement view = admin.createEQL("select * from " + OperationMeasurement.class.getName() +
                "(success=false).win:time(10 min).std:size()");

        pattern.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                long count = (Long) view.iterator().next().get("size");

                log.info(".update Info, error rate in the last 10 minutes is " + count);
            }
        });
    }

    private static final Log log = LogFactory.getLog(ErrorRateMonitor.class);
}
