package items;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MagicFlashLight extends Item implements IHasModel{

	public float height = 1.8F;
	public float width = 0.6F;
	public float eyeHeight = 1.62F;
	public float stepHeight = 1.0F;
	public float jumpFactor = 0.0F;

	public static KeyBinding shrink;
	public static KeyBinding grow;

	public double playerX;
	public double playerY;
	public double playerZ;



	public MagicFlashLight(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallstab);
		init();

		ItemInit.ITEMS.add(this);
	}

	public static void init()
	{
		shrink = new KeyBinding("key.shrink", Keyboard.KEY_V, "key.categories.gravityfalls");
		ClientRegistry.registerKeyBinding(shrink);

		grow = new KeyBinding("key.grow", Keyboard.KEY_G, "key.categories.gravityfalls");
		ClientRegistry.registerKeyBinding(grow);

		//Minecraft.getMinecraft().gameSettings.keyBindJump = KeysHandler.jump;
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}



	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).getHeldItemMainhand().equals(ItemInit.MAGICFLASHLIGHT))
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			
			if(height > 2 && Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				getMouseOver(player, worldIn, (int) height * 2);

			float heightChange = 0.015F;
			float widthChange = 0.005F;
			float eyeHeightChange = 0.013F;
			float stepHeightChange = 0.0083F;
			float jumpChange = 0.00016F;

			playerX = player.posX;
			playerY = player.posY;
			playerZ = player.posZ;

			AxisAlignedBB boundingBox = new AxisAlignedBB((double)player.getEntityBoundingBox().minX, (double)player.getEntityBoundingBox().minY, (double)player.getEntityBoundingBox().minZ, (double)player.getEntityBoundingBox().minX + width, (double)player.getEntityBoundingBox().minY + height, (double)player.getEntityBoundingBox().minZ + width);



			player.setEntityBoundingBox(boundingBox);

			if(grow.isKeyDown())
			{
				if(height < 10)
				{
					height += heightChange;
					width += widthChange;
					eyeHeight += eyeHeightChange;
					stepHeight += stepHeightChange;
					jumpFactor += jumpChange;

					player.eyeHeight = .9F * (height);
					player.stepHeight = stepHeight;
					player.jumpMovementFactor = jumpFactor; 


					
					player.height = height;
					player.width = width;
					player.setAIMoveSpeed(player.getAIMoveSpeed() + jumpFactor);
					//player.renderOffsetY = (float) (height - 1.8);

					/*
					if(height > 1.8)
					{
					//	player.motionX -= (height * height * .01);
					//	player.motionZ -= (height * height * .01);
						player.setVelocity(0, 0, 0);
					}
					 */

				}
			}

			if(shrink.isKeyDown())
			{

				if(height > 0.1)
				{
					height -= heightChange;
					width -= widthChange;
					eyeHeight -= eyeHeightChange;
					stepHeight -= stepHeightChange;
					jumpFactor -= jumpChange;

					player.eyeHeight = .9F * (height);
					player.stepHeight = stepHeight;
					player.jumpMovementFactor = jumpFactor; 

					
					player.height = height;
					player.width = width;
					player.setAIMoveSpeed(player.getAIMoveSpeed() + jumpFactor);
					//player.renderOffsetY = (float) (height - 1.8);

					/*
					if(height > 1.8)
					{
						//player.motionX -= (height * height * .01);
						//player.motionZ -= (height * height * .01);
						player.setVelocity(0, 0, 0);
					}
					 */

				}

			}


		}





		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	

	protected void setSize(float width, float height, EntityPlayer player)
	{
		if (width != player.width || height != player.height)
		{
			float f = player.width;
			player.width = width;
			player.height = height;

			if (player.width < f)
			{
				double d0 = (double)width / 2.0D;
				player.setEntityBoundingBox(new AxisAlignedBB(player.posX - d0, player.posY, player.posZ - d0, player.posX + (d0 / 2), player.posY + (double)player.height, player.posZ + (d0 / 2)));
				return;
			}

			AxisAlignedBB axisalignedbb = player.getEntityBoundingBox();
			player.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + ((double)player.width / 2), axisalignedbb.minY + (double)player.height, axisalignedbb.minZ + ((double)player.width / 2)));

			if (player.width > f && !player.world.isRemote)
			{
				player.move(MoverType.SELF, (double)(f - player.width), 0.0D, (double)(f - player.width));
			}

		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
	{

		//Height: 1.8F
		//Width: 0.6F
		//Eye Height: 1.62F
		//Step Height: 1.0F
		//JumpMovementFactor: 0.02F


		return super.onItemRightClick(worldIn, player, handIn);
	}
	
	
	public void getMouseOver(EntityPlayer player, World world, int reach)
	{

		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 0; f <= reach; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			List<Entity> list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));

			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);
				if(player instanceof EntityPlayerMP)
				{					
					EntityPlayerMP entityplayer = (EntityPlayerMP) player;

					
					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase mob = (EntityLivingBase) entity;

						mob.fallDistance = 5;
					}
				}				
			}
		}
	}


}
