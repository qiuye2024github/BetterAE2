package cn.qiuye.betterae2.datagen;

import cn.qiuye.betterae2.BetterAE2;
import cn.qiuye.betterae2.datagen.providers.LocalizationProvider;
import cn.qiuye.betterae2.datagen.providers.models.ItemModelProvider;
import cn.qiuye.betterae2.datagen.providers.recipes.BAE2RecipeProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterAE2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeClient(), new LocalizationProvider(generator));
        generator.addProvider(event.includeServer(), new ItemModelProvider(generator.getPackOutput(),
                event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new BAE2RecipeProvider(generator.getPackOutput()));
    }
}
