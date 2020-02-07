package commands;

import java.util.List;

import com.google.common.collect.Lists;

import main.Reference;
import net.minecraft.block.Block;
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
					Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, 0, 90, 0);

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
					Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, findPos(server.getEntityWorld()));
			}	
			else
				Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, ((EntityPlayer) sender).posX, ((EntityPlayer) sender).posY, ((EntityPlayer) sender).posZ);
		}
	}
	
	private BlockPos findPos(World world)
	{
		int x = (int) ((Math.random() * 200) - 100);
		int z = (int) ((Math.random() * 200) - 100);

		int y = 100;
		boolean foundGround = false;

		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			Block blockUp = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();
			Block blockDown = world.getBlockState(new BlockPos(x, y - 1, z)).getBlock();

			if(block == Blocks.AIR && blockUp == Blocks.AIR && blockDown != Blocks.AIR) {
				foundGround = true;
			}
		}

		if(foundGround)
			return new BlockPos(x, y, z);
		else
			return findPos(world);
	}	
}