using System;

namespace net.esper.support.bean
{
	
	public class SupportMarketDataBean
	{
		virtual public String Symbol
		{
			get { return symbol; }
		}

		virtual public double Price
		{
			get { return price; }
		}

		virtual public Nullable<Int64> Volume
		{
			get { return volume; }
		}

		virtual public String Feed
		{
			get { return feed; }			
		}

		private String symbol;
		private double price;
		private Nullable<Int64> volume;
		private String feed;
		
		public SupportMarketDataBean(String symbol, double price, Nullable<Int64> volume, String feed)
		{
			this.symbol = symbol;
			this.price = price;
			this.volume = volume;
			this.feed = feed;
		}
		
		public override String ToString()
		{
			return
			 "SupportMarketDataBean " +
			 "symbol=" + symbol + 
			 " price=" + price +
			 " volume=" + volume +
			 " feed=" + feed;
		}
	}
}
