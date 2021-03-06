package items;

import javax.annotation.Nullable;

import blocks.LightSource;
import handlers.KeyBindings;
import init.BlockInit;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.MessageDamageItem;
import network.MessageProcessBattery;
import network.Messages;

public class FlashLight extends ItemSword implements IHasModel
{
	public FlashLight(String name)
	{
		super(ToolMaterial.WOOD);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
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

				return entityIn != null && (entityIn.getHeldItemMainhand() == stack || entityIn.getHeldItemOffhand() == stack) && clicked ? 1.0F : 0.0F;
			}
		});

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

		boolean clicked = false;
		int counter = 10;

		if(nbt.hasKey("clicked"))
			clicked = nbt.getBoolean("clicked");
		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");

		if(clicked && counter == 0)
		{
			clicked = false;
			counter = 4;
		}
		else if(!clicked && counter == 0 && stack != null && stack.getItemDamage() <= 99)
		{
			clicked = true;
			counter = 4;
		}

		nbt.setBoolean("clicked", clicked);
		nbt.setInteger("counter", counter);

		stack.setTagCompound(nbt);

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{	
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		boolean clicked = false;
		int counter = 10;

		if(nbt.hasKey("clicked"))
			clicked = nbt.getBoolean("clicked");
		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");

		if(counter > 0)
			counter--;

		if(entity instanceof EntityPlayer && stack.getItemDamage() <= 99 && ((EntityPlayer) entity).getHeldItemMainhand().getItem() == ItemInit.FLASHLIGHT)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if(clicked && world.getWorldTime() % 120 == 0)
			{
				stack.damageItem(1, player);
			}
			
			//Client Side Only
			if(KeyBindings.BATTERY.isDown())
			{
				Messages.INSTANCE.sendToServer(new MessageProcessBattery(1));
			}

			if(clicked) {
				if ((player.getHeldItemMainhand() != null && LightSource.isLightEmittingItem(player.getHeldItemMainhand().getItem())) || (player.getHeldItemOffhand() != null && LightSource.isLightEmittingItem(player.getHeldItemOffhand().getItem())))
				{
					int blockX = MathHelper.floor(player.posX);
					int blockY = MathHelper.floor(player.posY-0.2D - player.getYOffset());
					int blockZ = MathHelper.floor(player.posZ);
					// place light at head level
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
			}
		} 
		nbt.setBoolean("clicked", clicked);
		nbt.setInteger("counter", counter);

		stack.setTagCompound(nbt);
	}
}