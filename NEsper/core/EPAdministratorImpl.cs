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

	    public EPStatement CreatePattern(String onExpression)
	    {
	        return CreatePatternStmt(onExpression, null);
	    }

	    public EPStatement CreateEQL(String eqlStatement)
	    {
	        return CreateEQLStmt(eqlStatement, null);
	    }

	    public EPStatement CreatePattern(String expression, String statementName)
	    {
	        if (statementName == null)
	        {
	            throw new ArgumentException("Invalid parameter, statement name cannot be null");
	        }
	        return CreatePatternStmt(expression, statementName);
	    }

	    public EPStatement CreateEQL(String eqlStatement, String statementName)
	    {
	        return CreateEQLStmt(eqlStatement, statementName);
	    }

        /// <summary>
        /// Creates the pattern.
        /// </summary>
        /// <param name="expression">The expression.</param>
        /// <returns></returns>
        public virtual EPStatement CreatePatternStmt(String expression)
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
                log.Debug(".CreatePattern Error validating expression", ex);
                throw new EPStatementException(ex.Message, expression);
            }
            catch (SystemException ex)
            {
                log.Debug(".CreatePattern Error validating expression", ex);
                throw new EPStatementException(ex.Message, expression);
            }

            if (log.IsDebugEnabled)
            {
                DebugFacility.DumpAST(walker.getAST());
            }

            if (walker.StatementSpec.StreamSpecs.Count > 1)
            {
                throw new SystemException("Unexpected multiple stream specifications encountered");
            }

            // Get pattern specification
			PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];

			// Create statement spec
			StatementSpecRaw statementSpec = new StatementSpecRaw();
			statementSpec.StreamSpecs.Add(patternStreamSpec);

			return services.StatementLifecycleSvc.CreateAndStart(statementSpec, expression, true, statementName);
        }

        /// <summary>
        /// Create a query language statement.
        /// </summary>
        /// <param name="eqlStatement">is the query language statement</param>
        /// <returns>
        /// EPStatement to poll data from or to add listeners to
        /// </returns>
        /// <throws>  EPException when the expression was not valid </throws>
        public virtual EPStatement CreateEQLStmt(String eqlStatement)
        {
			if (log.IsDebugEnabled)
			{
				log.Debug(".CreateEQLStmt statementName=" + statementName + " eqlStatement=" + eqlStatement);
			}

            AST ast = ParseHelper.parse(eqlStatement, eqlParseRule);
			EQLTreeWalker walker = new EQLTreeWalker(services.EngineImportService, services.PatternObjectResolutionService);

            try
            {
                ParseHelper.walk(ast, walker, eqlWalkRule, eqlStatement);
            }
            catch (ASTWalkException ex)
            {
                log.Error(".CreateEQLStmt Error validating expression", ex);
                throw new EPStatementException(ex.Message, eqlStatement);
            }
            catch (SystemException ex)
            {
                log.Error(".CreateEQLStmt Error validating expression", ex);
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

	    public EPStatement GetStatement(String name)
	    {
	        return services.StatementLifecycleSvc.GetStatementByName(name);
	    }

	    public IList<string> StatementNames
	    {
	        get { return services.StatementLifecycleSvc.StatementNames; }
	    }

	    public void StartAllStatements()
	    {
	        services.StatementLifecycleSvc.StartAllStatements();
	    }

	    public void StopAllStatements()
	    {
	        services.StatementLifecycleSvc.StopAllStatements();
	    }

	    public void DestroyAllStatements()
	    {
	        services.StatementLifecycleSvc.DestroyAllStatements();
	    }

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
