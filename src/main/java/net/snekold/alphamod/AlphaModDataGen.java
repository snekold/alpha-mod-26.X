package net.snekold.alphamod;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.snekold.alphamod.datagen.ModBlockLootTableProvider;
import net.snekold.alphamod.datagen.ModBlockTagsProvider;
import net.snekold.alphamod.datagen.ModModelProvider;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = Alphamod.MOD_ID)
public class AlphaModDataGen { // создание генераторов разных
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator(); //для моделек
        PackOutput packOutput = generator.getPackOutput();

        var lookupProvider = event.getLookupProvider(); // для тегов

        generator.addProvider(true,new ModModelProvider(packOutput)); // для моделек
        generator.addProvider(true,new ModBlockTagsProvider(packOutput, lookupProvider)); // для тегов
        generator.addProvider(true,new LootTableProvider(packOutput, Collections.emptySet(), // для таблицы выпадения
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
    }
}
