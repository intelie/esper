///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.collection;
using net.esper.eql.expression;
using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Specification object representing a complete EQL statement including all EQL constructs.
	/// </summary>
    public class StatementSpecRaw : MetaDefItem
    {
        private InsertIntoDesc insertIntoDesc;
        private SelectClauseStreamSelectorEnum selectStreamDirEnum = SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH;
        private SelectClauseSpec selectClauseSpec = new SelectClauseSpec();
        private List<StreamSpecRaw> streamSpecs = new List<StreamSpecRaw>();
        private List<OuterJoinDesc> outerJoinDescList = new List<OuterJoinDesc>();
        private ExprNode filterExprRootNode;
        private List<ExprNode> groupByExpressions = new List<ExprNode>();
        private ExprNode havingExprRootNode;
        private OutputLimitSpec outputLimitSpec;
        private List<Pair<ExprNode, Boolean>> orderByList = new List<Pair<ExprNode, Boolean>>();

        /// <summary>Returns the FROM-clause stream definitions.</summary>
        /// <returns>list of stream specifications</returns>
        public IList<StreamSpecRaw> StreamSpecs
        {
            get { return streamSpecs; }
        }

        /// <summary>Returns SELECT-clause list of expressions.</summary>
        /// <returns>list of expressions and optional name</returns>
        public SelectClauseSpec SelectClauseSpec
        {
            get { return selectClauseSpec; }
        }

        /// <summary>Gets or sets the WHERE-clause root node of filter expression.</summary>
        /// <returns>filter expression root node</returns>
        public ExprNode FilterExprRootNode
        {
            get { return filterExprRootNode; }
            set { filterExprRootNode = value; }
        }

        /// <summary>
        /// Returns the LEFT/RIGHT/FULL OUTER JOIN-type and property name descriptor, if applicable. Returns null if regular join.
        /// </summary>
        /// <returns>outer join type, stream names and property names</returns>
        public IList<OuterJoinDesc> OuterJoinDescList
        {
            get { return outerJoinDescList; }
        }

        /// <summary>Returns list of group-by expressions.</summary>
        /// <returns>group-by expression nodes as specified in group-by clause</returns>
        public IList<ExprNode> GroupByExpressions
        {
            get { return groupByExpressions; }
        }

        /// <summary>
        /// Gets or sets the expression root node representing the having-clause, if present,
        /// or null if no having clause was supplied.
        /// </summary>
        /// <returns>having-clause expression top node</returns>
        public ExprNode HavingExprRootNode
        {
            get { return havingExprRootNode; }
            set { havingExprRootNode = value; }
        }

        /// <summary>Gets or sets the output limit definition, if any.</summary>
        /// <returns>output limit spec</returns>
        public OutputLimitSpec OutputLimitSpec
        {
            get { return outputLimitSpec; }
            set { outputLimitSpec = value; }
        }

        /// <summary>
        /// Gets or sets a descriptor with the insert-into event name and optional list of columns.
        /// </summary>
        /// <returns>insert into specification</returns>
        public InsertIntoDesc InsertIntoDesc
        {
            get { return insertIntoDesc; }
            set { insertIntoDesc = value; }
        }

        /// <summary>
        /// Returns the list of order-by expression as specified in the ORDER BY clause.
        /// </summary>
        /// <returns>Returns the orderByList.</returns>
        public List<Pair<ExprNode, Boolean>> OrderByList
        {
            get { return orderByList; }
        }

        /// <summary>Returns the stream selector (rstream/istream).</summary>
        /// <returns>stream selector</returns>
        ///
        public SelectClauseStreamSelectorEnum SelectStreamSelectorEnum
        {
            get { return selectStreamDirEnum; }
        }

        /// <summary>Gets or sets the stream selector (rstream/istream/both etc).</summary>
        /// <returns>stream selector</returns>
        public SelectClauseStreamSelectorEnum SelectStreamDirEnum
        {
            get { return selectStreamDirEnum; }
            set { selectStreamDirEnum = value; }
        }
    }
} // End of namespace
