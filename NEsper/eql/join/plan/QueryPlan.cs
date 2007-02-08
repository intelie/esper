using System;
namespace net.esper.eql.join.plan
{
	
	/// <summary> Contains the query plan for all streams.</summary>
	public class QueryPlan
	{
		/// <summary> Return index specs.</summary>
		/// <returns> index specs
		/// </returns>
		virtual public QueryPlanIndex[] IndexSpecs
		{
			get
			{
				return indexSpecs;
			}
			
		}
		/// <summary> Return execution node specs.</summary>
		/// <returns> execution node specs
		/// </returns>
		virtual public QueryPlanNode[] ExecNodeSpecs
		{
			get
			{
				return execNodeSpecs;
			}
			
		}
		private QueryPlanIndex[] indexSpecs;
		private QueryPlanNode[] execNodeSpecs;
		
		/// <summary> Ctor.</summary>
		/// <param name="indexSpecs">- specs for indexes to create
		/// </param>
		/// <param name="execNodeSpecs">- specs for execution nodes to create
		/// </param>
		public QueryPlan(QueryPlanIndex[] indexSpecs, QueryPlanNode[] execNodeSpecs)
		{
			this.indexSpecs = indexSpecs;
			this.execNodeSpecs = execNodeSpecs;
		}
		
		public override String ToString()
		{
			System.Text.StringBuilder buffer = new System.Text.StringBuilder();
			buffer.Append("QueryPlanNode\n");
			buffer.Append(QueryPlanIndex.Print(indexSpecs));
			buffer.Append(QueryPlanNode.Print(execNodeSpecs));
			return buffer.ToString();
		}
	}
}