package cn.qiuye.betterae2.datagen.providers.models;

import cn.qiuye.betterae2.BetterAE2;
import cn.qiuye.betterae2.api.definition.ItemDefinition;
import cn.qiuye.betterae2.common.definition.BAE2Items;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("UnusedReturnValue")
public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BetterAE2.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        flatSingleLayer(BAE2Items.SUPER_SPEED_CARD, "item/super_speed_card");
    }

    private ItemModelBuilder flatSingleLayer(ItemDefinition<?> item, String texture) {
        String id = item.id().getPath();
        return singleTexture(
                id,
                mcLoc("item/generated"),
                "layer0",
                BetterAE2.id(texture));
    }
}
