package compatibilities;


import main.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilitiesHandler {

	@SubscribeEvent
	public void onAddCapabilites(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityLivingBase  && !event.getObject().hasCapability(SizeCapPro.sizeCapability, null))
		{
			final EntityLivingBase entity = (EntityLivingBase) event.getObject();
			final boolean transformed = false;
			final float defaultWidth = entity.width;
			final float defaultHeight = entity.height;
			final ISizeCap cap = new SizeDefaultCap(transformed, defaultWidth, defaultHeight);
			event.addCapability(new ResourceLocation(Reference.MODID, "Capability"), new SizeCapPro(cap));
		}
	}
}