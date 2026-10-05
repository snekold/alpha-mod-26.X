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
import net.snekold.alphamod.block.ModBlocks;
import net.snekold.alphamod.item.ModItems;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Alphamod.MOD_ID);  // константа креатив мод таба

    //новая вкладка OPAL_ITEMS_TAB
    public static final Supplier<CreativeModeTab> OPAL_ITEMS_TAB = CREATIVE_MODE_TABS.register("opal_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.OPAL.get())) // указываем класс
                    .title(Component.translatable("creativetab.alphamod.opal_items")) // надпись (ссылка на перевод)
                    .withTabsBefore(CreativeModeTabs.INGREDIENTS)  // подраздел таба
                    .withTabsAfter(Identifier.fromNamespaceAndPath(Alphamod.MOD_ID, "opal_blocks_tab")) //
                    .displayItems((itemDisplayParameters, output) -> { // отображаемые предметы
                        output.accept(ModItems.OPAL);
                        output.accept(ModItems.METAL_DETECTOR);
                        output.accept(ModItems.CHIPS);
                    })
                    .build());

    //новая вкладка OPAL_BLOCKS_TAB
    public static final Supplier<CreativeModeTab> OPAL_BLOCKS_TAB = CREATIVE_MODE_TABS.register("opal_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.OPAL_BLOCK.get()))  //указываем класс
                    .title(Component.translatable("creativetab.alphamod.opal_blocks"))  // надпись (ссылка на перевод)
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.OPAL_BLOCK);
                        output.accept(ModBlocks.ORE_OPAL_BLOCK);
                        output.accept(ModBlocks.ORE_DEEPSLATE_OPAL_BLOCK);
                        output.accept(ModBlocks.OPAL_NETHER_ORE);
                        output.accept(ModBlocks.OPAL_END_ORE);
                        output.accept(ModBlocks.ALT_BLOCK);
                    })
                    .build());


    public static void register(IEventBus eventBus) {   //регистрация изменений
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
