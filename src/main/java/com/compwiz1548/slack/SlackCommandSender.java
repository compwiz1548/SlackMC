package com.compwiz1548.slack;

import com.compwiz1548.slack.reference.Settings;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
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
    public String getCommandSenderName() {
        return Settings.serverName;
    }

    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
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
    public ChunkCoordinates getPlayerCoordinates() {
        return null;
    }

    @Override
    public World getEntityWorld() {
        return null;
    }
}

