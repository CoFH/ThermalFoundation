package cofh.thermal.lib.util.references;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import static cofh.lib.util.constants.Constants.ID_FORGE;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class ThermalTags {

    public static class Blocks {

        public static final TagKey<Block> LOGS_RUBBERWOOD = forgeTag("rubberwood_logs");

        public static final TagKey<Block> HARDENED_GLASS = thermalTag("glass/hardened");
        public static final TagKey<Block> ROCKWOOL = thermalTag("rockwool");

        // region HELPERS
        private static TagKey<Block> thermalTag(String name) {

            return BlockTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Block> forgeTag(String name) {

            return BlockTags.create(new ResourceLocation(ID_FORGE, name));
        }
        // endregion
    }

    public static class Items {

        public static final TagKey<Item> LOGS_RUBBERWOOD = forgeTag("rubberwood_logs");

        public static final TagKey<Item> BITUMEN = forgeTag("bitumen");
        public static final TagKey<Item> COAL_COKE = forgeTag("coal_coke");
        public static final TagKey<Item> SAWDUST = forgeTag("sawdust");
        public static final TagKey<Item> SLAG = forgeTag("slag");
        public static final TagKey<Item> TAR = forgeTag("tar");

        public static final TagKey<Item> MACHINE_DIES = thermalTag("crafting/dies");
        public static final TagKey<Item> MACHINE_CASTS = thermalTag("crafting/casts");

        public static final TagKey<Item> HARDENED_GLASS = thermalTag("glass/hardened");
        public static final TagKey<Item> ROCKWOOL = thermalTag("rockwool");

        // region HELPERS
        private static TagKey<Item> thermalTag(String name) {

            return ItemTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Item> forgeTag(String name) {

            return ItemTags.create(new ResourceLocation(ID_FORGE, name));
        }
        // endregion
    }

    public static class Fluids {

        public static final TagKey<Fluid> REDSTONE = forgeTag("redstone");
        public static final TagKey<Fluid> GLOWSTONE = forgeTag("glowstone");
        public static final TagKey<Fluid> ENDER = forgeTag("ender");

        public static final TagKey<Fluid> LATEX = forgeTag("latex");

        public static final TagKey<Fluid> CREOSOTE = forgeTag("creosote");
        public static final TagKey<Fluid> CRUDE_OIL = forgeTag("crude_oil");

        // region HELPERS
        private static TagKey<Fluid> thermalTag(String name) {

            return FluidTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Fluid> forgeTag(String name) {

            return FluidTags.create(new ResourceLocation(ID_FORGE, name));
        }
        // endregion
    }

}
