package compatibilities;

import java.util.concurrent.Callable;



import net.minecraftforge.common.capabilities.CapabilityManager;

public class Capabilities {

	public static void init()
	{
		CapabilityManager.INSTANCE.register(ISizeCap.class, new SizeCapStorage(), new CababilityFactory());
	}

	private static class CababilityFactory implements Callable<ISizeCap>
	{
		@Override
		public ISizeCap call() throws Exception
		{
			return new SizeDefaultCap();
		}
	}
}