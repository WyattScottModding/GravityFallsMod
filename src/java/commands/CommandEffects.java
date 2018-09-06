package commands;

import java.util.List;

import com.google.common.collect.Lists;

import init.PotionInit;
import main.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class CommandEffects extends CommandBase
{

	@Override
	public String getName()
	{
		return "effect";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "effect <player> <potion effect> <duration> <level>";	
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

		
		String potion = "freeze";
		int duration = 10;
		int level = 0;
		
		if(args[1] != null)
			potion = args[1];

		if(args[2] != null)
		{
			try
			{
				duration = Integer.parseInt(args[1]);
			}
			catch(NumberFormatException e)
			{
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Duration Invaild"));
				return;
			}
		}

		if(args[3] != null)
		{
			try
			{
				level = Integer.parseInt(args[2]);
			}
			catch(NumberFormatException e)
			{
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Level Invalid"));
				return;
			}
		}

		if(sender instanceof EntityPlayer)
		{
			if(potion.equals("freeze"))
			{	
					((EntityPlayer) sender).addPotionEffect(new PotionEffect(PotionInit.POTION_FREEZE, duration, level));
			}
		}
	}

}