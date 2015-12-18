package com.compwiz1548.slack;

import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Settings;
import com.compwiz1548.slack.util.LogHelper;
import com.compwiz1548.slack.util.Utils;
import com.google.common.io.ByteStreams;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class SlackReceiver implements HttpHandler {
    private final String token;
    private final String format;
    private HttpServer server;
    private MinecraftServer mcServer;

    public SlackReceiver() throws IOException {
        this.token = Settings.token;
        this.format = Settings.format;
        InetSocketAddress address = new InetSocketAddress(Settings.port);
        this.server = HttpServer.create(address, 0);
        this.server.createContext("/", this);
        this.server.setExecutor(Executors.newCachedThreadPool());
        this.mcServer = FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.server.start();
        } else {
            this.server.stop(0);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("POST")) {
            this.handleSlackMessage(exchange);
        }
    }

    private void handleSlackMessage(HttpExchange exchange) throws IOException {
        byte[] bytes = ByteStreams.toByteArray((InputStream) exchange.getRequestBody());
        String fromBytes = new String(bytes, "UTF-8");
        String[] contents = fromBytes.split(Pattern.quote("&"));
        HashMap<String, String> map = new HashMap<String, String>();
        for (String s : contents) {
            String[] array = s.split(Pattern.quote("="));
            if (array.length != 2) continue;
            map.put(array[0], array[1]);
        }
        String token = map.get("token");
        if (!(token != null && this.token.equals(token))) {
            return;
        }
        String username = map.get("user_name");
        if (username != null && username.equals("slackbot")) {
            return;
        }
        String text = map.get("text");
        if (text == null) {
            return;
        }

        LogHelper.info(text);
        text = URLDecoder.decode(text, "UTF-8");
        text = StringEscapeUtils.unescapeXml(text); //Fix for < and >

        if (text.startsWith("/")) {
            return;
        } else if (text.startsWith("!")) {
            text = text.substring(1); //Get rid of the !
            if (text.equals("who") || text.equals("players")) {
                String playerList = Utils.getPlayerList(mcServer);
                if (playerList.equals("")) {
                    Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), StatCollector.translateToLocal(Messages.General.NO_ONE_ONLINE));
                } else {
                    Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), String.format(StatCollector.translateToLocal(Messages.General.CURRENTLY_ONLINE), playerList));
                }
                return;
            } else {
                if (Settings.ops.contains(username)) {
                    mcServer.getCommandManager().executeCommand(new SlackCommandSender(true), text);
                    LogHelper.info(String.format("Executed command \"%s\" from Slack", text));
                }
                else
                    Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), StatCollector.translateToLocal(Messages.General.PERMISSION_DENIED));
                return;
            }
        }
        FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendChatMsg(new ChatComponentText(EnumChatFormatting.BOLD + "" + EnumChatFormatting.AQUA + "[Slack] " + EnumChatFormatting.RESET + String.format(format, username, text)));
    }

}

