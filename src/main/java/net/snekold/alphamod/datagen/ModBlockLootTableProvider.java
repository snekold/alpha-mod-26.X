package net.snekold.alphamod.datagen;


import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.snekold.alphamod.block.ModBlocks;
import net.snekold.alphamod.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        //выпадает тот же предмет, что и копается
        dropSelf(ModBlocks.OPAL_BLOCK.get());
        dropSelf(ModBlocks.RAW_OPAL_BLOCK.get());

        //один предмет
        add(ModBlocks.ORE_OPAL_BLOCK.get(),
                createOreDrop(ModBlocks.ORE_OPAL_BLOCK.get(), ModItems.RAW_OPAL.get()));

        add(ModBlocks.ORE_DEEPSLATE_OPAL_BLOCK.get(),
                createOreDrop(ModBlocks.ORE_DEEPSLATE_OPAL_BLOCK.get(), ModItems.RAW_OPAL.get()));

        //несколько предметов
        add(ModBlocks.OPAL_NETHER_ORE.get(),
                createMultipleOreDrops(ModBlocks.OPAL_NETHER_ORE.get(), ModItems.RAW_OPAL.get(), 2, 5));
        add(ModBlocks.OPAL_END_ORE.get(),
                createMultipleOreDrops(ModBlocks.OPAL_END_ORE.get(), ModItems.RAW_OPAL.get(), 5, 10));
    }

    //переопределили метод и создали свой чтобы выпадало несколько предметов
    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() { // отдаем все блоки и преобразуем в что-то важное
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
