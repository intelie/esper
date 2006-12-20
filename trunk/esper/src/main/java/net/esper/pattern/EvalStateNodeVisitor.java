package net.esper.pattern;

/**
 * Interface for visiting each element in the evaluation node tree for an event expression (see Visitor pattern).
 */
public interface EvalStateNodeVisitor
{
    /**
     * Invoked by each child node as part of accepting this visitor.
     * @param node is the node in the composite tree accepting the visitor
     * @param data is any additional useful to implementations
     * @return any additional data useful to implementations or null
     */
    public Object visit(EvalStateNode node, Object data);
}
