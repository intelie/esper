package com.espertech.esper.epl.parse;

import com.espertech.esper.epl.spec.AnnotationDesc;
import com.espertech.esper.epl.generated.EsperEPL2Ast;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.collection.Pair;
import org.antlr.runtime.tree.Tree;

import java.util.Map;
import java.util.HashMap;

public class ASTAnnotationHelper
{
    public static AnnotationDesc walk(Tree node) throws ASTWalkException
    {
        String name = node.getChild(0).getText();
        Map<String, Object> values = new HashMap<String, Object>();
        Object value = null;

        for (int i = 1; i < node.getChildCount(); i++)
        {
            if (node.getChild(i).getType() == EsperEPL2Ast.ANNOTATION_VALUE)
            {
                Pair<String, Object> entry = walkValuePair(node.getChild(i));
                values.put(entry.getFirst(), entry.getSecond());
            }
            else
            {
                value = walkValue(node.getChild(i));
            }
        }

        return new AnnotationDesc(name, value, values);
    }

    private static Object walkValue(Tree child)
    {
        return ASTConstantHelper.parse(child);
    }

    private static Pair<String, Object> walkValuePair(Tree node)
    {
        String name = node.getChild(0).getText();
        Object constant = ASTConstantHelper.parse(node.getChild(1));
        return new Pair<String, Object>(name, constant);
    }
}
