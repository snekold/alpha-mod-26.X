package net.snekold.alphamod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.block.ModBlocks;
import net.snekold.alphamod.item.ModItems;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries,output);
        }

        @Override
        public String getName() {
            return "AlphaMod Recipes";
        }
    }

    @Override
    protected void buildRecipes() { // основная логика
        // верстак
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BLOCK.get()) //чтобы получить опаловй блок
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.OPAL.get()) // нужно столько то символов опала
                .unlockedBy(getHasName(ModItems.OPAL.get()), has(ModItems.OPAL))
                .group("opal")
                .save(output);

        shapeless(RecipeCategory.MISC, ModItems.OPAL.get(), 9) //чтобы получить 9 кусочков опала
                .requires(ModBlocks.OPAL_BLOCK) // нужен 1 блок опала
                .unlockedBy(getHasName(ModBlocks.OPAL_BLOCK.get()), has(ModBlocks.OPAL_BLOCK))
                .group("opal")
                .save(output);

        //печка
        List<ItemLike> OPAL_SMELTABLES = List.of(ModItems.RAW_OPAL, ModBlocks.ORE_OPAL_BLOCK, //лист всех предметов, что могут плавится
                ModBlocks.ORE_DEEPSLATE_OPAL_BLOCK, ModBlocks.OPAL_NETHER_ORE, ModBlocks.OPAL_END_ORE);

        //обычная печь
        oreSmelting(OPAL_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.OPAL.get(), 0.25f, 200, "azurite");
        //необычная печь
        oreBlasting(OPAL_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.OPAL.get(), 0.25f, 100, "azurite");
    }

    //переопределяем метод для печки

    @Override
    protected <T extends AbstractCookingRecipe> void oreCooking(AbstractCookingRecipe.Factory<T> factory, List<ItemLike> smeltables,
                                                                RecipeCategory craftingCategory, CookingBookCategory cookingCategory, ItemLike result,
                                                                float experience, int cookingTime, String group, String fromDesc) {
        for(ItemLike itemlike : smeltables) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), craftingCategory, cookingCategory, result, experience, cookingTime, factory).group(group).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(output, Alphamod.MOD_ID + ":" + getItemName(result) + fromDesc + "_" + getItemName(itemlike));
        }
    }

}
