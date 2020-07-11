package init;

import main.Reference;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class AttributeInit {
	public static final IAttribute ENTITY_HEIGHT = new RangedAttribute((IAttribute) null, Reference.MODID + ".entityHeight",
			1.0F, Float.MIN_VALUE, Float.MAX_VALUE).setDescription("Entity Height").setShouldWatch(true);

	public static final IAttribute ENTITY_WIDTH = new RangedAttribute((IAttribute) null, Reference.MODID + ".entityWidth",
			1.0F, Float.MIN_VALUE, Float.MAX_VALUE).setDescription("Entity Width").setShouldWatch(true);
	
	
	
	public static final IAttribute SHAPESHIFTER_HEIGHT = new RangedAttribute((IAttribute) null, Reference.MODID + ".shapeshifterHeight",
			1.0F, Float.MIN_VALUE, Float.MAX_VALUE).setDescription("Shapeshifter Height").setShouldWatch(true);

	public static final IAttribute SHAPESHIFTER_WIDTH = new RangedAttribute((IAttribute) null, Reference.MODID + ".shapeshifterWidth",
			1.0F, Float.MIN_VALUE, Float.MAX_VALUE).setDescription("Shapeshifter Width").setShouldWatch(true);
}
