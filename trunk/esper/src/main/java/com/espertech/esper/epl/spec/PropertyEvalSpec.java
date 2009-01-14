package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Specification for property evaluation.
 */
public class PropertyEvalSpec implements MetaDefItem
{
    private List<PropertyEvalAtom> atoms;

    /**
     * Ctor.
     */
    public PropertyEvalSpec()
    {
        this.atoms = new ArrayList<PropertyEvalAtom>();
    }

    /**
     * Return a list of atoms.
     * @return atoms
     */
    public List<PropertyEvalAtom> getAtoms()
    {
        return atoms;
    }

    /**
     * Add an atom.
     * @param atom to add
     */
    public void add(PropertyEvalAtom atom)
    {
        atoms.add(atom);
    }
}
