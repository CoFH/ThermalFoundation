package cofh.thermal.core.data;

import cofh.lib.data.BlockStateProviderCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreBlockStateProvider extends BlockStateProviderCoFH {

    public TCoreBlockStateProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {

        super(gen, ID_THERMAL, existingFileHelper);
    }

    @Override
    public String getName() {

        return "Thermal Core: BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {

        var reg = BLOCKS;

        registerVanilla(reg);
        registerResources(reg);
        registerStorage(reg);
        registerBuildingBlocks(reg);
    }

    // region HELPERS
    private void registerVanilla(DeferredRegisterCoFH<Block> reg) {

        simpleBlock(reg.getSup(ID_CHARCOAL_BLOCK));
        simpleBlock(reg.getSup(ID_GUNPOWDER_BLOCK));
        axisBlock(reg.getSup(ID_BAMBOO_BLOCK), "bamboo_block");
        axisBlock(reg.getSup(ID_SUGAR_CANE_BLOCK), "sugar_cane_block");

        // TODO: Missing - Food Crates
    }

    private void registerResources(DeferredRegisterCoFH<Block> reg) {

        simpleBlock(reg.getSup(ID_APATITE_ORE));
        simpleBlock(reg.getSup(deepslate(ID_APATITE_ORE)));
        simpleBlock(reg.getSup(ID_CINNABAR_ORE));
        simpleBlock(reg.getSup(deepslate(ID_CINNABAR_ORE)));
        simpleBlock(reg.getSup(ID_NITER_ORE));
        simpleBlock(reg.getSup(deepslate(ID_NITER_ORE)));
        simpleBlock(reg.getSup(ID_SULFUR_ORE));
        simpleBlock(reg.getSup(deepslate(ID_SULFUR_ORE)));

        simpleBlock(reg.getSup(ID_LEAD_ORE));
        simpleBlock(reg.getSup(deepslate(ID_LEAD_ORE)));
        simpleBlock(reg.getSup(ID_NICKEL_ORE));
        simpleBlock(reg.getSup(deepslate(ID_NICKEL_ORE)));
        simpleBlock(reg.getSup(ID_SILVER_ORE));
        simpleBlock(reg.getSup(deepslate(ID_SILVER_ORE)));
        simpleBlock(reg.getSup(ID_TIN_ORE));
        simpleBlock(reg.getSup(deepslate(ID_TIN_ORE)));

        simpleBlock(reg.getSup(ID_RUBY_ORE));
        simpleBlock(reg.getSup(deepslate(ID_RUBY_ORE)));
        simpleBlock(reg.getSup(ID_SAPPHIRE_ORE));
        simpleBlock(reg.getSup(deepslate(ID_SAPPHIRE_ORE)));

        simpleBlock(reg.getSup(ID_OIL_SAND));
        simpleBlock(reg.getSup(ID_OIL_RED_SAND));
    }

    private void registerStorage(DeferredRegisterCoFH<Block> reg) {

        simpleBlock(reg.getSup(ID_APATITE_BLOCK));
        simpleBlock(reg.getSup(ID_CINNABAR_BLOCK));
        simpleBlock(reg.getSup(ID_NITER_BLOCK));
        simpleBlock(reg.getSup(ID_SULFUR_BLOCK));

        simpleBlock(reg.getSup(raw(ID_LEAD_BLOCK)));
        simpleBlock(reg.getSup(raw(ID_NICKEL_BLOCK)));
        simpleBlock(reg.getSup(raw(ID_SILVER_BLOCK)));
        simpleBlock(reg.getSup(raw(ID_TIN_BLOCK)));

        simpleBlock(reg.getSup(ID_LEAD_BLOCK));
        simpleBlock(reg.getSup(ID_NICKEL_BLOCK));
        simpleBlock(reg.getSup(ID_SILVER_BLOCK));
        simpleBlock(reg.getSup(ID_TIN_BLOCK));

        simpleBlock(reg.getSup(ID_BRONZE_BLOCK));
        simpleBlock(reg.getSup(ID_CONSTANTAN_BLOCK));
        simpleBlock(reg.getSup(ID_ELECTRUM_BLOCK));
        simpleBlock(reg.getSup(ID_INVAR_BLOCK));

        simpleBlock(reg.getSup(ID_ENDERIUM_BLOCK));
        simpleBlock(reg.getSup(ID_LUMIUM_BLOCK));
        simpleBlock(reg.getSup(ID_SIGNALUM_BLOCK));

        simpleBlock(reg.getSup(ID_RUBY_BLOCK));
        simpleBlock(reg.getSup(ID_SAPPHIRE_BLOCK));

        simpleBlock(reg.getSup(ID_SAWDUST_BLOCK));
        simpleBlock(reg.getSup(ID_COAL_COKE_BLOCK));
        simpleBlock(reg.getSup(ID_BITUMEN_BLOCK));
        // TODO: Add a top/bottom method to handle this easily.
        // storageBlock(reg.getSup(ID_TAR_BLOCK));
        // storageBlock(reg.getSup(ID_ROSIN_BLOCK));

        simpleBlock(reg.getSup(ID_RUBBER_BLOCK));
        simpleBlock(reg.getSup(ID_CURED_RUBBER_BLOCK));
        simpleBlock(reg.getSup(ID_SLAG_BLOCK));
        simpleBlock(reg.getSup(ID_RICH_SLAG_BLOCK));
    }

    private void registerBuildingBlocks(DeferredRegisterCoFH<Block> reg) {

        simpleBlock(reg.getSup(ID_OBSIDIAN_GLASS));
        simpleBlock(reg.getSup(ID_SIGNALUM_GLASS));
        simpleBlock(reg.getSup(ID_LUMIUM_GLASS));
        simpleBlock(reg.getSup(ID_ENDERIUM_GLASS));

        simpleBlock(reg.getSup(ID_POLISHED_SLAG));
        simpleBlock(reg.getSup(ID_CHISELED_SLAG));
        simpleBlock(reg.getSup(ID_SLAG_BRICKS));
        simpleBlock(reg.getSup(ID_CRACKED_SLAG_BRICKS));
        simpleBlock(reg.getSup(ID_POLISHED_RICH_SLAG));
        simpleBlock(reg.getSup(ID_CHISELED_RICH_SLAG));
        simpleBlock(reg.getSup(ID_RICH_SLAG_BRICKS));
        simpleBlock(reg.getSup(ID_CRACKED_RICH_SLAG_BRICKS));
    }
    // endregion
}
