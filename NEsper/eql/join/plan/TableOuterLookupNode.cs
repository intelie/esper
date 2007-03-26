using System;
using EventType = net.esper.events.EventType;
using ExecNode = net.esper.eql.join.exec.ExecNode;
using TableLookupStrategy = net.esper.eql.join.exec.TableLookupStrategy;
using TableOuterLookupExecNode = net.esper.eql.join.exec.TableOuterLookupExecNode;
using EventTable = net.esper.eql.join.table.EventTable;
using IndentWriter = net.esper.util.IndentWriter;
namespace net.esper.eql.join.plan
{
	
	/// <summary> Specifies exection of a table lookup with outer join using the a specified lookup plan.</summary>
	public class TableOuterLookupNode:QueryPlanNode
	{
		/// <summary> Returns lookup plan.</summary>
		/// <returns> lookup plan
		/// </returns>
		virtual internal TableLookupPlan LookupStrategySpec
		{
			get { return tableLookupPlan; }
		}

		private TableLookupPlan tableLookupPlan;
		
		/// <summary> Ctor.</summary>
		/// <param name="tableLookupPlan">plan for performing lookup
		/// </param>
		public TableOuterLookupNode(TableLookupPlan tableLookupPlan)
		{
			this.tableLookupPlan = tableLookupPlan;
		}

        /// <summary>
        /// Print a long readable format of the query node to the supplied PrintWriter.
        /// </summary>
        /// <param name="writer">is the indentation writer to print to</param>
		public override void Print(IndentWriter writer)
		{
			writer.WriteLine("TableOuterLookupNode " + " tableLookupPlan=" + tableLookupPlan);
		}

        /// <summary>
        /// Make execution node from this specification.
        /// </summary>
        /// <param name="indexesPerStream">tables build for each stream</param>
        /// <param name="streamTypes">event type of each stream</param>
        /// <returns>execution node matching spec</returns>
		public override ExecNode MakeExec(EventTable[][] indexesPerStream, EventType[] streamTypes)
		{
			TableLookupStrategy lookupStrategy = tableLookupPlan.MakeStrategy(indexesPerStream, streamTypes);
			
			return new TableOuterLookupExecNode(tableLookupPlan.IndexedStream, lookupStrategy);
		}
	}
}