package cn.qiuye.betterae2.api.register;

import cn.qiuye.betterae2.api.definition.ItemDefinition;
import cn.qiuye.betterae2.common.definition.BAE2Items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModRegisterHandler {
    // public static void initBlock(IForgeRegistry<Block> registry) {
    // for (var block : BAE2Blocks.getBlocks()) {
    // registry.register(block.id(), block.asBlock());
    // }
    // }

    public static void initItem(IForgeRegistry<Item> registry) {
        for (ItemDefinition<?> item : BAE2Items.getItems()) {
            registry.register(item.id(), item.asItem());
        }

        // for (ItemDefinition<?> item : BAE2Blocks.getBlocks()) {
        // registry.register(item.id(), item.asItem());
        // }
    }

    // public static void initBlockEntity(IForgeRegistry<BlockEntityType<?>> registry) {
    // for (var blockEntity : BAE2BlockEntities.getBlockEntityTypes().entrySet()) {
    // registry.register(blockEntity.getKey(), blockEntity.getValue());
    // }
    // }

    public static void init() {
        BAE2Items.init();
        // BAE2Blocks.init();
        // BAE2Parts.init();
    }
}
