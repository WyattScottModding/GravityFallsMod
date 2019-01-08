package updates;

import handlers.MyWorldData;
import handlers.PortalBlocks;
import handlers.SoundsHandler;
import init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class PortalUpdate
{
	public static NBTTagCompound nbt = new NBTTagCompound();

	public static boolean returnDevice = false;

	public static PortalBlocks portalBlocks = null;

	private static boolean setBlocks = false;
	
	public static boolean checkActive = false;
	public static int countdown = -1;
	
	
	public static void init(World world, EntityPlayer player)
	{			
		portalRender(player, world);

		if(!nbt.hasKey("portalActive"))
			nbt.setBoolean("portalActive", false);

		if(!nbt.hasKey("portalControl"))
			nbt.setBoolean("portalControl", false);
		
		
	}
	
	public static void initEntityLiving(EntityLivingBase entityLivingBase)
	{
		levitationRender(entityLivingBase);
	}
	
	public static void playSound(EntityPlayer player, World world)
	{
		if(countdown == 660)
			world.playSound(player, player.getPosition(), SoundsHandler.PORTAL_WORKING, SoundCategory.BLOCKS, 5.0F, 1.0F);
		else if(countdown == 1)
			world.playSound(player, player.getPosition(), SoundsHandler.PORTAL_FINISHED, SoundCategory.BLOCKS, 5.0F, 1.0F);
	}
	
	public static void portalRender(EntityPlayer player, World world)
	{		
		if(nbt.getBoolean("portalActive"))
		{
			if(countdown > 0)
				countdown--;
			else if(countdown == 0)
				portalOn(world);

			if(portalBlocks != null)
			{
				Block block = world.getBlockState(portalBlocks.getLeverPos()).getBlock();

				if(block != BlockInit.PORTAL_LEVER)
					removePortal(world);
			}
		}
		else
		{
			removePortal(world);
		}
	}	

	public static void levitationRender(EntityLivingBase entity)
	{
		if(nbt.getBoolean("portalActive"))
		{
			if(countdown < 35500 && countdown > 35460)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 27000 && countdown > 26880)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 20000 && countdown > 19820)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 14000 && countdown > 13760)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 8000 && countdown > 7700)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 6000 && countdown > 5680)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 4000 && countdown > 3660)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 3000 && countdown > 2640)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 2000 && countdown > 1620)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 1500 && countdown > 1100)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 1000 && countdown > 0)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));

		}
	}

	public static void portalOn(World world)
	{
		if(portalBlocks != null)
		{			
			nbt.setInteger("PortalX", portalBlocks.getPortal().get(0).getX() + 5);
			nbt.setInteger("PortalY", portalBlocks.getPortal().get(0).getY() - 4);
			nbt.setInteger("PortalZ", portalBlocks.getPortal().get(0).getZ());

			IBlockState state =  BlockInit.BLOCKTELEPORTER.getDefaultState();

			for(BlockPos element : portalBlocks.getPortal())
			{
				world.setBlockState(element, state);
			}
		}
	}

	public static void setPortal(World world)
	{
		if(portalBlocks != null && !setBlocks)
		{
			IBlockState state =  BlockInit.LIGHT_WHITE.getDefaultState();

			for(BlockPos element : portalBlocks.getTriangle())
			{
				world.setBlockState(element, state);
			}

			state =  BlockInit.LIGHT_BLACK.getDefaultState();

			for(BlockPos element : portalBlocks.getRing())
			{
				world.setBlockState(element, state);
			}

			state =  BlockInit.LIGHT_CYAN.getDefaultState();

			for(BlockPos element : portalBlocks.getCircle())
			{
				world.setBlockState(element, state);
			}

			setBlocks = true;
		}
	}

	public static void removePortal(World world)
	{
		if(portalBlocks != null && setBlocks)
		{
			IBlockState state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);

			for(BlockPos element : portalBlocks.getTriangle())
			{
				world.setBlockState(element, state);
			}

			state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK);

			for(BlockPos element : portalBlocks.getRing())
			{
				world.setBlockState(element, state);
			}

			state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN);

			for(BlockPos element : portalBlocks.getCircle())
			{
				world.setBlockState(element, state);
			}

			state =  Blocks.AIR.getDefaultState();

			for(BlockPos element : portalBlocks.getPortal())
			{
				world.setBlockState(element, state);
			}
			setBlocks = false;
		}
	}
	
	public static int getCountdown()
	{
		return countdown;
	}

}
