package net.esper.pattern;


/**
 * Interface for nodes in an expression evaluation state tree that are being informed by a child that the
 * event expression fragments (subtrees) which the child represents has turned true (evaluateTrue method)
 * or false (evaluateFalse).
 */
public interface Evaluator
{
    /**
     * Indicate a change in truth value to true.
     * @param matchEvent is the container for events that caused the change in truth value
     * @param fromNode is the node that indicates the change
     * @param isQuitted is an indication of whether the node continues listenening or stops listening
     */
    public void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted);

    /**
     * Indicate a change in truth value to false.
     * @param fromNode is the node that indicates the change
     */
    public void evaluateFalse(EvalStateNode fromNode);
}
