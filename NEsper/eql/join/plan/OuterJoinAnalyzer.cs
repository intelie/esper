using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.eql.spec;

namespace net.esper.eql.join.plan
{
	/// <summary>
	/// Analyzes an outer join descriptor list and builds a query graph model from it.
	/// The 'on' expression identifiers are extracted and placed in the query graph
	/// model as navigable relationships (by key and index properties) between streams.
	/// </summary>

	public class OuterJoinAnalyzer
	{
		/// <summary> Analyzes the outer join descriptor list to build a query graph model.</summary>
		/// <param name="outerJoinDescList">list of outer join descriptors
		/// </param>
		/// <param name="queryGraph">model containing relationships between streams that is written into
		/// </param>
		/// <returns> queryGraph object
		/// </returns>
		public static QueryGraph analyze( IList<OuterJoinDesc> outerJoinDescList, QueryGraph queryGraph )
		{
			foreach ( OuterJoinDesc outerJoinDesc in outerJoinDescList )
			{
                ExprIdentNode identNodeLeft = outerJoinDesc.LeftNode;
                ExprIdentNode identNodeRight = outerJoinDesc.RightNode;

				queryGraph.Add(
					identNodeLeft.StreamId,
					identNodeLeft.ResolvedPropertyName,
					identNodeRight.StreamId,
					identNodeRight.ResolvedPropertyName );
			}

			return queryGraph;
		}
	}
}
