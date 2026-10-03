package net.snekold.alphamod;

import net.minecraft.resources.ResourceKey;
import net.snekold.alphamod.block.ModBlocks;
import net.snekold.alphamod.creativemodetab.ModCreativeModeTabs;
import net.snekold.alphamod.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Alphamod.MOD_ID)
public class Alphamod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "alphamod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Alphamod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // в конце передаем кератив таб
        ModCreativeModeTabs.register(modEventBus);

        // в конце передаем предметы
        ModItems.register(modEventBus);

        // в конце передаем блоки
        ModBlocks.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // креатив таб (уже созданный самой игрой)
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) { // раздел
            event.accept(ModItems.OPAL); // опал в креатив таб
            event.accept(ModItems.RAW_OPAL);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) { // раздел
            event.accept(ModBlocks.OPAL_BLOCK); // опал в креатив таб, но только блок
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
