using System;

using net.esper.pattern;
using net.esper.schedule;
using net.esper.util;

namespace net.esper.core
{
	/// <summary>
    /// Method for Starting a pattern statement.
    /// </summary>
	
    public class EPPatternStmtStartMethod
	{
		private readonly EPServicesContext services;
		private readonly PatternStarter patternStarter;
		private readonly ScheduleBucket scheduleBucket;
		
		/// <summary> Ctor.</summary>
		/// <param name="services">services for Starting
		/// </param>
		/// <param name="patternStarter">pattern Start handle
		/// </param>
		
        public EPPatternStmtStartMethod(EPServicesContext services, PatternStarter patternStarter)
		{
			this.services = services;
			this.patternStarter = patternStarter;
			
			// Allocate the statement's schedule bucket which stays constant over it's lifetime.
			// The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
			scheduleBucket = services.SchedulingService.allocateBucket();
		}
		
		/// <summary> Start pattern.</summary>
		/// <param name="matchCallback">callback by pattern when matches are found
		/// </param>
		/// <returns> Stop method used for Stopping pattern
		/// </returns>

        public virtual EPStatementStopMethod Start(PatternMatchCallback matchCallback)
		{
			PatternContext context = new PatternContext(services.FilterService, services.SchedulingService, scheduleBucket, services.EventAdapterService);
			StopCallback stopCallback = patternStarter.Start(matchCallback, context);
			EPStatementStopMethod stopMethod = new EPStatementStopMethod(
				delegate() { stopCallback(); } );
			
			return stopMethod;
		}
	}
}
