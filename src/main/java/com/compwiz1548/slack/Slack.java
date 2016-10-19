package com.compwiz1548.slack;

import com.compwiz1548.slack.command.CommandSlack;
import com.compwiz1548.slack.handler.ConfigurationHandler;
import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Reference;
import com.compwiz1548.slack.reference.Settings;
import com.compwiz1548.slack.util.LogHelper;
import com.compwiz1548.slack.util.Pair;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Slack
{
    @Instance(Reference.MOD_ID)
    public static Slack instance;
    public boolean senderConnected = false;
    public boolean receiverConnected = false;
    private SlackSender slackSender;
    private SlackReceiver slackReceiver;

    @NetworkCheckHandler
    public boolean networkCheckHandler(Map<String, String> map, Side side)
    {
        return true;
    }

    @EventHandler
    private void preInit(FMLPreInitializationEvent event)
    {
        if (event.getSide() == Side.SERVER)
        {
            ConfigurationHandler.init(event.getSuggestedConfigurationFile());
            ServerActivityListener listener = new ServerActivityListener();
            MinecraftForge.EVENT_BUS.register(listener);
        }
        else
        {
            LogHelper.warn(Messages.General.CLIENT_SIDE);
        }
    }

    public Pair<Boolean, String> slackConnect()
    {
        try
        {
            if (slackSender != null)
            {
                slackSender.sendToSlack(SlackCommandSender.getInstance(), Messages.General.SERVER_DISCONNECTED);
                this.slackSender = null;
                senderConnected = false;
            }
            this.slackSender = new SlackSender(new URL(Settings.webhookURL));
            senderConnected = true;
            slackSender.sendToSlack(SlackCommandSender.getInstance(), String.format(Messages.General.SERVER_CONNECTED, Reference.VERSION));
        }
        catch (IOException e)
        {
            if (Settings.webhookURL.equals(""))
            {
                return new Pair<Boolean, String>(false, Messages.General.NO_URL);
            }
            else
            {
                return new Pair<Boolean, String>(false, Messages.General.INCORRECT_WEBHOOK);
            }
        }
        try
        {
            try
            {
                this.slackReceiver.setEnabled(false);
                receiverConnected = false;
            }
            catch (NullPointerException e)
            {
                //Do nothing (no previous receiver)
            }
            this.slackReceiver = new SlackReceiver();
            this.slackReceiver.setEnabled(true);
            receiverConnected = true;
        }
        catch (IOException e)
        {
            return new Pair<Boolean, String>(false, e.getStackTrace().toString());
        }
        return new Pair<Boolean, String>(true, "");
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void onServerStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandSlack());
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void onServerStarted(FMLServerStartedEvent event)
    {
        slackConnect();
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void onServerStopping(FMLServerStoppingEvent event)
    {
        if (senderConnected)
        {
            slackSender.sendToSlack(SlackCommandSender.getInstance(), Messages.General.SERVER_STOPPED);
        }
    }

    public SlackSender getSlackSender()
    {
        return this.slackSender;
    }
}

