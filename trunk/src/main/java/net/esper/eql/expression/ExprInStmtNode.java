package net.esper.eql.expression;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.esper.client.EPStatement;
import net.esper.client.UpdateListener;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.event.EventBean;
import net.esper.event.EventType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExprInStmtNode extends ExprInNode
{
	private final static Log log = LogFactory.getLog(ExprInStmtNode.class);
	
	private final PropertyIndexedEventTable indexTable;
	private final EPStatement statement;
	
	public ExprInStmtNode(boolean isNotIn, EPStatement statement)
	{
		super(isNotIn);
		this.statement = statement;
		EventType eventType = statement.getEventType();
		if(eventType.getPropertyNames().length != 1)
		{
			throw new IllegalArgumentException("ExprInStmtNode requires an EPStatement with only one selected property");
		}
		indexTable = new PropertyIndexedEventTable(-1, eventType, eventType.getPropertyNames());
		statement.addListener(getListenerToLocal());
	}

	public boolean equalsNode(ExprNode node)
	{
        if (!(node instanceof ExprInStmtNode))
        {
            return false;
        }

        ExprInStmtNode other = (ExprInStmtNode) node;
        return (other.isNotIn == this.isNotIn) && (other.statement == this.statement);
	}

	public String toExpressionString()
	{
		StringBuffer buffer = new StringBuffer();
  
		buffer.append(getChildNodes().get(0).toExpressionString());
        
		if (isNotIn)
        {
            buffer.append(" not in (");
        }
        else
        {
            buffer.append(" in (");
        }
		
		buffer.append(statement.getText());
		
        buffer.append(")");
        return buffer.toString();
	}

	public void validate(StreamTypeService streamTypeService,
			AutoImportService autoImportService) throws ExprValidationException
	{
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("The IN operator used with an EPStatement requires exactly 1 child expression");
        }
        
        List<Class> comparedTypes = new LinkedList<Class>();
        comparedTypes.add(getChildNodes().get(0).getType());
        String selectProperty = statement.getEventType().getPropertyNames()[0];
        comparedTypes.add(statement.getEventType().getPropertyType(selectProperty));
        
        determineCommonDenominatorType(comparedTypes);
	}

	public Object evaluate(EventBean[] eventsPerStream)
	{
		log.debug(".evaluate");
		Object value = getChildNodes().get(0).evaluate(eventsPerStream);
		Set<EventBean> events = indexTable.lookup(new Object[] { value });
		return applyIsNotIn(events != null && events.size() > 0);
	}
	
	private UpdateListener getListenerToLocal()
	{
		return new UpdateListener() 
		{
			 public void update(EventBean[] newEvents, EventBean[] oldEvents)
			 {
				 log.debug(".update");
				 indexTable.add(newEvents);
				 indexTable.remove(oldEvents);
			 }
		};
	}
}
