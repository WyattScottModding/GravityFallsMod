package commands;

import java.util.List;

import com.google.common.collect.Lists;

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

public class CommandDimensionTeleport extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList(Reference.MODID, "tp", "tpdim", "tpdimension");

	@Override
	public String getName()
	{
		return "tpdimension";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "tpdimension <id>";	
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

		String s = args[0];
		int dimensionID;

		try
		{
			dimensionID = Integer.parseInt(s);
		}
		catch(NumberFormatException e)
		{
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Dimension ID Invalid"));
			return;
		}

		if(sender instanceof EntityPlayer)
		{
			if(dimensionID == 0)
			{
				if(((EntityPlayer) sender).getBedLocation() != null)
				{
					BlockPos pos = new BlockPos(((EntityPlayer) sender).getBedLocation());

					Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, pos.getX(), pos.getY(), pos.getZ());
				}
				else
					Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, 0, 70, 0);

			}
			else if(dimensionID == 1)
			{
				Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, -10, 63, 10);
			}
			else if(dimensionID == 2)
			{
				Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, -18.5, 62, -18.5);
			}
			else if(dimensionID == -1 || dimensionID == 3)
			{
				if(sender instanceof EntityPlayer)
					attemptTeleport((EntityPlayer)sender, server.getEntityWorld(), dimensionID);
			}	
			else
				Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, ((EntityPlayer) sender).posX, ((EntityPlayer) sender).posY, ((EntityPlayer) sender).posZ);
		}
	}
	
	public void attemptTeleport(EntityPlayer player, World world, int dimension)
	{
		int x = 0;
		int y = 70;
		int z = 0;
		BlockPos pos = new BlockPos(x, y, z);
		int counter = 0;

		while(world.getBlockState(pos).getBlock() != Blocks.AIR && world.getBlockState(pos.down()).getBlock() == Blocks.AIR)
		{
			x = (int) (Math.random() * 400) - 200;
			z = (int) (Math.random() * 400) - 200;

			pos = new BlockPos(x, y, z);

			if(counter > 150000)
			{
				counter = 0;
				y = (int) (Math.random() * 20) - 10;
			}
		}
		Teleport.teleportToDimension(player, dimension, x, y, z);
	}
}