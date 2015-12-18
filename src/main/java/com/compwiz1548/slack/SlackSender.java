package com.compwiz1548.slack;

import com.compwiz1548.slack.util.LogHelper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.command.ICommandSender;

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

    private void send(String s) throws IOException {
        HashMap<Object, Object> payloadToSend = Maps.newHashMap();
        payloadToSend.put("payload", s);

        requestFactory.buildPostRequest(new GenericUrl(url), new UrlEncodedContent(payloadToSend)).execute();
    }

    public void sendToSlack(ICommandSender sender, String text) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("text", text);
            json.addProperty("username", sender.getCommandSenderName());
            if (!(sender instanceof SlackCommandSender)) {
                json.addProperty("icon_url", "https://minotar.net/helm/" + sender.getCommandSenderName() + ".png");
            }
            send(json.toString());
            //LogHelper.info(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

