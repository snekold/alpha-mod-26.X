package net.snekold.alphamod.block;

import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.snekold.alphamod.Alphamod;
import net.snekold.alphamod.block.custom.AltBlock;
import net.snekold.alphamod.block.custom.OpalBlock;
import net.snekold.alphamod.item.ModItems;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModBlocks { // для каждого блока нужно зарегистрировать предмет

    public static final DeferredRegister.Blocks BLOCKS = // константа для блоков аналогично как и предметов
            DeferredRegister.createBlocks(Alphamod.MOD_ID);


    public static final DeferredBlock<Block> OPAL_BLOCK = registerBlock("opal_block", // название в коде
            properties -> new OpalBlock(properties.strength(1f) // прочность
                    .requiresCorrectToolForDrops()  // указываем что нужна кирка (потом это подробно укажем в datagen)
                    .sound(SoundType.GLASS)   // звук
                    .explosionResistance(2.0f)  // прочность от взрыва
            ));

    public static final DeferredBlock<Block> ORE_OPAL_BLOCK = registerBlock("ore_opal_block",
            properties -> new DropExperienceBlock(UniformInt.of(1, 10),properties.strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel((state) -> 10)
                    .explosionResistance(15.0f)
            ));

    public static final DeferredBlock<Block> ORE_DEEPSLATE_OPAL_BLOCK = registerBlock("ore_deepslate_opal_block",
            properties -> new DropExperienceBlock(UniformInt.of(1, 10),properties.strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
                    .lightLevel((state) -> 10)
                    .explosionResistance(15.0f)
            ));

    public static final DeferredBlock<Block> OPAL_NETHER_ORE = registerBlock("opal_nether_ore",
            properties -> new DropExperienceBlock(UniformInt.of(1, 10),properties.strength(1f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHER_ORE)
                    .lightLevel((state) -> 10)
                    .explosionResistance(7.5f)
            ));

    public static final DeferredBlock<Block> OPAL_END_ORE = registerBlock("opal_end_ore",
            properties -> new DropExperienceBlock(UniformInt.of(1, 10),properties.strength(6f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel((state) -> 15)
                    .explosionResistance(60.0f)
            ));

    public static final DeferredBlock<Block> ALT_BLOCK = registerBlock("alt_block",
            properties -> new AltBlock(properties.strength(1f)
                    .noLootTable()
                    .sound(SoundType.GLASS)
                    .explosionResistance(2.0f)
                    .speedFactor(1.35f)
            ),Component.translatable("toolip.alphamod.alt_block.toolip")); // используем перегрузку метода для кастом подсказок

    public static final DeferredBlock<Block> OPAL_STAIRS = registerBlock("opal_stairs", // ступеньки из опала (начало ужаса)
            properties -> new StairBlock(ModBlocks.OPAL_BLOCK.get().defaultBlockState(), properties.strength(1f) // прочность и указание из какого блока будет ступеньки
                    .requiresCorrectToolForDrops()  // указываем что нужна кирка (потом это подробно укажем в datagen)
                    .sound(SoundType.GLASS)
                    .explosionResistance(2.0f)
            ));

    public static final DeferredBlock<Block> OPAL_SLAB = registerBlock("opal_slab", // полублоки из опала
            properties -> new SlabBlock(properties.strength(1f) // прочность и почему-то не указываем из какого блока состоит хз
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GLASS)
                    .explosionResistance(2.0f)
            ));

    public static final DeferredBlock<Block> OPAL_PRESSURE_PLATE = registerBlock("opal_pressure_plate", //из опала
            properties -> new PressurePlateBlock(BlockSetType.IRON,properties
                    .mapColor(MapColor.COLOR_BLUE) // какой цвет будет на мапе бумажной везде можео установить
                    .forceSolidOn() //Заставляет игру считать блок «твердым» для определенных механик (например, для просчета спавна мобов или путей их передвижения), даже если у него отключена коллизия.
                    .instrument(NoteBlockInstrument.BASS) // Если поставить нотный блок (Note Block) на этот блок, то нотный блок будет издавать звук баса (как на дереве).
                    .requiresCorrectToolForDrops()
                    .noCollision() //иначе поднимет вас
                    .strength(0.5F) // прочность
                    .pushReaction(PushReaction.DESTROY) //  При попытке сдвинуть блок поршнем, он не передвинется, а сразу сломается (разрушится) и выпадет в виде дропа (как факелы или цветы).
            ));

    public static final DeferredBlock<Block> OPAL_BUTTON = registerBlock("opal_button",
            properties -> new ButtonBlock(BlockSetType.IRON, 20, properties //20 - кол во тиков чтобы была нажатой
                    .noCollision()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY)
            ));

    //кастом подсказки (перегрузка методов, разная сигнатура)
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, Component... components) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function); // регистрируем блок и сохраняем в переменную
        registerBlockItem(name, toReturn, components); // передаем в наш метод снизу UPD - components для registerBlockItem снизу
        return toReturn;
    }

    // "..." означает массив который можно не заполнять или можно заполнить несколько
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Component... components) { // обьявляем что блок доступен в мире
        ModItems.ITEMS.registerItem(name, properties ->
                new BlockItem(block.get(), properties.useBlockDescriptionPrefix()){
                    @Override // тоже анонимный класс для расширенных подсказок для блоков
                    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                        for(var component : components){ // Для каждой переданной строки текста добавить её в подсказку предмета
                            builder.accept(component);
                        }
                        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
                    }
                });
        //properties.useBlockDescriptionPrefix() - означает что язык блока будет block.alphamod.opal_block
    }

    // прост

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function); // регистрируем блок и сохраняем в переменную
        registerBlockItem(name, toReturn); // передаем в наш метод снизу
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) { // обьявляем что блок доступен в мире
        ModItems.ITEMS.registerItem(name, properties ->
                new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
        //properties.useBlockDescriptionPrefix() - означает что язык блока будет block.alphamod.opal_block
    }

    public static void register(IEventBus eventBus){  // регистрация изменений
        BLOCKS.register(eventBus);
    }
}
