using System;

using net.esper.client;
using net.esper.events;
using net.esper.example.stockticker.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.rsi
{
    public class RSIListener
    {
        private double _avgLoss, _avgGain, _rs, _rsi;
        private int _rsiCount;

        public RSIListener()
        {
        }

        public void Reset()
        {
            _avgLoss = 0;
            _avgGain = 0;
            _rs = 0;
            _rsi = 0;
            _rsiCount = 0;
        }

        public double AvgLoss
        {
            get { return _avgLoss; }
        }

        public double AvgGain
        {
            get { return _avgGain; }
        }

        public double RS
        {
            get { return _rs; }
        }

        public double RSI
        {
            get { return _rsi; }
        }

        public int RSICount
        {
            get { return _rsiCount; }
        }

        public void Update(EventBean[] newEvents_, EventBean[] oldEvents_)
        {
            Object eventBean = newEvents_[0]["Tick"];
            StockTick tick = (StockTick)eventBean;
            log.Info(" Stock " + tick.StockSymbol + " Price " + tick.Price);
            eventBean = newEvents_[0]["AvgLoss"];
            _avgLoss = (Double)eventBean;
            if (_avgLoss == Double.MinValue)
            {
                log.Info(" Not Meaningful ");
            }
            else
            {
                _avgLoss = To1tenthPrecision((Double)eventBean);
                log.Info(" AvgLoss " + _avgLoss);
            }
            eventBean = newEvents_[0]["AvgGain"];
            _avgGain = (Double)eventBean;
            if (_avgGain == Double.MinValue)
            {
                log.Info(" Not Meaningful ");
            }
            else
            {
                _avgGain = To1tenthPrecision((Double)eventBean);
                log.Info(" AvgGain " + _avgGain);
            }

            eventBean = newEvents_[0]["RS"];
            _rs = (Double)eventBean;
            if (_rs == Double.MinValue)
            {
                log.Info(" Not Meaningful ");
            }
            else
            {
                _rs = To1tenthPrecision((Double)eventBean);
                log.Info(" RS " + _rs);
            }
            eventBean = newEvents_[0]["RSI"] ;
            _rsi = (Double)eventBean;
            if (_rsi == Double.MinValue)
            {
                log.Info(" Not Meaningful ");
            }
            else
            {
                _rsiCount++;
                _rsi = To1tenthPrecision((Double)eventBean);
                log.Info(" RSI " + _rsi);
            }
        }

        private double To1tenthPrecision(double aDouble)
        {
            int intValue = (int)(aDouble * 10);
            return intValue / 10.0;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
