package com.espertech.esperio.http.core;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.UnsupportedEncodingException;

public class URIUtil {

    public static URI withQuery(URI uri, String... params) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < params.length; i += 2) {
            String key = params[i];
            String val = i + 1 < params.length ? params[i + 1] : "";
            map.put(key, val);
        }
        return withQuery(uri, map);
    }

    public static URI withQuery(URI uri, Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        char separator = '?';
        for (Map.Entry<String, String> param : params.entrySet()) {
            query.append(separator);
            separator = '&';
            try {
                query.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                if (param.getValue().length() != 0) {
                    query.append('=');
                    query.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return URI.create(uri.toString() + query.toString());
    }
}
