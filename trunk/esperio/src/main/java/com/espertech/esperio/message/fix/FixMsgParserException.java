package com.espertech.esperio.message.fix;

public class FixMsgParserException extends Exception
{
    public FixMsgParserException(String message) {
        super(message);
    }

    public FixMsgParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
