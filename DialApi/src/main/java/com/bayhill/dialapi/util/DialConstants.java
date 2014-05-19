package com.bayhill.dialapi.util;

/**
 * Created by Emil on 2014-05-19.
 */
public class DialConstants {

    private static final String NEWLINE = "\r\n";

    /* Broadcast address and port */
    public static final String BROADCAST_SERVER_ADDRESS = "239.255.255.250";
    public static final int BROADCAST_SERVER_PORT = 1900;

    /* Definitions of search targets */
    private static final String SL_MSEARCH = "M-SEARCH * HTTP/1.1";
    public static final String ST_DIAL = "urn:dial-multiscreen-org:service:dial:1";

    /* Definition of MAN */
    private static final String MAN_DISCOVER = "\"ssdp:discover\"";

    /* Definition of headers */
    public static final String HEADER_HOST = "HOST";
    public static final String HEADER_MAN = "MAN";
    public static final String HEADER_ST = "ST";
    public static final String HEADER_MX = "MX";

    /* Definition of MX */
    private static final int MX = 4;

    /* Complete M-SEARCH message */
    public static String M_SEARCH =
            SL_MSEARCH + NEWLINE +
                    HEADER_HOST + ":" + BROADCAST_SERVER_ADDRESS + ":" + BROADCAST_SERVER_PORT + NEWLINE +
                    HEADER_MAN + ":" + MAN_DISCOVER + NEWLINE +
                    HEADER_ST + ":" + ST_DIAL + NEWLINE +
                    HEADER_MX + ":" + MX + NEWLINE + NEWLINE;
}
