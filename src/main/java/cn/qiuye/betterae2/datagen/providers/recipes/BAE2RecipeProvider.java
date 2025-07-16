package cn.qiuye.betterae2.datagen.providers.recipes;

import cn.qiuye.betterae2.BetterAE2;
import cn.qiuye.betterae2.common.definition.BAE2Items;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BAE2RecipeProvider extends RecipeProvider {

    static String C = "has_item";

    public BAE2RecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> c) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.TOOLS, BAE2Items.SUPER_SPEED_CARD)
                .pattern("ABA")
                .pattern("CDC")
                .pattern("ACA")
                .define('A', AEBlocks.QUARTZ_GLASS)
                .define('B', AEItems.CALCULATION_PROCESSOR)
                .define('C', AEItems.SPEED_CARD)
                .define('D', AEItems.SINGULARITY)
                .unlockedBy(C, has(AEItems.SPEED_CARD))
                .save(c, BetterAE2.id("super_speed_card"));
    }
}
