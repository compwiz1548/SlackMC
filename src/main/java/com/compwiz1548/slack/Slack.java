package com.compwiz1548.slack;

import com.compwiz1548.slack.command.CommandSlack;
import com.compwiz1548.slack.handler.ConfigurationHandler;
import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Reference;
import com.compwiz1548.slack.reference.Settings;
import com.compwiz1548.slack.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javafx.util.Pair;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Slack {
    @Instance(Reference.MOD_ID)
    public static Slack instance;
    public boolean senderConnected = false;
    public boolean receiverConnected = false;
    private SlackSender slackSender;
    private SlackReceiver slackReceiver;

    @NetworkCheckHandler
    public boolean networkCheckHandler(Map<String, String> map, Side side) {
        return true;
    }

    @EventHandler
    private void preInit(FMLPreInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            ConfigurationHandler.init(event.getSuggestedConfigurationFile());
            ServerActivityListener listener = new ServerActivityListener();
            FMLCommonHandler.instance().bus().register(listener);
            MinecraftForge.EVENT_BUS.register(listener);
            slackConnect();
        } else {
            LogHelper.warn(StatCollector.translateToLocal(Messages.General.CLIENT_SIDE));
        }
    }

    public Pair<Boolean, String> slackConnect() {
        try {
            if (slackSender != null) {
                slackSender.sendToSlack(SlackCommandSender.getInstance(), StatCollector.translateToLocal(Messages.General.SERVER_DISCONNECTED));
                this.slackSender = null;
                senderConnected = false;
            }
            this.slackSender = new SlackSender(new URL(Settings.webhookURL));
            senderConnected = true;
            slackSender.sendToSlack(SlackCommandSender.getInstance(), String.format(StatCollector.translateToLocal(Messages.General.SERVER_CONNECTED), Reference.VERSION));
        } catch (IOException e) {
            if (Settings.webhookURL.equals("")) {
                return new Pair<Boolean, String>(false, StatCollector.translateToLocal(Messages.General.NO_URL));
            } else {
                return new Pair<Boolean, String>(false, StatCollector.translateToLocal(Messages.General.INCORRECT_WEBHOOK));
            }
        }
        try {
            try {
                this.slackReceiver.setEnabled(false);
                receiverConnected = false;
            } catch (NullPointerException e) {
                //Do nothing (no previous receiver)
            }
            this.slackReceiver = new SlackReceiver(Settings.port, Settings.token, Settings.slackServerFormat);
            this.slackReceiver.setEnabled(true);
            receiverConnected = true;
        } catch (IOException e) {
            return new Pair<Boolean, String>(false, e.getStackTrace().toString());
        }
        return new Pair<Boolean, String>(true, "");
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSlack());
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void onServerStopping(FMLServerStoppingEvent event) {
        if (senderConnected) {
            slackSender.sendToSlack(SlackCommandSender.getInstance(), StatCollector.translateToLocal(Messages.General.SERVER_STOPPED));
        }
    }

    public SlackSender getSlackSender() {
        return this.slackSender;
    }
}

