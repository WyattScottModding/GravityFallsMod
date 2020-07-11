package handlers;

import entity.EntityBill;
import entity.EntityBillStatue;
import entity.EntityCipherWheel;
import entity.EntityEightBall;
import entity.EntityEvilTree;
import entity.EntityEyeBat;
import entity.EntityEyeBatHuge;
import entity.EntityForget;
import entity.EntityGnome;
import entity.EntityGolfCart;
import entity.EntityHideBehind;
import entity.EntityKeyhole;
import entity.EntityGideonBot;
import entity.EntitySecurityDroid;
import entity.EntityShapeShifter;
import entity.EntityTimeBaby;
import entity.EntityTimeCopDundgren;
import entity.EntityTimeCopLolph;
import entity.EntityUnicorn;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import render.RenderBill;
import render.RenderBillStatue;
import render.RenderCipherWheel;
import render.RenderEightBall;
import render.RenderEvilTree;
import render.RenderEyeBat;
import render.RenderEyeBatHuge;
import render.RenderForget;
import render.RenderGnome;
import render.RenderGolfCart;
import render.RenderHideBehind;
import render.RenderKeyhole;
import render.RenderGideonBot;
import render.RenderSecurityDroid;
import render.RenderShapeShifter;
import render.RenderTimeBaby;
import render.RenderTimeCopDundgren;
import render.RenderTimeCopLolph;
import render.RenderUnicorn;

public class RenderHandler {


	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityGnome.class, new IRenderFactory<EntityGnome>()
		{
			@Override
			public Render<? super EntityGnome> createRenderFor(RenderManager manager)
			{
				return new RenderGnome(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityForget.class, new IRenderFactory<EntityForget>()
		{
			@Override
			public Render<? super EntityForget> createRenderFor(RenderManager manager)
			{
				return new RenderForget(manager);
			}

		});


		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCopLolph.class, new IRenderFactory<EntityTimeCopLolph>()
		{
			@Override
			public Render<? super EntityTimeCopLolph> createRenderFor(RenderManager manager)
			{
				return new RenderTimeCopLolph(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCopDundgren.class, new IRenderFactory<EntityTimeCopDundgren>()
		{
			@Override
			public Render<? super EntityTimeCopDundgren> createRenderFor(RenderManager manager)
			{
				return new RenderTimeCopDundgren(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityGolfCart.class, new IRenderFactory<EntityGolfCart>()
		{
			@Override
			public Render<? super EntityGolfCart> createRenderFor(RenderManager manager)
			{
				return new RenderGolfCart(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityEyeBat.class, new IRenderFactory<EntityEyeBat>()
		{
			@Override
			public Render<? super EntityEyeBat> createRenderFor(RenderManager manager)
			{
				return new RenderEyeBat(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityHideBehind.class, new IRenderFactory<EntityHideBehind>()
		{
			@Override
			public Render<? super EntityHideBehind> createRenderFor(RenderManager manager)
			{
				return new RenderHideBehind(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntitySecurityDroid.class, new IRenderFactory<EntitySecurityDroid>()
		{
			@Override
			public Render<? super EntitySecurityDroid> createRenderFor(RenderManager manager)
			{
				return new RenderSecurityDroid(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityBill.class, new IRenderFactory<EntityBill>()
		{
			@Override
			public Render<? super EntityBill> createRenderFor(RenderManager manager)
			{
				return new RenderBill(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityEightBall.class, new IRenderFactory<EntityEightBall>()
		{
			@Override
			public Render<? super EntityEightBall> createRenderFor(RenderManager manager)
			{
				return new RenderEightBall(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityEyeBatHuge.class, new IRenderFactory<EntityEyeBatHuge>()
		{
			@Override
			public Render<? super EntityEyeBatHuge> createRenderFor(RenderManager manager)
			{
				return new RenderEyeBatHuge(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityKeyhole.class, new IRenderFactory<EntityKeyhole>()
		{
			@Override
			public Render<? super EntityKeyhole> createRenderFor(RenderManager manager)
			{
				return new RenderKeyhole(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityUnicorn.class, new IRenderFactory<EntityUnicorn>()
		{
			@Override
			public Render<? super EntityUnicorn> createRenderFor(RenderManager manager)
			{
				return new RenderUnicorn(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityGideonBot.class, new IRenderFactory<EntityGideonBot>()
		{
			@Override
			public Render<? super EntityGideonBot> createRenderFor(RenderManager manager)
			{
				return new RenderGideonBot(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityTimeBaby.class, new IRenderFactory<EntityTimeBaby>()
		{
			@Override
			public Render<? super EntityTimeBaby> createRenderFor(RenderManager manager)
			{
				return new RenderTimeBaby(manager);
			}

		});

		RenderingRegistry.registerEntityRenderingHandler(EntityShapeShifter.class, new IRenderFactory<EntityShapeShifter>()
		{
			@Override
			public Render<? super EntityShapeShifter> createRenderFor(RenderManager manager)
			{
				return new RenderShapeShifter(manager);
			}

		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBillStatue.class, new IRenderFactory<EntityBillStatue>()
		{
			@Override
			public Render<? super EntityBillStatue> createRenderFor(RenderManager manager)
			{
				return new RenderBillStatue(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCipherWheel.class, new IRenderFactory<EntityCipherWheel>()
		{
			@Override
			public Render<? super EntityCipherWheel> createRenderFor(RenderManager manager)
			{
				return new RenderCipherWheel(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilTree.class, new IRenderFactory<EntityEvilTree>()
		{
			@Override
			public Render<? super EntityEvilTree> createRenderFor(RenderManager manager)
			{
				return new RenderEvilTree(manager);
			}
		});
	}
}
