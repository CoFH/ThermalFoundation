package cofh.thermal.core.item;

import cofh.core.event.ArmorEvents;
import cofh.core.item.ArmorItemCoFH;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HazmatArmorItem extends ArmorItemCoFH {

    protected static final int AIR_DURATION = 600;

    public HazmatArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {

        super(materialIn, slot, builder);

        ArmorEvents.registerHazardResistArmor(this, RESISTANCE_RATIO[slot.getIndex()]);
        if (slot == EquipmentSlot.FEET) {
            ArmorEvents.registerFallResistArmor(this, 6.0D);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {

        if (this.slot == EquipmentSlot.HEAD) {
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
