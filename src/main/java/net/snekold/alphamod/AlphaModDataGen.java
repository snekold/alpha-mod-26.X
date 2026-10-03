package net.snekold.alphamod;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.snekold.alphamod.datagen.ModModelProvider;

@EventBusSubscriber(modid = Alphamod.MOD_ID)
public class AlphaModDataGen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        generator.addProvider(true,new ModModelProvider(packOutput));
    }
}
