using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;
using net.esper.example.qos_sla.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.qos_sla.monitor
{
    public class ErrorRateMonitor
    {
        public ErrorRateMonitor()
        {
            EPAdministrator admin = EPServiceProviderManager.GetDefaultProvider().EPAdministrator;

            EPStatement pattern = admin.CreatePattern("every timer:at(*, *, *, *, *, */10)");
            EPStatement view = admin.CreateEQL("select * from " + typeof(OperationMeasurement).FullName +
                    "(success=false).win:time(10 min).std:size()");

            pattern.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    IEnumerator<EventBean> viewEnum = view.GetEnumerator();
                    viewEnum.MoveNext();

                    long count = (long)viewEnum.Current["size"];

                    log.Info(".update Info, error rate in the last 10 minutes is " + count);
                }));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}