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

	public class HashDictionary<K,V> : EBaseDictionary<K,V>
	{
        /// <summary>
        /// Initializes a new instance of the <see cref="HashDictionary&lt;K, V&gt;"/> class.
        /// </summary>
		public HashDictionary()
			: base( new Dictionary<K,V>() )
		{
		}

        /// <summary>
        /// Initializes a new instance of the <see cref="HashDictionary&lt;K, V&gt;"/> class.
        /// </summary>
        public HashDictionary(int initialCapacity)
            : base(new Dictionary<K, V>(initialCapacity))
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="HashDictionary&lt;K, V&gt;"/> class.
        /// </summary>
		
		public HashDictionary(IEqualityComparer<K> eqComparer)
			: base( new Dictionary<K,V>( eqComparer ) )
		{
		}
	}
}
