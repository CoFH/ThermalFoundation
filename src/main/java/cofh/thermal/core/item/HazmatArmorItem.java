package cofh.thermal.core.item;

import cofh.core.event.ArmorEvents;
import cofh.core.item.ArmorItemCoFH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HazmatArmorItem extends ArmorItemCoFH {

    protected static final int AIR_DURATION = 600;

    public HazmatArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {

        super(materialIn, slot, builder);

        ArmorEvents.registerHazardResistArmor(this, RESISTANCE_RATIO[slot.getIndex()]);
        if (slot == EquipmentSlotType.FEET) {
            ArmorEvents.registerFallResistArmor(this, 6.0D);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        if (this.slot == EquipmentSlotType.HEAD) {
            if (player.getAirSupply() < player.getMaxAirSupply() && world.random.nextInt(3) > 0) {
                player.setAirSupply(player.getAirSupply() + 1);
            }
            // TODO: Revisit
            //            if (!player.areEyesInFluid(FluidTags.WATER)) {
            //                Utils.addPotionEffectNoEvent(player, new EffectInstance(Effects.WATER_BREATHING, AIR_DURATION, 0, false, false, true));
            //            }
        }
    }

}
