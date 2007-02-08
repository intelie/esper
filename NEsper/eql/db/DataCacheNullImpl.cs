using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.eql.db
{
	/// <summary>
    /// Null implementation for a data cache that doesn't ever hit.
    /// </summary>
	
    public class DataCacheNullImpl : DataCache
	{
		public DataCacheNullImpl()
		{
		}

        public IList<EventBean> GetCached(Object [] lookupKeys)
        {
        	return null ;
        }

        public void PutCached(Object[] lookupKeys, IList<EventBean> rows)
        {
        }
	}
}
