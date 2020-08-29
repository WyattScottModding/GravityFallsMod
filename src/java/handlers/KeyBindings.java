package handlers;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public enum KeyBindings 
{
	ITEM1("key.gravityfalls.item1", Keyboard.KEY_G),
	ITEM2("key.gravityfalls.item2", Keyboard.KEY_V),
	ARMOR1("key.gravityfalls.armor1", Keyboard.KEY_B),
	ARMOR2("key.gravityfalls.armor2", Keyboard.KEY_N),
	ARMOR3("key.gravityfalls.armor3", Keyboard.KEY_M),
	BATTERY("key.gravityfalls.battery", Keyboard.KEY_R),
	LEFT("key.gravityfalls.left", Keyboard.KEY_LEFT),
	RIGHT("key.gravityfalls.right", Keyboard.KEY_RIGHT),
	UP("key.gravityfalls.up", Keyboard.KEY_UP),
	DOWN("key.gravityfalls.down", Keyboard.KEY_DOWN);

	private final KeyBinding keybinding;
	
	private KeyBindings(String keyName, int defaultKeyCode)
	{
		keybinding = new KeyBinding(keyName, defaultKeyCode, "key.categories.gravityfalls");
	}
	
	public KeyBinding getkeybind()
	{
		return keybinding;
	}
	
	public boolean isPressed()
	{
		return keybinding.isPressed();
	}
	
	public boolean isDown()
	{
		return keybinding.isKeyDown();
	}

}
