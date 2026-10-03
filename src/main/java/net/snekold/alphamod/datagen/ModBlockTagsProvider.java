package net.snekold.alphamod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Alphamod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE) // указываем какие блоки будут добыватся киркой
                .add(ModBlocks.OPAL_BLOCK.get())
                .add(ModBlocks.OPAL_NETHER_ORE.get())
                .add(ModBlocks.OPAL_END_ORE.get())
                .add(ModBlocks.ORE_OPAL_BLOCK.get())
                .add(ModBlocks.ORE_DEEPSLATE_OPAL_BLOCK.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL) // но этой нужна алмазная кирка
                .add(ModBlocks.OPAL_END_ORE.get());
    }
}
