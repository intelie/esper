using System;
using System.Collections.Generic;

namespace net.esper.compat
{
    /// <summary>
    /// An extended dictionary based upon a closed hashing
    /// algorithm.
    /// </summary>
    /// <typeparam name="K"></typeparam>
    /// <typeparam name="V"></typeparam>

	public class EHashDictionary<K,V> : EBaseDictionary<K,V>
	{
        /// <summary>
        /// Initializes a new instance of the <see cref="EHashDictionary&lt;K, V&gt;"/> class.
        /// </summary>
		public EHashDictionary()
			: base( new Dictionary<K,V>() )
		{
		}
	}
}
