package com.espertech.esper.antlr;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;

import java.io.IOException;
import java.io.Reader;

/**
 * For use with ANTLR to create a case-insensitive token stream.
 */
public class NoCaseSensitiveStream extends ANTLRReaderStream
{
    /**
     * Ctor.
     * @param reader is the reader providing the characters to inspect
     * @throws IOException to indicate IO errors
     */
    public NoCaseSensitiveStream(Reader reader)
            throws IOException
    {
        super(reader);
    }

	public int LA(int i) {
		if ( i==0 ) {
			return 0; // undefined
		}
		if ( i<0 ) {
			i++; // e.g., translate LA(-1) to use offset 0
		}
		if ( (p+i-1) >= n ) {
            return CharStream.EOF;
        }
        return Character.toLowerCase(data[p+i-1]);
    }
}
