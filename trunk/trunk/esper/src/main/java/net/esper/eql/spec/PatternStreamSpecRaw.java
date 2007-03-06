package net.esper.eql.spec;

import net.esper.pattern.EvalNode;
import net.esper.view.ViewSpec;
import net.esper.core.EPServicesContext;
import net.esper.event.EventAdapterService;
import net.esper.eql.core.AutoImportService;

import java.util.List;

public class PatternStreamSpecRaw extends StreamSpecBase implements StreamSpecRaw
{
    private final EvalNode evalNode;

    /**
     * Ctor.
     * @param evalNode - pattern evaluation node representing pattern statement
     * @param viewSpecs - specifies what view to use to derive data
     * @param optionalStreamName - stream name, or null if none supplied
     */
    public PatternStreamSpecRaw(EvalNode evalNode, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
        this.evalNode = evalNode;
    }

    /**
     * Returns the pattern expression evaluation node for the top pattern operator.
     * @return parent pattern expression node
     */
    public EvalNode getEvalNode()
    {
        return evalNode;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      AutoImportService autoImportService)
    {
        return null;  //TODO
    }
}
