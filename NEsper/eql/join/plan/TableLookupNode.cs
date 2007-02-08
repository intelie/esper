using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.util;

namespace net.esper.eql.join.plan
{
    /// <summary>
    /// Specifies exection of a table lookup using the supplied plan for performing the lookup.
    /// </summary>

    public class TableLookupNode : QueryPlanNode
    {
        /// <summary> Returns lookup plan.</summary>
        /// <returns> lookup plan
        /// </returns>
        
        virtual public TableLookupPlan LookupStrategySpec
        {
            get { return tableLookupPlan; }
        }

        private TableLookupPlan tableLookupPlan;

        /// <summary> Ctor.</summary>
        /// <param name="tableLookupPlan">- plan for performing lookup
        /// </param>
        public TableLookupNode(TableLookupPlan tableLookupPlan)
        {
            this.tableLookupPlan = tableLookupPlan;
        }

        public override void Print(IndentWriter writer)
        {
            writer.WriteLine("TableLookupNode " + " tableLookupPlan=" + tableLookupPlan);
        }

        public override ExecNode MakeExec(EventTable[][] indexesPerStream, EventType[] streamTypes)
        {
            TableLookupStrategy lookupStrategy = tableLookupPlan.MakeStrategy(indexesPerStream, streamTypes);

            return new TableLookupExecNode(tableLookupPlan.IndexedStream, lookupStrategy);
        }
    }
}