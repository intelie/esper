package net.esper.eql.spec;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.StreamTypeServiceImpl;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterSpecParam;
import net.esper.pattern.EvalFilterNode;
import net.esper.pattern.EvalNode;
import net.esper.view.ViewSpec;
import net.esper.util.UuidGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
            throws ExprValidationException
    {
        // Determine al the filter nodes used in the pattern
        List<EvalFilterNode> filterNodes = recusiveFilterChildNodes(evalNode);
        
        // Resolve all event types; some filters are tagged and we keep the order in which they are specified
        LinkedHashMap<String, EventType> taggedEventTypes = new LinkedHashMap<String, EventType>();
        for (EvalFilterNode filterNode : filterNodes)
        {
            String eventName = filterNode.getRawFilterSpec().getEventTypeAlias();
            EventType eventType = FilterStreamSpecRaw.resolveType(eventName, eventAdapterService);
            String optionalTag = filterNode.getEventAsName();

            // If a tag was supplied for the type, the tags must stay with this type, i.e. a=BeanA -> b=BeanA -> a=BeanB is a no
            if (optionalTag != null)
            {
                EventType existingType = taggedEventTypes.get(optionalTag);
                if ((existingType != null) && (existingType != eventType))
                {
                    throw new IllegalArgumentException("Tag '" + optionalTag + "' for event '" + eventName +
                            "' has already been declared for events of type " + existingType.getUnderlyingType().getName());
                }
                taggedEventTypes.put(optionalTag, eventType);
            }

            // For this filter, filter types are all known tags at this time,
            // and additionally stream 0 (self) is our event type.
            // Stream type service allows resolution by property name event if that name appears in other tags.
            // by defaulting to stream zero.
            String selfStreamName = UuidGenerator.generate(filterNode);
            LinkedHashMap<String, EventType> filterTypes = new LinkedHashMap<String, EventType>();
            filterTypes.put(selfStreamName, eventType);
            filterTypes.putAll(taggedEventTypes);
            StreamTypeService streamTypeService = new StreamTypeServiceImpl(filterTypes, true);
            
            // Validate all nodes, make sure each returns a boolean and types are good;
            // Also decompose all AND super nodes into individual expressions
            // Stream zero is always the current event type, all others follow the order of the map (stream 1 to N).
            List<ExprNode> exprNodes = filterNode.getRawFilterSpec().getFilterExpressions();
            List<ExprNode> constituents = FilterStreamSpecRaw.validateAndDecompose(exprNodes, streamTypeService, autoImportService);

            // Make filter spec
            FilterSpec spec = FilterStreamSpecRaw.makeFilterSpec(eventType, constituents);
            filterNode.setFilterSpec(spec);
        }

        return new PatternStreamSpecCompiled(evalNode, taggedEventTypes, this.getViewSpecs(), this.getOptionalStreamName());
    }

    protected static List<EvalFilterNode> recusiveFilterChildNodes(EvalNode currentNode)
    {
        List<EvalFilterNode> nodeList = new ArrayList<EvalFilterNode>();
        recusiveFilterChildNodes(nodeList, currentNode);
        return nodeList;
    }

    private static void recusiveFilterChildNodes(List<EvalFilterNode> nodeList, EvalNode currentNode)
    {
        if (currentNode instanceof EvalFilterNode)
        {
            nodeList.add((EvalFilterNode) currentNode);
        }
        for (EvalNode node : currentNode.getChildNodes())
        {
            recusiveFilterChildNodes(nodeList, node);
        }
    }
}
