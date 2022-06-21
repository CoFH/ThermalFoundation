package cofh.thermal.core.util.recipes.machine;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.lib.util.recipes.IMachineInventory;
import cofh.thermal.lib.util.recipes.internal.BaseMachineRecipe;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cofh.lib.util.Constants.BASE_CHANCE_LOCKED;
import static cofh.lib.util.helpers.ItemHelper.itemsEqual;
import static cofh.thermal.lib.util.managers.AbstractManager.convert;

public class CrafterRecipe extends BaseMachineRecipe {

    protected final List<Ingredient> ingredients;
    protected final Set<ComparableItemStack> validItems = new ObjectOpenHashSet<>();
    protected Set<Fluid> validFluids = new ObjectOpenHashSet<>();

    public static final List<Float> CHANCE = Collections.singletonList(1.0F);
    public static final Pair<List<Integer>, List<Integer>> EMPTY_PAIR = Pair.of(Collections.emptyList(), Collections.emptyList());

    public CrafterRecipe(int energy, Recipe<?> recipe) {

        super(energy, 0);

        ingredients = recipe.getIngredients();

        for (Ingredient ing : ingredients) {
            for (ItemStack stack : ing.getItems()) {
                validItems.add(convert(stack));
                FluidUtil.getFluidContained(stack).ifPresent(fluidStack -> {
                    if (!fluidStack.isEmpty()) {
                        validFluids.add(fluidStack.getFluid());
                    }
                });
            }
        }
        outputItems.add(recipe.getResultItem());
        outputItemChances.add(BASE_CHANCE_LOCKED);
    }

    public boolean validItem(ItemStack item) {

        return validItems.contains(convert(item));
    }

    public boolean validFluid(FluidStack fluid) {

        return validFluids.contains(fluid.getFluid());
    }

    @Override
    public List<Float> getOutputItemChances(IMachineInventory inventory) {

        return CHANCE;
    }

    @Override
    public Pair<List<Integer>, List<Integer>> getInputItemAndFluidCounts(IMachineInventory inventory) {

        if (ingredients.isEmpty()) {
            return EMPTY_PAIR;
        }
        int found = 0;

        int storedFluidAmount = inventory.inputTanks().get(0).getAmount();

        int[] retItems = new int[inventory.inputSlots().size()];
        boolean foundItem = false;
        int retFluid = 0;

        if (storedFluidAmount > 0) {
            FluidStack storedFluid = inventory.inputTanks().get(0).getFluidStack();
            for (Ingredient ing : ingredients) {
                if (ing.isEmpty()) {
                    ++found;
                } else {
                    for (ItemStack stack : ing.getItems()) {
                        FluidStack fluid = FluidUtil.getFluidContained(stack).orElse(FluidStack.EMPTY);
                        if (FluidHelper.fluidsEqual(storedFluid, fluid) && storedFluidAmount - retFluid >= fluid.getAmount()) {
                            retFluid += fluid.getAmount();
                            ++found;
                            break;
                        }
                        int curFound = found;
                        for (int j = 0; j < retItems.length; ++j) {
                            ItemStack inSlot = inventory.inputSlots().get(j).getItemStack();
                            if (inSlot.getCount() > retItems[j] && itemsEqual(stack, inSlot)) {
                                ++retItems[j];
                                ++found;
                                foundItem = true;
                                break;
                            }
                        }
                        if (found > curFound) {
                            break;
                        }
                    }
                }
            }
        } else {
            for (Ingredient ing : ingredients) {
                if (ing.isEmpty()) {
                    ++found;
                } else {
                    for (ItemStack stack : ing.getItems()) {
                        int curFound = found;
                        for (int j = 0; j < retItems.length; ++j) {
                            ItemStack inSlot = inventory.inputSlots().get(j).getItemStack();
                            if (inSlot.getCount() > retItems[j] && itemsEqual(stack, inSlot)) {
                                ++retItems[j];
                                ++found;
                                foundItem = true;
                                break;
                            }
                        }
                        if (found > curFound) {
                            break;
                        }
                    }
                }
            }
        }
        if (found < ingredients.size()) {
            return EMPTY_PAIR;
        }
        List<Integer> itemCounts = foundItem ? IntStream.of(retItems).boxed().collect(Collectors.toList()) : Collections.emptyList();
        List<Integer> fluidCounts = retFluid > 0 ? Collections.singletonList(retFluid) : Collections.emptyList();
        return Pair.of(itemCounts, fluidCounts);
    }

}
