using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary>
    /// Builder manipulates a tree structure consisting of <seealso cref="FilterHandleSetNode"/> and <seealso cref="FilterParamIndexBase" />
    /// instances. Filters can be added to a top node (an instance of FilterHandleSetNode) via the add method. This
    /// method returns an instance of <seealso cref="IndexTreePath"/> which represents the tree path (list of indizes) that the
    /// filter callback was added to. To remove filters the same IndexTreePath instance must be passed in.
	/// <para>
    /// The implementation is designed to be multithread-safe in conjunction with the node classes manipulated by this
    /// class.
    /// </para>
	/// </summary>
    public sealed class IndexTreeBuilder
    {
        private EventType eventType;
        private TreeSet<FilterValueSetParam> remainingParameters;
        private FilterHandle filterHandle;
        private long currentThreadId;

        /// <summary>
        /// Initializes a new instance of the <see cref="IndexTreeBuilder"/> class.
        /// </summary>
        public IndexTreeBuilder()
        {
        }

        /// <summary>Add a filter callback according to the filter specification to the top node returninginformation to be used to remove the filter callback.</summary>
        /// <param name="filterValueSet">is the filter definition</param>
        /// <param name="filterHandle">is the callback to be added</param>
        /// <param name="topNode">node to be added to any subnode beneath it</param>
        /// <returns>an encapsulation of information need to allow for safe removal of the filter tree.</returns>

        public IndexTreePath Add(FilterValueSet filterValueSet,
                                        FilterHandle filterHandle,
                                        FilterHandleSetNode topNode)
        {
            this.eventType = filterValueSet.EventType;
            this.remainingParameters = CopySortParameters(filterValueSet.Parameters);
            this.filterHandle = filterHandle;
            this.currentThreadId = Thread.CurrentThread.ManagedThreadId;

            if (log.IsDebugEnabled)
            {
                log.Debug(".add (" + currentThreadId + ") Adding filter callback, " +
                          "  topNode=" + topNode +
                          "  filterHandle=" + this.filterHandle);
            }

            IndexTreePath treePathInfo = new IndexTreePath();

            AddToNode(topNode, treePathInfo);

            this.remainingParameters = null;
            this.filterHandle = null;

            return treePathInfo;
        }

        /// <summary>Remove an filterHandle from the given top node. The IndexTreePath instance passed in must be thesame as obtained when the same filterHandle was added.</summary>
        /// <param name="filterHandle">filter callback  to be removed</param>
        /// <param name="treePathInfo">encapsulates information need to allow for safe removal of the filterHandle</param>
        /// <param name="topNode">The top tree node beneath which the filterHandle was added</param>

        public void Remove(FilterHandle filterHandle,
                           IndexTreePath treePathInfo,
                           FilterHandleSetNode topNode)
        {
            this.remainingParameters = null;
            this.filterHandle = filterHandle;
            this.currentThreadId = Thread.CurrentThread.ManagedThreadId;

            if (log.IsDebugEnabled)
            {
                log.Debug(".remove (" + currentThreadId + ") Removing filterHandle " +
                          " from treepath=" + treePathInfo.ToString() +
                          "  topNode=" + topNode +
                          "  filterHandle=" + filterHandle);
            }

            RemoveFromNode(topNode, treePathInfo);

            this.filterHandle = null;
        }

        /// <summary>Add to the current node building up the tree path information.</summary>
        /// <param name="currentNode">is the node to add to</param>
        /// <param name="treePathInfo">is filled with information about which indizes were chosen to add the filter to</param>

        private void AddToNode(FilterHandleSetNode currentNode, IndexTreePath treePathInfo)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(
                    ".AddToNode (" + currentThreadId + ") Adding filterHandle," +
                    "  node=" + currentNode +
                    "  remainingParameters=" + PrintRemainingParameters());
            }

            // If no parameters are specified, add to current node, and done
            if (remainingParameters.Count == 0)
            {
                currentNode.NodeRWLock.AcquireWriterLock( LockConstants.WriterTimeout );
                currentNode.Add(filterHandle);
                currentNode.NodeRWLock.ReleaseWriterLock();
                return;
            }

            Pair<FilterValueSetParam, FilterParamIndexBase> pair = null;

            using (new ReaderLock(currentNode.NodeRWLock))
            {
                // Need to find an existing index that matches one of the filter parameters
                pair = IndexHelper.FindIndex(remainingParameters, currentNode.Indizes);

                // Found an index matching a filter parameter
                if (pair != null)
                {
                    remainingParameters.Remove(pair.First);
                    Object filterForValue = pair.First.FilterForValue;
                    FilterParamIndexBase index = pair.Second;
                    treePathInfo.Add(index, filterForValue);
                    AddToIndex(index, filterForValue, treePathInfo);
                    return;
                }
            }

            using (new WriterLock(currentNode.NodeRWLock))
            {
                // An index for any of the filter parameters was not found, create one
                pair = IndexHelper.FindIndex(remainingParameters, currentNode.Indizes);

                // Attempt to find an index again this time under a write lock
                if (pair != null)
                {
                    remainingParameters.Remove(pair.First);
                    Object filterForValue = pair.First.FilterForValue;
                    FilterParamIndexBase index = pair.Second;
                    treePathInfo.Add(index, filterForValue);
                    AddToIndex(index, filterForValue, treePathInfo);
                    return;
                }

                // No index found that matches any parameters, create a new one
                // Pick the next parameter for an index
                FilterValueSetParam parameterPickedForIndex = remainingParameters.First;
                remainingParameters.Remove(parameterPickedForIndex);

                FilterParamIndexBase _index = IndexFactory.CreateIndex(
                    eventType,
                    parameterPickedForIndex.PropertyName,
                    parameterPickedForIndex.FilterOperator);

                currentNode.Indizes.Add(_index);
                treePathInfo.Add(_index, parameterPickedForIndex.FilterForValue);
                AddToIndex(_index, parameterPickedForIndex.FilterForValue, treePathInfo);
            }
        }

        /// <summary>
        /// Remove an filterHandle from the current node, return true if the node is the node is empty now
        /// </summary>
        /// <param name="currentNode"></param>
        /// <param name="treePathInfo"></param>
        /// <returns></returns>

        private Boolean RemoveFromNode(FilterHandleSetNode currentNode,
                                       IndexTreePath treePathInfo)
        {
            Pair<FilterParamIndexBase, Object> nextPair = treePathInfo.RemoveFirst();

            // No remaining filter parameters
            if (nextPair == null)
            {
                using (WriterLock writeLock = new WriterLock(currentNode.NodeRWLock))
                {
                    Boolean isRemoved = currentNode.Remove(filterHandle);
                    Boolean isEmpty = currentNode.IsEmpty;

                    if (!isRemoved)
                    {
                        log.Warn(".RemoveFromNode (" + currentThreadId + ") Could not find the filterHandle to be removed within the supplied node , node=" +
                                currentNode + "  filterHandle=" + filterHandle);
                    }

                    return isEmpty;
                }
            }

            // Remove from index
            FilterParamIndexBase nextIndex = nextPair.First;
            Object filteredForValue = nextPair.Second;

            using (WriterLock writeLock = new WriterLock(currentNode.NodeRWLock))
            {
                Boolean _isEmpty = RemoveFromIndex(nextIndex, treePathInfo, filteredForValue);

                if (!_isEmpty)
                {
                    return false;
                }

                // Remove the index if the index is now empty
                if (nextIndex.Count == 0)
                {
                    Boolean isRemoved = currentNode.Remove(nextIndex);

                    if (!isRemoved)
                    {
                        log.Warn(
                            ".RemoveFromNode (" + currentThreadId + ") Could not find the index in index list for removal," +
                            "  index=" + nextIndex.ToString() +
                            "  filterHandle=" + filterHandle);
                        return false;
                    }
                }

                Boolean isEmptyNode = currentNode.IsEmpty;
                return isEmptyNode;
            }
        }

        /// <summary>
        /// Remove filterHandle from index, returning true if index empty after removal
        /// </summary>
        /// <param name="index"></param>
        /// <param name="treePathInfo"></param>
        /// <param name="filterForValue"></param>
        /// <returns></returns>
        private Boolean RemoveFromIndex(FilterParamIndexBase index,
                                        IndexTreePath treePathInfo,
                                        Object filterForValue)
        {
            using (WriterLock writeLock = new WriterLock(index.ReadWriteLock))
            {
                EventEvaluator eventEvaluator = index[filterForValue];
                if (eventEvaluator == null)
                {
                    if (log.IsWarnEnabled)
                    {
                        log.Warn(
                            ".RemoveFromIndex (" + currentThreadId + ") Could not find the filterHandle value in index," +
                            "  index=" + index.ToString() +
                            "  value=" + filterForValue.ToString() +
                            "  filterHandle=" + filterHandle);
                    }

                    return false;
                }

                if (eventEvaluator is FilterHandleSetNode)
                {
                    FilterHandleSetNode node = (FilterHandleSetNode)eventEvaluator;
                    Boolean isEmpty = RemoveFromNode(node, treePathInfo);

                    if (isEmpty)
                    {
                        // Since we are holding a write lock to this index, there should not be a chance that
                        // another thread had been adding anything to this FilterHandleSetNode
                        index.Remove(filterForValue);
                    }
                    int size = index.Count;
                    return (size == 0);
                }

                FilterParamIndexBase nextIndex = (FilterParamIndexBase)eventEvaluator;
                Pair<FilterParamIndexBase, Object> nextPair = treePathInfo.RemoveFirst();

                if (nextPair == null)
                {
                    log.Fatal(".RemoveFromIndex Expected an inner index to this index, this=" + this.ToString());
                    return false;
                }

                if (nextPair.First != nextIndex)
                {
                    log.Fatal(
                        ".RemoveFromIndex Expected an index for filterHandle that differs from the found index, this=" + this.ToString() +
                        "  expected=" + nextPair.First);
                    return false;
                }

                Object nextExpressionValue = nextPair.Second;

                Boolean _isEmpty = RemoveFromIndex(nextPair.First, treePathInfo, nextExpressionValue);

                if (_isEmpty)
                {
                    // Since we are holding a write lock to this index, there should not be a chance that
                    // another thread had been adding anything to this FilterHandleSetNode
                    index.Remove(filterForValue);
                }

                int _size = index.Count;
                return (_size == 0);
            }
        }

        /// <summary>Add to an index the value to filter for.</summary>
        /// <param name="index">is the index to add to</param>
        /// <param name="filterForValue">is the filter parameter value to add</param>
        /// <param name="treePathInfo">is the specification to fill on where is was added</param>

        private void AddToIndex(FilterParamIndexBase index,
                                Object filterForValue,
                                IndexTreePath treePathInfo)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(string.Format(".addToIndex ({0}) Adding to index {1}  expressionValue={2}", currentThreadId, index.ToString(), filterForValue));
            }

            EventEvaluator eventEvaluator = null ;

            try
            {
                index.ReadWriteLock.AcquireReaderLock(LockConstants.ReaderTimeout);
                eventEvaluator = index[filterForValue];

                // The filter parameter value already existed in bean, add and release locks
                if (eventEvaluator != null)
                {
                    bool added = AddToEvaluator(eventEvaluator, treePathInfo);
                    if (added)
                    {
                        return;
                    }
                }
            }
            finally
            {
                index.ReadWriteLock.ReleaseReaderLock();
            }

            using (new WriterLock(index.ReadWriteLock))
            {
                eventEvaluator = index[filterForValue];

                // It may exist now since another thread could have added the entry
                if (eventEvaluator != null)
                {
                    bool added = AddToEvaluator(eventEvaluator, treePathInfo);
                    if (added)
                    {
                        return;
                    }

                    // The found eventEvaluator must be converted to a new FilterHandleSetNode
                    FilterParamIndexBase nextIndex = (FilterParamIndexBase)eventEvaluator;
                    FilterHandleSetNode newNode = new FilterHandleSetNode();
                    newNode.Add(nextIndex);
                    index[filterForValue] = newNode;
                    AddToNode(newNode, treePathInfo);

                    return;
                }

                // The index does not currently have this filterHandle value,
                // if there are no remaining parameters, create a node
                if (remainingParameters.Count == 0)
                {
                    FilterHandleSetNode node = new FilterHandleSetNode();
                    AddToNode(node, treePathInfo);
                    index[filterForValue] = node;
                    return;
                }

                // If there are remaining parameters, create a new index for the next parameter
                FilterValueSetParam parameterPickedForIndex = remainingParameters.First;
                remainingParameters.Remove(parameterPickedForIndex);

                FilterParamIndexBase _nextIndex = IndexFactory.CreateIndex(
                    eventType,
                    parameterPickedForIndex.PropertyName,
                    parameterPickedForIndex.FilterOperator);

                index[filterForValue] = _nextIndex;
                treePathInfo.Add(_nextIndex, parameterPickedForIndex.FilterForValue);
                AddToIndex(_nextIndex, parameterPickedForIndex.FilterForValue, treePathInfo);
            }
        }

        /// <summary>Add filter callback to an event evaluator, which could be either an index node or a set node.</summary>
        /// <param name="eventEvaluator">to add the filterHandle to.</param>
        /// <param name="treePathInfo">is for holding the information on where the add occured</param>
        /// <returns>bool indicating if the eventEvaluator was successfully added</returns>

        private Boolean AddToEvaluator(EventEvaluator eventEvaluator, IndexTreePath treePathInfo)
        {
            if (eventEvaluator is FilterHandleSetNode)
            {
                FilterHandleSetNode node = (FilterHandleSetNode)eventEvaluator;
                AddToNode(node, treePathInfo);
                return true;
            }

            // Check if the next index matches any of the remaining filterHandle parameters
            FilterParamIndexBase nextIndex = (FilterParamIndexBase)eventEvaluator;

            FilterValueSetParam parameter = IndexHelper.FindParameter(remainingParameters, nextIndex);
            if (parameter != null)
            {
                remainingParameters.Remove(parameter);
                treePathInfo.Add(nextIndex, parameter.FilterForValue);
                AddToIndex(nextIndex, parameter.FilterForValue, treePathInfo);
                return true;
            }

            // This eventEvaluator does not work with any of the remaining filter parameters
            return false;
        }

        /// <summary>Copy the parameter list - this also sorts the parameter list.</summary>
        /// <param name="parameters">is a list of filter parameters</param>
        /// <returns>sorted set of filter parameters</returns>

        public static TreeSet<FilterValueSetParam> CopySortParameters(IList<FilterValueSetParam> parameters)
        {
            TreeSet<FilterValueSetParam> copy = new TreeSet<FilterValueSetParam>(new FilterValueSetParamComparator());

            foreach (FilterValueSetParam parameter in parameters)
            {
                copy.Add(parameter);
            }

            return copy;
        }

        private String PrintRemainingParameters()
        {
            StringBuilder buffer = new StringBuilder();

            int count = 0;
            foreach (FilterValueSetParam parameter in remainingParameters)
            {
                buffer.AppendFormat("  param({0})", count ) ;
                buffer.AppendFormat(" property={0}", parameter.PropertyName);
                buffer.AppendFormat(" operator={0}", parameter.FilterOperator);
                buffer.AppendFormat(" value={0}", parameter.FilterForValue);
                count++;
            }

            return buffer.ToString();
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
