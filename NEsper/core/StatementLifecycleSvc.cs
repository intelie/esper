///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.eql.spec;

namespace net.esper.core
{
    /// <summary>Handles statement management.</summary>
    public interface StatementLifecycleSvc
    {
        /// <summary>Create and start the statement.</summary>
        /// <param name="statementSpec">
        /// is the statement definition in bean object form, raw unvalidated and unoptimized.
        /// </param>
        /// <param name="expression">is the expression text</param>
        /// <param name="isPattern">
        /// is an indicator on whether this is a pattern statement and thus the iterator must return the last result,
        /// versus for non-pattern statements the iterator returns view content.
        /// </param>
        /// <param name="optStatementName">
        /// is an optional statement name, null if none was supplied
        /// </param>
        /// <returns>started statement</returns>
        EPStatement CreateAndStart(StatementSpecRaw statementSpec, String expression, bool isPattern, String optStatementName);

        /// <summary>Start statement by statement id.</summary>
        /// <param name="statementId">of the statement to start.</param>
        void Start(String statementId);

        /// <summary>Stop statement by statement id.</summary>
        /// <param name="statementId">of the statement to stop.</param>
        void Stop(String statementId);

        /// <summary>Destroy statement by statement id.</summary>
        /// <param name="statementId">statementId of the statement to destroy</param>
        void Destroy(String statementId);

        /// <summary>
        /// Returns the statement by the given name, or null if no such statement exists.
        /// </summary>
        /// <param name="name">is the statement name</param>
        /// <returns>statement for the given name, or null if no such statement existed</returns>
        EPStatement GetStatementByName(String name);

        /// <summary>
        /// Returns an array of statement names. If no statement has been created, an empty array is returned.
        /// <p>
        /// Only returns started and stopped statements.
        /// </summary>
        /// <returns>statement names</returns>
        String[] StatementNames { get; }

        /// <summary>
        /// Starts all stopped statements. First statement to fail supplies the exception.
        /// </summary>
        /// <throws>EPException to indicate a start error.</throws>
        void StartAllStatements();

        /// <summary>
        /// Stops all started statements. First statement to fail supplies the exception.
        /// </summary>
        /// <throws>EPException to indicate a start error.</throws>
        void StopAllStatements();

        /// <summary>
        /// Destroys all started statements. First statement to fail supplies the exception.
        /// </summary>
        /// <throws>EPException to indicate a start error.</throws>
        void DestroyAllStatements();

        /// <summary>
        /// Statements indicate that listeners have been added through this method.
        /// </summary>
        /// <param name="statementId">is the statement id for which listeners were added</param>
        /// <param name="listeners">is the set of listeners after adding the new listener</param>
        void UpdatedListeners(String statementId, ISet<UpdateListener> listeners);
    }
}
