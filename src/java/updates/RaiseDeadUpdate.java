package updates;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class RaiseDeadUpdate
{
	public static NBTTagCompound nbt = new NBTTagCompound();

	public static boolean zombieMessage = false;

	public static void init(World world, EntityPlayer player)
	{
		raiseDead(player);
	}



	public static void raiseDead(EntityPlayer player)
	{
		if(player.world.isDaytime())
		{
			nbt.setBoolean("raiseDead", false);
			zombieMessage = false;			
		}

		//Timer based on Difficulty
		boolean zombieSpawn = false;


		if(player.world.getDifficulty().equals(EnumDifficulty.PEACEFUL))
			zombieSpawn = false;
		else if(player.world.getDifficulty().equals(EnumDifficulty.EASY))
		{
			int random = (int)(Math.random() * 50);

			if(random == 0)
				zombieSpawn = true;
		}
		else if(player.world.getDifficulty().equals(EnumDifficulty.NORMAL))
		{
			int random = (int)(Math.random() * 40);

			if(random == 0)
				zombieSpawn = true;
		}
		else if(player.world.getDifficulty().equals(EnumDifficulty.HARD))
		{
			int random = (int)(Math.random() * 30);

			if(random == 0)
				zombieSpawn = true;
		}



		if(nbt.getBoolean("raiseDead") && !player.world.isRemote && zombieSpawn)
		{
			if(!zombieMessage)
			{
				ITextComponent msg = new TextComponentString("You feel a sharp chill go down your spine.");
				player.sendMessage(msg);
				zombieMessage = true;
			}

			int x = (int) ((Math.random() * 80) - 40 + player.posX);
			int z = (int) ((Math.random() * 80) - 40 + player.posZ);
			int y = findGround(player.world, x, (int) player.posY, z);

			if(y >= 0)
			{
				BlockPos pos1 = new BlockPos(x, y + 1, z);
				BlockPos pos2 = new BlockPos(x, y + 2, z);

				Block block1 = player.world.getBlockState(pos1).getBlock();
				Block block2 = player.world.getBlockState(pos2).getBlock();

				if(block1 == Blocks.AIR && block2 == Blocks.AIR)
				{

					EntityZombie zombie = new EntityZombie(player.world);
					zombie.setPosition(x, y + 1, z);

					player.world.spawnEntity(zombie);			
				}
			}
		}
		zombieSpawn = false;
	}

	private static int findGround(World world, int x, int playerY, int z)
	{
		int y = playerY + 15;
		boolean foundGround = false;

		while(!foundGround && y-- >= 0)
		{
			if(y < (playerY - 15))
				return -1;

			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = (block != Blocks.AIR && block != Blocks.TALLGRASS && block != Blocks.WATER  && block != Blocks.LAVA  && block != Blocks.FLOWING_WATER  && block != Blocks.FLOWING_LAVA);
		}

		if(foundGround)
			return y;

		return -1;
	}
}