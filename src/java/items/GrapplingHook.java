package items;

import javax.annotation.Nullable;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrapplingHook extends Item implements IHasModel
{
	public GrapplingHook(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		ItemInit.ITEMS.add(this);

		this.addPropertyOverride(new ResourceLocation("fired"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				//Set the NBT to a new NBT if it is null
				NBTTagCompound nbt = new NBTTagCompound();

				if(stack.getTagCompound() != null)
					nbt = stack.getTagCompound();

				boolean active = false;

				if(nbt.hasKey("active"))
					active = nbt.getBoolean("active");

				return entityIn != null && (entityIn.getHeldItemMainhand() == stack || entityIn.getHeldItemOffhand() == stack) && active ? 1.0F : 0.0F;
			}
		});
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		//If the entity is a player and is holding the grappling hook
		if (entityIn instanceof EntityPlayer && isSelected)
		{
			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();

			stack.setTagCompound(nbt);

			EntityPlayer player = (EntityPlayer)entityIn;

			//Get NBT values
			boolean active = false;
			double time = 0;
			float yaw = 0;
			float pitch = 0;

			if(nbt.hasKey("active"))
				active = nbt.getBoolean("active");
			if(nbt.hasKey("time"))
				time = nbt.getDouble("time");
			if(nbt.hasKey("yaw"))
				yaw = nbt.getFloat("yaw");
			if(nbt.hasKey("pitch"))
				pitch = nbt.getFloat("pitch");

			//Amount of movement
			float f = 0.8F;

			//If the grappling hook is currently pulling
			if(active) {

				//If the player is currently grappled to an object
				boolean stuckToSurface = false;

				if(time > 0)
				{
					double xMotion = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					double yMotion = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					double zMotion = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

					if(time < 0.2 && (xMotion != player.motionX || yMotion != player.motionY || zMotion != player.motionZ)) {
						time = 0;
						stuckToSurface = true;
					}
					else {
						player.motionX = xMotion;
						player.motionY = yMotion;
						player.motionZ = zMotion;

						time -= .05;
					}
				}
				else {
					time = 0;
					stuckToSurface = true;
				}

				if(stuckToSurface) {
					player.motionX = 0;
					player.motionY = 0;
					player.motionZ = 0;

					GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

					if(gameSettings.keyBindForward.isPressed() || gameSettings.keyBindBack.isPressed() || gameSettings.keyBindLeft.isPressed() || gameSettings.keyBindRight.isPressed()) {
						stuckToSurface = false;
						active = false;
					}
				}
			}
			nbt.setDouble("time", time);
			nbt.setBoolean("active", active);
			stack.setTagCompound(nbt);
		}
		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityLiving, EnumHand handIn)
	{
		ItemStack itemstack = entityLiving.getHeldItem(handIn);

		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(itemstack.getTagCompound() != null)
			nbt = itemstack.getTagCompound();

		itemstack.setTagCompound(nbt);

		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityLiving;

			boolean active = false;

			if(nbt.hasKey("active"))
				active = nbt.getBoolean("active");

			if (!active)
			{
				double distance;
				double time;

				//Only grapple if the object is less than 40 blocks away
				RayTraceResult blockPosition = player.rayTrace(40, 1.0F);

				int blockX = blockPosition.getBlockPos().getX();
				int blockY = blockPosition.getBlockPos().getY();
				int blockZ = blockPosition.getBlockPos().getZ();

				int playerX = player.getPosition().getX();
				int playerY = player.getPosition().getY();
				int playerZ = player.getPosition().getZ();

				distance = Math.sqrt(Math.pow(blockX - playerX, 2) + Math.pow(blockY - playerY, 2) + Math.pow(blockZ - playerZ, 2));
				time = distance / 14.68;

				if(worldIn.getBlockState(new BlockPos(blockX, blockY, blockZ)).getBlock() != Blocks.AIR)
					active = true;

				//Set NBT values
				nbt.setDouble("time", time);
				nbt.setFloat("yaw", player.rotationYaw);
				nbt.setFloat("pitch", player.rotationPitch);
			}
			else
				active = false;

			nbt.setBoolean("active", active);
			itemstack.setTagCompound(nbt);	
		}

		return (ActionResult<ItemStack>) new ActionResult(EnumActionResult.FAIL, itemstack);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, 1, true);
		}
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}