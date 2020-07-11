package init;

import compatibilities.ISizeCap;
import compatibilities.SizeCapPro;
import entity.EntityShapeShifter;
import main.ConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ShapeShifterAttribute
{
	@SubscribeEvent
	public void attachAttributes(EntityEvent.EntityConstructing event)
	{
		if(event.getEntity() instanceof EntityLivingBase)
		{
			final EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			final AbstractAttributeMap map = entity.getAttributeMap();

			map.registerAttribute(AttributeInit.SHAPESHIFTER_HEIGHT);
			map.registerAttribute(AttributeInit.SHAPESHIFTER_WIDTH);
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		if(entity instanceof EntityShapeShifter) {
			if(entity.hasCapability(SizeCapPro.sizeCapability, null))
			{
				final ISizeCap cap = entity.getCapability(SizeCapPro.sizeCapability, null);

				final boolean hasHeightModifier = entity.getAttributeMap().getAttributeInstance(AttributeInit.SHAPESHIFTER_HEIGHT).getModifiers().isEmpty();
				final boolean hasWidthModifier = entity.getAttributeMap().getAttributeInstance(AttributeInit.SHAPESHIFTER_WIDTH).getModifiers().isEmpty();
				double heightAttribute = entity.getAttributeMap().getAttributeInstance(AttributeInit.SHAPESHIFTER_HEIGHT).getAttributeValue();
				double widthAttribute = entity.getAttributeMap().getAttributeInstance(AttributeInit.SHAPESHIFTER_WIDTH).getAttributeValue();
				float height = (float) (cap.getDefaultHeight() * heightAttribute);
				float width = (float) (cap.getDefaultWidth() * widthAttribute);

				/* Makes Sure to only Run the Code IF the Entity Has Modifiers */
				if(hasHeightModifier != true || hasWidthModifier != true)
				{
					/* If the Entity Does have a Modifier get it's size before changing it's size */
					if(cap.getTrans() != true)
					{
						cap.setDefaultHeight(2.8F);
						cap.setDefaultWidth(1.8F);
						cap.setTrans(true);
					}

					/* Handles Resizing while true */
					if(cap.getTrans() == true)
					{
						width = MathHelper.clamp(width, ConfigHandler.SIZE_MIN / 3, width);
						height = MathHelper.clamp(height, ConfigHandler.SIZE_MIN, height);
						entity.height = height;
						entity.width = width;

						final double d0 = width / 2.0D;
						final AxisAlignedBB aabb = entity.getEntityBoundingBox();
						entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, aabb.minY, entity.posZ - d0,
								entity.posX + d0, aabb.minY + entity.height, entity.posZ + d0));
					}
				}
				else /* If the Entity Does not have any Modifiers */
				{
					/* Returned the Entities Size Back to Normal */
					if(cap.getTrans() == true)
					{
						height = cap.getDefaultHeight();
						width = cap.getDefaultWidth();

						entity.height = height;
						entity.width = width;
						final double d0 = width / 2.0D;
						final AxisAlignedBB aabb = entity.getEntityBoundingBox();
						entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, aabb.minY, entity.posZ - d0,
								entity.posX + d0, aabb.minY + height, entity.posZ + d0));

						cap.setTrans(false);
					}
				}
			}
		}
	}
	/*
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEntityRenderPre(RenderLivingEvent.Pre event)
	{
		final EntityLivingBase entity = event.getEntity();

		if(entity instanceof EntityShapeShifter) {
			if(entity.hasCapability(SizeCapPro.sizeCapability, null))
			{
				final ISizeCap cap = entity.getCapability(SizeCapPro.sizeCapability, null);

				if(cap.getTrans() == true)
				{
					float scaleHeight = entity.height / cap.getDefaultHeight();
					float scaleWidth = entity.width / cap.getDefaultWidth();

					GlStateManager.pushMatrix();
					GlStateManager.scale(scaleWidth, scaleHeight, scaleWidth);
					GlStateManager.translate(event.getX() / scaleWidth - event.getX(),
							event.getY() / scaleHeight - event.getY(), event.getZ() / scaleWidth - event.getZ());
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onLivingRenderPost(RenderLivingEvent.Post event)
	{
		final EntityLivingBase entity = event.getEntity();

		if(entity instanceof EntityShapeShifter) {
			if(entity.hasCapability(SizeCapPro.sizeCapability, null))
			{
				final ISizeCap cap = entity.getCapability(SizeCapPro.sizeCapability, null);

				if(cap.getTrans() == true)
				{
					GlStateManager.popMatrix();
				}
			}
		}
	}
	 */
}