package com.espertech.esperio.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;
import java.util.LinkedHashMap;

import com.espertech.esperio.http.core.URIUtil;

public class SupportHTTPClient {
    private static Log log = LogFactory.getLog(SupportHTTPClient.class);

    private final int port;
    private HttpClient httpclient;

    public SupportHTTPClient(int port) {
        this.port = port;
        httpclient = new DefaultHttpClient();
    }

    public void request(int port, String document, String... params) throws Exception {
        String uri = "http://localhost:" + port + "/" + document;
        URI requestURI = URIUtil.withQuery(new URI(uri), params);
        log.info("Requesting from URI " + requestURI);
        HttpGet httpget = new HttpGet(requestURI);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = null;
        try {
            responseBody = httpclient.execute(httpget, responseHandler);
        } catch (IOException e) {
            throw new RuntimeException("Error executing request:" + e.getMessage());
        }
    }


}
