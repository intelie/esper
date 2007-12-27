/*
 * Created on Apr 23, 2006
 *
 */
package net.esper.example.transaction.sim;

import net.esper.example.transaction.TxnEventBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** Outputs events using a bucket, whose contents are shuffled.
 * 
 * @author Hans Gilde
 *
 */
public class ShuffledBucketOutput {
    private static final Random random = RandomUtil.getNewInstance();
    
    private EventSource eventSource;
    private OutputStream outputStream;
    private int bucketSize;
    
    /**
     * @param eventSource
     * @param outputStream
     * @param bucketSize how many events should be in the bucket when it's shuffled?
     */
    public ShuffledBucketOutput(EventSource eventSource, OutputStream outputStream, int bucketSize) {
        this.eventSource = eventSource;
        this.outputStream = outputStream;
        this.bucketSize = bucketSize;
    }
    
    public void output() throws IOException {
        List<TxnEventBase> bucket = new ArrayList<TxnEventBase>(bucketSize);
                
        for (TxnEventBase e:eventSource) {
            bucket.add(e);
            if (bucket.size() == bucketSize) {
                outputBucket(bucket);
            }
        }
        
        if (bucket.size() > 0) {
            outputBucket(bucket);
        }
    }

    /**
     * @param bucket
     * @throws IOException
     */
    private void outputBucket(List<TxnEventBase> bucket) throws IOException {
        Collections.shuffle(bucket, random);
        outputStream.output(bucket);
        bucket.clear();
    }
}
