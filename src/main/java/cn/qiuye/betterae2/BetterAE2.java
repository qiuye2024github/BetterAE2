package cn.qiuye.betterae2;

import cn.qiuye.betterae2.api.MainCreativeMod;
import cn.qiuye.betterae2.api.register.ModRegisterHandler;
import cn.qiuye.betterae2.api.register.UpgradesInit;
import cn.qiuye.betterae2.client.BetterAE2Client;
import cn.qiuye.betterae2.integration.expatternprovider.EAECommonLoad;
import cn.qiuye.betterae2.util.Platform;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterAE2.MOD_ID)
public class BetterAE2 {

    public static final String MOD_ID = "betterae2";
    public static final Logger LOGGER = LogManager.getLogger();

    public static BetterAE2 INSTANCE;

    public BetterAE2() {
        INSTANCE = this;
        // ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BAE2Config.build());
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.register(BetterAE2Client.INSTANCE));
        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) {
                MainCreativeMod.init(event.getVanillaRegistry());
            }

            if (!event.getRegistryKey().equals(Registries.BLOCK)) {
                return;
            }

            // ModRegisterHandler.initBlock(ForgeRegistries.BLOCKS);
            ModRegisterHandler.initItem(ForgeRegistries.ITEMS);
            // ModRegisterHandler.initBlockEntity(ForgeRegistries.BLOCK_ENTITY_TYPES);
            // MenuTypeRegister.init(ForgeRegistries.MENU_TYPES);
        });
        ModRegisterHandler.init();
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
        // NetworkRegister.register();
        // CellRegister.register();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    // public MinecraftServer getServer() {
    // return ServerLifecycleHooks.getCurrentServer();
    // }

    public void clientSetup(FMLClientSetupEvent event) {
        BetterAE2Client.INSTANCE.init();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(this::postRegistrationInitialization).whenComplete((res, err) -> {
            if (err != null) {
                LOGGER.warn(err);
            }
        });
    }

    private void postRegistrationInitialization() {
        UpgradesInit.init();
        // if (Platform.isModLoaded("ae2")) {
        // AE2CommonLoad.init();
        // }
        if (Platform.isModLoaded("expatternprovider")) {
            EAECommonLoad.init();
        }
    }
}
