/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.eql.generated.EsperEPLTree;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 * For selection of the AST tree walk rule to use.
 */
public interface WalkRuleSelector
{
    /**
     * Implementations can invoke a walk rule of their choice on the walker and AST passed in.
     * @param walker - to invoke walk rule on
     * @throws org.antlr.runtime.RecognitionException - throw on walk errors
     */
    public void invokeWalkRule(EsperEPLTree walker) throws RecognitionException;
}



