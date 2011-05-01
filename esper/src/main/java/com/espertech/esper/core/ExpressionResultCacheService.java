package com.espertech.esper.core;

import com.espertech.esper.client.EventBean;

import java.lang.ref.SoftReference;
import java.util.*;

/**
 * Provides 3 caches on the statement-level:
 * <p>
 * (A) On the level of indexed event properties:
 *     Properties that are wrapped in EventBean instances, such as for Enumeration Methods, get wrapped only once for the same event.
 *     The cache is keyed by property-name and EventBean reference and maintains a Collection<EventBean>.
 * <p>
 * (B) On the level of enumeration method:
 *     If a enumeration method expression is invoked within another enumeration method expression (not counting expression declarations),
 *     for example "source.where(a => source.minBy(b => b.x))" the "source.minBy(b => b.x)" is not dependent on any other lambda so the result gets cached.
 *     The cache is keyed by the enumeration-method-node as an IdentityHashMap and verified by a context stack (Long[]) that is built in nested evaluation calls.
 * <p>
 * (C) On the level of expression declaration:
 *     a) for non-enum evaluation and for enum-evaluation a separate cache
 *     b) The cache is keyed by the prototype-node as an IdentityHashMap and verified by a events-per-stream (EventBean[]) that is maintained or rewritten.
 */
public class ExpressionResultCacheService {

    private HashMap<String, SoftReference<ExpressionResultCacheEntry<EventBean, Collection<EventBean>>>> collPropertyCache;
    private IdentityHashMap<Object, SoftReference<ExpressionResultCacheEntry<EventBean[], Object>>> exprDeclCacheObject;
    private IdentityHashMap<Object, SoftReference<ExpressionResultCacheEntry<EventBean[], Collection<EventBean>>>> exprDeclCacheCollection;
    private IdentityHashMap<Object, SoftReference<ExpressionResultCacheEntry<Long[], Object>>> enumMethodCache;

    private Deque<ExpressionResultCacheStackEntry> callStack;
    private Deque<Long> lastValueCacheStack;

    public void pushStack(ExpressionResultCacheStackEntry lambda) {
        if (callStack == null) {
            callStack = new ArrayDeque<ExpressionResultCacheStackEntry>();
            lastValueCacheStack = new ArrayDeque<Long>(10);
        }
        callStack.push(lambda);
    }

    public boolean popLambda() {
        callStack.remove();
        return callStack.isEmpty();
    }    

    public Deque<ExpressionResultCacheStackEntry> getStack() {
        return callStack;
    }

    public ExpressionResultCacheEntry<EventBean, Collection<EventBean>> getPropertyColl(String propertyNameFullyQualified, EventBean reference) {
        initPropertyCollCache();
        SoftReference<ExpressionResultCacheEntry<EventBean, Collection<EventBean>>> ref = collPropertyCache.get(propertyNameFullyQualified);
        if (ref == null) {
            return null;
        }
        ExpressionResultCacheEntry<EventBean, Collection<EventBean>> entry = ref.get();
        if (entry == null) {
            return null;
        }
        if (entry.getReference() != reference) {
            return null;
        }
        return entry;
    }

    public void savePropertyColl(String propertyNameFullyQualified, EventBean reference, Collection<EventBean> events) {
        ExpressionResultCacheEntry<EventBean, Collection<EventBean>> entry = new ExpressionResultCacheEntry<EventBean, Collection<EventBean>>(reference, events);
        collPropertyCache.put(propertyNameFullyQualified, new SoftReference<ExpressionResultCacheEntry<EventBean, Collection<EventBean>>>(entry));
    }

    public ExpressionResultCacheEntry<EventBean[], Object> getDeclaredExpressionLastValue(Object node, EventBean[] eventsPerStream) {
        initExprDeclaredCacheObject();
        SoftReference<ExpressionResultCacheEntry<EventBean[], Object>> ref = this.exprDeclCacheObject.get(node);
        if (ref == null) {
            return null;
        }
        ExpressionResultCacheEntry<EventBean[], Object> entry = ref.get();
        if (entry == null) {
            return null;
        }
        EventBean[] cacheEvents = entry.getReference();
        if (cacheEvents.length != eventsPerStream.length) {
            return null;
        }
        for (int i = 0; i < cacheEvents.length; i++) {
            if (cacheEvents[i] != eventsPerStream[i]) {
                return null;
            }
        }
        return entry;
    }

