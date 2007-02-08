using System;

namespace net.esper.eql.view
{
	/// <summary> Invoked to perform output processing.</summary>
	/// <param name="doOutput">- true if the batched events should actually be output as well as processed, false if they should just be processed
	/// </param>
	/// <param name="forceUpdate">- true if output should be made even when no updating events have arrived
	/// </param>
	
    public delegate void OutputCallback(bool doOutput, bool forceUpdate);
}