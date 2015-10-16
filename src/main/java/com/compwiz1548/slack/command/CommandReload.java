package com.compwiz1548.slack.command;

import com.compwiz1548.slack.Slack;
import com.compwiz1548.slack.handler.ConfigurationHandler;
import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Names;
import com.compwiz1548.slack.util.LogHelper;
import com.compwiz1548.slack.util.Pair;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class CommandReload extends CommandBase {
    @Override
    public String getCommandName() {
        return Names.Commands.RELOAD;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return Messages.Commands.RELOAD_COMMAND_USAGE;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        ConfigurationHandler.reloadAll();
        Pair<Boolean, String> success = Slack.instance.slackConnect();
        if (success.getKey()) {
            if (commandSender instanceof EntityPlayerMP) {
                commandSender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(Messages.Commands.RELOAD_COMMAND_SUCCESS)));
            }
            LogHelper.info(StatCollector.translateToLocal(Messages.Commands.RELOAD_COMMAND_SUCCESS));
        } else {
            if (commandSender instanceof EntityPlayerMP) {
                commandSender.addChatMessage(new ChatComponentText(success.getValue()));
            }
            LogHelper.info(success.getValue());
        }
    }
}
