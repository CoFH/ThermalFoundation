package cofh.thermal.lib.util.recipes.internal;

import cofh.lib.inventory.IItemStackAccess;
import cofh.thermal.lib.util.recipes.IMachineInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class DisenchantMachineRecipe extends BaseMachineRecipe {

    public DisenchantMachineRecipe(int energy, float experience) {

        super(energy, experience);
    }

    public DisenchantMachineRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

        super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
    }

    private int getEnchantmentXp(ItemStack stack) {

        int encXP = 0;
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
        for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
            if (!enchantment.isCurse()) {
                encXP += enchantment.getMinEnchantability(level);
            }
        }
        return encXP;
    }

    // region IMachineRecipe
    @Override
    public float getXp(IMachineInventory inventory) {

        int encXP = 0;
        for (IItemStackAccess holder : inventory.inputSlots()) {
            encXP += getEnchantmentXp(holder.getItemStack());
        }
        return encXP + experience * inventory.getMachineProperties().getXpMod();
    }
    // endregion
}
