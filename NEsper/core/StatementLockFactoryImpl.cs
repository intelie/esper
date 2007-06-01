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
	/// <summary>Provides statement-level locks.</summary>
	public class StatementLockFactoryImpl : StatementLockFactory
	{
	    public ManagedLock GetStatementLock(String statementName, String expressionText)
	    {
	        return new ManagedLockImpl(statementName);
	    }
	}
} // End of namespace
