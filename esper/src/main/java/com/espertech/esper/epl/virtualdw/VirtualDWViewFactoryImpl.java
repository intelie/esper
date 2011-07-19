/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EventBeanFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.event.EventAdapterServiceHelper;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;

public class VirtualDWViewFactoryImpl implements ViewFactory, DataWindowViewFactory, VirtualDWViewFactory {
    private static Log log = LogFactory.getLog(VirtualDWViewFactoryImpl.class);

    private Serializable customConfiguration;
    private ViewFactoryContext viewFactoryContext;
    private List<ExprNode> viewParameters;
    private String namedWindowName;
    private VirtualDataWindowFactory virtualDataWindowFactory;
    private EventType parentEventType;
    private Object[] viewParameterArr;
    private ExprNode[] viewParameterExp;

    public VirtualDWViewFactoryImpl(Class first, String namedWindowName, Serializable customConfiguration) throws ViewProcessingException {
        if (!JavaClassHelper.isImplementsInterface(first, VirtualDataWindowFactory.class)) {
            throw new ViewProcessingException("Virtual data window factory class " + first.getName() + " does not implement the interface " + VirtualDataWindowFactory.class.getName());
        }
        this.customConfiguration = customConfiguration;
        this.namedWindowName = namedWindowName;
        virtualDataWindowFactory = (VirtualDataWindowFactory) JavaClassHelper.instantiate(VirtualDataWindowFactory.class, first.getName());
    }

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException {
        this.viewFactoryContext = viewFactoryContext;
        this.viewParameters = viewParameters;
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException {
        this.parentEventType = parentEventType;

        ExprNode[] validatedNodes = ViewFactorySupport.validate(viewFactoryContext.getViewName(), parentEventType, viewFactoryContext.getStatementContext(), viewParameters, true);
        viewParameterArr = new Object[validatedNodes.length];
        for (int i = 0; i < validatedNodes.length; i++) {
            try {
                viewParameterArr[i] = ViewFactorySupport.evaluateAssertNoProperties(viewFactoryContext.getViewName(), validatedNodes[i], i, viewFactoryContext.getStatementContext());
            }
            catch (Exception ex) {
                // expected
            }
        }

        viewParameterExp = ViewFactorySupport.validate(viewFactoryContext.getViewName(), parentEventType, viewFactoryContext.getStatementContext(), viewParameters, true);
    }

    public boolean canProvideCapability(ViewCapability viewCapability) {
        return (viewCapability instanceof ViewCapDataWindowAccess) || (viewCapability instanceof RemoveStreamViewCapability);
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback) {
    }

    public View makeView(StatementContext statementContext) {
        EventBeanFactory factory = EventAdapterServiceHelper.getFactoryForType(parentEventType, statementContext.getEventAdapterService());
        VirtualDataWindowOutStreamImpl outputStream = new VirtualDataWindowOutStreamImpl();
        VirtualDataWindowContext context = new VirtualDataWindowContext(statementContext, parentEventType, viewParameterArr, viewParameterExp, factory, outputStream, namedWindowName, viewFactoryContext, customConfiguration);
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
