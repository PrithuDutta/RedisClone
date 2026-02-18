package com.Commands;
/**
 * Protocol class to format responses according to a simple protocol.
 * Supports integer, error, simple string, and bulk string responses.
 * Each response type is prefixed with a specific character and ends with CRLF.
 * For example:
 * - Integer: ":<number>\r\n"
 * - Error: "-<message>\r\n"
 * - Simple String: "+<message>\r\n"
 * - Bulk String: "$<length>\r\n<message>\r\n" (or
 * "$-1\r\n" for null)
 */
public final class Protocol {
    public static final String CRLF = "\r\n";

    private Protocol() {
        // Private constructor to prevent instantiation 
    }

    public static String integer(int value) {
        return ":" + value + CRLF;
    }

    public static String error(String message) {
        return "-" + message + CRLF;
    }

    public static String simpleString(String message) {
        return "+" + message + CRLF;
    }

    public static String bulkString(String message) {
        if (message == null) {
            return "$-1" + CRLF;
        }
        return "$" + message.length() + CRLF + message + CRLF;
    }
}