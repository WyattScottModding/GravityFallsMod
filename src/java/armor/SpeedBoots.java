package armor;

import org.lwjgl.input.Keyboard;

import handlers.KeyBindings;
import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import network.MessageMysticAmulet;
import network.MessageUpdateSpeedBoots;
import network.Messages;

public class SpeedBoots extends ItemArmor implements IHasModel
{
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
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		int counter = 0;
		float speed = 1.0F;

		double prevX1 = -5;
		double prevX2 = -5;
		double prevX3 = -5;
		double prevX4 = -5;

		double prevZ1 = -5;
		double prevZ2 = -5;
		double prevZ3 = -5;
		double prevZ4 = -5;

		boolean onWall = false;

		int deBug = 0;

		double motionX = 0;
		double motionZ = 0;

		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");
		if(nbt.hasKey("speed"))
			speed = nbt.getFloat("speed");

		if(nbt.hasKey("prevX1"))
			prevX1 = nbt.getDouble("prevX1");
		if(nbt.hasKey("prevX2"))
			prevX2 = nbt.getDouble("prevX2");
		if(nbt.hasKey("prevX3"))
			prevX3 = nbt.getDouble("prevX3");
		if(nbt.hasKey("prevX4"))
			prevX4 = nbt.getDouble("prevX4");

		if(nbt.hasKey("prevZ1"))
			prevZ1 = nbt.getDouble("prevZ1");
		if(nbt.hasKey("prevZ2"))
			prevZ2 = nbt.getDouble("prevZ2");
		if(nbt.hasKey("prevZ3"))
			prevZ3 = nbt.getDouble("prevZ3");
		if(nbt.hasKey("prevZ4"))
			prevZ4 = nbt.getDouble("prevZ4");

		if(nbt.hasKey("onWall"))
			onWall = nbt.getBoolean("onWall");
		if(nbt.hasKey("deBug"))
			deBug = nbt.getInteger("deBug");
		if(nbt.hasKey("motionX"))
			motionX = nbt.getDouble("motionX");
		if(nbt.hasKey("motionZ"))
			motionZ = nbt.getDouble("motionZ");

		if(counter < 10)
			counter++;
		else if(counter == 10)
			counter = 0;

		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;
			Item feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

			if(feet == ItemInit.SPEED_BOOTS)
			{
				if(KeyBindings.ARMOR1.isDown())
					Messages.INSTANCE.sendToServer(new MessageUpdateSpeedBoots(1));
				else if(KeyBindings.ARMOR2.isDown())
					Messages.INSTANCE.sendToServer(new MessageUpdateSpeedBoots(2));

				//Amount of movement
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 5, (int)speed));

				motionX = player.motionX;
				motionZ = player.motionZ;

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

				if(speed > 15 && Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() && xMovementWaterFlowing && xMovementLava && xMovementLavaFlowing && xMovementAir && xMovementWater && player.posX == prevX1 && player.posX == prevX2 && player.posX == prevX3 && player.posX == prevX4)
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

				prevX4 = prevX3;
				prevX3 = prevX2;
				prevX2 = prevX1;
				prevX1 = player.posX;

				boolean zMovementAir = world.getBlockState(pos1).getBlock() != Blocks.AIR || world.getBlockState(pos2).getBlock() != Blocks.AIR;
				boolean zMovementWater = world.getBlockState(pos3).getBlock() != Blocks.WATER || world.getBlockState(pos4).getBlock() != Blocks.WATER;		
				boolean zMovementWaterFlowing = world.getBlockState(pos3).getBlock() != Blocks.FLOWING_WATER || world.getBlockState(pos4).getBlock() != Blocks.FLOWING_WATER;		
				boolean zMovementLava = world.getBlockState(pos3).getBlock() != Blocks.LAVA || world.getBlockState(pos4).getBlock() != Blocks.LAVA;		
				boolean zMovementLavaFlowing = world.getBlockState(pos3).getBlock() != Blocks.FLOWING_LAVA || world.getBlockState(pos4).getBlock() != Blocks.FLOWING_LAVA;

				if(speed > 15 && Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() && zMovementWaterFlowing && zMovementLava && zMovementLavaFlowing && zMovementAir && zMovementWater && player.posZ == prevZ1 && player.posZ == prevZ2 && player.posZ == prevZ3 && player.posZ == prevZ4)
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
				prevZ4 = prevZ3;
				prevZ3 = prevZ2;
				prevZ2 = prevZ1;
				prevZ1 = player.posZ;

				GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

				//Running on Water
				if(gameSettings.keyBindForward.isPressed())
				{
					Messages.INSTANCE.sendToServer(new MessageUpdateSpeedBoots(3));
				}
			}
		}

		nbt.setInteger("counter", counter);
		nbt.setFloat("speed", speed);

		nbt.setDouble("prevX1", prevX1);
		nbt.setDouble("prevX2", prevX2);
		nbt.setDouble("prevX3", prevX3);
		nbt.setDouble("prevX4", prevX4);

		nbt.setDouble("prevZ1", prevZ1);
		nbt.setDouble("prevZ2", prevZ2);
		nbt.setDouble("prevZ3", prevZ3);
		nbt.setDouble("prevZ4", prevZ4);

		nbt.setBoolean("onWall", onWall);
		nbt.setInteger("deBug", deBug);
		nbt.setDouble("motionX", motionX);
		nbt.setDouble("motionZ", motionZ);

		stack.setTagCompound(nbt);

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		int counter = 0;

		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");

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