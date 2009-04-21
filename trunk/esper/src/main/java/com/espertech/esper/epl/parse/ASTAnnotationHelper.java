package com.espertech.esper.epl.parse;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.generated.EsperEPL2Ast;
import com.espertech.esper.epl.spec.AnnotationDesc;
import org.antlr.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class ASTAnnotationHelper
{
    public static AnnotationDesc walk(Tree node) throws ASTWalkException
    {
        String name = node.getChild(0).getText();
        List<Pair<String, Object>> values = new ArrayList<Pair<String, Object>>();
        Object value = null;

        for (int i = 1; i < node.getChildCount(); i++)
        {
            if (node.getChild(i).getType() == EsperEPL2Ast.ANNOTATION_VALUE)
            {
                Pair<String, Object> entry = walkValuePair(node.getChild(i));
                values.add(new Pair<String, Object>(entry.getFirst(), entry.getSecond()));
            }
            else
            {
                value = walkValue(node.getChild(i));
                values.add(new Pair<String, Object>("value", value));
            }
        }

        return new AnnotationDesc(name, values);
    }

    private static Object walkValue(Tree child)
    {
        return ASTConstantHelper.parse(child);
    }

    private static Pair<String, Object> walkValuePair(Tree node)
    {
        String name = node.getChild(0).getText();
        if (node.getChild(1).getType() == EsperEPL2Ast.ANNOTATION_ARRAY)
        {
            Object[] values = walkArray(node.getChild(1));
            return new Pair<String, Object>(name, values);
        }
        if (node.getChild(1).getType() == EsperEPL2Ast.ANNOTATION)
        {
            AnnotationDesc anno = walk(node.getChild(1));
            return new Pair<String, Object>(name, anno);
        }
        else
        {
            Object constant = ASTConstantHelper.parse(node.getChild(1));
            return new Pair<String, Object>(name, constant);
        }
    }

    private static Object[] walkArray(Tree node)
    {
        Object[] values = new Object[node.getChildCount()];
        for (int i = 0; i < node.getChildCount(); i++)
        {
            values[i] = walkValue(node.getChild(i));
        }
        return values;
    }
}
