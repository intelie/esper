package com.espertech.esper.pattern;

import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * This class represents a match-until observer in the evaluation tree representing any event expressions.
 */
public final class EvalMatchUntilNode extends EvalNode
{
    private final EvalMatchUntilSpec spec;
    private String[] tagsArrayed;

    public EvalMatchUntilNode(EvalMatchUntilSpec spec)
    {
        this.spec = spec;
    }

    public EvalMatchUntilSpec getSpec()
    {
        return spec;
    }

    public String[] getTagsArrayed()
    {
        return tagsArrayed;
    }

    public void setTagsArrayedSet(Set<String> tagsArrayedSet)
    {
        if (tagsArrayedSet != null)
        {
            tagsArrayed = tagsArrayedSet.toArray(new String[tagsArrayedSet.size()]);
        }
        else
        {
            tagsArrayed = new String[0];
        }
    }

    public final EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context,
                                                 Object stateNodeId)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".newState");
        }

        // if the high and low are bounded to the same value, there should be no until
        if ((spec.getLowerBounds() != null) && (spec.getLowerBounds() == spec.getUpperBounds()))
        {
            if (getChildNodes().size() > 2)
            {
                throw new IllegalStateException("Expected number of child nodes incorrect, expected 1 (no-until) or 2 (with until) child nodes, found "
                        + getChildNodes().size() + " for bound match");
            }
        }
        else
        {
            // expecting a match-expression and an until-expression
            if (getChildNodes().size() != 2)
            {
                throw new IllegalStateException("Expected number of child nodes incorrect, expected 2 child nodes, found "
                        + getChildNodes().size());
            }
        }

        return context.getPatternStateFactory().makeMatchUntilState(parentNode, this, beginState, stateNodeId);
    }

    public final String toString()
    {
        return ("EvalMatchUntilNode children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalMatchUntilNode.class);
}
