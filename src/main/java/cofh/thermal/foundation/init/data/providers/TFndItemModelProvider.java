package cofh.thermal.foundation.init.data.providers;

import cofh.lib.init.data.ItemModelProviderCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.foundation.init.registries.TFndIDs.*;

public class TFndItemModelProvider extends ItemModelProviderCoFH {

    public TFndItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {

        super(output, ID_THERMAL, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        registerBlockItemModels();

        var reg = ITEMS;

        registerResources(reg);
    }

    // region ITEM HELPERS
    private void registerResources(DeferredRegisterCoFH<Item> reg) {

        metalSet(reg, "lead");
        metalSet(reg, "nickel");
        metalSet(reg, "silver");
        metalSet(reg, "tin");

        alloySet(reg, "bronze");
        alloySet(reg, "constantan");
        alloySet(reg, "electrum");
        alloySet(reg, "invar");

        gemSet(reg, "ruby");
        gemSet(reg, "sapphire");
    }
    // endregion

    private void registerBlockItemModels() {

        var reg = BLOCKS;

        registerResourceBlocks(reg);
        registerStorageBlocks(reg);
    }

    // region BLOCK HELPERS
    private void registerResourceBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(ID_APATITE_ORE));
        blockItem(reg.getSup(ID_CINNABAR_ORE));
        blockItem(reg.getSup(ID_NITER_ORE));
        blockItem(reg.getSup(ID_SULFUR_ORE));

        blockItem(reg.getSup(ID_LEAD_ORE));
        blockItem(reg.getSup(ID_NICKEL_ORE));
        blockItem(reg.getSup(ID_SILVER_ORE));
        blockItem(reg.getSup(ID_TIN_ORE));

        blockItem(reg.getSup(ID_RUBY_ORE));
        blockItem(reg.getSup(ID_SAPPHIRE_ORE));

        blockItem(reg.getSup(deepslate(ID_APATITE_ORE)));
        blockItem(reg.getSup(deepslate(ID_CINNABAR_ORE)));
        blockItem(reg.getSup(deepslate(ID_NITER_ORE)));
        blockItem(reg.getSup(deepslate(ID_SULFUR_ORE)));

        blockItem(reg.getSup(deepslate(ID_LEAD_ORE)));
        blockItem(reg.getSup(deepslate(ID_NICKEL_ORE)));
        blockItem(reg.getSup(deepslate(ID_SILVER_ORE)));
        blockItem(reg.getSup(deepslate(ID_TIN_ORE)));

        blockItem(reg.getSup(deepslate(ID_RUBY_ORE)));
        blockItem(reg.getSup(deepslate(ID_SAPPHIRE_ORE)));

        blockItem(reg.getSup(ID_OIL_SAND));
        blockItem(reg.getSup(ID_OIL_RED_SAND));
    }

    private void registerStorageBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(raw(ID_LEAD_BLOCK)));
        blockItem(reg.getSup(raw(ID_NICKEL_BLOCK)));
        blockItem(reg.getSup(raw(ID_SILVER_BLOCK)));
        blockItem(reg.getSup(raw(ID_TIN_BLOCK)));

        blockItem(reg.getSup(ID_LEAD_BLOCK));
        blockItem(reg.getSup(ID_NICKEL_BLOCK));
        blockItem(reg.getSup(ID_SILVER_BLOCK));
        blockItem(reg.getSup(ID_TIN_BLOCK));

        blockItem(reg.getSup(ID_BRONZE_BLOCK));
        blockItem(reg.getSup(ID_CONSTANTAN_BLOCK));
        blockItem(reg.getSup(ID_ELECTRUM_BLOCK));
        blockItem(reg.getSup(ID_INVAR_BLOCK));

        blockItem(reg.getSup(ID_RUBY_BLOCK));
        blockItem(reg.getSup(ID_SAPPHIRE_BLOCK));
    }
    // endregion
}
