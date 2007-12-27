package net.esper.eql.spec;

import net.esper.filter.FilterSpecCompiled;
import net.esper.eql.expression.ExprNode;

import java.util.List;
import java.util.ArrayList;

/**
 * Specification for use of an existing named window.
 */
public class NamedWindowConsumerStreamSpec extends StreamSpecBase implements StreamSpecCompiled
{
    private String windowName;
    private List<ExprNode> filterExpressions;

    /**
     * Ctor.
     * @param windowName - specifies the name of the named window
     * @param optionalAsName - an alias or null if none defined
     * @param viewSpecs - is the view specifications
     * @param filterExpressions - the named window filters
     */
    public NamedWindowConsumerStreamSpec(String windowName, String optionalAsName, List<ViewSpec> viewSpecs, List<ExprNode> filterExpressions, boolean isUnidirectional)
    {
        super(optionalAsName, viewSpecs, isUnidirectional);
        this.windowName = windowName;
        this.filterExpressions = filterExpressions; 
    }

    /**
     * Returns the window name.
     * @return window name
     */
    public String getWindowName()
    {
        return windowName;
    }

    /**
     * Returns list of filter expressions onto the named window, or no filter expressions if none defined.
     * @return list of filter expressions
     */
    public List<ExprNode> getFilterExpressions()
    {
        return filterExpressions;
    }
}
