package net.esper.eql.spec;

import net.esper.util.MetaDefItem;
import net.esper.event.EventAdapterService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.named.NamedWindowService;
import net.esper.eql.expression.ExprNode;
import net.esper.pattern.PatternObjectResolutionService;
import net.esper.schedule.TimeProvider;

import java.util.List;
import java.util.ArrayList;

public class ReferenceJoinStreamSpec extends StreamSpecBase implements StreamSpecRaw, StreamSpecCompiled, MetaDefItem
{
    private String referenceName;
    private List<ExprNode> expressions;

    public ReferenceJoinStreamSpec(String optionalStreamName, String referenceName, List<ExprNode> expressions)
    {
        super(optionalStreamName, new ArrayList<ViewSpec>());
        this.referenceName = referenceName;
        this.expressions = expressions;
    }

    public String getReferenceName()
    {
        return referenceName;
    }

    public List<ExprNode> getExpressions()
    {
        return expressions;
    }

    public ReferenceJoinStreamSpec(String referenceName, List<ExprNode> expressions)
    {
        this.referenceName = referenceName;
        this.expressions = expressions;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      MethodResolutionService methodResolutionService,
                                      PatternObjectResolutionService patternObjectResolutionService,
                                      TimeProvider timeProvider,
                                      NamedWindowService namedWindowService)
    {
        return this;
    }
}
