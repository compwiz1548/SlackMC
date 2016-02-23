package com.compwiz1548.slack.handler;

import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Settings;
import com.compwiz1548.slack.util.Pair;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class ConfigurationHandler {
    public static Configuration configuration;
    private static Pair<String, String> webhookURL;
    private static Pair<String, String> port;
    private static Pair<String, String> token;
    private static Pair<String, String> format;
    private static Pair<String, String> ops;
    private static Pair<String, String> serverName;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        webhookURL = new Pair<String, String>(StatCollector.translateToLocal(Messages.Config.WEBHOOK_URL_KEY), StatCollector.translateToLocal(Messages.Config.WEBHOOK_URL_COMMENT));
        port = new Pair<String, String>(StatCollector.translateToLocal(Messages.Config.PORT_KEY), StatCollector.translateToLocal(Messages.Config.PORT_COMMENT));
        token = new Pair<String, String>(StatCollector.translateToLocal(Messages.Config.TOKEN_KEY), StatCollector.translateToLocal(Messages.Config.TOKEN_COMMENT));
        format = new Pair<String, String>(StatCollector.translateToLocal(Messages.Config.FORMAT_KEY), StatCollector.translateToLocal(Messages.Config.FORMAT_COMMENT));
        ops = new Pair<String, String>(StatCollector.translateToLocal(Messages.Config.OPS_KEY), StatCollector.translateToLocal(Messages.Config.OPS_COMMENT));
        serverName = new Pair<String, String>(StatCollector.translateToLocal(Messages.Config.NAME_KEY), StatCollector.translateToLocal(Messages.Config.NAME_COMMENT));

        Settings.webhookURL = configuration.getString(webhookURL.getKey(), Configuration.CATEGORY_GENERAL, "", webhookURL.getValue());
        Settings.port = configuration.getInt(port.getKey(), Configuration.CATEGORY_GENERAL, 25127, 1024, 65535, port.getValue());
        Settings.token = configuration.getString(token.getKey(), Configuration.CATEGORY_GENERAL, "", token.getValue());
        Settings.format = configuration.getString(format.getKey(), Configuration.CATEGORY_GENERAL, "<%s> %s", format.getValue());
        Settings.ops = new LinkedList<String>(Arrays.asList(configuration.getStringList(ops.getKey(), Configuration.CATEGORY_GENERAL, new String[]{"example1", "example2"}, ops.getValue())));
        Settings.serverName = configuration.getString(serverName.getKey(), Configuration.CATEGORY_GENERAL, "[Server]", serverName.getValue());

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean setWebhookURL(String url) {
        Settings.webhookURL = url;
        configuration.get(Configuration.CATEGORY_GENERAL, webhookURL.getKey(), "", webhookURL.getValue()).set(Settings.webhookURL);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setToken(String newToken) {
        Settings.token = newToken;
        configuration.get(Configuration.CATEGORY_GENERAL, token.getKey(), "", token.getValue()).set(Settings.token);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setPort(int newPort) {
        Settings.port = newPort;
        configuration.get(Configuration.CATEGORY_GENERAL, port.getKey(), 25127, port.getValue()).set(Settings.port);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setFormat(String newFormat) {
        Settings.format = newFormat;
        configuration.get(Configuration.CATEGORY_GENERAL, format.getKey(), "<%s> %s", format.getValue()).set(Settings.format);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean addOP(String username) {
        Settings.ops.add(username);
        configuration.get(Configuration.CATEGORY_GENERAL, ops.getKey(), "<%s> %s", ops.getValue()).set(Settings.ops.toArray(new String[0]));

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean delOP(String username) {
        int index = Settings.ops.indexOf(username);
        if (index < 0) {
            return false;
        }
        Settings.ops.remove(index);
        configuration.get(Configuration.CATEGORY_GENERAL, ops.getKey(), "<%s> %s", ops.getValue()).set(Settings.ops.toArray(new String[0]));

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static void reloadAll() {
        loadConfiguration();
    }
}
