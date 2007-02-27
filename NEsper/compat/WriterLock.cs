using System;
using System.Collections.Generic;
using System.Threading;

namespace net.esper.compat
{
    public class WriterLock : IDisposable
    {
        private ReaderWriterLock m_lockObj;

        public WriterLock(ReaderWriterLock lockObj)
        {
            this.m_lockObj = lockObj;
            this.m_lockObj.AcquireWriterLock(LockConstants.ReaderTimeout);
        }

        public void Dispose()
        {
            this.m_lockObj.ReleaseWriterLock();
        }
    }
}
