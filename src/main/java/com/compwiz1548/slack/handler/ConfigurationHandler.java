package com.compwiz1548.slack.handler;

import com.compwiz1548.slack.reference.Settings;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
        Settings.webhookURL = configuration.getString("webhook-url", Configuration.CATEGORY_GENERAL, "<url-here>", "Webhook from Slack.");
        Settings.ip = configuration.getString("server-ip", Configuration.CATEGORY_GENERAL, "0.0.0.0", "This server's IP");
        Settings.port = configuration.getInt("port", Configuration.CATEGORY_GENERAL, 25017, 1024, 65535, "Port to listen for incoming Slack conection.");
        Settings.token = configuration.getString("token", Configuration.CATEGORY_GENERAL, "", "Token from Slack");
        Settings.slackServerFormat = configuration.getString("slack-server-format", Configuration.CATEGORY_GENERAL, "(%s) %s", "Format to use for incoming Slack messages");
        Settings.channel = configuration.getString("channels", Configuration.CATEGORY_GENERAL, "", "Channel to send to");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase("SlackMC")) {
            loadConfiguration();
        }
    }
}
