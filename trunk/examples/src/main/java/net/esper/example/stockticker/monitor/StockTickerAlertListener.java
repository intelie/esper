package net.esper.example.stockticker.monitor;

import net.esper.client.UpdateListener;
import net.esper.client.EPServiceProvider;
import net.esper.example.stockticker.eventbean.PriceLimit;
import net.esper.example.stockticker.eventbean.StockTick;
import net.esper.example.stockticker.eventbean.LimitAlert;
import net.esper.event.EventBean;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class StockTickerAlertListener implements UpdateListener
{
    private final EPServiceProvider epService;
    private final PriceLimit limit;
    private final StockTick initialPriceTick;

    public StockTickerAlertListener(EPServiceProvider epService, PriceLimit limit, StockTick initialPriceTick)
    {
        this.epService = epService;
        this.limit = limit;
        this.initialPriceTick = initialPriceTick;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        Object event =  newEvents[0].get("tick");
        StockTick tick = (StockTick) event;

        log.debug(".update Alert for stock=" + tick.getStockSymbol() +
                  "  price=" + tick.getPrice() +
                  "  initialPriceTick=" + initialPriceTick.getPrice() +
                  "  limt=" + limit.getLimitPct());

        LimitAlert alert = new LimitAlert(tick, limit, initialPriceTick.getPrice());
        epService.getEPRuntime().emit(alert);
    }

    private static final Log log = LogFactory.getLog(StockTickerAlertListener.class);
}

