package gui;

import java.io.IOException;

import init.ItemInit;
import main.GravityFalls;
import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBook1 extends GuiScreen
{
	private static final ResourceLocation PAGE1 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page1.png");
	private static final ResourceLocation PAGE2 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page2.png");
	private static final ResourceLocation PAGE3 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page3.png");
	private static final ResourceLocation PAGE4 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page4.png");
	private static final ResourceLocation PAGE5 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page5.png");
	private static final ResourceLocation PAGE6 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page6.png");
	private static final ResourceLocation PAGE7 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page7.png");
	private static final ResourceLocation PAGE8 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page8.png");

	private static final ResourceLocation PAGE1_2 = new ResourceLocation(Reference.MODID + ":textures/gui/journal1/page1-2.png");

	public int currentPage = 1;
	public final int pageCount = 8;
	protected int xSize = 176;
	protected int ySize = 166;
	
	public GuiBook1() 
	{
		super();
	}

	@Override
	public void initGui() 
	{
		buttonList.add(new GuiButtonExt(1, (width / 2) - (xSize) + 10, (height / 2) - (ySize / 2) + 161, 60, 20, "Left"));
		buttonList.add(new GuiButtonExt(2, (width / 2) - (xSize) + 282, (height / 2) - (ySize / 2) + 161, 60, 20, "Right"));

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
		
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if(currentPage == 1)
		{
			EntityPlayer player = GravityFalls.proxy.getClientPlayer();
			InventoryPlayer playerInv = player.inventory;
			
			if(playerInv.hasItemStack(new ItemStack(ItemInit.BLACK_LIGHT)))
				this.mc.getTextureManager().bindTexture(PAGE1_2);
			else
				this.mc.getTextureManager().bindTexture(PAGE1);
		}
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
		else if(currentPage == 8)
			this.mc.getTextureManager().bindTexture(PAGE8);

		Gui.drawModalRectWithCustomSizedTexture((width / 2) - (xSize), (height / 2) - (ySize / 2) - 5, 0, 0, xSize * 2, ySize, 355, 170);

		if(currentPage == 1)
			buttonList.get(0).enabled = false;
		else
			buttonList.get(0).enabled = true;

		if(currentPage == pageCount)
			buttonList.get(1).enabled = false;
		else
			buttonList.get(1).enabled = true;
		
		//LeftButton
		((GuiButton)this.buttonList.get(0)).drawButton(this.mc, mouseX, mouseY, partialTicks);

		//RightButton
		((GuiButton)this.buttonList.get(1)).drawButton(this.mc, mouseX, mouseY, partialTicks);
		
		switchPage();
	}
	
	@Override
	protected void renderToolTip(ItemStack stack, int x, int y) {
		super.renderToolTip(stack, x, y);
	}

	public void switchPage()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		
		if(settings.keyBindLeft.isKeyDown())
		{
			currentPage--;
		}
		if(settings.keyBindRight.isKeyDown())
		{
			currentPage++;
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}