// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.IO;

namespace net.esper.support.util
{
	public class SimpleByteArrayOutputStream : OutputStream
	{
	    protected byte[] buf = null;
	    protected int size = 0;

	    public SimpleByteArrayOutputStream() {
	        This(5 * 1024);
	    }

	    public SimpleByteArrayOutputStream(int initSize) {
	        this.size = 0;
	        this.buf = new byte[initSize];
	    }

	    /**
	     * Ensures that we have a large enough buffer for the given size.
	     */
	    private void VerifyBufferSize(int sz) {
	        if (sz > buf.length) {
	            byte[] old = buf;
	            buf = new byte[Math.Max(sz, 2 * buf.length )];
	            Array.Copy(old, 0, buf, 0, old.length);
	            old = null;
	        }
	    }

	    public int GetSize() {
	        return size;
	    }

	    public byte[] GetByteArray() {
	        return buf;
	    }

	    public void Write(byte[] b) {
	        VerifyBufferSize(size + b.length);
	        Array.Copy(b, 0, buf, size, b.length);
	        size += b.length;
	    }

	    public void Write(byte[] b, int off, int len) {
	        VerifyBufferSize(size + len);
	        Array.Copy(b, off, buf, size, len);
	        size += len;
	    }

	    public void Write(int b) {
	        VerifyBufferSize(size + 1);
	        buf[size++] = (byte) b;
	    }

	    public void Reset() {
	        size = 0;
	    }

	    public InputStream GetInputStream() {
	        return new SimpleByteArrayInputStream(buf, size);
	    }

	}
} // End of namespace
