package cofh.thermal.foundation.init.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static cofh.lib.util.constants.ModIds.ID_FORGE;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class TFndTags {

    private TFndTags() {

    }

    public static class Blocks {

        public static final TagKey<Block> ORES_APATITE = forgeTag("ores/apatite");
        public static final TagKey<Block> ORES_CINNABAR = forgeTag("ores/cinnabar");
        public static final TagKey<Block> ORES_LEAD = forgeTag("ores/lead");
        public static final TagKey<Block> ORES_NICKEL = forgeTag("ores/nickel");
        public static final TagKey<Block> ORES_NITER = forgeTag("ores/niter");
        public static final TagKey<Block> ORES_RUBY = forgeTag("ores/ruby");
        public static final TagKey<Block> ORES_SAPPHIRE = forgeTag("ores/sapphire");
        public static final TagKey<Block> ORES_SILVER = forgeTag("ores/silver");
        public static final TagKey<Block> ORES_SULFUR = forgeTag("ores/sulfur");
        public static final TagKey<Block> ORES_TIN = forgeTag("ores/tin");

        public static final TagKey<Block> STORAGE_BLOCKS_APATITE = forgeTag("storage_blocks/apatite");
        public static final TagKey<Block> STORAGE_BLOCKS_BRONZE = forgeTag("storage_blocks/bronze");
        public static final TagKey<Block> STORAGE_BLOCKS_CINNABAR = forgeTag("storage_blocks/cinnabar");
        public static final TagKey<Block> STORAGE_BLOCKS_CONSTANTAN = forgeTag("storage_blocks/constantan");
        public static final TagKey<Block> STORAGE_BLOCKS_ELECTRUM = forgeTag("storage_blocks/electrum");
        public static final TagKey<Block> STORAGE_BLOCKS_INVAR = forgeTag("storage_blocks/invar");
        public static final TagKey<Block> STORAGE_BLOCKS_LEAD = forgeTag("storage_blocks/lead");
        public static final TagKey<Block> STORAGE_BLOCKS_NICKEL = forgeTag("storage_blocks/nickel");
        public static final TagKey<Block> STORAGE_BLOCKS_NITER = forgeTag("storage_blocks/niter");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_LEAD = forgeTag("storage_blocks/raw_lead");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_NICKEL = forgeTag("storage_blocks/raw_nickel");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = forgeTag("storage_blocks/raw_silver");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_TIN = forgeTag("storage_blocks/raw_tin");
        public static final TagKey<Block> STORAGE_BLOCKS_RUBY = forgeTag("storage_blocks/ruby");
        public static final TagKey<Block> STORAGE_BLOCKS_SAPPHIRE = forgeTag("storage_blocks/sapphire");
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = forgeTag("storage_blocks/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_SULFUR = forgeTag("storage_blocks/sulfur");
        public static final TagKey<Block> STORAGE_BLOCKS_TIN = forgeTag("storage_blocks/tin");

        // region HELPERS
        private static TagKey<Block> thermalTag(String name) {

            return BlockTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Block> forgeTag(String name) {

            return BlockTags.create(new ResourceLocation(ID_FORGE, name));
        }
        // endregion
    }

    public class Items {

        public static final TagKey<Item> ORES_APATITE = forgeTag("ores/apatite");
        public static final TagKey<Item> ORES_CINNABAR = forgeTag("ores/cinnabar");
        public static final TagKey<Item> ORES_LEAD = forgeTag("ores/lead");
        public static final TagKey<Item> ORES_NICKEL = forgeTag("ores/nickel");
        public static final TagKey<Item> ORES_NITER = forgeTag("ores/niter");
        public static final TagKey<Item> ORES_RUBY = forgeTag("ores/ruby");
        public static final TagKey<Item> ORES_SAPPHIRE = forgeTag("ores/sapphire");
        public static final TagKey<Item> ORES_SILVER = forgeTag("ores/silver");
        public static final TagKey<Item> ORES_SULFUR = forgeTag("ores/sulfur");
        public static final TagKey<Item> ORES_TIN = forgeTag("ores/tin");

        public static final TagKey<Item> STORAGE_BLOCKS_BRONZE = forgeTag("storage_blocks/bronze");
        public static final TagKey<Item> STORAGE_BLOCKS_CONSTANTAN = forgeTag("storage_blocks/constantan");
        public static final TagKey<Item> STORAGE_BLOCKS_ELECTRUM = forgeTag("storage_blocks/electrum");
        public static final TagKey<Item> STORAGE_BLOCKS_INVAR = forgeTag("storage_blocks/invar");
        public static final TagKey<Item> STORAGE_BLOCKS_LEAD = forgeTag("storage_blocks/lead");
        public static final TagKey<Item> STORAGE_BLOCKS_NICKEL = forgeTag("storage_blocks/nickel");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_LEAD = forgeTag("storage_blocks/raw_lead");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_NICKEL = forgeTag("storage_blocks/raw_nickel");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = forgeTag("storage_blocks/raw_silver");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_TIN = forgeTag("storage_blocks/raw_tin");
        public static final TagKey<Item> STORAGE_BLOCKS_RUBY = forgeTag("storage_blocks/ruby");
        public static final TagKey<Item> STORAGE_BLOCKS_SAPPHIRE = forgeTag("storage_blocks/sapphire");
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = forgeTag("storage_blocks/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_TIN = forgeTag("storage_blocks/tin");

        // region HELPERS
        private static TagKey<Item> thermalTag(String name) {

            return ItemTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Item> forgeTag(String name) {

            return ItemTags.create(new ResourceLocation(ID_FORGE, name));
        }
        // endregion
    }

}
