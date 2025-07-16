package cn.qiuye.betterae2.api;

import cn.qiuye.betterae2.BetterAE2;
import cn.qiuye.betterae2.api.definition.ItemDefinition;
import cn.qiuye.betterae2.common.definition.BAE2Items;

import appeng.block.AEBaseBlock;
import appeng.block.AEBaseBlockItem;
import appeng.items.AEBaseItem;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.ArrayList;
import java.util.List;

public class MainCreativeMod {

    private static final List<ItemDefinition<?>> itemDefinitions = new ArrayList<>();

    public static final ResourceKey<CreativeModeTab> MAIN = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            BetterAE2.id("main"));

    public static void init(Registry<CreativeModeTab> registry) {
        var tab = CreativeModeTab.builder()
                .title(Component.translatable("tooltip.bae2.creative.tab"))
                .icon(() -> BAE2Items.SUPER_SPEED_CARD.stack(1))
                .displayItems(MainCreativeMod::buildDisplayItems)
                .build();
        Registry.register(registry, MAIN, tab);
    }

    public static void add(ItemDefinition<?> itemDef) {
        itemDefinitions.add(itemDef);
    }

    private static void buildDisplayItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters,
                                          CreativeModeTab.Output output) {
        for (var itemDef : itemDefinitions) {
            var item = itemDef.asItem();

            if (item instanceof AEBaseBlockItem baseItem && baseItem.getBlock() instanceof AEBaseBlock baseBlock) {
                baseBlock.addToMainCreativeTab(output);
            } else if (item instanceof AEBaseItem baseItem) {
                baseItem.addToMainCreativeTab(output);
            } else {
                output.accept(itemDef);
            }
        }
    }
}
