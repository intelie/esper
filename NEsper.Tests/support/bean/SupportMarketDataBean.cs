using System;

namespace net.esper.support.bean
{
	public class SupportMarketDataBean
    {
        #region "Properties (lowercase)"
        virtual public String symbol
		{
            get { return _symbol; }
		}

		virtual public double price
		{
			get { return _price; }
		}

		virtual public long? volume
		{
            get { return _volume; }
		}

		virtual public String feed
		{
            get { return _feed; }
        }
        #endregion

        #region "Properties"
        virtual public String Symbol
        {
            get { return _symbol; }
        }

        virtual public double Price
        {
            get { return _price; }
        }

        virtual public long? Volume
        {
            get { return _volume; }
        }

        virtual public String Feed
        {
            get { return _feed; }
        }
        #endregion

        private String _symbol;
        private double _price;
        private long? _volume;
        private String _feed;
		
		public SupportMarketDataBean(String symbol, double price, long? volume, String feed)
		{
			this._symbol = symbol;
			this._price = price;
			this._volume = volume;
			this._feed = feed;
		}
		
		public override String ToString()
		{
			return
			 "SupportMarketDataBean" +
			 " symbol=" + symbol + 
			 " price=" + price +
			 " volume=" + volume +
			 " feed=" + feed;
		}
	}
}
