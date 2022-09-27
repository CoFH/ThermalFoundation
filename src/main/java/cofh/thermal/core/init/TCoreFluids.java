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

        REDSTONE_FLUID = RedstoneFluid.create().still();
        GLOWSTONE_FLUID = GlowstoneFluid.create().still();
        ENDER_FLUID = EnderFluid.create().still();

        SAP_FLUID = SapFluid.create().still();
        SYRUP_FLUID = SyrupFluid.create().still();
        RESIN_FLUID = ResinFluid.create().still();
        TREE_OIL_FLUID = TreeOilFluid.create().still();
        LATEX_FLUID = LatexFluid.create().still();

        CREOSOTE_FLUID = CreosoteFluid.create().still();
        CRUDE_OIL_FLUID = CrudeOilFluid.create().still();
        HEAVY_OIL_FLUID = HeavyOilFluid.create().still();
        LIGHT_OIL_FLUID = LightOilFluid.create().still();
        REFINED_FUEL_FLUID = RefinedFuelFluid.create().still();
    }

    public static void setup() {

        BOTTLE_DRAIN_MAP.put(ITEMS.get("syrup_bottle"), (stack -> new FluidStack(SYRUP_FLUID.get(), BOTTLE_VOLUME)));

        BOTTLE_FILL_MAP.put(fluid -> fluid.getFluid().equals(SYRUP_FLUID.get()), fluid -> new ItemStack(ITEMS.get("syrup_bottle")));
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
