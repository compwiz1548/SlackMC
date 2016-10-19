package com.compwiz1548.slack.command;

import com.compwiz1548.slack.reference.Messages;
import com.compwiz1548.slack.reference.Names;
import com.compwiz1548.slack.util.LogHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSlack extends CommandBase
{
    private static List<CommandBase> modCommands = new ArrayList<CommandBase>();
    private static List<String> commands = new ArrayList<String>();

    static
    {
        modCommands.add(new CommandReload());
        modCommands.add(new CommandWebHook());
        modCommands.add(new CommandToken());
        modCommands.add(new CommandPort());
        modCommands.add(new CommandFormat());
        modCommands.add(new CommandOP());

        for (CommandBase commandBase : modCommands)
        {
            commands.add(commandBase.getCommandName());
        }
    }

    @Override
    public String getCommandName()
    {
        return Names.Commands.BASE_COMMAND;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender)
    {
        return Messages.Commands.BASE_COMMAND_USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender commandSender, String[] args)
    {
        if (args.length >= 1)
        {
            for (CommandBase command : modCommands)
            {
                if (command.getCommandName().equalsIgnoreCase(args[0]) && command.checkPermission(server, commandSender))
                {
                    try
                    {
                        command.execute(server, commandSender, args);
                    }
                    catch (CommandException e)
                    {

                    }
                }
            }
        }
        else
        {
            if (commandSender instanceof EntityPlayerMP)
            {
                commandSender.addChatMessage(new TextComponentString(Messages.Commands.BASE_COMMAND_USAGE));
            }
            LogHelper.info(Messages.Commands.BASE_COMMAND_USAGE);
        }
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
    {
        return Collections.emptyList();
    }
}
