package com.compwiz1548.slack;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.Maps;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SlackSender {
    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
    private URL url;

    public SlackSender(URL url) {
        this.url = url;
    }

    public void send(String s) throws IOException {
        HashMap<Object, Object> payloadToSend = Maps.newHashMap();
        payloadToSend.put("payload", s);

        requestFactory.buildPostRequest(new GenericUrl(url), new UrlEncodedContent(payloadToSend)).execute();
    }
}

