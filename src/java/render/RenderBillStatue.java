package render;

import entity.EntityBillStatue;
import entity.EntityGnome;
import entity.EntityGolfCart;
import entity.EntityTimeCopLolph;
import main.Reference;
import models.ModelBillStatue;
import models.ModelGnome;
import models.ModelGolfCart;
import models.ModelTimeCopLolph;
import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBillStatue extends RenderLivingBase<EntityBillStatue>
{
    /** A constant instance of the armor stand texture, wrapped inside a ResourceLocation wrapper. */
    public static final ResourceLocation TEXTURE_BILL_STATUE = new ResourceLocation(Reference.MODID + ":textures/entity/bill_statue.png");

    public RenderBillStatue(RenderManager manager)
    {
        super(manager, new ModelBillStatue(), 0.0F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBillStatue entity)
    {
        return TEXTURE_BILL_STATUE;
    }

    public ModelBillStatue getMainModel()
    {
        return (ModelBillStatue)super.getMainModel();
    }

    protected void applyRotations(EntityBillStatue entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
        float f = (float)(entityLiving.world.getTotalWorldTime() - entityLiving.punchCooldown) + partialTicks;

        if (f < 5.0F)
        {
            GlStateManager.rotate(MathHelper.sin(f / 1.5F * (float)Math.PI) * 3.0F, 0.0F, 1.0F, 0.0F);
        }
    }

    protected boolean canRenderName(EntityBillStatue entity)
    {
        return entity.getAlwaysRenderNameTag();
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityBillStatue entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (entity.hasMarker())
        {
            this.renderMarker = true;
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        if (entity.hasMarker())
        {
            this.renderMarker = false;
        }
    }
}