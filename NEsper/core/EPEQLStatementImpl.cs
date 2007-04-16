using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;
using net.esper.events;
using net.esper.dispatch;
using net.esper.view;
using net.esper.eql.expression;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.core
{
    /// <summary> Statement implementation for EQL statements.
    /// <para>
    /// The statement Starts on construction.
    /// When listeners are added and removed from the view a child dispatch view is
    /// added/removed to/from the parent view to support push mode.
    /// </para>
    /// </summary>
    public class EPEQLStatementImpl : EPStatementSupport, EPStatement
    {
        /// <summary>
        /// Returns the underlying expression text or XML.
        /// </summary>
        /// <value></value>
        /// <returns> expression text
        /// </returns>
        virtual public String Text
        {
            get { return expressionText; }
        }

        /// <summary>
        /// Returns the type of events the iterable returns.
        /// </summary>
        /// <value></value>
        /// <returns> event type of events the iterator returns
        /// </returns>
        virtual public EventType EventType
        {
            get { return parentView.EventType; }
        }

        private readonly String expressionText;
        private readonly UpdateDispatchView dispatchChildView;
        private readonly EPEQLStmtStartMethod startMethod;

        private Viewable parentView;
        private EPStatementStopMethod stopMethod;

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="expressionText">expression</param>
        /// <param name="dispatchService">for dispatching</param>
        /// <param name="startMethod">to Start the view</param>

        public EPEQLStatementImpl(String expressionText, DispatchService dispatchService, EPEQLStmtStartMethod startMethod)
        {
            this.expressionText = expressionText;
            this.dispatchChildView = new UpdateDispatchView(this.Listeners, dispatchService);
            this.startMethod = startMethod;

            Start();
        }

        /// <summary>
        /// Stop the statement.
        /// </summary>
        public virtual void Stop()
        {
            if (stopMethod == null)
            {
                throw new SystemException("View statement already Stopped");
            }

            if (this.Listeners.Count > 0)
            {
                parentView.RemoveView(dispatchChildView);
            }

            stopMethod();
            stopMethod = null;
            parentView = null;
        }

        /// <summary>
        /// Start the statement.
        /// </summary>
        public virtual void Start()
        {
            if (stopMethod != null)
            {
                throw new SystemException("View statement already Started");
            }

            Pair<Viewable, EPStatementStopMethod> pair;

            try
            {
                pair = startMethod.Start();
            }
            catch (ExprValidationException ex)
            {
                log.Debug(".Start Error Starting view", ex);
                throw new EPStatementException("Error Starting view: " + ex.Message, expressionText);
            }
            catch (ViewProcessingException ex)
            {
                log.Debug(".Start Error Starting view", ex);
                throw new EPStatementException("Error Starting view: " + ex.Message, expressionText);
            }

            parentView = pair.First;
            stopMethod = pair.Second;

            if (this.Listeners.Count > 0)
            {
                parentView.AddView(dispatchChildView);
            }
        }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public virtual IEnumerator<EventBean> GetEnumerator()
        {
            // Return null if not Started
            if (parentView == null)
            {
                return null;
            }

            return parentView.GetEnumerator();
        }

        #region IEnumerable Members

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="T:System.Collections.IEnumerator"></see> object that can be used to iterate through the collection.
        /// </returns>
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        #endregion

        /// <summary>
        /// Called when the last listener is removed.
        /// </summary>
        public override void ListenerStop()
        {
            if (parentView != null)
            {
                parentView.RemoveView(dispatchChildView);
            }
        }

        /// <summary>
        /// Called when the first listener is added.
        /// </summary>
        public override void ListenerStart()
        {
            if (parentView != null)
            {
                parentView.AddView(dispatchChildView);
            }
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}