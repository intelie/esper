using System;

using net.esper.compat;

namespace net.esper.collection
{
	/// <summary> Functions as a key value for Maps where keys need to be composite values.
	/// The class allows a Map that uses MultiKeyUntyped entries for key values to use multiple objects as keys.
	/// It calculates the hashCode from the key objects on construction and caches the hashCode.
	/// </summary>

    public sealed class MultiKey<T>
	{
		/// <summary> Returns the key value array.</summary>
		/// <returns> key value array
		/// </returns>
		
        public T[] Array
		{
			get { return keys; }
		}

		private readonly T[] keys;
		private readonly int hashCode;
		
		/// <summary> Constructor for multiple keys supplied in an object array.</summary>
		/// <param name="keys">is an array of key objects
		/// </param>

        public MultiKey(T[] keys)
		{
			if (keys == null)
			{
				throw new ArgumentException("The array of keys must not be null");
			}
			
			int total = 0;
			for (int i = 0; i < keys.Length; i++)
			{
				if (keys[i] != null)
				{
					total ^= keys[i].GetHashCode();
				}
			}
			
			this.hashCode = total;
			this.keys = keys;
		}
		
		/// <summary> Returns the number of key objects.</summary>
		/// <returns> size of key object array
		/// </returns>
		
        public int Count
		{
            get { return keys.Length; }
		}
		
		/// <summary> Returns the key object at the specified position.</summary>
		/// <param name="index">is the array position
		/// </param>
		/// <returns> key object at position
		/// </returns>
		
        public T this[int index]
		{
            get { return keys[index]; }
		}
		
		public override bool Equals(Object other)
		{
			if (other == this)
			{
				return true;
			}

			if (other is MultiKey<T>)
			{
				MultiKey<T> otherKeys = (MultiKey<T>) other;
				return ArrayHelper.AreEqual( keys, otherKeys.keys ) ;
			}

			return false;
		}
		
		public override int GetHashCode()
		{
			return hashCode;
		}
		
		public override String ToString()
		{
			return "MultiKeyUntyped" + ArrayHelper.Render( keys ) ;
		}
	}
}
