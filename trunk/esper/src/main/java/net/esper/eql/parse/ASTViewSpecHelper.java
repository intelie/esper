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

import net.esper.eql.generated.EqlTokenTypes;
import net.esper.eql.spec.ViewSpec;
import antlr.collections.AST;

/**
 * Builds a view specification from view AST nodes.
 */
public class ASTViewSpecHelper implements EqlTokenTypes
{
    /**
     * Build a view specification from the AST node supplied.
     * @param node - parse node
     * @return view spec
     * @throws ASTWalkException is thrown to indicate an error in node parsing
     */
    public static ViewSpec buildSpec(AST node) throws ASTWalkException
    {
        String objectNamespace = node.getFirstChild().getText();
        String objectName = node.getFirstChild().getNextSibling().getText();

        List<Object> objectParams = new LinkedList<Object>();

        AST child = node.getFirstChild().getNextSibling().getNextSibling();
        while (child != null)
        {
            Object object = ASTParameterHelper.makeParameter(child);
            objectParams.add(object);
            child = child.getNextSibling();
        }

        ViewSpec viewSpec = new ViewSpec(objectNamespace, objectName, objectParams);
        return viewSpec;
    }
}
