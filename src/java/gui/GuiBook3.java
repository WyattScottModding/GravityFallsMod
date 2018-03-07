package gui;

import containers.ContainerBook1;
import containers.ContainerUraniumFurnace;
import main.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tileEntities.TileEntityBook1;
import tileEntities.TileEntityUraniumFurnace;

@SideOnly(Side.CLIENT)
public class GuiBook3 extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/journal3_portal.png");

	private final InventoryPlayer playerInv;
	public TileEntityBook1 tileBook3;

	public GuiBook3(InventoryPlayer playerInventory, TileEntityBook1 furnaceInventory) 
	{
		super(new ContainerBook1(playerInventory, furnaceInventory));
		playerInv = playerInventory;
		tileBook3 = furnaceInventory;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
        this.drawModalRectWithCustomSizedTexture((width / 2) - (xSize), (height / 2) - (ySize / 2), 0, 0, xSize * 2, ySize, 355, 170);

	
	}

	
}