using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.eql.parse;
using net.esper.eql.generated;
using net.esper.eql.spec;
using net.esper.pattern;
using net.esper.util;

using TokenStreamException = antlr.TokenStreamException;
using RecognitionException = antlr.RecognitionException;
using AST = antlr.collections.AST;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.core
{
    /// <summary>
    /// Implementation for the admin interface.
    /// </summary>

    public class EPAdministratorImpl : EPAdministrator
    {
        private class AnonymousClassParseRuleSelector : ParseRuleSelector
        {
            public virtual void invokeParseRule(EQLStatementParser parser)
            {
                parser.startPatternExpressionRule();
            }
        }
        private class AnonymousClassWalkRuleSelector : WalkRuleSelector
        {
            public virtual void invokeWalkRule(EQLBaseWalker walker, AST ast)
            {
                walker.startPatternExpressionRule(ast);
            }
        }
        private class AnonymousClassParseRuleSelector1 : ParseRuleSelector
        {
            public virtual void invokeParseRule(EQLStatementParser parser)
            {
                parser.startEQLExpressionRule();
            }
        }
        private class AnonymousClassWalkRuleSelector1 : WalkRuleSelector
        {
            public virtual void invokeWalkRule(EQLBaseWalker walker, AST ast)
            {
                walker.startEQLExpressionRule(ast);
            }
        }
        private static ParseRuleSelector patternParseRule;
        private static ParseRuleSelector eqlParseRule;
        private static WalkRuleSelector patternWalkRule;
        private static WalkRuleSelector eqlWalkRule;

		private readonly EPServicesContext services;
		private readonly ConfigurationOperations configurationOperations;

        /// <summary> Constructor - takes the services context as argument.</summary>
        /// <param name="services">references to services</param>
		/// <param name="configurationOperations">runtime configuration operations</param>
		
	    public EPAdministratorImpl(EPServicesContext services, ConfigurationOperations configurationOperations)
	    {
	        this.services = services;
	        this.configurationOperations = configurationOperations;
	    }

        /// <summary>
        /// Create and starts an event pattern statement for the expressing string passed.
        /// <p>The engine assigns a unique name to the statement.</p>
        /// </summary>
        /// <param name="onExpression">must follow the documented syntax for pattern statements</param>
        /// <returns>
        /// EPStatement to poll data from or to add listeners to
        /// </returns>
        /// <throws>  EPException when the expression was not valid </throws>
	    public EPStatement CreatePattern(String onExpression)
	    {
	        return CreatePatternStmt(onExpression, null);
	    }

        /// <summary>
        /// Create and starts an EQL statement.
        /// <p>The engine assigns a unique name to the statement.  The returned statement is in started state.</p>
        /// </summary>
        /// <param name="eqlStatement">is the query language statement</param>
        /// <returns>
        /// EPStatement to poll data from or to add listeners to
        /// </returns>
        /// <throws>  EPException when the expression was not valid </throws>
	    public EPStatement CreateEQL(String eqlStatement)
	    {
	        return CreateEQLStmt(eqlStatement, null);
	    }

        /// <summary>
        /// Creates the pattern.
        /// </summary>
        /// <param name="expression">The expression.</param>
        /// <param name="statementName">Name of the statement.</param>
        /// <returns></returns>
	    public EPStatement CreatePattern(String expression, String statementName)
	    {
	        if (statementName == null)
	        {
	            throw new ArgumentException("Invalid parameter, statement name cannot be null");
	        }
	        return CreatePatternStmt(expression, statementName);
	    }

        /// <summary>
        /// Create and starts an EQL statement.
        /// <para>
        /// The statement name is optimally a unique name. If a statement of the same name
        /// has already been created, the engine assigns a postfix to create a unique statement name.
        /// </para>
        /// </summary>
        /// <param name="eqlStatement">is the query language statement</param>
        /// <param name="statementName">is the name to assign to the statement for use in manageing the statement</param>
        /// <returns>
        /// EPStatement to poll data from or to add listeners to
        /// </returns>
        /// <throws>EPException when the expression was not valid</throws>
	    public EPStatement CreateEQL(String eqlStatement, String statementName)
	    {
	        return CreateEQLStmt(eqlStatement, statementName);
	    }

        /// <summary>
        /// Creates the pattern.
        /// </summary>
        /// <param name="expression">The expression.</param>
        /// <param name="statementName">Name of the statement.</param>
        /// <returns></returns>
        public virtual EPStatement CreatePatternStmt(String expression, String statementName)
        {
            // Parse and walk
            AST ast = ParseHelper.parse(expression, patternParseRule);
            EQLTreeWalker walker = new EQLTreeWalker(services.EngineImportService, services.PatternObjectResolutionService);

            try
            {
                ParseHelper.walk(ast, walker, patternWalkRule, expression);
            }
            catch (ASTWalkException ex)
            {
                log.Debug(".createPattern Error validating expression", ex);
                throw new EPStatementException(ex.Message, expression);
            }
            catch (Exception ex)
            {
                log.Debug(".createPattern Error validating expression", ex);
                throw new EPStatementException(ex.Message, expression);
            }

            if (log.IsDebugEnabled)
            {
                DebugFacility.DumpAST(walker.getAST());
            }

            if (walker.StatementSpec.StreamSpecs.Count > 1)
            {
                throw new IllegalStateException("Unexpected multiple stream specifications encountered");
            }

            // Get pattern specification
            PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw)walker.StatementSpec.StreamSpecs[0];

            // Create statement spec
            StatementSpecRaw statementSpec = new StatementSpecRaw();
            statementSpec.StreamSpecs.Add(patternStreamSpec);

            return services.StatementLifecycleSvc.CreateAndStart(statementSpec, expression, true, statementName);
        }

        /// <summary>
        /// Create a query language statement.
        /// </summary>
        /// <param name="eqlStatement">The query language statement</param>
        /// <param name="statementName">Name of the statement.</param>
        /// <returns>
        /// EPStatement to poll data from or to add listeners to
        /// </returns>
        /// <throws>  EPException when the expression was not valid </throws>
        public virtual EPStatement CreateEQLStmt(String eqlStatement, String statementName)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".createEQLStmt statementName=" + statementName + " eqlStatement=" + eqlStatement);
            }

            AST ast = ParseHelper.parse(eqlStatement, eqlParseRule);
            EQLTreeWalker walker = new EQLTreeWalker(services.EngineImportService, services.PatternObjectResolutionService);

            try
            {
                ParseHelper.walk(ast, walker, eqlWalkRule, eqlStatement);
            }
            catch (ASTWalkException ex)
            {
                log.Error(".CreateEQL Error validating expression", ex);
                throw new EPStatementException(ex.Message, eqlStatement);
            }
            catch (Exception ex)
            {
                log.Error(".CreateEQL Error validating expression", ex);
                throw new EPStatementException(ex.Message, eqlStatement);
            }

            if (log.IsDebugEnabled)
            {
                DebugFacility.DumpAST(walker.getAST());
            }

            // Specifies the statement
            StatementSpecRaw statementSpec = walker.StatementSpec;

            EPStatement statement = services.StatementLifecycleSvc.CreateAndStart(statementSpec, eqlStatement, false, statementName);

            log.Debug(".CreateEQLStmt Statement created and started");
            return statement;
        }

        /// <summary>
        /// Returns the statement by the given statement name. Returns null if a statement of that name has not
        /// been created, or if the statement by that name has been destroyed.
        /// </summary>
        /// <param name="name">is the statement name to return the statement for</param>
        /// <returns>
        /// statement for the given name, or null if no such started or stopped statement exists
        /// </returns>
	    public EPStatement GetStatement(String name)
	    {
	        return services.StatementLifecycleSvc.GetStatementByName(name);
	    }

        /// <summary>
        /// Returns the statement names of all started and stopped statements.
        /// &lt;p&gt;
        /// This excludes the name of destroyed statements.
        /// </summary>
        /// <value></value>
        /// <returns>statement names</returns>
	    public IList<string> StatementNames
	    {
	        get { return services.StatementLifecycleSvc.StatementNames; }
	    }

        /// <summary>
        /// Starts all statements that are in stopped state. Statements in started state
        /// are not affected by this method.
        /// </summary>
        /// <throws>EPException when an error occured starting statements.</throws>
	    public void StartAllStatements()
	    {
	        services.StatementLifecycleSvc.StartAllStatements();
	    }

        /// <summary>
        /// Stops all statements that are in started state. Statements in stopped state are not affected by this method.
        /// </summary>
        /// <throws>EPException when an error occured stopping statements</throws>
	    public void StopAllStatements()
	    {
	        services.StatementLifecycleSvc.StopAllStatements();
	    }

        /// <summary>
        /// Stops and destroys all statements.
        /// </summary>
        /// <throws>EPException when an error occured stopping or destroying statements</throws>
	    public void DestroyAllStatements()
	    {
	        services.StatementLifecycleSvc.DestroyAllStatements();
	    }

        /// <summary>
        /// Returns configuration operations for runtime engine configuration.
        /// </summary>
        /// <value></value>
        /// <returns>runtime engine configuration operations</returns>
	    public ConfigurationOperations Configuration
	    {
	        get { return configurationOperations; }
	    }
		
        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// Initializes the <see cref="EPAdministratorImpl"/> class.
        /// </summary>
        static EPAdministratorImpl()
        {
            patternParseRule = new AnonymousClassParseRuleSelector();
            patternWalkRule = new AnonymousClassWalkRuleSelector();

            eqlParseRule = new AnonymousClassParseRuleSelector1();
            eqlWalkRule = new AnonymousClassWalkRuleSelector1();
        }
    }
}
