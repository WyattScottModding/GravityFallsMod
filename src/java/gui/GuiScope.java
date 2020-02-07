package gui;

import main.Reference;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScope extends GuiScreen
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/scope.png");
	protected int xSize = 176;
	protected int ySize = 166;

	public GuiScope() 
	{
		super();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
        Gui.drawModalRectWithCustomSizedTexture((width / 2) - (xSize / 2), (height / 2) - (ySize / 2), 0, 0, 175, 165, 175, 165);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderToolTip(ItemStack stack, int x, int y) {
		super.renderToolTip(stack, x, y);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}