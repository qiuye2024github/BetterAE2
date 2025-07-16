package cn.qiuye.betterae2.common.definition;

import cn.qiuye.betterae2.api.MainCreativeMod;
import cn.qiuye.betterae2.api.definition.ItemDefinition;

import appeng.api.upgrades.Upgrades;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static cn.qiuye.betterae2.BetterAE2.id;

@SuppressWarnings("unused")
public class BAE2Items {

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    public static final ItemDefinition<Item> SUPER_SPEED_CARD = item("Super Speed Card", "超速卡",
            id("super_speed_card"), Upgrades::createUpgradeCardItem);

    static <T extends Item> ItemDefinition<T> item(String eng, String zh, ResourceLocation id,
                                                   Function<Item.Properties, T> factory) {
        Item.Properties p = new Item.Properties();
        T item = factory.apply(p);
        ItemDefinition<T> definition = new ItemDefinition<>(id, item, eng, zh);
        MainCreativeMod.add(definition);
        ITEMS.add(definition);
        return definition;
    }

    public static List<ItemDefinition<?>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    public static void init() {}
}
