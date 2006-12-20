package net.esper.example.rsi;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.example.stockticker.eventbean.StockTick;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class RSIListener implements UpdateListener
{
    private double _avgLoss, _avgGain, _rs, _rsi;
    private int _rsiCount;

    public RSIListener()
    {
    }

    public void reset()
    {
    	_avgLoss = 0;
    	_avgGain = 0;
    	_rs = 0;
    	_rsi = 0;
    	_rsiCount = 0;
    }

    public double getAvgLoss()
    {
    	return _avgLoss;
    }

    public double getAvgGain()
    {
    	return _avgGain;
    }

    public double getRS()
    {
    	return _rs;
    }

    public double getRSI()
    {
    	return _rsi;
    }

    public int getRSICount()
    {
    	return _rsiCount;
    }

    public void update(EventBean[] newEvents_, EventBean[] oldEvents_)
    {
    	Object event = newEvents_[0].get("tick");
        StockTick tick = (StockTick) event;
    	_log.info(" Stock " + tick.getStockSymbol() + " Price " + tick.getPrice());
    	event = newEvents_[0].get("avgLoss");
    	_avgLoss = (Double)event;
    	if (_avgLoss == Double.MIN_VALUE)
    	{
    		_log.info(" Not Meaningful ");
    	}
    	else
    	{
    		_avgLoss = to1tenthPrecision((Double)event);
    		_log.info(" AvgLoss " + _avgLoss);
    	}
    	event = newEvents_[0].get("avgGain");
    	_avgGain = (Double)event;
    	if (_avgGain == Double.MIN_VALUE)
    	{
    		_log.info(" Not Meaningful ");
    	}
    	else
    	{
    		_avgGain = to1tenthPrecision((Double)event);
    		_log.info(" AvgGain " + _avgGain);
    	}
    	event = newEvents_[0].get("RS");
    	_rs = (Double)event;
    	if (_rs == Double.MIN_VALUE)
    	{
    		_log.info(" Not Meaningful ");
    	}
    	else
    	{
        	_rs = to1tenthPrecision((Double)event);
    		_log.info(" RS " + _rs);
    	}
    	event = newEvents_[0].get("RSI");
    	_rsi = (Double)event;
    	if (_rsi == Double.MIN_VALUE)
    	{
    		_log.info(" Not Meaningful ");
    	}
    	else
    	{
    		_rsiCount ++;
        	_rsi = to1tenthPrecision((Double)event);
    		_log.info(" RSI " + _rsi);
    	}
    }

    private double to1tenthPrecision(double aDouble)
    {
        int intValue = (int) (aDouble * 10);
        return intValue / 10.0;
    }

    private static final Log _log = LogFactory.getLog(RSIListener.class);
}

