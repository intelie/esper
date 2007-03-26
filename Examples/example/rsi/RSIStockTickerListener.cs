using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;
using net.esper.example.stockticker.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.rsi
{
    /// <summary>
    /// RSI gives you the trend for a stock and for more complete explanation, you can
    /// visit the link:<see cref="http://www.stockcharts.com/education/IndicatorAnalysis/indic_RSI.html">RSI</see>
    /// <para>
    /// After a definite number of stock events, or accumulation period, the first RSI is
    /// computed.  Then for each subsequent stock event, the RSI calculations use the previous
    /// period’s Average Gain andLoss to determine the “smoothed RSI”.
    /// </para>
    /// </summary>

    public class RSIStockTickerListener
    {
        private readonly EPServiceProvider _epService;
        private EventBean[] _oldEvents = null;
        private int _period;
        private int _count = 0;
        private List<Double> _adv, _decl;
        private double _avgGain = Double.MinValue, _avgLoss = Double.MinValue;
        private double _rs = Double.MinValue, _rsi = Double.MinValue;

        public RSIStockTickerListener(EPServiceProvider epService_, int periods_)
        {
            _epService = epService_;
            _period = periods_;
            _oldEvents = null;
            _adv = new List<Double>();
            _decl = new List<Double>();
        }

        public void reset(int period_)
        {
            _period = period_;
            _oldEvents = null;
            _adv.Clear();
            _decl.Clear();
            _avgGain = Double.MinValue;
            _avgLoss = Double.MinValue;
            _rs = Double.MinValue;
            _rsi = Double.MinValue;
        }

        public int Count
        {
            get { return _count; }
        }

        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            Object eventBean = newEvents[0]["tick"];
            StockTick newTick = (StockTick)eventBean;
            _log.Info(".update for stock=" + newTick.StockSymbol + "  price=" + newTick.Price);

            if (_oldEvents != null)
            {
                eventBean = _oldEvents[0]["Tick"];
                StockTick oldTick = (StockTick)eventBean;
                Compute(newTick, oldTick);
                _epService.EPRuntime.SendEvent(new RSIEvent(newTick, _avgLoss, _avgGain, _rs, _rsi));
            }
            _oldEvents = newEvents;
        }

        private void Compute(StockTick newTick_, StockTick oldTick_)
        {
            _count++;
            double change = newTick_.Price - oldTick_.Price;
            if (_count <= _period)
            {
                if (change > 0)
                {
                    _log.Info(".Count " + _count + " Advance " + change);
                    _adv.Add(change);
                }
                else
                {
                    _log.Info(".Count " + _count + " Decline " + change);
                    _decl.Add(change);
                }
            }

            if (_count >= _period)
            {
                if (_count == _period)
                {
                    _avgLoss = AvgValueList(_decl);
                    _avgGain = AvgValueList(_adv);
                }
                else
                {
                    _adv.Clear();
                    _decl.Clear();
                    double adv = 0.0;
                    double decl = 0.0;
                    if (change > 0)
                    {
                        _log.Info(".Count " + _count + " Advance " + change);
                        adv = change;
                    }
                    else
                    {
                        _log.Info(".Count " + _count + " Decline " + change);
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
                    _rs = Math.Abs(_avgGain / _avgLoss);
                    double to1 = 1.0 + _rs;
                    double to100 = 100.0 / to1;
                    _rsi = 100.0 - to100;
                }
            }
        }

        private double AvgValueList(List<Double> lValues_)
        {
            double sum = 0.0;

            foreach (Double val in lValues_)
            {
                sum = sum + val;
            }
            return (sum / _count);
        }

        private static readonly Log _log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}

