///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.collection
{
	/// <summary>
	/// A fast collection backed by an array with severe limitations. Allows direct access to the backing array
	/// - this must be used with care as old elements could be in the array and the array is only valid until
	/// the number of elements indicated by size.
	/// <p>
	/// Implements only the add, size and clear methods of the collection interface.
	/// </p>
	/// <p>
	/// When running out of space for the underlying array, allocates a new array of double the size of the
	/// current array.
	/// </p>
	/// <p>
	/// Not synchronized and not thread-safe.
	/// </p>
	/// </summary>

	public class ArrayBackedCollection<T> : ICollection<T>
	{
	    private int lastIndex;
	    private int currentIndex;
	    private T[] handles;

	    /// <summary>Ctor.</summary>
	    /// <param name="currentSize">is the initial size of the backing array.</param>
	    public ArrayBackedCollection(int currentSize)
	    {
	        this.lastIndex = currentSize - 1;
	        this.currentIndex = 0;
	        this.handles = new T[currentSize];
	    }

        /// <summary>
        /// Adds an item to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to add to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
        public void Add(T item)
        {
	        if (currentIndex <= lastIndex)
	        {
	            handles[currentIndex++] = item;
	            return;
	        }

	        // allocate more by duplicating the current size
	        int newSize = lastIndex * 2 + 2;
	        T[] newHandles = new T[newSize];
            
            handles.CopyTo(newHandles, 0);
	        handles = newHandles;
	        lastIndex = newSize - 1;

	        // add
	        handles[currentIndex++] = item;
	        return;
	    }

        /// <summary>
        /// Removes all items from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only. </exception>
	    
        public void Clear()
	    {
	        currentIndex = 0;
	    }

        /// <summary>
        /// Gets the number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>The number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</returns>
	    
        public int Count
	    {
	        get { return currentIndex; }
	    }

	    /// <summary>
	    /// Returns the backing object array, valid until the current size.
	    /// <p>
	    /// Applications must ensure to not read past current size as old elements can be encountered.
	    /// </summary>
	    /// <returns>backing array</returns>
	    
        public T[] Array
	    {
	        get { return handles; }
	    }

        /// <summary>
        /// Gets a value indicating whether this instance is empty.
        /// </summary>
        /// <value><c>true</c> if this instance is empty; otherwise, <c>false</c>.</value>
	    public bool IsEmpty
	    {
	        get { throw new UnsupportedOperationException(); }
	    }

        /// <summary>
        /// Determines whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> contains a specific value.
        /// </summary>
        /// <param name="item">The object to locate in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <returns>
        /// true if item is found in the <see cref="T:System.Collections.Generic.ICollection`1"></see>; otherwise, false.
        /// </returns>
        public bool Contains(T item)
        {
	        throw new UnsupportedOperationException();
	    }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
	    public IEnumerator<T> GetEnumerator()
	    {
            for (int ii = 0; ii < currentIndex; ii++)
            {
                yield return handles[ii];
            }
	    }

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="T:System.Collections.IEnumerator"></see> object that can be used to iterate through the collection.
        /// </returns>
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            for (int ii = 0; ii < currentIndex; ii++)
            {
                yield return handles[ii];
            }
        }

        /// <summary>
        /// Copies the elements of the <see cref="T:System.Collections.Generic.ICollection`1"></see> to an <see cref="T:System.Array"></see>, starting at a particular <see cref="T:System.Array"></see> index.
        /// </summary>
        /// <param name="array">The one-dimensional <see cref="T:System.Array"></see> that is the destination of the elements copied from <see cref="T:System.Collections.Generic.ICollection`1"></see>. The <see cref="T:System.Array"></see> must have zero-based indexing.</param>
        /// <param name="arrayIndex">The zero-based index in array at which copying begins.</param>
        /// <exception cref="T:System.ArgumentOutOfRangeException">arrayIndex is less than 0.</exception>
        /// <exception cref="T:System.ArgumentNullException">array is null.</exception>
        /// <exception cref="T:System.ArgumentException">array is multidimensional.-or-arrayIndex is equal to or greater than the length of array.-or-The number of elements in the source <see cref="T:System.Collections.Generic.ICollection`1"></see> is greater than the available space from arrayIndex to the end of the destination array.-or-Type T cannot be cast automatically to the type of the destination array.</exception>
        public void CopyTo(T[] array, int arrayIndex)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Gets a value indicating whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.
        /// </summary>
        /// <value></value>
        /// <returns>true if the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only; otherwise, false.</returns>
        public bool IsReadOnly
        {
            get { return false; }
        }

        /// <summary>
        /// Removes the first occurrence of a specific object from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to remove from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <returns>
        /// true if item was successfully removed from the <see cref="T:System.Collections.Generic.ICollection`1"></see>; otherwise, false. This method also returns false if item is not found in the original <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </returns>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
        public bool Remove(T item)
        {
            throw new NotImplementedException();
        }

	    ///<summary>
	    ///Determines the index of a specific item in the <see cref="T:System.Collections.Generic.IList`1"></see>.
	    ///</summary>
	    ///
	    ///<returns>
	    ///The index of item if found in the list; otherwise, -1.
	    ///</returns>
	    ///
	    ///<param name="item">The object to locate in the <see cref="T:System.Collections.Generic.IList`1"></see>.</param>
	    public int IndexOf(T item)
	    {
	        throw new NotImplementedException();
	    }

	    ///<summary>
	    ///Inserts an item to the <see cref="T:System.Collections.Generic.IList`1"></see> at the specified index.
	    ///</summary>
	    ///
	    ///<param name="item">The object to insert into the <see cref="T:System.Collections.Generic.IList`1"></see>.</param>
	    ///<param name="index">The zero-based index at which item should be inserted.</param>
	    ///<exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.IList`1"></see> is read-only.</exception>
	    ///<exception cref="T:System.ArgumentOutOfRangeException">index is not a valid index in the <see cref="T:System.Collections.Generic.IList`1"></see>.</exception>
	    public void Insert(int index, T item)
	    {
	        throw new NotImplementedException();
	    }

	    ///<summary>
	    ///Removes the <see cref="T:System.Collections.Generic.IList`1"></see> item at the specified index.
	    ///</summary>
	    ///
	    ///<param name="index">The zero-based index of the item to remove.</param>
	    ///<exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.IList`1"></see> is read-only.</exception>
	    ///<exception cref="T:System.ArgumentOutOfRangeException">index is not a valid index in the <see cref="T:System.Collections.Generic.IList`1"></see>.</exception>
	    public void RemoveAt(int index)
	    {
	        throw new NotImplementedException();
	    }

	    ///<summary>
	    ///Gets or sets the element at the specified index.
	    ///</summary>
	    ///
	    ///<returns>
	    ///The element at the specified index.
	    ///</returns>
	    ///
	    ///<param name="index">The zero-based index of the element to get or set.</param>
	    ///<exception cref="T:System.ArgumentOutOfRangeException">index is not a valid index in the <see cref="T:System.Collections.Generic.IList`1"></see>.</exception>
	    ///<exception cref="T:System.NotSupportedException">The property is set and the <see cref="T:System.Collections.Generic.IList`1"></see> is read-only.</exception>
	    public T this[int index]
	    {
	        get { throw new NotImplementedException(); }
	        set { throw new NotImplementedException(); }
	    }
	}
} // End of namespace
