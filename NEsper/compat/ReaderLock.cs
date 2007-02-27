using System;
using System.Collections.Generic;
using System.Threading;

namespace net.esper.compat
{
    public class ReaderLock : IDisposable
    {
        private ReaderWriterLock m_lockObj;

        public ReaderLock(ReaderWriterLock lockObj)
        {
            this.m_lockObj = lockObj;
            this.m_lockObj.AcquireReaderLock(LockConstants.ReaderTimeout);
        }

        public void Dispose()
        {
            this.m_lockObj.ReleaseReaderLock();
        }
    }
}
