/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import java.util.LinkedList;
import java.util.List;

import net.esper.eql.spec.ViewSpec;
import org.antlr.runtime.tree.Tree;

/**
 * Builds a view specification from view AST nodes.
 */
public class ASTViewSpecHelper
{
    /**
     * Build a view specification from the AST node supplied.
     * @param node - parse node
     * @return view spec
     * @throws ASTWalkException is thrown to indicate an error in node parsing
     */
    public static ViewSpec buildSpec(Tree node, long engineTime) throws ASTWalkException
    {
        String objectNamespace = node.getChild(0).getText();
        String objectName = node.getChild(1).getText();

        List<Object> objectParams = new LinkedList<Object>();

        for (int i = 2; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            Object object = ASTParameterHelper.makeParameter(child, engineTime);
            objectParams.add(object);
        }

        return new ViewSpec(objectNamespace, objectName, objectParams);
    }
}
