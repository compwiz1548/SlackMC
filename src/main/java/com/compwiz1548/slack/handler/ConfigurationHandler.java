package com.compwiz1548.slack.handler;

import com.compwiz1548.slack.reference.Settings;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {
    public static Configuration configuration;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        Settings.webhookURL = configuration.getString("webhook-url", Configuration.CATEGORY_GENERAL, "", "URL from Slack Incoming Webhook.");
        Settings.port = configuration.getInt("port", Configuration.CATEGORY_GENERAL, 25127, 1024, 65535, "Port to listen for incoming Slack connection.");
        Settings.token = configuration.getString("token", Configuration.CATEGORY_GENERAL, "", "Token from Slack Outgoing Webhook.");
        Settings.slackServerFormat = configuration.getString("slack-server-format", Configuration.CATEGORY_GENERAL, "(%s) %s", "Format to use for incoming Slack messages");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean setWebhookURL(String url) {
        Settings.webhookURL = url;
        configuration.get(Configuration.CATEGORY_GENERAL, "webhook-url", "", "URL from Slack Incoming Webhook.").set(url);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setToken(String token) {
        Settings.token = token;
        configuration.get(Configuration.CATEGORY_GENERAL, "token", "", "Token from Slack Outgoing Webhook.").set(token);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static boolean setPort(int port) {
        Settings.port = port;
        configuration.get(Configuration.CATEGORY_GENERAL, "Port", 25127, "Port to listen for incoming Slack connection.").set(port);

        if (configuration.hasChanged()) {
            configuration.save();
        }
        return true;
    }

    public static void reloadAll() {
        loadConfiguration();
    }
}
