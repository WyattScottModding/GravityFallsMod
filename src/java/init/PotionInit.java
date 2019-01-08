package init;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import potions.Freeze;

public class PotionInit 
{
	//.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, MathHelper.getRandomUUID().toString(), 3.0D, 2)
	
	public static final Potion FREEZE_EFFECT = new Freeze("freeze_potion", true, 16529206, 0, 0).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), -1.0D, 2).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, MathHelper.getRandomUUID().toString(), -1.0D, 2);

	public static final PotionType FREEZE_POTION = new PotionType("freeze_potion", new PotionEffect[] {new PotionEffect(FREEZE_EFFECT, 400)}).setRegistryName("freeze_potion");
	public static final PotionType FREEZE_POTION_LONG = new PotionType("freeze_potion", new PotionEffect[] {new PotionEffect(FREEZE_EFFECT, 800)}).setRegistryName("freeze_potion_long");

	public static void registerPotions()
	{
		registerPotion(FREEZE_POTION, FREEZE_POTION_LONG, FREEZE_EFFECT);
		registerPotionMixes();
	}
	
	public static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion  effect)
	{
		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);
	}
	
	private static void registerPotionMixes()
	{
		//PotionHelper.addMix(TUBERCULOSIS_EFFECT, Items.REDSTONE, TUBERCULOSIS_POTION_LONG);
		//PotionHelper.addMix(PotionTypes.AWKWARD, ItemInit.SALT_PETRE_DUST, TUBERCULOSIS_EFFECT);
	}
}