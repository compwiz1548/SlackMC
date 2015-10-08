package com.compwiz1548.slack;

import com.compwiz1548.slack.handler.ConfigurationHandler;
import com.compwiz1548.slack.reference.Settings;
import com.compwiz1548.slack.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Mod(modid = "SlackMC", name = "SlackMC", version = "1.0")
public class Slack {
    @Instance("SlackMC")
    public static Slack instance;
    private SlackSender slackReceiver;
    private SlackReceiver slackSender;

    @NetworkCheckHandler
    public boolean networkCheckHandler(Map<String, String> map, Side side) {
        return true;
    }

    @EventHandler
    private void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        ServerActivityListener listener = new ServerActivityListener();
        FMLCommonHandler.instance().bus().register(listener);
        MinecraftForge.EVENT_BUS.register(listener);

        if (Settings.webhookURL.equals("<url here>")) {
            LogHelper.log(Level.WARN, "No URL specified! Go to the config and specify one, then run '/slack reload'");
            return;
        }
        try {
            this.slackReceiver = new SlackSender(new URL(Settings.webhookURL));
        } catch (IOException e) {
            LogHelper.log(Level.WARN, (e.getMessage()));
        }
        try {
            this.slackSender = new SlackReceiver(Settings.port, Settings.token, Settings.slackServerFormat);
            this.slackSender.setEnabled(true);
        } catch (IOException e) {
            LogHelper.log(Level.WARN, (e.getMessage()));
        }
    }

    @EventHandler
    public void fmlLifeCycle(FMLServerStartedEvent event) {
        Slack.instance.sendToSlack(SlackCommandSender.getInstance(), "Server started.");
    }

    @EventHandler
    public void fmlLifeCycle(FMLServerStoppingEvent event) {
        Slack.instance.sendToSlack(SlackCommandSender.getInstance(), "Server stopping.");
    }

    public void sendToSlack(ICommandSender sender, String text) {
        try {
            JSONObject json = new JSONObject();
            json.put("text", text);
            json.put("username", sender.getCommandSenderName());
            if (!(sender instanceof SlackCommandSender)) {
                json.put("icon_url", "https://minotar.net/avatar/" + sender.getCommandSenderName() + ".png");
            }
            instance.slackReceiver.send(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

