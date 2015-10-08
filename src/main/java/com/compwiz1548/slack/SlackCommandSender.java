package com.compwiz1548.slack;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class SlackCommandSender implements ICommandSender {
    private static final ICommandSender INSTANCE = new SlackCommandSender();

    private SlackCommandSender() {
    }

    public static ICommandSender getInstance() {
        return INSTANCE;
    }

    @Override
    public String getCommandSenderName() {
        return "[Server]";
    }

    @Override
    public IChatComponent func_145748_c_() {
        return null;
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {

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

