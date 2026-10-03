package net.snekold.alphamod.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.item.ModItems;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Alphamod.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.OPAL.get(), ModelTemplates.FLAT_ITEM); // датаген опала
        itemModels.generateFlatItem(ModItems.RAW_OPAL.get(), ModelTemplates.FLAT_ITEM);


    }
}
