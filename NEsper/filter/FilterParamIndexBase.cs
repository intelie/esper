///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Threading;

namespace net.esper.filter
{
	/// <summary>
	/// Each implementation of this abstract class represents an index of filter parameter constants supplied in filter
	/// parameters in filter specifications that feature the same event property and operator.
	/// <p>
	/// For example, a filter with a parameter of "count EQUALS 10" would be represented as index
	/// for a property named "count" and for a filter operator typed "EQUALS". The index
	/// would store a value of "10" in its internal structure.
	/// <p>
	/// Implementations make sure that the type of the Object constant in get and put calls matches the event property type.
	/// </summary>
	public abstract class FilterParamIndexBase : EventEvaluator
	{
	    private readonly FilterOperator filterOperator;

	    /// <summary>Constructor.</summary>
	    /// <param name="filterOperator">is the type of comparison performed.</param>
	    public FilterParamIndexBase(FilterOperator filterOperator)
	    {
	        this.filterOperator = filterOperator;
	    }

	    /// <summary>
	    /// Get the event evaluation instance associated with the constant. Returns null if no entry found for the constant.
	    /// The calling class must make sure that access to the underlying resource is protected
	    /// for multi-threaded access, the GetReadWriteLock() method must supply a lock for this purpose.
	    /// </summary>
	    /// <param name="filterConstant">
	    /// is the constant supplied in the event filter parameter
	    /// </param>
	    /// <returns>
	    /// event evaluator stored for the filter constant, or null if not found
	    /// </returns>
	    protected abstract EventEvaluator Get(Object filterConstant);

	    /// <summary>
	    /// Store the event evaluation instance for the given constant. Can override an existing value
	    /// for the same constant.
	    /// The calling class must make sure that access to the underlying resource is protected
	    /// for multi-threaded access, the GetReadWriteLock() method must supply a lock for this purpose.
	    /// </summary>
	    /// <param name="filterConstant">is the constant supplied in the filter parameter</param>
	    /// <param name="evaluator">to be stored for the constant</param>
	    protected abstract void Put(Object filterConstant, EventEvaluator evaluator);

	    /// <summary>
	    /// Remove the event evaluation instance for the given constant. Returns true if
	    /// the constant was found, or false if not.
	    /// The calling class must make sure that access to the underlying resource is protected
	    /// for multi-threaded writes, the GetReadWriteLock() method must supply a lock for this purpose.
	    /// </summary>
	    /// <param name="filterConstant">is the value supplied in the filter paremeter</param>
	    /// <returns>true if found and removed, false if not found</returns>
	    protected abstract bool Remove(Object filterConstant);

	    /// <summary>
	    /// Return the number of distinct filter parameter constants stored.
	    /// The calling class must make sure that access to the underlying resource is protected
	    /// for multi-threaded writes, the GetReadWriteLock() method must supply a lock for this purpose.
	    /// </summary>
	    /// <returns>Number of entries in index</returns>
	    protected abstract int Count { get ; }

	    /// <summary>Supplies the lock for protected access.</summary>
	    /// <returns>lock</returns>
	    protected abstract ReaderWriterLock ReadWriteLock { get ; }

	    /// <summary>Returns the filter operator that the index matches for.</summary>
	    /// <returns>filter operator</returns>
	    public FilterOperator FilterOperator
	    {
            get { return filterOperator; }
	    }

	    public override String ToString()
	    {
	        return "filterOperator=" + filterOperator;
	    }
	}
} // End of namespace
