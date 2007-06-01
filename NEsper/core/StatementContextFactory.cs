///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.core
{
	/// <summary>
	/// Interface for a factory class that makes statement context specific to a statement.
	/// </summary>
	public interface StatementContextFactory
	{
	    /// <summary>
	    /// Create a new statement context consisting of statement-level services.
	    /// </summary>
	    /// <param name="statementId">is the statement is</param>
	    /// <param name="statementName">is the statement name</param>
	    /// <param name="expression">is the statement expression</param>
	    /// <param name="engineServices">is engine services</param>
	    /// <returns>statement context</returns>
	    StatementContext MakeContext(String statementId, String statementName, String expression,
	                                        EPServicesContext engineServices);
	}
} // End of namespace
