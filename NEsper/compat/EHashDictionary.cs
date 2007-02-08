using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	public class EHashDictionary<K,V> : EBaseDictionary<K,V>
	{
		public EHashDictionary()
			: base( new Dictionary<K,V>() )
		{
		}
	}
}
