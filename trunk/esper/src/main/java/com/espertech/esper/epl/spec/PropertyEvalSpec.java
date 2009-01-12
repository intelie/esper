package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;

import java.util.ArrayList;
import java.util.List;

public class PropertyEvalSpec implements MetaDefItem
{
    private List<PropertyEvalAtom> atoms;

    public PropertyEvalSpec()
    {
        this.atoms = new ArrayList<PropertyEvalAtom>();
    }

    public List<PropertyEvalAtom> getAtoms()
    {
        return atoms;
    }

    public void add(PropertyEvalAtom atom)
    {
        atoms.add(atom);
    }
}