    public void saveDeclaredExpressionLastValue(Object node, EventBean[] eventsPerStream, Object result) {
        EventBean[] copy = new EventBean[eventsPerStream.length];
        System.arraycopy(eventsPerStream, 0, copy, 0, copy.length);
        ExpressionResultCacheEntry<EventBean[], Object> entry = new ExpressionResultCacheEntry<EventBean[], Object>(copy, result);
        exprDeclCacheObject.put(node, new SoftReference<ExpressionResultCacheEntry<EventBean[], Object>>(entry));
    }

    public ExpressionResultCacheEntry<EventBean[], Collection<EventBean>> getDeclaredExpressionLastColl(Object node, EventBean[] eventsPerStream) {
        initExprDeclaredCacheCollection();
        SoftReference<ExpressionResultCacheEntry<EventBean[], Collection<EventBean>>> ref = this.exprDeclCacheCollection.get(node);
        if (ref == null) {
            return null;
        }
        ExpressionResultCacheEntry<EventBean[], Collection<EventBean>> entry = ref.get();
        if (entry == null) {
            return null;
        }
        EventBean[] cacheEvents = entry.getReference();
        if (cacheEvents.length != eventsPerStream.length) {
            return null;
        }
        for (int i = 0; i < cacheEvents.length; i++) {
            if (cacheEvents[i] != eventsPerStream[i]) {
                return null;
            }
        }
        return entry;
    }

    public void saveDeclaredExpressionLastColl(Object node, EventBean[] eventsPerStream, Collection<EventBean> result) {
        EventBean[] copy = new EventBean[eventsPerStream.length];
        System.arraycopy(eventsPerStream, 0, copy, 0, copy.length);
        ExpressionResultCacheEntry<EventBean[], Collection<EventBean>> entry = new ExpressionResultCacheEntry<EventBean[], Collection<EventBean>>(copy, result);
        exprDeclCacheCollection.put(node, new SoftReference<ExpressionResultCacheEntry<EventBean[], Collection<EventBean>>>(entry));
    }

    public ExpressionResultCacheEntry<Long[], Object> getEnumerationMethodLastValue(Object node) {
        initEnumMethodCache();
        SoftReference<ExpressionResultCacheEntry<Long[], Object>> ref = enumMethodCache.get(node);
        if (ref == null) {
            return null;
        }
        ExpressionResultCacheEntry<Long[], Object> entry = ref.get();
        if (entry == null) {
            return null;
        }
        Long[] required = entry.getReference();
        if (required.length != lastValueCacheStack.size()) {
            return null;
        }
        Iterator<Long> prov = lastValueCacheStack.iterator();
        for (int i = 0; i < lastValueCacheStack.size(); i++) {
            if (!required[i].equals(prov.next())) {
                return null;
            }
        }
        return entry;
    }

    public void saveEnumerationMethodLastValue(Object node, Object result) {
        Long[] snapshot = lastValueCacheStack.toArray(new Long[lastValueCacheStack.size()]);
        ExpressionResultCacheEntry<Long[], Object> entry = new ExpressionResultCacheEntry<Long[], Object>(snapshot, result);
        enumMethodCache.put(node, new SoftReference<ExpressionResultCacheEntry<Long[], Object>>(entry));
    }

    private void initEnumMethodCache() {
        if (enumMethodCache == null) {
            enumMethodCache = new IdentityHashMap<Object, SoftReference<ExpressionResultCacheEntry<Long[], Object>>>();
        }
    }

    private void initPropertyCollCache() {
        if (collPropertyCache == null) {
            collPropertyCache = new HashMap<String, SoftReference<ExpressionResultCacheEntry<EventBean, Collection<EventBean>>>>();
        }
    }

    private void initExprDeclaredCacheObject() {
        if (exprDeclCacheObject == null) {
            exprDeclCacheObject = new IdentityHashMap<Object, SoftReference<ExpressionResultCacheEntry<EventBean[], Object>>>();
        }
    }

    private void initExprDeclaredCacheCollection() {
        if (exprDeclCacheCollection == null) {
            exprDeclCacheCollection = new IdentityHashMap<Object, SoftReference<ExpressionResultCacheEntry<EventBean[], Collection<EventBean>>>>();
        }
    }

    public void pushContext(long contextNumber) {
        lastValueCacheStack.push(contextNumber);
    }

    public void popContext() {
        lastValueCacheStack.remove();
    }
}
