package cofh.thermal.foundation.data;

import cofh.lib.data.BlockStateProviderCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.foundation.init.TFndIDs.*;

public class TFndBlockStateProvider extends BlockStateProviderCoFH {

    public TFndBlockStateProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {

        super(gen, ID_THERMAL, existingFileHelper);
    }

    @Override
    public String getName() {

        return "Thermal Foundation: BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {

        var reg = BLOCKS;

        registerResources(reg);
        registerStorage(reg);
    }

    // region HELPERS
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

        simpleBlock(reg.getSup(ID_RUBY_BLOCK));
        simpleBlock(reg.getSup(ID_SAPPHIRE_BLOCK));
    }
    // endregion
}
