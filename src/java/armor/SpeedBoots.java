package armor;

import org.lwjgl.input.Keyboard;

import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
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

	public static KeyBinding faster;
	public static KeyBinding slower;

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

	public SpeedBoots(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);
		init();

		ItemInit.ITEMS.add(this);

	}

	public static void init()
	{
		faster = new KeyBinding("key.faster", Keyboard.KEY_N, "key.categories.gravityfalls");
		ClientRegistry.registerKeyBinding(faster);

		slower = new KeyBinding("key.slower", Keyboard.KEY_B, "key.categories.gravityfalls");
		ClientRegistry.registerKeyBinding(slower);

		//Minecraft.getMinecraft().gameSettings.keyBindJump = KeysHandler.jump;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			RayTraceResult blockPosition = player.rayTrace(100, 1.0F);

			IBlockState block = world.getBlockState(blockPosition.getBlockPos());

			Block blockType = block.getBlock();

			if(faster.isKeyDown() && speed < 100)
				speed += .4F;
			else if(slower.isKeyDown() && speed > 2)
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

				/*
				
				//Running on Water
				BlockPos pos5 = new BlockPos(player.posX, player.posY - 1, player.posZ);
				BlockPos pos6 = new BlockPos(player.posX, player.posY, player.posZ);
				
				boolean waterBelow = world.getBlockState(pos5).getBlock() == Blocks.WATER && world.getBlockState(pos6).getBlock() == Blocks.AIR;
				
				double speedVector = Math.sqrt(Math.pow(player.motionX, 2) + Math.pow(player.motionZ, 2));
				
				if(waterBelow && speed > 15 && speedVector > 1)
				{
					player.motionY = 0;
					player.motionZ = this.motionZ * 1.1;
					player.motionX = this.motionX * 1.1;
				}
				*/
			}
			
			
			
		}
		

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}




	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");

	}

}
