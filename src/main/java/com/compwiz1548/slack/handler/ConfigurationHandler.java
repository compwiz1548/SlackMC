package com.compwiz1548.slack.handler;

import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Settings;
import javafx.util.Pair;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {
    public static Configuration configuration;
    private static Pair<String, String> webhookURL;
    private static Pair<String, String> port;
    private static Pair<String, String> token;
    private static Pair<String, String> format;

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

        Settings.webhookURL = configuration.getString(webhookURL.getKey(), Configuration.CATEGORY_GENERAL, "", webhookURL.getValue());
        Settings.port = configuration.getInt(port.getKey(), Configuration.CATEGORY_GENERAL, 25127, 1024, 65535, port.getValue());
        Settings.token = configuration.getString(token.getKey(), Configuration.CATEGORY_GENERAL, "", token.getValue());
        Settings.format = configuration.getString(format.getKey(), Configuration.CATEGORY_GENERAL, "<%s> %s", format.getValue());

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean setWebhookURL(String url) {
        Settings.webhookURL = url;
        configuration.get(Configuration.CATEGORY_GENERAL, webhookURL.getKey(), "", webhookURL.getValue()).set(url);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setToken(String newToken) {
        Settings.token = newToken;
        configuration.get(Configuration.CATEGORY_GENERAL, token.getKey(), "", token.getValue()).set(newToken);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setPort(int newPort) {
        Settings.port = newPort;
        configuration.get(Configuration.CATEGORY_GENERAL, port.getKey(), 25127, port.getValue()).set(newPort);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setFormat(String newFormat) {
        Settings.format = newFormat;
        configuration.get(Configuration.CATEGORY_GENERAL, format.getKey(), "<%s> %s", format.getValue()).set(newFormat);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static void reloadAll() {
        loadConfiguration();
    }
}
