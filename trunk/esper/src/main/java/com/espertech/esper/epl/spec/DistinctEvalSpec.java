package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class DistinctEvalSpec implements MetaDefItem, Serializable
{
    private List<ExprNode> atoms;

    /**
     * Ctor.
     */
    public DistinctEvalSpec(List<ExprNode> atoms)
    {
        this.atoms = atoms;
    }

    /**
     * Return a list of atoms.
     * @return atoms
     */
    public List<ExprNode> getAtoms()
    {
        return atoms;
    }

    public void setAtoms(List<ExprNode> atoms)
    {
        this.atoms = atoms;
    }
}
