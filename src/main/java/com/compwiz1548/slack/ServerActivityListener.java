package com.compwiz1548.slack;

import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.commons.lang3.StringUtils;

public class ServerActivityListener
{
    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event)
    {
        if (Slack.instance.senderConnected)
        {
            Slack.instance.getSlackSender().sendToSlack(event.getPlayer(), event.getMessage());
        }
    }

    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (Slack.instance.senderConnected)
        {
            Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.player.getGameProfile().getName() + " joined the game.");
        }
    }

    @SubscribeEvent
    public void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if (Slack.instance.senderConnected)
        {
            Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.player.getGameProfile().getName() + " left the game.");
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            if (Slack.instance.senderConnected)
            {
                Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.getEntityLiving().getCombatTracker().getDeathMessage().getUnformattedText() + ".");
            }
        }
    }

    @SubscribeEvent
    public void onAchievement(AchievementEvent event)
    {
        if (((EntityPlayerMP) event.getEntityPlayer()).getStatFile().hasAchievementUnlocked(event.getAchievement()))
        { // getStatFile
            // This is necessary because the Achievement event fires even if an achievement is already unlocked.
            return;
        }
        if (!((EntityPlayerMP) event.getEntityPlayer()).getStatFile().canUnlockAchievement(event.getAchievement()))
        { // getStatFile
            // This is necessary because the Achievement event fires even if an achievement can not be unlocked yet.
            return;
        }
        if (Slack.instance.senderConnected)
        {
            Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.getEntityPlayer().getGameProfile().getName() + " has earned the achievement [" + event.getAchievement().getStatName().getUnformattedText() + "]");
        }
    }

    @SubscribeEvent
    public void onServerCommand(CommandEvent event)
    {
        if (event.getCommand() instanceof CommandBroadcast)
        {
            if (Slack.instance.senderConnected)
                Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), StringUtils.join(event.getParameters(), " "));
        }
    }
}

