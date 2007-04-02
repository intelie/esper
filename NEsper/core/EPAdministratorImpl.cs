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

        private EPServicesContext services;

        /// <summary> Constructor - takes the services context as argument.</summary>
        /// <param name="services">references to services
        /// </param>
        public EPAdministratorImpl(EPServicesContext services)
        {
            this.services = services;
        }

        /// <summary>
        /// Creates the pattern.
        /// </summary>
        /// <param name="expression">The expression.</param>
        /// <returns></returns>
        public virtual EPStatement CreatePattern(String expression)
        {
            // Parse and walk
            AST ast = ParseHelper.parse(expression, patternParseRule);
            EQLTreeWalker walker = new EQLTreeWalker(services.EventAdapterService);

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
            PatternStreamSpec patternStreamSpec = (PatternStreamSpec)walker.StatementSpec.StreamSpecs[0];

            // Create Start method
            EvalRootNode rootNode = new EvalRootNode();
            rootNode.AddChildNode(patternStreamSpec.EvalNode);
            EPPatternStmtStartMethod startMethod = new EPPatternStmtStartMethod(services, rootNode);

            // Generate event type
            EDictionary<String, EventType> eventTypes = patternStreamSpec.TaggedEventTypes;
            EventType eventType = services.EventAdapterService.CreateAnonymousMapTypeUnd(eventTypes);

            EPPatternStatementImpl patternStatement = new EPPatternStatementImpl(expression, eventType, services.DispatchService, services.EventAdapterService, startMethod);

            return patternStatement;
        }

        /// <summary>
        /// Create a query language statement.
        /// </summary>
        /// <param name="eqlStatement">is the query language statement</param>
        /// <returns>
        /// EPStatement to poll data from or to add listeners to
        /// </returns>
        /// <throws>  EPException when the expression was not valid </throws>
        public virtual EPStatement CreateEQL(String eqlStatement)
        {
            AST ast = ParseHelper.parse(eqlStatement, eqlParseRule);
            EQLTreeWalker walker = new EQLTreeWalker(services.EventAdapterService);

            try
            {
                ParseHelper.walk(ast, walker, eqlWalkRule, eqlStatement);
            }
            catch (ASTWalkException ex)
            {
                log.Debug(".CreateEQL Error validating expression", ex);
                throw new EPStatementException(ex.Message, eqlStatement);
            }
            catch (SystemException ex)
            {
                log.Debug(".CreateEQL Error validating expression", ex);
                throw new EPStatementException(ex.Message, eqlStatement);
            }

            if (log.IsDebugEnabled)
            {
                DebugFacility.DumpAST(walker.getAST());
            }


            // Create Start method
            StatementSpec statementSpec = walker.StatementSpec;
            EPEQLStmtStartMethod startMethod = new EPEQLStmtStartMethod(statementSpec, eqlStatement, services);

            return new EPEQLStatementImpl(eqlStatement, services.DispatchService, startMethod);
        }

        private static Log log = LogFactory.GetLog(typeof(EPAdministratorImpl));

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
