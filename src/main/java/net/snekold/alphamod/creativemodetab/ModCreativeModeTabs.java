package net.snekold.alphamod.creativemodetab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.item.ModItems;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Alphamod.MOD_ID);

    public static final Supplier<CreativeModeTab> OPAL_ITEMS_TAB = CREATIVE_MODE_TABS.register("opal_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.OPAL.get()))
                    .title(Component.translatable("creativetab.alphamod.opal_items"))
                    .withTabsBefore(CreativeModeTabs.INGREDIENTS)
                    .withTabsAfter(Identifier.fromNamespaceAndPath(Alphamod.MOD_ID, "opal_blocks_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.OPAL);
                        output.accept(ModItems.RAW_OPAL);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> OPAL_BLOCKS_TAB = CREATIVE_MODE_TABS.register("opal_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAW_OPAL.get()))
                    .title(Component.translatable("creativetab.alphamod.opal_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {


                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
