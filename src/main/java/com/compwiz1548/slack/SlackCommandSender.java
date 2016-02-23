package com.compwiz1548.slack;

import com.compwiz1548.slack.reference.Settings;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SlackCommandSender implements ICommandSender {
    private static final ICommandSender INSTANCE = new SlackCommandSender(false);
    private boolean broadcastResult;

    public SlackCommandSender(boolean broadcastResult) {
        this.broadcastResult = broadcastResult;
    }

    public static ICommandSender getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return Settings.serverName;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {
        if (broadcastResult) {
            Slack.instance.getSlackSender().sendToSlack(this, p_145747_1_.getUnformattedText());
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
        return true;
    }

    @Override
    public BlockPos getPosition() {
        return new BlockPos(0, 0, 0);
    }

    @Override
    public Vec3 getPositionVector() {
        return new Vec3(0.0D, 0.0D, 0.0D);
    }

    @Override
    public World getEntityWorld() {
        return MinecraftServer.getServer().worldServers[0];
    }

    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }

    @Override
    public boolean sendCommandFeedback() {
        return false;
    }

    @Override
    public void setCommandStat(CommandResultStats.Type type, int amount) {

    }
}

