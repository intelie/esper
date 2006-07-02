package net.esper.eql.parse;

import antlr.RecognitionException;
import antlr.collections.AST;
import net.esper.eql.generated.EQLBaseWalker;

/**
 * For selection of the AST tree walk rule to use.
 */
public interface WalkRuleSelector
{
    /**
     * Implementations can invoke a walk rule of their choice on the walker and AST passed in.
     * @param walker - to invoke walk rule on
     * @param ast - AST to walk
     * @throws RecognitionException - throw on walk errors
     */
    public void invokeWalkRule(EQLBaseWalker walker, AST ast) throws RecognitionException;
}



