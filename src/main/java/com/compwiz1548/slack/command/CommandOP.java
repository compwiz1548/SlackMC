package com.compwiz1548.slack.command;

import com.compwiz1548.slack.handler.ConfigurationHandler;
import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Names;
import com.compwiz1548.slack.util.LogHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class CommandOP extends CommandBase {

    @Override
    public String getCommandName() {
        return Names.Settings.OP;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return Messages.Commands.OP_COMMAND_USAGE;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (args.length < 2) {
            if (commandSender instanceof EntityPlayerMP) {
                commandSender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(Messages.Commands.OP_COMMAND_USAGE)));
            }
            LogHelper.info(StatCollector.translateToLocal(Messages.Commands.OP_COMMAND_USAGE));
        } else if (args[1].equals("add")) {
            boolean success = ConfigurationHandler.addOP(args[2]);
            if (success) {
                if (commandSender instanceof EntityPlayerMP) {
                    commandSender.addChatMessage(new ChatComponentText(String.format(StatCollector.translateToLocal(Messages.Commands.OP_COMMAND_ADD_SUCCESS), args[2])));
                }
                LogHelper.info(String.format(StatCollector.translateToLocal(Messages.Commands.OP_COMMAND_ADD_SUCCESS), args[2]));
            }
        } else if (args[1].equals("remove")) {
            boolean success = ConfigurationHandler.delOP(args[2]);
            if (success) {
                if (commandSender instanceof EntityPlayerMP) {
                    commandSender.addChatMessage(new ChatComponentText(String.format(StatCollector.translateToLocal(Messages.Commands.OP_COMMAND_REMOVE_SUCCESS), args[2])));
                }
                LogHelper.info(String.format(StatCollector.translateToLocal(Messages.Commands.OP_COMMAND_REMOVE_SUCCESS), args[2]));
            }
        }
    }
}
