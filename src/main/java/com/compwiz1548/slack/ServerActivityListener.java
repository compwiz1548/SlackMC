package com.compwiz1548.slack;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import org.apache.commons.lang3.StringUtils;

public class ServerActivityListener {
    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
        if (Slack.instance.senderConnected) {
            Slack.instance.getSlackSender().sendToSlack(event.player, event.message);
        }
    }

    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (Slack.instance.senderConnected) {
            Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.player.getGameProfile().getName() + " joined the game.");
        }
    }

    @SubscribeEvent
    public void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        if (Slack.instance.senderConnected) {
            Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.player.getGameProfile().getName() + " left the game.");
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            if (Slack.instance.senderConnected) {
                Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.entityLiving.func_110142_aN().func_151521_b().getUnformattedText() + ".");
            }
        }
    }

    @SubscribeEvent
    public void onAchievement(AchievementEvent event) {
        if (((EntityPlayerMP) event.entityPlayer).func_147099_x().hasAchievementUnlocked(event.achievement)) { // getStatFile
            // This is necessary because the Achievement event fires even if an achievement is already unlocked.
            return;
        }
        if (!((EntityPlayerMP) event.entityPlayer).func_147099_x().canUnlockAchievement(event.achievement)) { // getStatFile
            // This is necessary because the Achievement event fires even if an achievement can not be unlocked yet.
            return;
        }
        if (Slack.instance.senderConnected) {
            Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), event.entityPlayer.getGameProfile().getName() + " has earned the achievement [" + event.achievement.func_150951_e().getUnformattedText() + "]");
        }
    }

    @SubscribeEvent
    public void onServerCommand(CommandEvent event) {
        if (event.command instanceof CommandBroadcast) {
            if (Slack.instance.senderConnected)
                Slack.instance.getSlackSender().sendToSlack(SlackCommandSender.getInstance(), StringUtils.join(event.parameters, " "));
        }
    }
}

