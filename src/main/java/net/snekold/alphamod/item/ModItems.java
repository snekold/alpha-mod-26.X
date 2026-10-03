package net.snekold.alphamod.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.snekold.alphamod.Alphamod;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alphamod.MOD_ID); // константа итема

    public static final DeferredItem<Item> OPAL = ITEMS.registerSimpleItem("opal"); // регистрация опала
    public static final DeferredItem<Item> RAW_OPAL = ITEMS.registerSimpleItem("raw_opal");

    public static void register(IEventBus iEventBus) { // регистрация предметов
        ITEMS.register(iEventBus);
    }
}
