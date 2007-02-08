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
        /// <param name="services">- references to services
        /// </param>
        public EPAdministratorImpl(EPServicesContext services)
        {
            this.services = services;
        }

        public virtual EPStatement createPattern(String expression)
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
                log.Debug(".createPattern Error validating expression", ex);
                throw new EPStatementException(ex.Message, expression);
            }
            catch (SystemException ex)
            {
                log.Debug(".createPattern Error validating expression", ex);
                throw new EPStatementException(ex.Message, expression);
            }

            if (log.IsDebugEnabled)
            {
                DebugFacility.dumpAST(walker.getAST());
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

        public virtual EPStatement createEQL(String eqlStatement)
        {
            AST ast = ParseHelper.parse(eqlStatement, eqlParseRule);
            EQLTreeWalker walker = new EQLTreeWalker(services.EventAdapterService);

            try
            {
                ParseHelper.walk(ast, walker, eqlWalkRule, eqlStatement);
            }
            catch (ASTWalkException ex)
            {
                log.Debug(".createEQL Error validating expression", ex);
                throw new EPStatementException(ex.Message, eqlStatement);
            }
            catch (SystemException ex)
            {
                log.Debug(".createEQL Error validating expression", ex);
                throw new EPStatementException(ex.Message, eqlStatement);
            }

            if (log.IsDebugEnabled)
            {
                DebugFacility.dumpAST(walker.getAST());
            }


            // Create Start method
            StatementSpec statementSpec = walker.StatementSpec;
            EPEQLStmtStartMethod startMethod = new EPEQLStmtStartMethod(statementSpec, eqlStatement, services);

            return new EPEQLStatementImpl(eqlStatement, services.DispatchService, startMethod);
        }

        private static Log log = LogFactory.GetLog(typeof(EPAdministratorImpl));

        static EPAdministratorImpl()
        {
            patternParseRule = new AnonymousClassParseRuleSelector();
            patternWalkRule = new AnonymousClassWalkRuleSelector();

            eqlParseRule = new AnonymousClassParseRuleSelector1();
            eqlWalkRule = new AnonymousClassWalkRuleSelector1();
        }
    }
}
