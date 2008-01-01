package net.esper.antlr;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;

import java.io.IOException;
import java.io.Reader;

public class NoCaseSensitiveStream extends ANTLRReaderStream
{
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
