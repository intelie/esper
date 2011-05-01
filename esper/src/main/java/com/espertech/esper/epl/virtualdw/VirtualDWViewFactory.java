package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EventBeanFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.event.EventAdapterServiceHelper;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.*;

import java.util.List;

public class VirtualDWViewFactory implements ViewFactory, DataWindowViewFactory {
    private String namedWindowName;
    private VirtualDataWindowFactory virtualDataWindowFactory;
    private EventType parentEventType;
    private Object[] viewParameterArr;

    public VirtualDWViewFactory(Class first, String namedWindowName) throws ViewProcessingException {
        if (!JavaClassHelper.isImplementsInterface(first, VirtualDataWindowFactory.class)) {
            throw new ViewProcessingException("Virtual data window factory class " + first.getName() + " does not implement the interface " + VirtualDataWindowFactory.class.getName());
        }
        this.namedWindowName = namedWindowName;
        virtualDataWindowFactory = (VirtualDataWindowFactory) JavaClassHelper.instantiate(VirtualDataWindowFactory.class, first.getName());
    }

    public void setViewParameters(ViewFactoryContext context, List<ExprNode> viewParameters) throws ViewParameterException {
        ExprNode[] validatedNodes = ViewFactorySupport.validate(context.getViewName(), parentEventType, context.getStatementContext(), viewParameters, true);
        viewParameterArr = new Object[validatedNodes.length];
        for (int i = 0; i < validatedNodes.length; i++) {
            viewParameterArr[i] = ViewFactorySupport.evaluateAssertNoProperties(context.getViewName(), validatedNodes[i], i, context.getStatementContext());
        }
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException {
        this.parentEventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability) {
        return (viewCapability instanceof ViewCapDataWindowAccess) || (viewCapability instanceof RemoveStreamViewCapability);
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback) {
    }

    public View makeView(StatementContext statementContext) {
        EventBeanFactory factory = EventAdapterServiceHelper.getFactoryForType(parentEventType, statementContext.getEventAdapterService());
        VirtualDataWindowOutStreamImpl outputStream = new VirtualDataWindowOutStreamImpl();
        VirtualDataWindowContext context = new VirtualDataWindowContext(statementContext, parentEventType, viewParameterArr, factory, outputStream, namedWindowName);
        VirtualDataWindow window = virtualDataWindowFactory.create(context);
        VirtualDWViewImpl view = new VirtualDWViewImpl(window, parentEventType, namedWindowName);
        outputStream.setView(view);
        return view;
    }

    public EventType getEventType() {
        return parentEventType;
    }

    public boolean canReuse(View view) {
        return false;
    }
}
