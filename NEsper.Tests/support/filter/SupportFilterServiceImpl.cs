using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.events;
using net.esper.filter;

namespace net.esper.support.filter
{

    public class SupportFilterServiceImpl : FilterService
    {
        virtual public long NumEventsEvaluated
        {
            get
            {
                throw new NotSupportedException();
            }

        }
        private IList<Pair<FilterValueSet, FilterCallback>> added = new List<Pair<FilterValueSet, FilterCallback>>();
        private IList<FilterCallback> removed = new List<FilterCallback>();

        public virtual void Evaluate(EventBean _event)
        {
            throw new System.NotSupportedException();
        }

        public virtual void Add(FilterValueSet filterValueSet, FilterCallback callback)
        {
            added.Add(new Pair<FilterValueSet, FilterCallback>(filterValueSet, callback));
        }

        public virtual void Remove(FilterCallback callback)
        {
            removed.Add(callback);
        }

        public IList<Pair<FilterValueSet, FilterCallback>> getAdded()
        {
            return added;
        }

        public IList<FilterCallback> getRemoved()
        {
            return removed;
        }
    }
}
