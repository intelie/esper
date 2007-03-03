/*
 * Created on Apr 23, 2006
 *
 */

using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;

using net.esper.example.transaction;

namespace net.esper.example.transaction.sim
{
    /** Outputs events using a bucket, whose contents are shuffled.
     * 
     * @author Hans Gilde
     *
     */
    public class ShuffledBucketOutput
    {
        private static readonly Random random = RandomUtil.GetNewInstance();

        private EventSource eventSource;
        private OutputStream outputStream;
        private int bucketSize;

        /**
         * @param eventSource
         * @param outputStream
         * @param bucketSize how many events should be in the bucket when it's shuffled?
         */
        public ShuffledBucketOutput(EventSource eventSource, OutputStream outputStream, int bucketSize)
        {
            this.eventSource = eventSource;
            this.outputStream = outputStream;
            this.bucketSize = bucketSize;
        }

        public void Output()
        {
            List<TxnEventBase> bucket = new List<TxnEventBase>(bucketSize);

            foreach (TxnEventBase e in eventSource)
            {
                bucket.Add(e);
                if (bucket.Count == bucketSize)
                {
                    OutputBucket(bucket);
                }
            }

            if (bucket.Count > 0)
            {
                OutputBucket(bucket);
            }
        }

        private void OutputBucket(List<TxnEventBase> bucket)
        {
            Collections.shuffle(bucket, random);
            outputStream.Output(bucket);
            bucket.Clear();
        }
    }
}
