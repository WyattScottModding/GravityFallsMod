package network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


public class Messages {

    public static SimpleNetworkWrapper INSTANCE;
    private static int ID = 0;
    
    public static int nextID() {
    	return ID++;
    }

    public static void registerMessages(String channelName) {
    	INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
    	
    	INSTANCE.registerMessage(MessageOpenBook1.Handler.class, MessageOpenBook1.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenBook2.Handler.class, MessageOpenBook2.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenBook3.Handler.class, MessageOpenBook3.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenScope.Handler.class, MessageOpenScope.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenComputer.Handler.class, MessageOpenComputer.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenReturnDevice.Handler.class, MessageOpenReturnDevice.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageTeleportPlayer.Handler.class, MessageTeleportPlayer.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageShapeShifterUpdate.Handler.class, MessageShapeShifterUpdate.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageShapeShifterModel.Handler.class, MessageShapeShifterModel.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenFordWorkbench.Handler.class, MessageOpenFordWorkbench.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessagePlaySound.Handler.class, MessagePlaySound.class, nextID(), Side.CLIENT);

    }
}