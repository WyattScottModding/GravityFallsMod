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
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setCreativeTab(GravityFalls.gravityfallsmagic);
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
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;

			if(player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.MAGICFLASHLIGHT)))
			{
				

				float heightChange = 0.015F;
				float widthChange = 0.005F;
				float eyeHeightChange = 0.013F;
				float stepHeightChange = 0.0083F;
				float jumpChange = 0.00016F;

				if(grow.isPressed() || shrink.isPressed())
				{
					playerX = player.posX;
					playerY = player.posY;
					playerZ = player.posZ;
				}

				//	AxisAlignedBB boundingBox = new AxisAlignedBB((double)player.getEntityBoundingBox().minX, (double)player.getEntityBoundingBox().minY, (double)player.getEntityBoundingBox().minZ, (double)player.getEntityBoundingBox().minX + width, (double)player.getEntityBoundingBox().minY + height, (double)player.getEntityBoundingBox().minZ + width);

				//	player.setEntityBoundingBox(boundingBox);

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


						setSize(width, height, player);
						player.setAIMoveSpeed(player.getAIMoveSpeed() + jumpFactor);
						player.renderOffsetY = (float) (height - 1.8);

						//GL11.glScaled(widthChange + 1, heightChange + 1, widthChange + 1);
						
						player.setPosition(playerX, playerY, playerZ);
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


						setSize(width, height, player);
						player.setAIMoveSpeed(player.getAIMoveSpeed() + jumpFactor);
						player.renderOffsetY = (float) (height - 1.8);

						//GL11.glScaled(widthChange + 1, heightChange + 1, widthChange + 1);
						
						
						player.setPosition(playerX, playerY, playerZ);
					}

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

	//		if (player.width < f)
	//		{
	//			double d0 = (double)width / 2.0D;
	//			player.setEntityBoundingBox(new AxisAlignedBB(player.posX - d0, player.posY, player.posZ - d0, player.posX + (d0 / 2), player.posY + (double)player.height, player.posZ + (d0 / 2)));
	//			return;
	//		}

			//	AxisAlignedBB axisalignedbb = player.getEntityBoundingBox();
			//	player.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + ((double)player.width / 2), axisalignedbb.minY + (double)player.height, axisalignedbb.minZ + ((double)player.width / 2)));

			AxisAlignedBB boundingBox = new AxisAlignedBB((double)player.posX, (double)player.posY, (double)player.posZ, (double)player.posX + width, (double)player.posY + height, (double)player.posZ + width);

			player.setEntityBoundingBox(boundingBox);
			

	//		if (player.width > f && !player.world.isRemote)
	//		{
	//			player.move(MoverType.SELF, (double)(f - player.width), 0.0D, (double)(f - player.width));
	//		}

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


	


}
