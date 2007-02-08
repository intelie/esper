using System;

namespace net.esper.util
{
    /// <summary> General pupose callback to Stop a resource and free it's underlying resources.</summary>

    public interface StopCallback
    {
        /// <summary> Stops the underlying resources.</summary>

        void Stop();
    }

    public delegate void StopCallbackDelegate() ;

    public class StopCallbackImpl : StopCallback
    {
        private StopCallbackDelegate stopDelegate;

        public StopCallbackImpl(StopCallbackDelegate StopDelegate)
        {
            this.stopDelegate = stopDelegate;
        }

        public void Stop()
        {
            stopDelegate();
        }
    }
}