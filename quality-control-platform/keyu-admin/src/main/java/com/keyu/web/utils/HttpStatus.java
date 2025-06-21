package com.keyu.web.utils;

/**
 * Constants enumerating the HTTP status codes.
 * All status codes defined in RFC1945 (HTTP/1.0), RFC2616 (HTTP/1.1), and
 * RFC2518 (WebDAV) are listed.
 *
 * @see StatusLine
 *
 * @since 4.0
 */
public class HttpStatus {

    private HttpStatus() {
        throw new IllegalStateException("Utility class");
    }

    // --- 2xx Success ---

    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    public static final int SC_OK = 200;
    /** {@code 201 Created} (HTTP/1.0 - RFC 1945) */
    public static final int SC_CREATED = 201;
    /** {@code 202 Accepted} (HTTP/1.0 - RFC 1945) */
    public static final int SC_ACCEPTED = 202;

    /** {@code 404 Not Found} (HTTP/1.0 - RFC 1945) */
    public static final int SC_NOT_FOUND = 404;
    /** {@code 405 Method Not Allowed} (HTTP/1.1 - RFC 2616) */
    public static final int SC_METHOD_NOT_ALLOWED = 405;
    /** {@code 417 Expectation Failed} (HTTP/1.1 - RFC 2616) */
    public static final int SC_EXPECTATION_FAILED = 417;

    // --- 5xx Server Error ---

    /** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    public static final int SC_INTERNAL_SERVER_ERROR = 500;

}
