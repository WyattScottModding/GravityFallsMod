package gui;

import java.io.IOException;

import containers.ContainerBook1;
import containers.ContainerUraniumFurnace;
import main.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tileEntities.TileEntityBook1;
import tileEntities.TileEntityUraniumFurnace;

@SideOnly(Side.CLIENT)
public class GuiBook3 extends GuiContainer
{
	private static final ResourceLocation PAGE1 = new ResourceLocation(Reference.MODID + ":textures/gui/journal3/page1.png");
	private static final ResourceLocation PAGE2 = new ResourceLocation(Reference.MODID + ":textures/gui/journal3/page2.png");
	private static final ResourceLocation PAGE3 = new ResourceLocation(Reference.MODID + ":textures/gui/journal3/page3.png");
	private static final ResourceLocation PAGE4 = new ResourceLocation(Reference.MODID + ":textures/gui/journal3/page4.png");
	private static final ResourceLocation PAGE5 = new ResourceLocation(Reference.MODID + ":textures/gui/journal3/page5.png");
	private static final ResourceLocation PAGE6 = new ResourceLocation(Reference.MODID + ":textures/gui/journal3/page6.png");
	private static final ResourceLocation PAGE7 = new ResourceLocation(Reference.MODID + ":textures/gui/journal3/page7.png");

	private final InventoryPlayer playerInv;
	public TileEntityBook1 tileBook3;
	public int currentPage = 1;
	public final int pageCount = 7;

	public GuiBook3(InventoryPlayer playerInventory, TileEntityBook1 furnaceInventory) 
	{
		super(new ContainerBook1(playerInventory, furnaceInventory));
		playerInv = playerInventory;
		tileBook3 = furnaceInventory;
	}

	@Override
	public void initGui() 
	{
		buttonList.add(new GuiButtonExt(1, width/2 - 150, 200, 60, 20, "Left"));
		buttonList.add(new GuiButtonExt(2, width/2 + 100, 200, 60, 20, "Right"));

		super.initGui();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		switch(button.id)
		{
		case 1:
			currentPage--;
			break;
		case 2:
			currentPage++;
		}

		super.actionPerformed(button);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);

		//LeftButton
		((GuiButton)this.buttonList.get(0)).drawButton(this.mc, mouseX, mouseY, partialTicks);

		//RightButton
		((GuiButton)this.buttonList.get(1)).drawButton(this.mc, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if(currentPage == 1)
			this.mc.getTextureManager().bindTexture(PAGE1);
		else if(currentPage == 2)
			this.mc.getTextureManager().bindTexture(PAGE2);
		else if(currentPage == 3)
			this.mc.getTextureManager().bindTexture(PAGE3);
		else if(currentPage == 4)
			this.mc.getTextureManager().bindTexture(PAGE4);
		else if(currentPage == 5)
			this.mc.getTextureManager().bindTexture(PAGE5);
		else if(currentPage == 6)
			this.mc.getTextureManager().bindTexture(PAGE6);
		else if(currentPage == 7)
			this.mc.getTextureManager().bindTexture(PAGE7);

		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawModalRectWithCustomSizedTexture((width / 2) - (xSize), (height / 2) - (ySize / 2) - 5, 0, 0, xSize * 2, ySize, 355, 170);

		if(currentPage == 1)
			buttonList.get(0).enabled = false;
		else
			buttonList.get(0).enabled = true;

		if(currentPage == pageCount)
			buttonList.get(1).enabled = false;
		else
			buttonList.get(1).enabled = true;
	}
}