package com.espertech.esper.client.deploy;

/**
 * Exception thrown when an EPL text could not be parsed.
 */
public class ParseException extends Exception {

    /**
     * Ctor.
     * @param message error message
     */
    public ParseException(String message) {
        super(message);
    }
}
