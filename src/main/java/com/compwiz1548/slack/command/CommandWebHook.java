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

public class CommandWebHook extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return Names.Settings.URL;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender)
    {
        return Messages.Commands.URL_COMMAND_USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender commandSender, String[] args)
    {
        if (args.length < 2)
        {
            if (commandSender instanceof EntityPlayerMP)
            {
                commandSender.addChatMessage(new TextComponentString(Messages.Commands.URL_COMMAND_USAGE));
            }
            LogHelper.info(Messages.Commands.URL_COMMAND_USAGE);
        }
        else if (args[1].equals("reset"))
        {
            boolean success = ConfigurationHandler.setWebhookURL("");
            if (success)
            {
                if (commandSender instanceof EntityPlayerMP)
                {
                    commandSender.addChatMessage(new TextComponentString(Messages.Commands.URL_COMMAND_RESET));
                }
                LogHelper.info(Messages.Commands.URL_COMMAND_RESET);
            }
        }
        else if (!args[1].startsWith("https://hooks.slack.com/services/"))
        {
            if (commandSender instanceof EntityPlayerMP)
            {
                commandSender.addChatMessage(new TextComponentString(Messages.General.INCORRECT_WEBHOOK));
            }
            LogHelper.info(Messages.General.INCORRECT_WEBHOOK);
        }
        else
        {
            boolean success = ConfigurationHandler.setWebhookURL(args[1]);
            if (success)
            {
                if (commandSender instanceof EntityPlayerMP)
                {
                    commandSender.addChatMessage(new TextComponentString(String.format(Messages.Commands.URL_COMMAND_SUCCESS, Settings.webhookURL)));
                }
                LogHelper.info(String.format(Messages.Commands.URL_COMMAND_SUCCESS, Settings.webhookURL));
            }
        }
    }
}
