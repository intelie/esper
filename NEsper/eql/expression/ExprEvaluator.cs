using System;

using net.esper.events ;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Interface for evaluating of an event tuple.
    /// </summary>

    public interface ExprEvaluator
    {
        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <returns>evaluation result, a boolean value for OR/AND-type evalution nodes.</returns>
        
        Object Evaluate(EventBean[] eventsPerStream);
    }
}