package items;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import entity.EntityEvilTree;
import init.ItemInit;
import main.ConfigHandler;
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
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class InfinitySidedDie extends Item implements IHasModel{

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
		ItemStack stack = playerIn.getHeldItemMainhand();

		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();
		
		int cooldown = 20;

		if(nbt.hasKey("cooldown"))
			cooldown = nbt.getInteger("cooldown");
		
		if(cooldown == 0 && !worldIn.isRemote)
		{
			ItemStack itemstack = playerIn.getHeldItem(handIn);

			boolean success = useDice(itemstack, worldIn, playerIn, cooldown);

			if(success)
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(ItemInit.INFINITY_SIDED_DIE));
			else
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, new ItemStack(ItemInit.INFINITY_SIDED_DIE));
		}

		return new ActionResult<ItemStack>(EnumActionResult.FAIL, new ItemStack(ItemInit.INFINITY_SIDED_DIE));
	}


	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();
		
		int cooldown = 20;
		
		if(nbt.hasKey("cooldown"))
			cooldown = nbt.getInteger("cooldown");
		
		if(cooldown > 0)
			cooldown--;
		
		nbt.setInteger("cooldown", cooldown);
		stack.setTagCompound(nbt);

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	
	public boolean useDice(ItemStack stack, World world, EntityLivingBase entityLiving, int cooldown)
	{
		if(entityLiving instanceof EntityPlayer && cooldown == 0)
		{
			EntityPlayer playerIn = (EntityPlayer) entityLiving;

			int diceRoll; 
			
			if(ConfigHandler.INFINITY_SIDED_DICE)
				diceRoll = (int) (Math.random() * 54) + 1;
			else
				diceRoll = (int) (Math.random() * 48) + 1;
			
			ITextComponent rollMessage = new TextComponentString("Dice Rolled");
			playerIn.sendMessage(rollMessage);

			Random rand = new Random();
			for(int countparticles = 1; countparticles <= 6; countparticles++)
			{
				world.spawnParticle(EnumParticleTypes.SPELL, playerIn.posX + (rand.nextDouble() - 0.5D) * (double)playerIn.width, playerIn.posY + rand.nextDouble() * (double)playerIn.height - (double)playerIn.getYOffset(), playerIn.posZ + (rand.nextDouble() - 0.5D) * (double)playerIn.width, 0.0D, 0.0D, 0.0D);
			}
			
			if(diceRoll == 1)
			{
				world.spawnEntity(new EntityEvilTree(world, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ));
			}
			else if(diceRoll == 2)
			{
				playerIn.stepHeight = 2;
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
				ITextComponent msg = new TextComponentString("You rolled an 8!");
				playerIn.sendMessage(msg);
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
				world.setWorldTime(0);
			}
			else if(diceRoll == 12)
			{
				int posX = (int) playerIn.posX;
				int posY = (int) playerIn.posY;
				int posZ = (int) playerIn.posZ;

				AxisAlignedBB bb = new AxisAlignedBB(posX - 25, posY - 25, posZ - 25, posX + 25, posY + 25, posZ + 25);
				List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(playerIn, bb);
				
				for(Entity element : list) {
					element.onKillCommand();
				}
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
				playerIn.world.spawnEntity(new EntityHorse(world));
			}
			else if(diceRoll == 18)
			{
				playerIn.world.spawnEntity(new EntityCreeper(world));
			}
			else if(diceRoll == 19)
			{
				playerIn.world.spawnEntity(new EntityWitch(world));
			}
			else if(diceRoll == 20)
			{
				playerIn.world.spawnEntity(new EntityZombie(world));
			}
			else if(diceRoll == 21)
			{
				playerIn.changeDimension(0);
			}
			else if(diceRoll == 22)
			{
				double random = Math.random() * (playerIn.posY - 2);

				playerIn.setLocationAndAngles(playerIn.posX, playerIn.posY - random, playerIn.posZ, playerIn.cameraYaw, playerIn.cameraPitch);
			}
			else if(diceRoll == 23)
			{
				playerIn.addItemStackToInventory(new ItemStack(ItemInit.PINE_HAT));
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
				playerIn.setPosition(playerIn.posX, playerIn.posY + 15, playerIn.posZ);
			}
			else if(diceRoll == 33)
			{
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

				world.setBlockToAir(posBelow1);
				world.setBlockToAir(posBelow2);
				world.setBlockToAir(posBelow3);
				world.setBlockToAir(posBelow4);
				world.setBlockToAir(posBelow5);
				world.setBlockToAir(posBelow6);
				world.setBlockToAir(posBelow7);
				world.setBlockToAir(posBelow8);
				world.setBlockToAir(posBelow9);
				world.setBlockToAir(posBelow10);
			}
			else if(diceRoll == 35)
			{
				EntityZombie zombie = new EntityZombie(world);
				world.spawnEntity(zombie);
				world.spawnEntity(zombie);
				world.spawnEntity(zombie);
				world.spawnEntity(zombie);
				world.spawnEntity(zombie);
			}
			else if(diceRoll == 36)
			{
				EntityWitch witch = new EntityWitch(world);
				world.spawnEntity(witch);
				world.spawnEntity(witch);
				world.spawnEntity(witch);
			}
			else if(diceRoll == 37)
			{
				playerIn.addExperienceLevel(100);
			}
			else if(diceRoll == 38)
			{
				int yPos = (int)(Math.random() * 200);
				playerIn.setPosition(playerIn.posX, yPos, playerIn.posZ);
			}
			else if(diceRoll == 39)
			{
				playerIn.performHurtAnimation();
			}
			else if(diceRoll == 40)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 6000, 0));
			}
			else if(diceRoll == 41)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.SPEED, 6000, 5));
			}
			else if(diceRoll == 42)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 6000, 5));
			}
			else if(diceRoll == 43)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.HASTE, 6000, 5));
			}
			else if(diceRoll == 44)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 5));
			}
			else if(diceRoll == 45)
			{
				ITextComponent msg = new TextComponentString("Ok Boomer.");
				playerIn.sendMessage(msg);
			}
			else if(diceRoll == 46)
			{
				ITextComponent msg = new TextComponentString("You now have ligma.");
				playerIn.sendMessage(msg);
			}
			else if(diceRoll == 47)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.POISON, 500, 1));
			}
			else if(diceRoll == 48)
			{
				int x = (int) playerIn.posX;
				int y = (int) playerIn.posY;
				int z = (int) playerIn.posZ;

				
				AxisAlignedBB bb = new AxisAlignedBB(x - 50, y - 50, z - 50, x + 50, y + 50, z + 50);
				List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
				
				int random = (int)(Math.random() * list.size());
				
				if(!(list.get(random) instanceof EntityPlayer)) {
					ITextComponent msg = new TextComponentString("You killed " + list.get(random).toString());
					playerIn.sendMessage(msg);
				}
				
				list.get(random).onKillCommand();
			}
			else if(diceRoll == 49 && ConfigHandler.INFINITY_SIDED_DICE)
			{
				world.setRainStrength(10.0F);
			}
			else if(diceRoll == 50 && ConfigHandler.INFINITY_SIDED_DICE)
			{
				world.setSkylightSubtracted(100);
			}
			else if(diceRoll == 51 && ConfigHandler.INFINITY_SIDED_DICE)
			{
				world.setThunderStrength(10.0F);
			}
			else if(diceRoll == 52 && ConfigHandler.INFINITY_SIDED_DICE)
			{
				GL11.glScalef(10, 10, 10);
			}
			else if(diceRoll == 53 && ConfigHandler.INFINITY_SIDED_DICE)
			{
				GL11.glScalef(0.1F, 0.1F, 0.1F);
			}
			else if(diceRoll == 54 && ConfigHandler.INFINITY_SIDED_DICE)
			{
				world.setSkylightSubtracted(5);
			}
			else if(diceRoll == 55 && ConfigHandler.INFINITY_SIDED_DICE)
			{
				GL11.glViewport(5, 5, 10, 10);
			}

			cooldown = 20;

			return true;
		}
		return false;
	}
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}

}