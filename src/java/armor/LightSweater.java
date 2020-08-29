package armor;

import javax.annotation.Nullable;

import handlers.KeyBindings;
import init.BlockInit;
import init.ItemInit;
import items.ItemBasic;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.MessageDamageItem;
import network.MessageMoveEntity;
import network.MessageProcessBattery;
import network.MessageProcessLightSweaterClick;
import network.Messages;

public class LightSweater extends ItemArmor implements IHasModel {

	public LightSweater(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);
		this.setMaxDamage(100);

		this.addPropertyOverride(new ResourceLocation("on"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				//Set the NBT to a new NBT if it is null
				NBTTagCompound nbt = new NBTTagCompound();

				if(stack.getTagCompound() != null)
					nbt = stack.getTagCompound();

				boolean clicked = false;

				if(nbt.hasKey("clicked"))
					clicked = nbt.getBoolean("clicked");

				return entityIn != null && entityIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST) == stack && clicked ? 1.0F : 0.0F;
			}
		});

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		//Client Side Only
		if(world.isRemote)
		{
			if(KeyBindings.BATTERY.isDown())
			{
				Messages.INSTANCE.sendToServer(new MessageProcessBattery(2));
			}

			//Damages Sweater over time when being used  Default: 120
			if(world.getWorldTime() % 120 == 0)
			{
				Messages.INSTANCE.sendToServer(new MessageDamageItem());
			}

			//Processes turning the sweater on and off
			if(KeyBindings.ARMOR1.isDown()) {
				Messages.INSTANCE.sendToServer(new MessageProcessLightSweaterClick());
			}
		}
		//Server Side Only
		else
		{
			//Set the NBT to a new NBT if it is null
			NBTTagCompound stackNBT = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				stackNBT = stack.getTagCompound();

			boolean clicked = false;
			int counter = 0;

			if(stackNBT.hasKey("clicked"))
				clicked = stackNBT.getBoolean("clicked");
			if(stackNBT.hasKey("counter"))
				counter = stackNBT.getInteger("counter");
			
			if(counter > 0)
				counter--;


			if(stack.getItemDamage() <= 99 && clicked)
			{
				// place light at head level
				int blockX = MathHelper.floor(player.posX);
				int blockY = MathHelper.floor(player.posY-0.2D - player.getYOffset());
				int blockZ = MathHelper.floor(player.posZ);

				BlockPos blockLocation = new BlockPos(blockX, blockY, blockZ).up();
				if (player.world.getBlockState(blockLocation).getBlock() == Blocks.AIR)
				{
					player.world.setBlockState(blockLocation, BlockInit.LIGHT_SOURCE.getDefaultState());

				}
				else if (player.world.getBlockState(blockLocation.add(player.getLookVec().x, 
						player.getLookVec().y, player.getLookVec().z)).getBlock() == Blocks.AIR)
				{
					player.world.setBlockState(blockLocation.add(player.getLookVec().x, player.getLookVec().y, 
							player.getLookVec().z), BlockInit.LIGHT_SOURCE.getDefaultState());
				}
			}

			stackNBT.setBoolean("clicked", clicked);
			stackNBT.setInteger("counter", counter);

			stack.setTagCompound(stackNBT);
		}
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}