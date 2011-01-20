package com.espertech.esper.support.client;

import com.espertech.esper.client.hook.*;

import java.util.ArrayList;
import java.util.List;

public class SupportConditionHandlerFactory implements ConditionHandlerFactory {

    private static List<ConditionHandlerFactoryContext> factoryContexts = new ArrayList<ConditionHandlerFactoryContext>();
    private static List<SupportConditionHandler> handlers = new ArrayList<SupportConditionHandler>();

    public ConditionHandler getHandler(ConditionHandlerFactoryContext context) {
        factoryContexts.add(context);
        SupportConditionHandler handler = new SupportConditionHandler();
        handlers.add(handler);
        return handler;
    }

    public static List<ConditionHandlerFactoryContext> getFactoryContexts() {
        return factoryContexts;
    }

    public static List<SupportConditionHandler> getHandlers() {
        return handlers;
    }

    public static SupportConditionHandler getLastHandler() {
        return handlers.get(handlers.size() - 1);
    }

    public static class SupportConditionHandler implements ConditionHandler {
        private List<ConditionHandlerContext> contexts = new ArrayList<ConditionHandlerContext>();

        public SupportConditionHandler() {
        }

        public void handle(ConditionHandlerContext context) {
            contexts.add(context);
        }

        public List<ConditionHandlerContext> getContexts() {
            return contexts;
        }
    }
}
