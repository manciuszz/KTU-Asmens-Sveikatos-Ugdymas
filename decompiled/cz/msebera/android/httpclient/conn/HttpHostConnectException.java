package cz.msebera.android.httpclient.conn;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.Arrays;

@Immutable
public class HttpHostConnectException extends ConnectException {
    private static final long serialVersionUID = -3194482710275220224L;
    private final HttpHost host;

    @Deprecated
    public HttpHostConnectException(HttpHost host, ConnectException cause) {
        this(cause, host, null);
    }

    public HttpHostConnectException(IOException cause, HttpHost host, InetAddress... remoteAddresses) {
        StringBuilder append = new StringBuilder().append("Connect to ").append(host != null ? host.toHostString() : "remote host");
        String str = (remoteAddresses == null || remoteAddresses.length <= 0) ? "" : MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Arrays.asList(remoteAddresses);
        append = append.append(str);
        if (cause == null || cause.getMessage() == null) {
            str = " refused";
        } else {
            str = " failed: " + cause.getMessage();
        }
        super(append.append(str).toString());
        this.host = host;
        initCause(cause);
    }

    public HttpHost getHost() {
        return this.host;
    }
}
