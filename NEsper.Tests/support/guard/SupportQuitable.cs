using System;

using net.esper.pattern.guard;

namespace net.esper.support.guard
{
    public class SupportQuitable : Quitable
    {
        virtual public int GetAndResetQuitCounter()
        {
            return quitCounter;
        }

        public int quitCounter = 0;

        public virtual void GuardQuit()
        {
            quitCounter++;
        }
    }
}