package net.esper.example.stockticker.monitor;

import net.esper.example.stockticker.eventbean.PriceLimit;
import net.esper.example.stockticker.eventbean.StockTick;
import net.esper.client.*;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockTickerMonitor
{
    private final EPServiceProvider epService;

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
	    EPStatement factory = epService.getEPAdministrator().createPattern(expressionText);

	    factory.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                PriceLimit limitBean = (PriceLimit) newEvents[0].get("limit");

                if (log.isDebugEnabled())
                {
                    log.debug(".update Received new limit, user=" + limitBean.getUserId() +
                          "  stock=" + limitBean.getStockSymbol() +
                          "  pct=" + limitBean.getLimitPct());
                }

                new StockTickerMonitor(StockTickerMonitor.this.epService, limitBean);
            }
	    });
    }

    public StockTickerMonitor(EPServiceProvider epService, PriceLimit limit)
    {
        this.epService = epService;
        this.limit = limit;

        String expressionText = "every limit=PriceLimit" +
                "(userId='" + limit.getUserId() + "'," +
                "stockSymbol='" + limit.getStockSymbol() + "')";
        newLimitListener = epService.getEPAdministrator().createPattern(expressionText);

        newLimitListener.addListener(new UpdateListener ()
        {
		    public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(".update Received an override limit, stopping listeners");
                }

		        die();
		    }
	    });

        expressionText = "tick=StockTick(stockSymbol='" + limit.getStockSymbol() + "')";
        initialPriceListener = epService.getEPAdministrator().createPattern(expressionText);

        initialPriceListener.addListener(new UpdateListener ()
        {
		    public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                StockTick tick = (StockTick) newEvents[0].get("tick");
                PriceLimit limit = StockTickerMonitor.this.limit;

                initialPriceListener = null;

                double limitPct = limit.getLimitPct();
                double upperLimit = tick.getPrice() * (1.0 + (limitPct/100.0));
                double lowerLimit = tick.getPrice() * (1.0 - (limitPct/100.0));

                if (log.isDebugEnabled())
                {
                    log.debug(".update Received initial tick, stock=" + tick.getStockSymbol() +
                          "  price=" + tick.getPrice() +
                          "  limit.limitPct=" + limitPct +
                          "  lowerLimit=" + lowerLimit +
                          "  upperLimit=" + upperLimit);
                }

                StockTickerAlertListener listener = new StockTickerAlertListener(StockTickerMonitor.this.epService, limit, tick);

                String expressionText = "every tick=StockTick" +
                         "(stockSymbol='" + limit.getStockSymbol() + "', price < " + lowerLimit + ")";
                lowPriceListener = StockTickerMonitor.this.epService.getEPAdministrator().createPattern(expressionText);
                lowPriceListener.addListener(listener);

                expressionText = "every tick=StockTick" +
                        "(stockSymbol='" + limit.getStockSymbol() + "', price > " + upperLimit + ")";
                highPriceListener = StockTickerMonitor.this.epService.getEPAdministrator().createPattern(expressionText);
                highPriceListener.addListener(listener);
		    }
	    });
    }

    private void die()
    {
	    if (newLimitListener != null) newLimitListener.removeAllListeners();
        if (initialPriceListener != null) initialPriceListener.removeAllListeners();
	    if (lowPriceListener != null) lowPriceListener.removeAllListeners();
        if (highPriceListener != null) highPriceListener.removeAllListeners();
    }

    private static final Log log = LogFactory.getLog(StockTickerMonitor.class);
}
