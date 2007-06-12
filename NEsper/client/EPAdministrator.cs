// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Collections.Generic;

namespace net.esper.client
{
	/// <summary> Administrative interface to the event stream processing engine. Includes methods to create patterns and EQL statements.</summary>
	public interface EPAdministrator
	{
		/// <summary> Create and starts an event pattern statement for the expressing string passed.
		/// <p>The engine assigns a unique name to the statement.</p>
		/// </summary>
		/// <param name="onExpression">must follow the documented syntax for pattern statements
		/// </param>
		/// <returns> EPStatement to poll data from or to add listeners to
		/// </returns>
		/// <throws>  EPException when the expression was not valid </throws>
		EPStatement CreatePattern(String onExpression);

		/// <summary> Create and starts an EQL statement.
		/// <para>The engine assigns a unique name to the statement.  The returned statement is in started state.</para>
		/// </summary>
		/// <param name="eqlStatement">is the query language statement
		/// </param>
		/// <returns> EPStatement to poll data from or to add listeners to
		/// </returns>
		/// <throws>  EPException when the expression was not valid </throws>
		EPStatement CreateEQL(String eqlStatement);

		/// <summary>
		/// Create and starts an event pattern statement for the expressing string passed and assign the name passed.
		/// <para>
		/// The statement name is optimally a unique name. If a statement of the same name
		/// has already been created, the engine assigns a postfix to create a unique statement name.
        /// </para>
		/// </summary>
		/// <param name="onExpression">
		/// must follow the documented syntax for pattern statements
		/// </param>
		/// <param name="statementName">
		/// is the name to assign to the statement for use in manageing the statement
		/// </param>
		/// <returns>EPStatement to poll data from or to add listeners to</returns>
		/// <throws>EPException when the expression was not valid</throws>
		EPStatement CreatePattern(String onExpression, String statementName);

		/// <summary>
		/// Create and starts an EQL statement.
        /// <para>
		/// The statement name is optimally a unique name. If a statement of the same name
		/// has already been created, the engine assigns a postfix to create a unique statement name.
		/// </para>
		/// </summary>
		/// <param name="eqlStatement">is the query language statement</param>
		/// <param name="statementName">
		/// is the name to assign to the statement for use in manageing the statement
		/// </param>
		/// <returns>EPStatement to poll data from or to add listeners to</returns>
		/// <throws>EPException when the expression was not valid</throws>
		EPStatement CreateEQL(String eqlStatement, String statementName);

		/// <summary>
		/// Returns the statement by the given statement name. Returns null if a statement of that name has not
		/// been created, or if the statement by that name has been destroyed.
		/// </summary>
		/// <param name="name">is the statement name to return the statement for</param>
		/// <returns>
		/// statement for the given name, or null if no such started or stopped statement exists
		/// </returns>
		EPStatement GetStatement(String name);

		/// <summary>
		/// Returns the statement names of all started and stopped statements.
        /// <para>
		/// This excludes the name of destroyed statements.
		/// </para>
		/// </summary>
		/// <returns>statement names</returns>
		IList<String> StatementNames { get ; }

		/// <summary>
		/// Starts all statements that are in stopped state. Statements in started state
		/// are not affected by this method.
		/// </summary>
		/// <throws>EPException when an error occured starting statements.</throws>
		void StartAllStatements();

		/// <summary>
		/// Stops all statements that are in started state. Statements in stopped state are not affected by this method.
		/// </summary>
		/// <throws>EPException when an error occured stopping statements</throws>
		void StopAllStatements();

		/// <summary>Stops and destroys all statements.</summary>
		/// <throws>EPException when an error occured stopping or destroying statements</throws>
		void DestroyAllStatements();

		/// <summary>Returns configuration operations for runtime engine configuration.</summary>
		/// <returns>runtime engine configuration operations</returns>
		ConfigurationOperations Configuration { get ; }
	}
}
