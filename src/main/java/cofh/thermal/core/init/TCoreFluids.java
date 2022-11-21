package cofh.thermal.core.init;

import cofh.thermal.core.fluid.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

import static cofh.core.util.helpers.FluidHelper.BOTTLE_DRAIN_MAP;
import static cofh.core.util.helpers.FluidHelper.BOTTLE_FILL_MAP;
import static cofh.lib.util.Constants.BOTTLE_VOLUME;
import static cofh.thermal.core.ThermalCore.ITEMS;

public class TCoreFluids {

    private TCoreFluids() {

    }

    public static void register() {

        REDSTONE_FLUID = RedstoneFluid.instance().still();
        GLOWSTONE_FLUID = GlowstoneFluid.instance().still();
        ENDER_FLUID = EnderFluid.instance().still();

        SAP_FLUID = SapFluid.instance().still();
        SYRUP_FLUID = SyrupFluid.instance().still();
        RESIN_FLUID = ResinFluid.instance().still();
        TREE_OIL_FLUID = TreeOilFluid.instance().still();
        LATEX_FLUID = LatexFluid.instance().still();

        CREOSOTE_FLUID = CreosoteFluid.instance().still();
        CRUDE_OIL_FLUID = CrudeOilFluid.instance().still();
        HEAVY_OIL_FLUID = HeavyOilFluid.instance().still();
        LIGHT_OIL_FLUID = LightOilFluid.instance().still();
        REFINED_FUEL_FLUID = RefinedFuelFluid.instance().still();
    }

    public static void setup() {

        BOTTLE_DRAIN_MAP.put(ITEMS.get("syrup_bottle"), (stack -> new FluidStack(SYRUP_FLUID.get(), BOTTLE_VOLUME)));

        BOTTLE_FILL_MAP.put(fluid -> fluid.getFluid().equals(SYRUP_FLUID.get()), fluid -> new ItemStack(ITEMS.get("syrup_bottle")));

        // TODO: Flammability
        //        FireBlock fire = (FireBlock) Blocks.FIRE;
        //        fire.setFlammable(CrudeOilFluid.instance().block().get(), 5, 10);
    }

    public static Supplier<ForgeFlowingFluid> REDSTONE_FLUID;
    public static Supplier<ForgeFlowingFluid> GLOWSTONE_FLUID;
    public static Supplier<ForgeFlowingFluid> ENDER_FLUID;

    public static Supplier<ForgeFlowingFluid> SAP_FLUID;
    public static Supplier<ForgeFlowingFluid> SYRUP_FLUID;
    public static Supplier<ForgeFlowingFluid> RESIN_FLUID;
    public static Supplier<ForgeFlowingFluid> TREE_OIL_FLUID;
    public static Supplier<ForgeFlowingFluid> LATEX_FLUID;

    public static Supplier<ForgeFlowingFluid> CREOSOTE_FLUID;
    public static Supplier<ForgeFlowingFluid> CRUDE_OIL_FLUID;
    public static Supplier<ForgeFlowingFluid> HEAVY_OIL_FLUID;
    public static Supplier<ForgeFlowingFluid> LIGHT_OIL_FLUID;
    public static Supplier<ForgeFlowingFluid> REFINED_FUEL_FLUID;

}
