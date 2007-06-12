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
        /// <param name="isNewData">indicates whether we are dealing with new data (istream) or old data (rstream)</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>

        Object Evaluate(EventBean[] eventsPerStream, bool isNewData);
    }

    public delegate Object ExprEvaluatorDelegate(EventBean[] eventsPerStream, bool isNewData);

    /// <summary>
    /// Implements the ExprEvaluator with a delegate.
    /// </summary>

    public class ExprEvaluatorImpl : ExprEvaluator
    {
        private ExprEvaluatorDelegate m_delegate;

        /// <summary>
        /// Initializes a new instance of the <see cref="ExprEvaluatorImpl"/> class.
        /// </summary>
        /// <param name="dg">The dg.</param>
        public ExprEvaluatorImpl(ExprEvaluatorDelegate dg)
        {
            this.m_delegate = dg;
        }

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <param name="isNewData">indicates whether we are dealing with new data (istream) or old data (rstream)</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>

        public Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
        {
            return m_delegate(eventsPerStream, isNewData);
        }
    }
}
