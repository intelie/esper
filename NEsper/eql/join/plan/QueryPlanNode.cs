using System;
using System.IO;
using System.Text;

using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.plan
{
	/// <summary>
	/// Specification node for a query execution plan to be extended by specific execution specification nodes.
	/// </summary>
	
	public abstract class QueryPlanNode
	{
		/// <summary> Make execution node from this specification.</summary>
		/// <param name="indexesPerStream">tables build for each stream
		/// </param>
		/// <param name="streamTypes">event type of each stream
		/// </param>
		/// <returns> execution node matching spec
		/// </returns>
		public abstract ExecNode MakeExec(EventTable[][] indexesPerStream, EventType[] streamTypes);
		
		/// <summary> Print a long readable format of the query node to the supplied PrintWriter.</summary>
		/// <param name="writer">is the indentation writer to print to
		/// </param>
		public abstract void Print(IndentWriter writer);
		
		/// <summary> Print in readable format the execution plan spec.</summary>
		/// <param name="execNodeSpecs">plans to print
		/// </param>
		/// <returns> readable text with plans
		/// </returns>
		public static String Print(QueryPlanNode[] execNodeSpecs)
		{
            StringWriter writer = new StringWriter();
            writer.WriteLine( "QueryPlanNode[]" );
			
			for (int i = 0; i < execNodeSpecs.Length; i++)
			{
				writer.WriteLine("  node spec " + i + " :");
				
				IndentWriter indentWriter = new IndentWriter(writer, 4, 2);
				execNodeSpecs[i].Print(indentWriter);
			}
			
			return writer.ToString();
		}
	}
}