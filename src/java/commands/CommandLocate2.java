package commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import handlers.RegistryHandler;
import main.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class CommandLocate2 extends CommandBase
{
	@Override
	public String getName()
	{
		return "locate";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) 
	{
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length < 1)
			return;

		String s = args[0];

		if(s.equals("Bunker"))
		{
			if(RegistryHandler.nbt.hasKey("Bunker"))
			{
				String string = RegistryHandler.nbt.getString("Bunker");
				sender.sendMessage(new TextComponentString(string));
			}
		}
		else if(s.equals("MysteryShack"))
		{
			if(RegistryHandler.nbt.hasKey("MysteryShack"))
			{
				String string = RegistryHandler.nbt.getString("MysteryShack");
				sender.sendMessage(new TextComponentString(string));
			}
		}
		else if(s.equals("Journal3"))
		{
			if(RegistryHandler.nbt.hasKey("Journal3"))
			{
				String string = RegistryHandler.nbt.getString("Journal3");
				sender.sendMessage(new TextComponentString(string));
			}
		}
		else if(s.equals("UFO"))
		{
			if(RegistryHandler.nbt.hasKey("UFO"))
			{
				String string = RegistryHandler.nbt.getString("UFO");
				sender.sendMessage(new TextComponentString(string));
			}
		}
	}


	public String getUsage(ICommandSender sender)
	{
		return "commands.locate.usage";
	}

	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"Bunker", "MysteryShack", "Journal3", "UFO"}) : Collections.emptyList();
	}
}