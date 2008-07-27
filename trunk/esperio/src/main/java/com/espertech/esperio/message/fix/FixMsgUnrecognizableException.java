package com.espertech.esperio.message.fix;

public class FixMsgUnrecognizableException extends FixMsgParserException
{
    public FixMsgUnrecognizableException(String message, String fixMsgText) {
        super(message + " for message text '" + fixMsgText + "'");
    }

    public FixMsgUnrecognizableException(String message) {
        super(message);
    }
}