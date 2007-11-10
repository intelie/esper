package net.esper.eql.spec;

import net.esper.filter.FilterSpecCompiled;

import java.util.List;
import java.util.ArrayList;

/**
 * Specification for use of an existing named window.
 */
public class NamedWindowConsumerStreamSpec extends StreamSpecBase implements StreamSpecCompiled
{
    private String windowName;

    /**
     * Ctor.
     * @param windowName - specifies the name of the named window
     * @param optionalAsName - an alias or null if none defined
     * @param viewSpecs - is the view specifications
     */
    public NamedWindowConsumerStreamSpec(String windowName, String optionalAsName, List<ViewSpec> viewSpecs)
    {
        super(optionalAsName, viewSpecs);
        this.windowName = windowName;
    }

    /**
     * Returns the window name.
     * @return window name
     */
    public String getWindowName()
    {
        return windowName;
    }
}
