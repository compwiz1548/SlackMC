package com.compwiz1548.slack.command;

import com.compwiz1548.slack.handler.ConfigurationHandler;
import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Names;
import com.compwiz1548.slack.reference.Settings;
import com.compwiz1548.slack.util.LogHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandFormat extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return Names.Settings.FORMAT;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender)
    {
        return Messages.Commands.FORMAT_COMMAND_USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender commandSender, String[] args)
    {

        if (args.length < 2)
        {
            if (commandSender instanceof EntityPlayerMP)
            {
                commandSender.addChatMessage(new TextComponentString(String.format(Messages.Commands.FORMAT_COMMAND_CURRENT, Settings.format)));
            }
            LogHelper.info(String.format(Messages.Commands.FORMAT_COMMAND_CURRENT, Settings.format));

        }
        else if (args[1].equals("reset"))
        {
            boolean success = ConfigurationHandler.setFormat("<%s> %s");
            if (success)
            {
                if (commandSender instanceof EntityPlayerMP)
                {
                    commandSender.addChatMessage(new TextComponentString(Messages.Commands.FORMAT_COMMAND_RESET));
                }
                LogHelper.info(Messages.Commands.FORMAT_COMMAND_RESET);
            }
        }
        else
        {
            String format = "";
            for (int i = 1; i < args.length; i++)
            {
                format = format + " " + args[i];
            }
            boolean success = ConfigurationHandler.setFormat(format.trim());
            if (success)
            {
                if (commandSender instanceof EntityPlayerMP)
                {
                    commandSender.addChatMessage(new TextComponentString(String.format(Messages.Commands.FORMAT_COMMAND_SUCCESS, Settings.format)));
                }
                LogHelper.info(String.format(Messages.Commands.FORMAT_COMMAND_SUCCESS, Settings.format));
            }
        }
    }
}
