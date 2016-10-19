package com.compwiz1548.slack;

import com.compwiz1548.slack.reference.Settings;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SlackCommandSender implements ICommandSender
{
    private static final ICommandSender INSTANCE = new SlackCommandSender(false);
    private boolean broadcastResult;

    public SlackCommandSender(boolean broadcastResult)
    {
        this.broadcastResult = broadcastResult;
    }

    public static ICommandSender getInstance()
    {
        return INSTANCE;
    }

    public MinecraftServer getServer()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    @Override
    public String getName()
    {
        return Settings.serverName;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TextComponentString(this.getName());
    }

    @Override
    public void addChatMessage(ITextComponent component)
    {
        if (broadcastResult)
        {
            Slack.instance.getSlackSender().sendToSlack(this, component.getUnformattedText());
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(int permLevel, String commandName)
    {
        return true;
    }

    @Override
    public BlockPos getPosition()
    {
        return new BlockPos(0, 0, 0);
    }

    @Override
    public Vec3d getPositionVector()
    {
        return new Vec3d(0.0D, 0.0D, 0.0D);
    }

    @Override
    public World getEntityWorld()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().worldServers[0];
    }

    @Override
    public Entity getCommandSenderEntity()
    {
        return null;
    }

    @Override
    public boolean sendCommandFeedback()
    {
        return false;
    }

    @Override
    public void setCommandStat(CommandResultStats.Type type, int amount)
    {

    }
}

