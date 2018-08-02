package armor;

import org.lwjgl.input.Keyboard;

import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class SpeedBoots extends ItemArmor implements IHasModel
{
	public float speed = 1.0F;

	public double prevX1 = -5;
	public double prevX2 = -5;
	public double prevX3 = -5;
	public double prevX4 = -5;

	public double prevZ1 = -5;
	public double prevZ2 = -5;
	public double prevZ3 = -5;
	public double prevZ4 = -5;
	
	public boolean onWall = false;
	public int deBug = 0;
	
	public double motionX;
	public double motionZ;
	
	private int counter = 0;

	public SpeedBoots(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);

		ItemInit.ITEMS.add(this);
	}


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(counter < 10)
			counter++;
		else if(counter == 10)
			counter = 0;
		
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			RayTraceResult blockPosition = player.rayTrace(100, 1.0F);

			IBlockState block = world.getBlockState(blockPosition.getBlockPos());

			Block blockType = block.getBlock();

			if(Keyboard.isKeyDown(Keyboard.KEY_N) && speed < 100)
				speed += .4F;
			else if(Keyboard.isKeyDown(Keyboard.KEY_B) && speed > 2)
				speed -= .4F;

			if(RegistryHandler.getSpeed())
			{
				float yaw = player.rotationYaw;
				float pitch = player.rotationPitch;


				//Amount of movement
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 5, (int)speed));

				this.motionX = player.motionX;
				this.motionZ = player.motionZ;
				
				player.stepHeight = (int) (speed / 10) + 1;

				player.fallDistance = 0;
				
				//Running up Walls
				BlockPos pos1 = new BlockPos(player.posX, player.posY, player.posZ + 1);
				BlockPos pos2 = new BlockPos(player.posX, player.posY, player.posZ - 1);
				BlockPos pos3 = new BlockPos(player.posX + 1, player.posY, player.posZ);
				BlockPos pos4 = new BlockPos(player.posX - 1, player.posY, player.posZ);

				boolean xMovementAir = world.getBlockState(pos3).getBlock() != Blocks.AIR || world.getBlockState(pos4).getBlock() != Blocks.AIR;		
				boolean xMovementWater = world.getBlockState(pos3).getBlock() != Blocks.WATER || world.getBlockState(pos4).getBlock() != Blocks.WATER;		
				boolean xMovementWaterFlowing = world.getBlockState(pos3).getBlock() != Blocks.FLOWING_WATER || world.getBlockState(pos4).getBlock() != Blocks.FLOWING_WATER;		
				boolean xMovementLava = world.getBlockState(pos3).getBlock() != Blocks.LAVA || world.getBlockState(pos4).getBlock() != Blocks.LAVA;		
				boolean xMovementLavaFlowing = world.getBlockState(pos3).getBlock() != Blocks.FLOWING_LAVA || world.getBlockState(pos4).getBlock() != Blocks.FLOWING_LAVA;		
				
				if(speed > 15 && Keyboard.isKeyDown(Keyboard.KEY_W) && xMovementWaterFlowing && xMovementLava && xMovementLavaFlowing && xMovementAir && xMovementWater && player.posX == this.prevX1 && player.posX == this.prevX2 && player.posX == this.prevX3 && player.posX == this.prevX4)
				{
					player.motionY = (double) speed / 15;
					onWall = true;
					
					
				}
				else if(onWall)
				{
					player.motionY = -(double) speed / 15;
					deBug++;
					
					if(deBug == 8)
					{
						onWall = false;
						deBug = 0;
					}
				}

				this.prevX4 = this.prevX3;
				this.prevX3 = this.prevX2;
				this.prevX2 = this.prevX1;
				this.prevX1 = player.posX;

				boolean zMovementAir = world.getBlockState(pos1).getBlock() != Blocks.AIR || world.getBlockState(pos2).getBlock() != Blocks.AIR;
				boolean zMovementWater = world.getBlockState(pos3).getBlock() != Blocks.WATER || world.getBlockState(pos4).getBlock() != Blocks.WATER;		
				boolean zMovementWaterFlowing = world.getBlockState(pos3).getBlock() != Blocks.FLOWING_WATER || world.getBlockState(pos4).getBlock() != Blocks.FLOWING_WATER;		
				boolean zMovementLava = world.getBlockState(pos3).getBlock() != Blocks.LAVA || world.getBlockState(pos4).getBlock() != Blocks.LAVA;		
				boolean zMovementLavaFlowing = world.getBlockState(pos3).getBlock() != Blocks.FLOWING_LAVA || world.getBlockState(pos4).getBlock() != Blocks.FLOWING_LAVA;
				
				if(speed > 15 && Keyboard.isKeyDown(Keyboard.KEY_W) && zMovementWaterFlowing && zMovementLava && zMovementLavaFlowing && zMovementAir && zMovementWater && player.posZ == this.prevZ1 && player.posZ == this.prevZ2 && player.posZ == this.prevZ3 && player.posZ == this.prevZ4)
				{
					if(speed < 45)
						player.motionY = (double) speed / 15;
					else
						player.motionY = 3;
					
					onWall = true;
				}
				else if(onWall)
				{
					if(speed < 45)
						player.motionY = -(double) speed / 15;
					else
						player.motionY = -3;
					deBug++;
					
					if(deBug == 8)
					{
						onWall = false;
						deBug = 0;
					}
				}
				this.prevZ4 = this.prevZ3;
				this.prevZ3 = this.prevZ2;
				this.prevZ2 = this.prevZ1;
				this.prevZ1 = player.posZ;

				//Running on Water
				if(player.isOverWater() && speed > 15 && Keyboard.isKeyDown(Keyboard.KEY_W) && world.getBlockState(player.getPosition().down()).getBlock() != Blocks.AIR)
				{
					if(world.getBlockState(player.getPosition()).getBlock() == Blocks.WATER)
						player.motionY = 1.0F;
					else
						player.motionY = 0.0F;

					float f = speed / 30;
					float yaw2 = player.rotationYaw;
					float pitch2 = player.rotationPitch;

					player.motionX = (double)(-MathHelper.sin(yaw2 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch2 / 180.0F * (float)Math.PI) * f);
					player.motionZ = (double)(MathHelper.cos(yaw2 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch2 / 180.0F * (float)Math.PI) * f);
				}

			}	
		}
		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{		
		if(counter == 0 || counter == 1)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-1.png";
		else if(counter == 2 || counter == 3)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-2.png";
		else if(counter == 4 || counter == 5)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-3.png";
		else if(counter == 6 || counter == 7)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-4.png";
		else //if(counter == 8 || counter == 9)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-5.png";
	}
	
	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}