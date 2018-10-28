package commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import handlers.RegistryHandler;
import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandLocate;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import worldgen.WorldGenCustomStructures;

public class CommandLocate2 extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList(Reference.MODID, "locateGravity", "locateGravityFalls");
	private final List<String> STRUCTURES = new ArrayList();

	@Override
	public String getName()
	{
		STRUCTURES.add("mysteryshack");
		STRUCTURES.add("ufo");
		STRUCTURES.add("bunker");
		STRUCTURES.add("journal3");

		return "locateFalls";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "locateFalls <string>";	
	}

	@Override
	public List<String> getAliases()
	{
		return aliases;
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

		System.out.println("Execute");
		
		String s = args[0];

		BlockPos pos = null;

		if(RegistryHandler.structures != null)
		{
			pos = RegistryHandler.structures.locate(sender.getEntityWorld(), s, sender.getPosition());
			
			if(pos.getX() == -1 && pos.getY() == -1 && pos.getZ() == -1)
			{
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Structure Not Found"));
				return;
			}
			
			sender.sendMessage(new TextComponentString("The structure " + s + " is located at: " + pos.toString()));
			
			System.out.println("Structure: " + pos.toString());
			return;
		}
		else
		{
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Not Able To Execute"));
			return;
		}

	}


	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, STRUCTURES): Collections.<String>emptyList();
	}

}