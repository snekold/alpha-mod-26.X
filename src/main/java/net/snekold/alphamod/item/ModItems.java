package net.snekold.alphamod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.food.ModFoods;
import net.snekold.alphamod.item.custom.MetalDetectorItem;

import java.util.function.Consumer;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alphamod.MOD_ID); // константа итема

    public static final DeferredItem<Item> OPAL = ITEMS.registerSimpleItem("opal"); // регистрация опала

    public static final DeferredItem<Item> METAL_DETECTOR = ITEMS.registerItem("metal_detector",
            properties -> new MetalDetectorItem(properties.durability(64))); // указываем что предмет ломаемый и макс значение поломоности 64

    // анонимный класс переопределяем метод appendHoverText {...};
    public static final DeferredItem<Item> CHIPS = ITEMS.registerItem("chips",
            properties -> new Item(properties.food(ModFoods.CHIPS, ModFoods.CHIPS_CONSUMABLE)){
                @Override
                public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                    builder.accept(Component.translatable("toolip.alphamod.chips.toolip"));
                    super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ENDER_COAL = ITEMS.registerItem("end_coal",
            properties -> new Item(properties.stacksTo(99)));

    public static void register(IEventBus iEventBus) { // регистрация предметов
        ITEMS.register(iEventBus);
    }
}
