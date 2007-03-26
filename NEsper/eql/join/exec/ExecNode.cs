using System;
using System.Collections.Generic;
using System.IO;

using EventBean = net.esper.events.EventBean;
using IndentWriter = net.esper.util.IndentWriter;

namespace net.esper.eql.join.exec
{
	/// <summary> Interface for an execution node that looks up events and builds a result set contributing to an overall
	/// join result set.
	/// </summary>
	
    public abstract class ExecNode
	{
		/// <summary> Process single event using the prefill events to compile lookup results.</summary>
		/// <param name="lookupEvent">event to look up for or query for
		/// </param>
		/// <param name="prefillPath">set of events currently in the example tuple to serve
		/// as a prototype for result rows.
		/// </param>
		/// <param name="result">is the list of tuples to add a result row to
		/// </param>
		public abstract void Process(EventBean lookupEvent, EventBean [] prefillPath, IList < EventBean [] > result);
		
		/// <summary> Output the execution strategy.</summary>
		/// <param name="writer">to output to
		/// </param>
		public abstract void Print(IndentWriter writer);
		
		/// <summary> Print in readable format the execution strategy.</summary>
		/// <param name="execNode">execution node to print
		/// </param>
		/// <returns> readable text with execution nodes constructed for actual streams
		/// </returns>
		public static String Print(ExecNode execNode)
		{
			StringWriter buf = new StringWriter();
			IndentWriter indentWriter = new IndentWriter(buf, 4, 2);
			execNode.Print(indentWriter);
			
			return buf.ToString();
		}
	}
}