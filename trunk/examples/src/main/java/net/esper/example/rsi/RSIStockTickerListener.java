package net.esper.example.rsi;

import net.esper.client.UpdateListener;
import net.esper.client.EPServiceProvider;
import net.esper.example.stockticker.eventbean.StockTick;
import net.esper.event.EventBean;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * RSI gives you the trend for a stock and for more complete explanation, you can visit the link:
 * <a href="http://www.stockcharts.com/education/IndicatorAnalysis/indic_RSI.html">RSI</a>
 * <p>
 * After a definite number of stock events, or accumulation period, the first RSI is computed.
 * Then for each subsequent stock event, the RSI calculations use the previous period’s Average Gain and
 * Loss to determine the “smoothed RSI”.
 */
public class RSIStockTickerListener implements UpdateListener
{
    private final EPServiceProvider _epService;
    private EventBean[] _oldEvents = null;
    private int _period;
    private int _count = 0;
    private List<Double> _adv, _decl;
    private double _avgGain = Double.MIN_VALUE, _avgLoss = Double.MIN_VALUE;
    private double _rs = Double.MIN_VALUE, _rsi = Double.MIN_VALUE;


    public RSIStockTickerListener(EPServiceProvider epService_, int periods_)
    {
        _epService = epService_;
        _period = periods_;
        _oldEvents = null;
        _adv = new ArrayList<Double>();
        _decl = new ArrayList<Double>();
    }

    public void reset(int period_)
    {
    	_period = period_;
        _oldEvents = null;
        _adv.clear();
        _decl.clear();
        _avgGain = Double.MIN_VALUE;
        _avgLoss = Double.MIN_VALUE;
        _rs = Double.MIN_VALUE;
        _rsi = Double.MIN_VALUE;
    }

    public int getCount()
    {
    	return _count;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
		Object event =  newEvents[0].get("tick");
		StockTick newTick = (StockTick) event;
       	_log.info(".update for stock=" + newTick.getStockSymbol() + "  price=" + newTick.getPrice());

    	if (_oldEvents != null)
    	{
    		event =  _oldEvents[0].get("tick");
    		StockTick oldTick = (StockTick) event;
    		compute(newTick, oldTick);
    		_epService.getEPRuntime().sendEvent(new RSIEvent(newTick, _avgLoss, _avgGain, _rs, _rsi));
    	}
   		_oldEvents = newEvents;
    }

    private void compute(StockTick newTick_, StockTick oldTick_)
    {
    	_count++;
    	double change =  newTick_.getPrice() - oldTick_.getPrice();
    	if ( _count <= _period)
    	{
        	if (change > 0)
        	{
               	_log.info(".Count " + _count + " Advance "  + change);
               	_adv.add(change);
        	}
        	else
        	{
               	_log.info(".Count " + _count + " Decline "  + change);
               	_decl.add(change);
        	}
    	}

    	if (_count >=_period)
    	{
    		if (_count == _period)
    		{
    			_avgLoss = avgValueList(_decl);
    			_avgGain = avgValueList(_adv);
    		}
    		else
    		{
    			_adv.clear();
    			_decl.clear();
    			double adv = 0.0;
    			double decl = 0.0;
	        	if (change > 0)
	        	{
	        		_log.info(".Count " + _count + " Advance "  + change);
	        		adv = change;
	        	}
	        	else
	        	{
	               	_log.info(".Count " + _count + " Decline "  + change);
	               	decl = change;
	        	}
        		_avgGain = ((_avgGain * (_period - 1)) + adv) / _period;
        		_avgLoss = ((_avgLoss * (_period - 1)) + decl) / _period;
    		}
    		if (_avgLoss == 0)
    		{
    			_rs = 100.0;
    			_rsi = 100.0;
    		}
    		else
    		{
    			_rs = Math.abs(_avgGain/_avgLoss);
    			double to1 = 1.0 + _rs;
    			double to100 = 100.0/to1;
    			_rsi = 100.0 - to100;
    		}
    	}
    }

    private double avgValueList(List<Double> lValues_)
    {
    	double sum = 0.0;
    	for (Iterator<Double> itV= (lValues_.iterator()); itV.hasNext();)
    	{
    		double val = (Double) (itV.next());
    		sum = sum + val;
    	}
    	return (sum/_count);
    }

    private static final Log _log = LogFactory.getLog(RSIStockTickerListener.class);
}

