package items;

import org.lwjgl.opengl.GL11;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class InfinitySidedDie extends ItemBow implements IHasModel{

	public int timeSkyLight = 0;
	public boolean countSkyLight = false;
	public World world = null;
	public int cooldown = 20;

	public InfinitySidedDie(String name)
	{
		this.setMaxStackSize(1);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(cooldown == 0)
		{
			ItemStack itemstack = playerIn.getHeldItem(handIn);

			this.world = worldIn;

			ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, false);
			if (ret != null) 
				return ret;


			onPlayerStoppedUsing(itemstack, worldIn, playerIn, 10);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(ItemInit.INFINITY_SIDED_DIE));

		}		

		return new ActionResult<ItemStack>(EnumActionResult.FAIL, new ItemStack(ItemInit.INFINITY_SIDED_DIE));

	}


	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(cooldown > 0)
			cooldown--;

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		world = worldIn;
		if(entityLiving instanceof EntityPlayer && cooldown == 0)
		{
			EntityPlayer playerIn = (EntityPlayer) entityLiving;

			int diceRoll = (int) (Math.random() * 45) + 1;

			if(diceRoll == 1)
			{
				worldIn.setSeaLevel(80);
			}
			else if(diceRoll == 2)
			{
				playerIn.changeDimension(-1);
			}
			else if(diceRoll == 3)
			{
				playerIn.setFire(20);
			}
			else if(diceRoll == 4)
			{
				playerIn.setAbsorptionAmount(30.0F);
			}
			else if(diceRoll == 5)
			{
				double randx = Math.random() * 2000 - 1000;
				double randz = Math.random() * 2000 - 1000;

				playerIn.setLocationAndAngles(playerIn.posX + randx, playerIn.posY, playerIn.posZ + randz, playerIn.cameraYaw, playerIn.cameraPitch);
			}
			else if(diceRoll == 6)
			{
				playerIn.addExperience(1000);
			}
			else if(diceRoll == 7)
			{
				playerIn.addExperience(100);
			}
			else if(diceRoll == 8)
			{
				playerIn.addItemStackToInventory(new ItemStack(ItemInit.PINE_HAT));
			}
			else if(diceRoll == 9)
			{
				playerIn.addItemStackToInventory(new ItemStack(Items.DIAMOND_HELMET));
			}
			else if(diceRoll == 10)
			{
				playerIn.addItemStackToInventory(new ItemStack(Blocks.GOLD_BLOCK));
			}
			else if(diceRoll == 11)
			{
				worldIn.setWorldTime(0);
			}
			else if(diceRoll == 12)
			{
				playerIn.spawnSweepParticles();
				playerIn.spawnSweepParticles();
				playerIn.spawnSweepParticles();
				playerIn.spawnSweepParticles();
			}
			else if(diceRoll == 13)
			{
				playerIn.respawnPlayer();
			}
			else if(diceRoll == 14)
			{
				playerIn.addExhaustion(10.0F);
			}
			else if(diceRoll == 15)
			{
				playerIn.addItemStackToInventory(new ItemStack(Blocks.DIRT));
			}
			else if(diceRoll == 16)
			{
				playerIn.addItemStackToInventory(new ItemStack(Blocks.BLUE_GLAZED_TERRACOTTA));
			}
			else if(diceRoll == 17)
			{
				playerIn.world.spawnEntity(new EntityHorse(worldIn));
			}
			else if(diceRoll == 18)
			{
				playerIn.world.spawnEntity(new EntityCreeper(worldIn));
			}
			else if(diceRoll == 19)
			{
				playerIn.world.spawnEntity(new EntityWitch(worldIn));
			}
			else if(diceRoll == 20)
			{
				playerIn.world.spawnEntity(new EntityZombie(worldIn));
			}
			else if(diceRoll == 21)
			{
				playerIn.changeDimension(0);
			}
			else if(diceRoll == 22)
			{
				double rand = Math.random() * (playerIn.posY - 2);

				playerIn.setLocationAndAngles(playerIn.posX, playerIn.posY - rand, playerIn.posZ, playerIn.cameraYaw, playerIn.cameraPitch);
			}
			else if(diceRoll == 23)
			{
				ITextComponent msg = new TextComponentString("You rolled an 8!");
				playerIn.sendMessage(msg);
			}
			else if(diceRoll == 24)
			{
				playerIn.addItemStackToInventory(new ItemStack(Blocks.IRON_BLOCK));
			}
			else if(diceRoll == 25)
			{
				playerIn.addItemStackToInventory(new ItemStack(Blocks.EMERALD_BLOCK));
			}
			else if(diceRoll == 26)
			{
				playerIn.addItemStackToInventory(new ItemStack(Items.DIAMOND_HOE));
			}
			else if(diceRoll == 27)
			{
				ITextComponent msg = new TextComponentString("Bill is not to be trusted");
				playerIn.sendMessage(msg);
			}
			else if(diceRoll == 28)
			{
				int roll = (int)(Math.random() * 9999999);
				ITextComponent msg = new TextComponentString("You rolled a: " + Integer.toString(roll));
				playerIn.sendMessage(msg);
			}
			else if(diceRoll == 29)
			{
				ITextComponent msg = new TextComponentString("This dice is illegal, you will be arrested if you are seen carrying it.");
				playerIn.sendMessage(msg);
			}
			else if(diceRoll == 30)
			{
				playerIn.setArrowCountInEntity(30);
			}
			else if(diceRoll == 31)
			{
				BlockPos posFeet = playerIn.getPosition();
				playerIn.world.setBlockState(posFeet, Blocks.LAVA.getDefaultState());
			}
			else if(diceRoll == 32)
			{
				playerIn.bedLocation.add(1000, 20, 1000);
			}
			else if(diceRoll == 33)
			{
				BlockPos posFeet = playerIn.getPosition();
				BlockPos posHead = playerIn.getPosition().add(0, 1, 0);
				BlockPos posFront = playerIn.getPosition().add(0, 1, -1);
				BlockPos posBehind = playerIn.getPosition().add(-1, 1, 0);
				BlockPos posLeft = playerIn.getPosition().add(0, 1, 1);
				BlockPos posRight = playerIn.getPosition().add(1, 1, 0);
				BlockPos posBelow = playerIn.getPosition().add(0, -1, 0);
				BlockPos posAbove = playerIn.getPosition().add(0, 2, 0);
				BlockPos posCorner1 = playerIn.getPosition().add(1, 1, 1);
				BlockPos posCorner2 = playerIn.getPosition().add(-1, 1, 1);
				BlockPos posCorner3 = playerIn.getPosition().add(1, 1, -1);
				BlockPos posCorner4 = playerIn.getPosition().add(-1, 1, -1);

				playerIn.world.setBlockState(posFeet, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posHead, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posFront, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posBehind, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posLeft, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posRight, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posBelow, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posAbove, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posCorner1, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posCorner2, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posCorner3, Blocks.OBSIDIAN.getDefaultState());
				playerIn.world.setBlockState(posCorner4, Blocks.OBSIDIAN.getDefaultState());
			}
			else if(diceRoll == 34)
			{
				BlockPos posBelow1 = playerIn.getPosition().add(0, -1, 0);
				BlockPos posBelow2 = playerIn.getPosition().add(0, -2, 0);
				BlockPos posBelow3 = playerIn.getPosition().add(0, -3, 0);
				BlockPos posBelow4 = playerIn.getPosition().add(0, -4, 0);
				BlockPos posBelow5 = playerIn.getPosition().add(0, -5, 0);
				BlockPos posBelow6 = playerIn.getPosition().add(0, -6, 0);
				BlockPos posBelow7 = playerIn.getPosition().add(0, -7, 0);
				BlockPos posBelow8 = playerIn.getPosition().add(0, -8, 0);
				BlockPos posBelow9 = playerIn.getPosition().add(0, -9, 0);
				BlockPos posBelow10 = playerIn.getPosition().add(0, -10, 0);

				worldIn.setBlockToAir(posBelow1);
				worldIn.setBlockToAir(posBelow2);
				worldIn.setBlockToAir(posBelow3);
				worldIn.setBlockToAir(posBelow4);
				worldIn.setBlockToAir(posBelow5);
				worldIn.setBlockToAir(posBelow6);
				worldIn.setBlockToAir(posBelow7);
				worldIn.setBlockToAir(posBelow8);
				worldIn.setBlockToAir(posBelow9);
				worldIn.setBlockToAir(posBelow10);
			}
			else if(diceRoll == 35)
			{
				worldIn.setRainStrength(10.0F);
			}
			else if(diceRoll == 36)
			{
				worldIn.setSkylightSubtracted(100);
				countSkyLight = true;

			}
			else if(diceRoll == 37)
			{
				worldIn.setThunderStrength(10.0F);
			}
			else if(diceRoll == 38)
			{
				EntityZombie zombie = new EntityZombie(worldIn);
				worldIn.spawnEntity(zombie);
				worldIn.spawnEntity(zombie);
				worldIn.spawnEntity(zombie);
				worldIn.spawnEntity(zombie);
				worldIn.spawnEntity(zombie);
			}
			else if(diceRoll == 39)
			{
				EntityWitch witch = new EntityWitch(worldIn);
				worldIn.spawnEntity(witch);
				worldIn.spawnEntity(witch);
				worldIn.spawnEntity(witch);
			}
			else if(diceRoll == 40)
			{
				GL11.glScalef(10, 10, 10);
			}
			else if(diceRoll == 41)
			{
				GL11.glScalef(0.1F, 0.1F, 0.1F);
			}
			else if(diceRoll == 42)
			{
				world.setSkylightSubtracted(5);
			}
			else if(diceRoll == 43)
			{
				playerIn.addExperienceLevel(100);
			}
			else if(diceRoll == 44)
			{
				GL11.glViewport(5, 5, 10, 10);
			}
			else if(diceRoll == 45)
			{
				int y = (int)(Math.random() * 200);
				playerIn.setPosition(playerIn.posX, y, playerIn.posZ);
			}

			cooldown = 20;

		}
	}
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
}