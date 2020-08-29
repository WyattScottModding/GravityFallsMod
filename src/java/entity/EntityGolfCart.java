package entity;

import java.util.Collection;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import gui.GuiGolfCart;
import handlers.KeyBindings;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import network.MessageChangeSize;
import network.MessageHandleGolfCart;
import network.MessageOpenGUI;
import network.MessageUpdatePlayerMotionClient;
import network.Messages;

public class EntityGolfCart extends EntityLiving implements IInventoryChangedListener
{
	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityGolfCart.class, DataSerializers.FLOAT);

	public double acceleration = 1.0;
	public ContainerHorseChest inventory;
	private net.minecraftforge.items.IItemHandler itemHandler = null; 
	public int batteryLife;

	public EntityGolfCart(World worldIn)
	{
		super(worldIn);
		this.setSize(1.8F, 2.8F);
		this.stepHeight = 1;
		this.world = worldIn;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.batteryLife = 100;
		this.initChest();
	}

	public EntityGolfCart(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
	}

	protected int getInventorySize()
	{
		return 25;
	}

	protected Item getDropItem()
	{
		return ItemInit.GOLF_CART;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isEntityInvulnerable(source))
		{
			return false;
		}
		else if(source != DamageSource.GENERIC)
		{
			return false;
		}
		else if (!this.world.isRemote && !this.isDead)
		{
			if (source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && this.isPassenger(source.getTrueSource()))
			{
				return false;
			}
			else
			{
				this.markVelocityChanged();

				if (this.world.getGameRules().getBoolean("doEntityDrops"))
				{
					this.dropItemWithOffset(getDropItem(), 1, 0.0F);
					InventoryHelper.dropInventoryItems(world, this, inventory);
				}
				this.setDead();

				return true;
			}
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItemMainhand();

		this.height = 0.7F;
		this.width = 2.5F;


		if(stack != null && stack.getItem() == Items.SPAWN_EGG)
		{
			return super.processInteract(player, hand);
		}
		else
		{
			//If the player is sneaking, open the gui
			if(player.isSneaking())
			{
				this.openGUI(player);
				return true;
			}
			//If the golf cart is not being riden, this player will become the driver
			else if(!this.isBeingRidden())
			{
				if(stack != null && stack.interactWithEntity(player, this, hand))
				{
					return true;
				}
				else
				{
					this.mountToCart(player, true);
					Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
					return true;
				}
			}
			//If there is only 1 player in the golf cart, this player will become the rider
			else if(!hasRider())
			{
				this.mountToCart(player, false);
				return true;
			}
			else
			{
				//	Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
				return super.processInteract(player, hand);
			}
		}
	}

	/**
	 * If at least 2 entities are riding this
	 */
	public boolean hasRider()
	{
		return this.getPassengers().size() >= 2;
	}

	private void mountToCart(EntityPlayer player, boolean driver)
	{
		player.rotationYaw = this.rotationYaw;
		player.rotationPitch = this.rotationPitch;

		player.startRiding(this, true);
		
		double y = this.posY + this.getMountedYOffset();

		if(driver)
			player.setPosition(this.posX + Math.sin(this.rotationYaw / 180 * Math.PI)*.8 - Math.cos(this.rotationYaw / 180 * Math.PI)*.2, y, this.posZ - Math.cos(this.rotationYaw / 180 * Math.PI)*.8 - Math.sin(this.rotationYaw / 180 * Math.PI)*.2);
		else
			player.setPosition(this.posX + Math.sin(this.rotationYaw / 180 * Math.PI)*.8 - Math.cos(this.rotationYaw / 180 * Math.PI)*1.2, y, this.posZ - Math.cos(this.rotationYaw / 180 * Math.PI)*.8 - Math.sin(this.rotationYaw / 180 * Math.PI)*1.2);
	}

	@Override
	public boolean canBeSteered()
	{
		Entity entity = this.getControllingPassenger();
		return entity instanceof EntityLivingBase;	
	}

	@Override
	public void travel(float strafe, float vertical, float forward) 
	{
		if(this.isBeingRidden() && this.canBeSteered() && this.batteryLife > 0)
		{
			//float rotationAmount = -(strafe * 8);
			forward *= acceleration;
			EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();

			this.rotationYaw = entitylivingbase.rotationYaw;
			this.prevRotationYaw = this.rotationYaw;
			this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.renderYawOffset = this.rotationYaw;
			this.rotationYawHead = this.renderYawOffset;
			strafe = 0;//strafe = entitylivingbase.moveStrafing * 0.5F;
			forward = entitylivingbase.moveForward;

			if(this.hasRider() && this.getPassengers().get(1) instanceof EntityLivingBase)
			{
				EntityLivingBase entity = (EntityLivingBase) this.getPassengers().get(1);
				entity.moveForward = entitylivingbase.moveForward;
			}
			
			//Consume Battery Life when moving
			if(world.getWorldTime() % 40 == 0)
			{
				//Moving forward drains the battery more than moving backward
				if(forward > 0)
					this.batteryLife--;
				else if(forward < 0 && world.getWorldTime() % 3 == 0)
					this.batteryLife--;
			}

			//Moving backwards is slower
			if(forward <= 0.0f)
				forward *= 0.4f;

			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;

			if(this.canPassengerSteer())
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				super.travel(strafe, vertical, forward);
			}
			else if(entitylivingbase instanceof EntityPlayer)
			{
				this.motionX = 0.0d;
				this.motionY = 0.0d;
				this.motionZ = 0.0d;
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0f;

			if(f2 > 1.0f)
			{
				f2 = 1.0f;
			}

			this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4f;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.jumpMovementFactor = 0.02f;
			super.travel(strafe, vertical, forward);
		}
	}

	@Nullable
	public Entity getControllingPassenger()
	{
		return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
	}

	@Override
	protected boolean isMovementBlocked()
	{
		return false;
	}

	@Override
	public boolean canRiderInteract() 
	{
		return true;
	}

	@Override
	public boolean shouldRiderSit() 
	{
		return true;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) 
	{
		return true;
	}

	@Override
	public boolean isAIDisabled()
	{
		return false;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public boolean canBePushed() 
	{
		return !this.isBeingRidden();
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	public void onUpdate() {

		this.fallDistance = 0;

		for(Entity passenger : this.getPassengers())
			updatePassenger(passenger);
		
		//Consume Battery
		if(this.batteryLife <= 50 && inventory.getStackInSlot(0).getItem() == ItemInit.BATTERY)
		{
			inventory.decrStackSize(0, 1);
			inventory.markDirty();
			this.batteryLife += 50;
		}
		super.onUpdate();
	}

	@Override
	public void performHurtAnimation() {
	}

	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
	}

	@Override
	protected void playHurtSound(DamageSource source) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.BLOCK_STONE_HIT;
	}

	@Override
	public void enablePersistence() 
	{
		super.enablePersistence();
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		super.notifyDataManagerChange(key);
	}

	@Override
	public void updatePassenger(Entity passenger) {
		if (this.isPassenger(passenger))
		{	
			Entity driver = null;
			if(this.getPassengers().size() > 0)
				driver = this.getPassengers().get(0);

			Entity rider = null;
			if(this.getPassengers().size() > 1)
				rider = this.getPassengers().get(1);
			
			double y = this.posY + this.getMountedYOffset();

			if(driver != null && passenger == driver)
				passenger.setPosition(this.posX + Math.sin(this.rotationYaw / 180 * Math.PI)*.8 - Math.cos(this.rotationYaw / 180 * Math.PI)*.2, y, this.posZ - Math.cos(this.rotationYaw / 180 * Math.PI)*.8 - Math.sin(this.rotationYaw / 180 * Math.PI)*.2);
			else if(rider != null && passenger == rider)
				passenger.setPosition(this.posX + Math.sin(this.rotationYaw / 180 * Math.PI)*.8 - Math.cos(this.rotationYaw / 180 * Math.PI)*1.2, y, this.posZ - Math.cos(this.rotationYaw / 180 * Math.PI)*.8 - Math.sin(this.rotationYaw / 180 * Math.PI)*1.2);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.inventory.getSizeInventory(); ++i)
		{
			ItemStack itemstack = this.inventory.getStackInSlot(i);

			if (!itemstack.isEmpty())
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				itemstack.writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		compound.setInteger("batteryLife", this.batteryLife);

		compound.setTag("Items", nbttaglist);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		NBTTagList nbttaglist = compound.getTagList("Items", 10);

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < this.inventory.getSizeInventory())
			{
				this.inventory.setInventorySlotContents(j, new ItemStack(nbttagcompound));
			}
		}
		
		this.batteryLife = compound.getInteger("batteryLife");
	}

	/**
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	 */
	public void onInventoryChanged(IInventory invBasic)
	{
	}

	public void openGUI(EntityPlayer playerEntity)
	{
		if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(playerEntity)))
		{
			this.inventory.setCustomName(this.getName());
			//Minecraft.getMinecraft().displayGuiScreen(new GuiGolfCart(playerEntity.inventory, this.inventory, this, playerEntity));
			playerEntity.openGui(GravityFalls.instance, ConfigHandler.GOLF_CART, playerEntity.world, (int) playerEntity.posX, (int) playerEntity.posY, (int) playerEntity.posZ);
		}
	}

	public int getInventoryColumns()
	{
		return 6;
	}

	public String getName()
	{
		return "Golf Cart";
	}

	protected void initChest()
	{
		ContainerHorseChest containerhorsechest = this.inventory;
		this.inventory = new ContainerHorseChest(this.getName(), this.getInventorySize());
		this.inventory.setCustomName(this.getName());

		if (containerhorsechest != null)
		{
			containerhorsechest.removeInventoryChangeListener(this);
			int i = Math.min(containerhorsechest.getSizeInventory(), this.inventory.getSizeInventory());

			for (int j = 0; j < i; ++j)
			{
				ItemStack itemstack = containerhorsechest.getStackInSlot(j);

				if (!itemstack.isEmpty())
				{
					this.inventory.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}

		this.inventory.addInventoryChangeListener(this);
		this.itemHandler = new net.minecraftforge.items.wrapper.InvWrapper(this.inventory);
	}
	
	/**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
     @Override
    public double getMountedYOffset()
    {
        return (double)this.height * 0.1D;
    }
}