using System;

namespace net.esper.util
{
    /// <summary>
    ///  General pupose callback to Stop a resource and free it's underlying resources.
    /// </summary>

    public interface StopCallback
    {
        /// <summary> Stops the underlying resources.</summary>

        void Stop();
    }

    /// <summary>
    /// Delegate that is used for wrapping the StopCallback interface.
    /// </summary>

    public delegate void StopCallbackDelegate() ;

    /// <summary>
    /// Proxy implementation for StopCallback
    /// </summary>

    public class StopCallbackImpl : StopCallback
    {
        private StopCallbackDelegate stopDelegate;

        /// <summary>
        /// Initializes a new instance of the <see cref="StopCallbackImpl"/> class.
        /// </summary>
        /// <param name="stopDelegate">The stop delegate.</param>
        public StopCallbackImpl(StopCallbackDelegate stopDelegate)
        {
            this.stopDelegate = stopDelegate;
        }

        /// <summary>
        /// Stops the underlying resources.
        /// </summary>
        public void Stop()
        {
            stopDelegate();
        }
    }
}
