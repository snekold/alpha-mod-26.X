package net.snekold.alphamod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.item.ModItems;
import net.snekold.alphamod.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider { // для тегов предметов только

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Alphamod.MOD_ID);
    }

    // логика
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(Items.ANDESITE)
                .add(ModItems.CHIPS.get());
    }
}
