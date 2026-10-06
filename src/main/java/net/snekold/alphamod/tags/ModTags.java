package net.snekold.alphamod.tags;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.snekold.alphamod.Alphamod;

public class ModTags { // класс для тегов своих
    public static class Blocks{ //для блоков
        public static final TagKey<Block> METAL_DETECTABLES = createTag("metal_detectables"); // тег для метал детектора

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(Identifier.fromNamespaceAndPath(Alphamod.MOD_ID, name));
        }
    }
    public static class Items{ // для предметов (пока не нужно) РЕАЛИЗАЦИЯ В ModItemTagsProvider ОТДЕЛЬНО ОТ БЛОКОВ
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(Identifier.fromNamespaceAndPath(Alphamod.MOD_ID, name));
        }

    }
}
