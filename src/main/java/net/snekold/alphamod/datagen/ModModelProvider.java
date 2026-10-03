package net.snekold.alphamod.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.block.ModBlocks;
import net.snekold.alphamod.item.ModItems;

public class ModModelProvider extends ModelProvider { // класс для генерации даты
    public ModModelProvider(PackOutput output) {
        super(output, Alphamod.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        /* items */
        itemModels.generateFlatItem(ModItems.OPAL.get(), ModelTemplates.FLAT_ITEM); // датаген опала
        itemModels.generateFlatItem(ModItems.RAW_OPAL.get(), ModelTemplates.FLAT_ITEM);

        /* blocks */
        blockModels.createTrivialCube(ModBlocks.OPAL_BLOCK.get());// датаген опала, но только блок
        blockModels.createTrivialCube(ModBlocks.RAW_OPAL_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ORE_OPAL_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ORE_DEEPSLATE_OPAL_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.OPAL_NETHER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.OPAL_END_ORE.get());

    }
}
