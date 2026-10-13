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
        itemModels.generateFlatItem(ModItems.METAL_DETECTOR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHIPS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ENDER_COAL.get(), ModelTemplates.FLAT_ITEM);

        /* blocks */
        //blockModels.createTrivialCube(ModBlocks.OPAL_BLOCK.get());// датаген опала, но только блок UPD- теперь делаем семейство опала
        blockModels.createTrivialCube(ModBlocks.ORE_OPAL_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ORE_DEEPSLATE_OPAL_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.OPAL_NETHER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.OPAL_END_ORE.get());
        blockModels.createTrivialCube(ModBlocks.ALT_BLOCK.get());

        blockModels.family(ModBlocks.OPAL_BLOCK.get()) // семья опала
                .stairs(ModBlocks.OPAL_STAIRS.get()) // ступеньки
                .slab(ModBlocks.OPAL_SLAB.get())
                .button(ModBlocks.OPAL_BUTTON.get())
                .pressurePlate(ModBlocks.OPAL_PRESSURE_PLATE.get())
        ; // полублоки тип


    }
}
