package items;

import org.lwjgl.input.Keyboard;

import handlers.ArmorDetector;
import handlers.KeyBindings;
import handlers.SoundsHandler;
import init.BlockInit;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.Messages;

public class Book3 extends ItemWrittenBook implements IHasModel{

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public Book3(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public boolean isEnchantable(ItemStack stack) 
	{
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
		
		if(gameSettings.keyBindSprint.isKeyDown())
		{
			if(player != null && player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.BOOK3))) {
				player.openGui(GravityFalls.instance, ConfigHandler.GUI_JOURNAL3, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		IBlockState state = BlockInit.Book3.getDefaultState().withProperty(FACING, player.getHorizontalFacing());

		IBlockState block1 = world.getBlockState(pos);
		IBlockState block2 = world.getBlockState(pos.up());

		if(player.isCreative())
		{
			if(block1.getBlock() != Blocks.AIR && block2.getBlock() == Blocks.AIR && block1.getBlock() != BlockInit.PORTAL_CONTROL)
			{
				if(block1.isFullBlock())
				{
					world.setBlockState(pos.up(), state);
				}
			}
		}
		else
		{
			if(block1.getBlock() != Blocks.AIR && block2.getBlock() == Blocks.AIR && block1.getBlock() != BlockInit.PORTAL_CONTROL)
			{
				if(block1.isFullBlock())
				{
					world.setBlockState(pos.up(), state);
					player.getHeldItemMainhand().shrink(1);
				}
			}
		}

		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		boolean raiseDead = false;
		boolean isDown = false;
		boolean playSound = false;

		if(nbt.hasKey("raiseDead"))
			raiseDead = nbt.getBoolean("raiseDead");
		if(nbt.hasKey("isDown"))
			isDown = nbt.getBoolean("isDown");
		if(nbt.hasKey("playSound"))
			playSound = nbt.getBoolean("playSound");

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			if(world.isDaytime())
				raiseDead = false;

			if(isSelected && !raiseDead) {
				if(KeyBindings.BATTERY.isDown())
				{
					if(!isDown) {
						if(world.getDifficulty().equals(EnumDifficulty.PEACEFUL))
						{
							ITextComponent msg = new TextComponentString("You must not be in peaceful mode to raise the dead.");
							player.sendMessage(msg);
						}
						else if(world.isDaytime())
						{
							ITextComponent msg = new TextComponentString("You can only summon the dead at night.");
							player.sendMessage(msg);
						}
						else
						{
							//RaiseDeadUpdate.nbt.setBoolean("raiseDead", true);
							raiseDead = true;
							playSound = true;
							ITextComponent msg = new TextComponentString("You feel a sharp chill go down your spine.");
							player.sendMessage(msg);
						}

						isDown = true;
					}
				}
				else
					isDown = false;
			}

			if(raiseDead) {
				raiseDead(player, world);
			}
		}
		else if(entity instanceof EntityPlayer && playSound) {
			EntityPlayer player = (EntityPlayer) entity;

			world.playSound(player, player.getPosition(), SoundsHandler.ITEM_RAISEDEAD, SoundCategory.VOICE, 1.0F, 1.0F);
			playSound = false;
		}
		nbt.setBoolean("raiseDead", raiseDead);
		nbt.setBoolean("isDown", isDown);
		nbt.setBoolean("playSound", playSound);
	}

	public void raiseDead(EntityPlayer player, World world)
	{
		//Timer based on Difficulty
		boolean zombieSpawn = false;


		if(world.getDifficulty().equals(EnumDifficulty.PEACEFUL))
			zombieSpawn = false;
		else if(world.getDifficulty().equals(EnumDifficulty.EASY))
		{
			int random = (int)(Math.random() * 50);

			if(random == 0)
				zombieSpawn = true;
		}
		else if(world.getDifficulty().equals(EnumDifficulty.NORMAL))
		{
			int random = (int)(Math.random() * 40);

			if(random == 0)
				zombieSpawn = true;
		}
		else if(world.getDifficulty().equals(EnumDifficulty.HARD))
		{
			int random = (int)(Math.random() * 30);

			if(random == 0)
				zombieSpawn = true;
		}


		if(zombieSpawn)
		{
			int x = (int) ((Math.random() * 80) - 40 + player.posX);
			int z = (int) ((Math.random() * 80) - 40 + player.posZ);
			int y = findGround(world, x, (int) player.posY, z);

			if(y >= 0)
			{
				BlockPos pos1 = new BlockPos(x, y + 1, z);
				BlockPos pos2 = new BlockPos(x, y + 2, z);

				Block block1 = world.getBlockState(pos1).getBlock();
				Block block2 = world.getBlockState(pos2).getBlock();

				if(block1 == Blocks.AIR && block2 == Blocks.AIR)
				{
					EntityZombie zombie = new EntityZombie(world);
					zombie.setPosition(x, y + 1, z);

					world.spawnEntity(zombie);			
				}
			}
		}
		zombieSpawn = false;
	}

	private int findGround(World world, int x, int playerY, int z)
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


	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return false;
	}
}