using System;

using net.esper.client;
using net.esper.events;
using net.esper.example.stockticker.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.stockticker.monitor
{
    public class StockTickerAlertListener
    {
        private readonly EPServiceProvider epService;
        private readonly PriceLimit limit;
        private readonly StockTick initialPriceTick;

        public StockTickerAlertListener(EPServiceProvider epService, PriceLimit limit, StockTick initialPriceTick)
        {
            this.epService = epService;
            this.limit = limit;
            this.initialPriceTick = initialPriceTick;
        }

        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            Object eventBean = newEvents[0]["Tick"];
            StockTick tick = (StockTick)eventBean;

            log.Debug(".update Alert for stock=" + tick.StockSymbol +
                      "  price=" + tick.Price +
                      "  initialPriceTick=" + initialPriceTick.Price +
                      "  limt=" + limit.LimitPct);

            LimitAlert alert = new LimitAlert(tick, limit, initialPriceTick.Price);
            epService.EPRuntime.Emit(alert);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
