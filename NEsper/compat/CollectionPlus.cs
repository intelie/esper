using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	/// <summary>
	/// Collection that wraps another collection + an item
	/// </summary>
	public class CollectionPlus<T> : ICollection<T>
	{
		private ICollection<T> m_baseCollection ;
		private T m_additionalItem;
		
		public CollectionPlus(ICollection<T> baseCollection, T item)
		{
			this.m_baseCollection = baseCollection;
			this.m_additionalItem = item;
		}
		
		public int Count {
			get {
				return m_baseCollection.Count + 1 ;
			}
		}
		
		public bool IsReadOnly {
			get {
				return true;
			}
		}
		
		public void Add(T item)
		{
			throw new NotSupportedException();
		}
		
		public void Clear()
		{
			throw new NotSupportedException();
		}
		
		public bool Contains(T item)
		{
			if ( Object.Equals( m_additionalItem, item ) ) {
				return true ;
			}
			
			return m_baseCollection.Contains(item);
		}
		
		public void CopyTo(T[] array, int arrayIndex)
		{
			array[arrayIndex++] = m_additionalItem;
			m_baseCollection.CopyTo(array, arrayIndex);
		}
		
		public bool Remove(T item)
		{
			throw new NotSupportedException();
		}
		
		public IEnumerator<T> GetEnumerator()
		{
			yield return m_additionalItem ;
			
			foreach( T item in m_baseCollection ) {
				yield return item;
			}
		}
		
		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			yield return m_additionalItem ;
			
			foreach( T item in m_baseCollection ) {
				yield return item;
			}
		}
	}
}
