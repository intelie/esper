///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.util;

namespace net.esper.core
{
	/// <summary>
	/// Class exists once per statement and hold statement resource Lock(s).
	/// <p>
	/// Use by {@link EPRuntimeImpl} for determining callback-statement affinity and locking of statement
	/// resources.
	/// </summary>
	public class EPStatementHandle : MetaDefItem
	{
	    private readonly String statementId;
	    private readonly ManagedLock statementLock;
	    private readonly int hashCode;
	    private EPStatementDispatch optionalDispatchable;

        // handles self-join (ie. statement where from-clause lists the same event type or a super-type more then once)
	    // such that the internal dispatching must occur after both matches are processed
	    private bool canSelfJoin;

	    /// <summary>Ctor.</summary>
	    /// <param name="statementId">
	    /// is the statement id uniquely indentifying the handle
	    /// </param>
	    /// <param name="statementLock">is the statement resource lock</param>
	    /// <param name="expressionText">is the expression</param>
	    public EPStatementHandle(String statementId, ManagedLock statementLock, String expressionText)
	    {
	        this.statementId = statementId;
	        this.statementLock = statementLock;
	        hashCode = expressionText.GetHashCode() ^ statementLock.GetHashCode();
	    }

	    /// <summary>
	    /// Set the statement's self-join flag to indicate the the statement may join to itself,
	    /// that is a single event may dispatch into multiple streams or patterns for the same statement,
	    /// requiring internal dispatch logic to not shortcut evaluation of all filters for the statement
	    /// within one lock, requiring the callback handle to be sorted.
	    /// </summary>

        public bool CanSelfJoin
	    {
			get { return this.canSelfJoin ; }
			set { this.canSelfJoin = value; }
	    }

	    /// <summary>
	    /// Returns true if the statement potentially self-joins amojng the events it processes.
	    /// </summary>
	    /// <returns>
	    /// true for self-joins possible, false for not possible (most statements)
	    /// </returns>
	    public bool IsCanSelfJoin
	    {
	        get { return canSelfJoin; }
	    }

	    /// <summary>Returns statement resource lock.</summary>
	    /// <returns>lock</returns>
	    public ManagedLock StatementLock
	    {
	        get { return statementLock; }
	    }

	    /// <summary>
	    /// Provides a callback for use when statement processing for filters and schedules is done,
	    /// for use by join statements that require an explicit indicator that all
	    /// joined streams results have been processed.
	    /// </summary>
	    public EPStatementDispatch OptionalDispatchable
	    {
			get { return this.optionalDispatchable ; }
	        set { this.optionalDispatchable = value; }
	    }

	    /// <summary>
	    /// Invoked by {@link net.esper.client.EPRuntime} to indicate that a statements's
	    /// filer and schedule processing is done, and now it's time to process join results.
	    /// </summary>
	    public void InternalDispatch()
	    {
	        if (optionalDispatchable != null)
	        {
	            optionalDispatchable.Execute();
	        }
	    }

	    public override bool Equals(Object otherObj)
	    {
	        EPStatementHandle other = otherObj as EPStatementHandle;
			if (other == null)
	        {
	            return false;
	        }

	        if (other.statementId.Equals(this.statementId))
	        {
	            return true;
	        }
	        return false;
	    }

	    public override int GetHashCode()
	    {
	        return hashCode;
	    }
	}
} // End of namespace
