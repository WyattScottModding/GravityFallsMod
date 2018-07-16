package gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import containers.ContainerBook1;
import containers.ContainerComputer;
import containers.ContainerUraniumFurnace;
import handlers.RegistryHandler;
import handlers.SoundsHandler;
import io.netty.buffer.Unpooled;
import main.Reference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tileEntities.TileEntityBook1;
import tileEntities.TileEntityComputer;
import tileEntities.TileEntityUraniumFurnace;

@SideOnly(Side.CLIENT)
public class GuiComputer extends GuiContainer
{
	private static final ResourceLocation TEXTFIELD = new ResourceLocation(Reference.MODID + ":textures/gui/textfield.png");
	private static final ResourceLocation TEXTFIELDRED = new ResourceLocation(Reference.MODID + ":textures/gui/textfieldred.png");
	private static final ResourceLocation SCREENON = new ResourceLocation(Reference.MODID + ":textures/gui/screenon.png");
	private static final ResourceLocation COUNTDOWN = new ResourceLocation(Reference.MODID + ":textures/gui/countdownscreen.png");

	private GuiTextField textField;
	private GuiTextField countdownText;
	private final TileEntityComputer tileentity;
	
	public int countdown = 18000;

	public boolean wrongPassword = false;
	private int counter = 0;

	public boolean computerOn = false;

	public boolean portalActive = false;

	public GuiComputer(InventoryPlayer playerInventory, TileEntityComputer tileentity) 
	{
		super(new ContainerComputer(playerInventory, tileentity));
		this.tileentity = tileentity;

	}

	@Override
	public void initGui()
	{
		this.textField = new GuiTextField(2, this.fontRenderer, 30, 105, 100, 12);
		this.textField.setMaxStringLength(8);
		this.textField.setFocused(true);
		this.textField.setEnabled(true);
		this.textField.setTextColor(1113860);
		
		//this.countdownText = new GuiLabel(fontRenderer, 501, 100, 100, 100, 10, 13369344);
		
		
		this.countdownText = new GuiTextField(2, this.fontRenderer, 110, 210, 250, 50);
		this.countdownText.setFocused(true);
		this.countdownText.setEnabled(true);
		this.countdownText.setTextColor(13369344);
	

		Keyboard.enableRepeatEvents(true);

		super.initGui();
	}

	@Override
	public void updateScreen()
	{
		portalActive = RegistryHandler.getPortal();

		this.textField.updateCursorCounter();

		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			if(this.textField.getText().equals("STANFORD"))
			{
				computerOn = true;
			}
			else
			{
				BlockPos pos = new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);

				wrongPassword = true;
				this.mc.world.playSound(pos, SoundsHandler.BLOCK_PASSWORD, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
				this.textField.setEnabled(false);
			}
		}
		if(wrongPassword)
			counter++;

		if(counter == 30)
		{
			counter = 0;
			wrongPassword = false;
			this.textField.setText("");
			this.textField.setEnabled(true);

		}

		String text = textField.getText().toUpperCase();

		textField.setText(text);
		
		countdown = RegistryHandler.getCountdown();
		
		int hour = countdown / 1000;
		int minute = (countdown / 1000) % 60;
		int second = (countdown % 1000) % 60;
		
		countdownText.setText(hour + " : " + minute + " : " + second);
	//	countdownText.drawString(this.fontRenderer, hour + " : " + minute + " : " + second, 10, 10, 13369344);

		countdown--;

		super.updateScreen();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if(!computerOn)
		{
			this.textField.drawTextBox();
		}
		else if(computerOn && portalActive)
		{
		//	this.countdownText.drawTextBox();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if(wrongPassword)
		{
			this.mc.getTextureManager().bindTexture(TEXTFIELDRED);
		}
		else if(computerOn && portalActive)
		{
			this.mc.getTextureManager().bindTexture(COUNTDOWN);
		}
		else if(computerOn)
		{
			this.mc.getTextureManager().bindTexture(SCREENON);
		}
		else
		{
			this.mc.getTextureManager().bindTexture(TEXTFIELD);
		}

		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawModalRectWithCustomSizedTexture((width / 2) - (xSize) - 10, (height / 2) - (ySize / 2) - 10, 0, 0, 370, 190, 370, 200);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		if (button.enabled && button.id == 0)
		{
			PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
			packetbuffer.writeString(this.textField.getText());
		}



		super.actionPerformed(button);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException 
	{
		this.textField.textboxKeyTyped(typedChar, keyCode);



		super.keyTyped(typedChar, keyCode);
	}
}