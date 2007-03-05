using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;
using net.esper.example.stockticker.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.stockticker.monitor
{
    public class StockTickerMonitor
    {
        private readonly EPServiceProvider epService;

        private PriceLimit limit = null;

        private EPStatement newLimitListener = null;
        private EPStatement initialPriceListener = null;
        private EPStatement lowPriceListener = null;
        private EPStatement highPriceListener = null;

        public StockTickerMonitor(EPServiceProvider epService)
        {
            this.epService = epService;

            // Listen to all limits to be set
            String expressionText = "every limit=PriceLimit()";
            EPStatement factory = epService.EPAdministrator.CreatePattern(expressionText);

            factory.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    PriceLimit limitBean = (PriceLimit)newEvents[0]["Limit"];

                    if (log.IsDebugEnabled)
                    {
                        log.Debug(".update Received new limit, user=" + limitBean.UserId +
                              "  stock=" + limitBean.StockSymbol +
                              "  pct=" + limitBean.LimitPct);
                    }

                    new StockTickerMonitor(this.epService, limitBean);
                }));
        }

        public StockTickerMonitor(EPServiceProvider epService, PriceLimit limit)
        {
            this.epService = epService;
            this.limit = limit;

            String expressionText = "every limit=PriceLimit" +
                    "(userId='" + limit.UserId + "'," +
                    "stockSymbol='" + limit.StockSymbol + "')";
            newLimitListener = epService.EPAdministrator.CreatePattern(expressionText);

            newLimitListener.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    if (log.IsDebugEnabled)
                    {
                        log.Debug(".update Received an override limit, stopping listeners");
                    }

                    Die();
                }));

            expressionText = "tick=StockTick(stockSymbol='" + limit.StockSymbol + "')";
            initialPriceListener = epService.EPAdministrator.CreatePattern(expressionText);

            initialPriceListener.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    StockTick tick = (StockTick)newEvents[0]["tick"];
                    PriceLimit dlimit = this.limit;

                    initialPriceListener = null;

                    double limitPct = dlimit.LimitPct;
                    double upperLimit = tick.Price * (1.0 + (limitPct / 100.0));
                    double lowerLimit = tick.Price * (1.0 - (limitPct / 100.0));

                    if (log.IsDebugEnabled)
                    {
                        log.Debug(".update Received initial tick, stock=" + tick.StockSymbol +
                              "  price=" + tick.Price +
                              "  limit.limitPct=" + limitPct +
                              "  lowerLimit=" + lowerLimit +
                              "  upperLimit=" + upperLimit);
                    }

                    StockTickerAlertListener listener = new StockTickerAlertListener(this.epService, limit, tick);

                    String lExpressionText = "every tick=StockTick" +
                             "(stockSymbol='" + limit.StockSymbol + "', price < " + lowerLimit + ")";
                    lowPriceListener = this.epService.EPAdministrator.CreatePattern(lExpressionText);
                    lowPriceListener.AddListener(listener.Update);

                    lExpressionText = "every tick=StockTick" +
                            "(stockSymbol='" + limit.StockSymbol + "', price > " + upperLimit + ")";
                    highPriceListener = this.epService.EPAdministrator.CreatePattern(lExpressionText);
                    highPriceListener.AddListener(listener.Update);
                }));
        }

        private void Die()
        {
            if (newLimitListener != null) newLimitListener.RemoveAllListeners();
            if (initialPriceListener != null) initialPriceListener.RemoveAllListeners();
            if (lowPriceListener != null) lowPriceListener.RemoveAllListeners();
            if (highPriceListener != null) highPriceListener.RemoveAllListeners();
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
