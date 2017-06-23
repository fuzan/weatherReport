package http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class WeatherServer extends NanoHTTPD {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServer.class.getName());

    public WeatherServer(int port) {
        super(port);
    }

    public WeatherServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        final Method httpMethod = session.getMethod();
        final Map<String, String> headers = session.getHeaders();
        final String uri = session.getUri();
        final String queries = session.getQueryParameterString();
        LOG.info("{}, {}, {}, {}", httpMethod, headers, uri, queries);

        return super.serve(session);
    }

    public static void main(String[] args) {

    }
}
