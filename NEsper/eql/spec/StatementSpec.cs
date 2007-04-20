using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.eql.expression;

namespace net.esper.eql.spec
{
	/// <summary>
    /// Specification object representing a complete EQL statement including all EQL constructs.
    /// </summary>

    public class StatementSpec
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="StatementSpec"/> class.
        /// </summary>
        public StatementSpec()
        {
        }

        /// <summary> Returns the WHERE-clause root node of filter expression.</summary>
        /// <returns> filter expression root node
        /// </returns>
        virtual public ExprNode FilterRootNode
        {
            get
            {
                return filterExprRootNode;
            }

        }
        /// <summary>
        /// Gets or sets expression root node representing the having-clause, if present, or null if no having clause was supplied.
        /// </summary>
        /// <value>The having expr root node.</value>
        virtual public ExprNode HavingExprRootNode
        {
            get
            {
                return havingExprRootNode;
            }

            set
            {
                this.havingExprRootNode = value;
            }

        }
        /// <summary>
        /// Gets or sets the output limit definition, if any.
        /// </summary>
        /// <value>The output limit spec.</value>
        virtual public OutputLimitSpec OutputLimitSpec
        {
            get
            {
                return outputLimitSpec;
            }

            set
            {
                this.outputLimitSpec = value;
            }

        }
        /// <summary>
        /// Gets or sets a descriptor with the insert-into event name and optional list of columns.
        /// </summary>
        /// <value>The insert into desc.</value>
        virtual public InsertIntoDesc InsertIntoDesc
        {
            get
            {
                return insertIntoDesc;
            }

            set
            {
                this.insertIntoDesc = value;
            }

        }
        /// <summary> Returns the stream selector (rstream/istream).</summary>
        /// <returns> stream selector
        /// </returns>
        virtual public SelectClauseStreamSelectorEnum SelectStreamSelectorEnum
        {
            get
            {
                return selectStreamDirEnum;
            }

        }
        /// <summary>
        /// Sets the where clause filter expression node.
        /// </summary>
        /// <value>The filter expr root node.</value>
        virtual public ExprNode FilterExprRootNode
        {
        	get { return this.filterExprRootNode ; }
            set { this.filterExprRootNode = value; }
        }
        /// <summary>
        /// Sets the stream selector (rstream/istream/both etc).
        /// </summary>
        /// <value>The select stream dir enum.</value>
        virtual public SelectClauseStreamSelectorEnum SelectStreamDirEnum
        {
            set
            {
                this.selectStreamDirEnum = value;
            }

        }
        private InsertIntoDesc insertIntoDesc;
        private SelectClauseStreamSelectorEnum selectStreamDirEnum = SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH;
        private IList<SelectExprElementUnnamedSpec> selectListExpressions = new List<SelectExprElementUnnamedSpec>();
        private IList<StreamSpec> streamSpecs = new List<StreamSpec>();
        private IList<OuterJoinDesc> outerJoinDescList = new List<OuterJoinDesc>();
        private ExprNode filterExprRootNode;
        private IList<ExprNode> groupByExpressions = new List<ExprNode>();
        private ExprNode havingExprRootNode;
        private OutputLimitSpec outputLimitSpec;
        private IList<Pair<ExprNode, Boolean>> orderByList = new List<Pair<ExprNode, Boolean>>();

        /// <summary> Returns the FROM-clause stream definitions.</summary>
        /// <returns> list of stream specifications
        /// </returns>

        public IList<StreamSpec> StreamSpecs
        {
        	get { return streamSpecs; }
        }

        /// <summary> Returns SELECT-clause list of expressions.</summary>
        /// <returns> list of expressions and optional name
        /// </returns>
        public IList<SelectExprElementUnnamedSpec> SelectListExpressions
        {
        	get { return selectListExpressions; }
        }

        /// <summary> Returns the LEFT/RIGHT/FULL OUTER JOIN-type and property name descriptor, if applicable. Returns null if regular join.</summary>
        /// <returns> outer join type, stream names and property names
        /// </returns>
        public IList<OuterJoinDesc> OuterJoinDescList
        {
        	get { return outerJoinDescList; }
        }

        /// <summary> Returns list of group-by expressions.</summary>
        /// <returns> group-by expression nodes as specified in group-by clause
        /// </returns>
        public IList<ExprNode> GroupByExpressions
        {
        	get { return groupByExpressions; }
        }

        /// <summary> Returns the list of order-by expression as specified in the ORDER BY clause.</summary>
        /// <returns> Returns the orderByList.
        /// </returns>
        public IList<Pair<ExprNode, Boolean>> OrderByList
        {
        	get { return orderByList; }
        }
    }
}